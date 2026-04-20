// src/main/java/com/bjfu/paper/service/editorialOffice/TechCheckServiceImpl.java
package com.bjfu.paper.service.editorialOffice;

import com.bjfu.paper.util.TechCheckUtil;
import com.bjfu.paper.util.FileProcessorUtil;
import com.bjfu.paper.mapper.ManuscriptMapper;
import com.bjfu.paper.mapper.FileMapper;
import com.bjfu.paper.mapper.TechnicalCheckMapper;
import com.bjfu.paper.mapper.FormatIssuesMapper;
import com.bjfu.paper.model.entity.Manuscript;
import com.bjfu.paper.model.entity.File;
import com.bjfu.paper.model.entity.TechnicalCheck;
import com.bjfu.paper.model.entity.FormatIssues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TechCheckServiceImpl implements TechCheckService {
    
    private static final Logger logger = LoggerFactory.getLogger(TechCheckServiceImpl.class);
    
    @Autowired
    private ManuscriptMapper manuscriptMapper;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private FileMapper fileMapper;
    
    @Autowired
    private TechnicalCheckMapper technicalCheckMapper;

    @Autowired
    private FormatIssuesMapper formatIssuesMapper;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public List<Manuscript> getPendingManuscripts(int page, int pageSize, String keyword) {
        try {
            int offset = (page - 1) * pageSize;
            
            List<Manuscript> manuscripts = manuscriptMapper.selectPendingManuscriptsByPage(offset, pageSize, keyword);
            
            logger.info("查询到待审查稿件数量: {}", manuscripts.size());
            return manuscripts;
            
        } catch (Exception e) {
            logger.error("查询待审查稿件列表失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取稿件列表失败: " + e.getMessage());
        }
    }
    
    @Override
    public int countPendingManuscripts(String keyword) {
        try {
            int count = manuscriptMapper.countPendingManuscripts(keyword);
            logger.info("统计待审查稿件数量: {}", count);
            return count;
        } catch (Exception e) {
            logger.error("统计待审查稿件数量失败: {}", e.getMessage(), e);
            throw new RuntimeException("统计稿件数量失败: " + e.getMessage());
        }
    }
    
    @Override
    public Manuscript getManuscriptDetail(String msId) {
        try {
            Manuscript manuscript = manuscriptMapper.selectManuscriptDetail(msId);
            if (manuscript != null) {
                // 查询版本信息
                String versionSql = "SELECT " +
                                  "    version_num, " +
                                  "    original_file_path, " +
                                  "    cover_letter_path, " +
                                  "    response_letter_path, " +
                                  "    upload_time " +
                                  "FROM versions " +
                                  "WHERE ms_id = ? " +
                                  "ORDER BY version_num DESC LIMIT 1";
                
                try {
                    Map<String, Object> versionInfo = jdbcTemplate.queryForMap(versionSql, msId);
                    manuscript.setCurrentVersion((Integer) versionInfo.get("version_num"));
                } catch (Exception e) {
                    logger.warn("获取稿件版本信息失败 - msId: {}, 错误: {}", msId, e.getMessage());
                    manuscript.setCurrentVersion(1);
                }
            }
            return manuscript;
        } catch (Exception e) {
            logger.error("获取稿件详情失败 - msId: {}, 错误: {}", msId, e.getMessage(), e);
            throw new RuntimeException("获取稿件详情失败: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public boolean submitTechCheck(String msId, String result, String comment, 
                                  Map<String, Object> checkData) {
        try {
            // 1. 先执行自动检查（如果未提供检查数据）
            if (checkData == null || checkData.isEmpty()) {
                checkData = performCompleteCheck(msId);
            }
            
            // 2. 原有的人工审查逻辑
            String newStatus;
            switch (result) {
                case "pass":
                    newStatus = "AWAITING_EDITOR_ASSIGNMENT";
                    break;
                case "reject":
                    newStatus = "REJECTED";
                    break;
                case "fail":
                default:
                    newStatus = "Incomplete_Submission";
                    break;
            }
            
            // 3. 更新稿件状态
            Manuscript manuscript = new Manuscript();
            manuscript.setMsId(msId);
            manuscript.setStatus(newStatus);
            manuscript.setFormReviewResult(result.equals("pass") ? "Pass" : "Reject");
            manuscript.setFormReviewFeedback(comment);
            
            int updateCount = manuscriptMapper.updateById(manuscript);
            
            if (updateCount > 0) {
                // 4. 记录审查操作日志（包含自动检查结果）
                recordCheckLog(msId, result, comment, checkData);
                
                // 5. 如果审查不通过，发送详细问题给作者
                if ("fail".equals(result) || "reject".equals(result)) {
                    sendDetailedFeedbackToAuthor(msId, checkData, comment);
                }
                
                return true;
            }
            
            return false;
            
        } catch (Exception e) {
            logger.error("提交审查结果失败: {}", e.getMessage(), e);
            throw new RuntimeException("提交审查结果失败: " + e.getMessage());
        }
    }
    
    private void recordCheckLog(String msId, String result, String comment, Map<String, Object> checkData) {
        try {
            String logSql = "INSERT INTO logs (operator_id, operate_type, ms_id, content, ip_address) " +
                          "VALUES (?, ?, ?, ?, ?)";
            
            int operatorId = 1; // 假设当前操作用户ID为1（编辑部管理员）
            String operateType = "形式审查";
            
            String resultText;
            switch (result) {
                case "pass": resultText = "通过"; break;
                case "fail": resultText = "退回修改"; break;
                case "reject": resultText = "拒稿"; break;
                default: resultText = result;
            }
            
            String content = String.format("稿件 %s 形式审查%s。审查意见: %s", 
                                         msId, resultText, comment);
            
            if (checkData != null) {
                // 安全地获取检查数据
                boolean wordCountPass = false;
                boolean plagiarismPass = false;
                
                if (checkData.containsKey("wordCount")) {
                    Map<String, Object> wordCountResult = (Map<String, Object>) checkData.get("wordCount");
                    wordCountPass = "PASS".equals(wordCountResult.get("status"));
                }
                
                if (checkData.containsKey("plagiarism")) {
                    Map<String, Object> plagiarismResult = (Map<String, Object>) checkData.get("plagiarism");
                    plagiarismPass = "PASS".equals(plagiarismResult.get("status"));
                }
                
                content += String.format(" [字数检查: %s, 查重检查: %s]", 
                                       wordCountPass ? "通过" : "不通过",
                                       plagiarismPass ? "通过" : "不通过");
            }
            
            String ipAddress = "127.0.0.1";
            
            jdbcTemplate.update(logSql, operatorId, operateType, msId, content, ipAddress);
            
        } catch (Exception e) {
            logger.error("记录审查日志失败: {}", e.getMessage());
        }
    }
    
    private void recordCheckIssues(String msId, Map<String, Object> checkData) {
        try {
            String createTableSql = "CREATE TABLE IF NOT EXISTS check_issues (" +
                                   "issue_id INT AUTO_INCREMENT PRIMARY KEY, " +
                                   "ms_id VARCHAR(20) NOT NULL, " +
                                   "issue_type VARCHAR(50), " +
                                   "description TEXT, " +
                                   "severity VARCHAR(20), " +
                                   "record_time DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                                   "FOREIGN KEY (ms_id) REFERENCES manuscript(ms_id) ON DELETE CASCADE" +
                                   ")";
            jdbcTemplate.execute(createTableSql);
            
            // 安全地处理格式问题
            if (checkData != null && checkData.containsKey("format")) {
                Map<String, Object> formatResult = (Map<String, Object>) checkData.get("format");
                if (formatResult.containsKey("issues")) {
                    Object issuesObj = formatResult.get("issues");
                    if (issuesObj instanceof List) {
                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> issues = (List<Map<String, Object>>) issuesObj;
                        
                        String issueSql = "INSERT INTO check_issues (ms_id, issue_type, description, severity) " +
                                         "VALUES (?, ?, ?, ?)";
                        
                        for (Map<String, Object> issue : issues) {
                            String issueType = (String) issue.getOrDefault("type", "格式问题");
                            String description = (String) issue.getOrDefault("description", "格式不规范");
                            String severity = (String) issue.getOrDefault("severity", "中等");
                            
                            jdbcTemplate.update(issueSql, msId, issueType, description, severity);
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            logger.error("记录检查问题失败: {}", e.getMessage());
        }
    }

    /**
     * 从数据库获取文件路径
     */
    private String getManuscriptFilePathFromDatabase(String msId) {
        try {
            String sql = "SELECT original_file_path FROM versions " +
                        "WHERE ms_id = ? ORDER BY version_num DESC LIMIT 1";
            return jdbcTemplate.queryForObject(sql, String.class, msId);
        } catch (Exception e) {
            logger.error("从数据库获取文件路径失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 使用文件路径处理稿件
     */
    private Map<String, Object> processFileWithPath(String msId, String filePath, Manuscript manuscript) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            logger.info("尝试处理文件路径: {}", filePath);
            
            // 尝试直接处理文件
            Map<String, Object> fileProcessResult = FileProcessorUtil.processFileByType(filePath, "PDF");
            
            if ((Boolean) fileProcessResult.get("success")) {
                String content = (String) fileProcessResult.get("content");
                int wordCount = (int) fileProcessResult.get("wordCount");
                
                // 执行各项检查
                Map<String, Object> wordCountResult = performWordCountCheck(wordCount, "PDF");
                Map<String, Object> formatResult = performFormatCheck(content, "PDF");
                Map<String, Object> plagiarismResult = performPlagiarismCheck(content, manuscript);
                
                String overallResult = determineOverallResult(wordCountResult, formatResult, plagiarismResult);
                
                result.put("success", true);
                result.put("wordCount", wordCountResult);
                result.put("format", formatResult);
                result.put("plagiarism", plagiarismResult);
                result.put("overallResult", overallResult);
                result.put("manuscript", manuscript);
                result.put("fileInfo", fileProcessResult);
                result.put("fileType", "PDF");
                result.put("note", "从数据库路径处理文件");
                
            } else {
                // 如果文件处理失败，抛出异常而不是使用模拟检查
                logger.error("文件路径处理失败: {}", fileProcessResult.get("error"));
                throw new RuntimeException("文件处理失败: " + fileProcessResult.get("error"));
            }
            
        } catch (Exception e) {
            logger.error("文件路径处理失败: {}", e.getMessage());
            throw new RuntimeException("文件路径处理失败: " + e.getMessage());
        }
        
        return result;
    }
    
    @Override
    @Transactional
    public boolean unsubmitManuscript(String msId, String reason) {
        try {
            logger.info("退回稿件修改: msId={}, reason={}", msId, reason);
            
            Manuscript manuscript = new Manuscript();
            manuscript.setMsId(msId);
            manuscript.setStatus("Incomplete_Submission");
            manuscript.setFormReviewFeedback(reason);
            
            int updateCount = manuscriptMapper.updateById(manuscript);
            
            if (updateCount > 0) {
                String logSql = "INSERT INTO logs (operator_id, operate_type, ms_id, content, ip_address) " +
                              "VALUES (?, ?, ?, ?, ?)";
                
                int operatorId = 1;
                String operateType = "退回修改";
                String content = String.format("稿件 %s 被退回修改。原因: %s", msId, reason);
                String ipAddress = "127.0.0.1";
                
                jdbcTemplate.update(logSql, operatorId, operateType, msId, content, ipAddress);
                
                sendNotificationToAuthor(msId, reason, "退回修改");
                
                return true;
            }
            
            return false;
            
        } catch (Exception e) {
            logger.error("退回稿件失败: {}", e.getMessage(), e);
            throw new RuntimeException("退回稿件失败: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public boolean batchPass(List<String> msIds, String comment) {
        try {
            if (msIds == null || msIds.isEmpty()) {
                logger.warn("批量通过稿件：未选择稿件");
                return false;
            }
            
            logger.info("批量通过稿件: count={}, ids={}", msIds.size(), msIds);
            
            int updateCount = manuscriptMapper.batchUpdateStatus(msIds, "AWAITING_EDITOR_ASSIGNMENT");
            
            if (updateCount > 0) {
                String logSql = "INSERT INTO logs (operator_id, operate_type, content, ip_address) " +
                              "VALUES (?, ?, ?, ?)";
                
                int operatorId = 1;
                String operateType = "批量形式审查";
                String content = String.format("批量通过 %d 篇稿件：%s。备注：%s", 
                                             updateCount, 
                                             String.join(", ", msIds), 
                                             comment);
                String ipAddress = "127.0.0.1";
                
                jdbcTemplate.update(logSql, operatorId, operateType, content, ipAddress);
                
                logger.info("批量通过成功：更新了 {} 篇稿件", updateCount);
                return true;
            }
            
            logger.warn("批量通过失败：未更新任何稿件");
            return false;
            
        } catch (Exception e) {
            logger.error("批量操作失败: {}", e.getMessage(), e);
            throw new RuntimeException("批量操作失败: " + e.getMessage());
        }
    }
    
    @Override
    public Map<String, Object> getStatistics() {
        try {
            Map<String, Object> stats = new HashMap<>();
            
            // 使用Mapper查询统计数据
            stats.put("total", manuscriptMapper.countTotal());
            stats.put("pending", manuscriptMapper.countByStatusPending());
            stats.put("passed", manuscriptMapper.countByStatusPassed());
            stats.put("failed", manuscriptMapper.countByStatusFailed());
            
            // 查询今日处理数
            String todaySql = "SELECT COUNT(DISTINCT ms_id) FROM logs " +
                             "WHERE operate_type = '形式审查' " +
                             "AND DATE(operate_time) = CURDATE()";
            int todayCount = 0;
            try {
                todayCount = jdbcTemplate.queryForObject(todaySql, Integer.class);
            } catch (Exception e) {
                logger.warn("查询今日处理数失败: {}", e.getMessage());
            }
            stats.put("todayCount", todayCount);
            
            // 平均处理时间
            String avgTimeSql = "SELECT AVG(TIMESTAMPDIFF(MINUTE, m.submit_time, l.operate_time)) " +
                               "FROM manuscript m " +
                               "JOIN logs l ON m.ms_id = l.ms_id " +
                               "WHERE l.operate_type = '形式审查' " +
                               "AND m.status IN ('待初审', 'Reject', 'Need Revision')";
            Double avgTime = 45.0;
            try {
                avgTime = jdbcTemplate.queryForObject(avgTimeSql, Double.class);
                if (avgTime == null) avgTime = 45.0;
            } catch (Exception e) {
                logger.warn("查询平均处理时间失败: {}", e.getMessage());
            }
            stats.put("avgTime", avgTime.intValue());
            
            // 各状态分布
            Map<String, Integer> statusDistribution = new HashMap<>();
            String statusSql = "SELECT status, COUNT(*) as count FROM manuscript " +
                             "WHERE status IN ('待形式审查', 'Need Revision') " +
                             "GROUP BY status";
            
            List<Map<String, Object>> statusList = jdbcTemplate.queryForList(statusSql);
            for (Map<String, Object> row : statusList) {
                String status = (String) row.get("status");
                Long count = (Long) row.get("count");
                statusDistribution.put(status, count.intValue());
            }
            stats.put("statusDistribution", statusDistribution);
            
            logger.info("获取统计数据: total={}, pending={}, passed={}, failed={}", 
                       stats.get("total"), stats.get("pending"), stats.get("passed"), stats.get("failed"));
            
            return stats;
            
        } catch (Exception e) {
            logger.error("获取统计数据失败: {}", e.getMessage(), e);
            return getEmptyStatistics();
        }
    }
    
    @Override
    public boolean updateManuscript(Manuscript manuscript) {
        try {
            int result = manuscriptMapper.updateById(manuscript);
            return result > 0;
        } catch (Exception e) {
            logger.error("更新稿件信息失败: {}", e.getMessage());
            return false;
        }
    }
    
    @Override
    public Manuscript getManuscriptById(String msId) {
        try {
            return manuscriptMapper.selectById(msId);
        } catch (Exception e) {
            logger.error("获取稿件失败: {}", e.getMessage());
            return null;
        }
    }
    
    private Map<String, Object> getEmptyStatistics() {
        Map<String, Object> emptyStats = new HashMap<>();
        emptyStats.put("total", 0);
        emptyStats.put("pending", 0);
        emptyStats.put("passed", 0);
        emptyStats.put("failed", 0);
        emptyStats.put("avgTime", 0);
        emptyStats.put("todayCount", 0);
        emptyStats.put("statusDistribution", new HashMap<>());
        return emptyStats;
    }
    
    private void sendNotificationToAuthor(String msId, String reason, String action) {
        try {
            String authorSql = "SELECT u.email, u.full_name, m.title " +
                             "FROM manuscript m " +
                             "JOIN user u ON m.submitter_id = u.user_id " +
                             "WHERE m.ms_id = ?";
            
            Map<String, Object> authorInfo = jdbcTemplate.queryForMap(authorSql, msId);
            String email = (String) authorInfo.get("email");
            String authorName = (String) authorInfo.get("full_name");
            String title = (String) authorInfo.get("title");
            
            logger.info("发送通知给作者 - msId: {}, 作者: {}, 邮箱: {}, 操作: {}, 原因: {}", 
                       msId, authorName, email, action, reason);
            
            System.out.println("=== 发送通知 ===");
            System.out.println("收件人: " + email);
            System.out.println("主题: 稿件《" + title + "》(" + msId + ") " + action + "通知");
            System.out.println("内容: 尊敬的 " + authorName + "，您的稿件《" + title + "》(" + msId + ") 已被" + action + "。");
            System.out.println("原因: " + reason);
            System.out.println("=================");
            
        } catch (Exception e) {
            logger.error("获取作者信息失败: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public Map<String, Object> performCompleteCheck(String msId) {
        try {
            // 1. 获取稿件信息
            Manuscript manuscript = getManuscriptDetail(msId);
            if (manuscript == null) {
                throw new RuntimeException("稿件不存在: " + msId);
            }
            
            // 2. 从 file 表获取稿件文件（主路径）
            List<File> manuscriptFiles = fileMapper.selectAllManuscriptFiles(msId);
            
            if (manuscriptFiles.isEmpty()) {
                // 不应该静默回退到 versions，应该记录错误
                logger.error("稿件 {} 在 file 表中没有文件记录", msId);
                throw new RuntimeException("稿件文件不存在于文件表中");
            }
            
            // 3. 使用最新上传的文件
            File manuscriptFile = manuscriptFiles.get(0); // 已按时间排序
            String filePath = manuscriptFile.getFilePath();
            String fileType = manuscriptFile.getFileType();
            
            // 4. 验证文件路径
            if (filePath == null || filePath.trim().isEmpty()) {
                logger.error("稿件 {} 文件路径为空", msId);
                throw new RuntimeException("文件路径为空");
            }
            
            // 5. 执行检查
            return performCheckWithFile(msId, manuscript, filePath, fileType);
            
        } catch (Exception e) {
            logger.error("执行稿件审查失败: {}", e.getMessage(), e);
            // 返回错误信息而不是尝试其他表
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("error", "审查失败: " + e.getMessage());
            result.put("suggestion", "请检查文件表记录或重新上传文件");
            return result;
        }
    }
    
    /**
     * 执行具体的文件检查
     */
    private Map<String, Object> performCheckWithFile(String msId, 
                                                   Manuscript manuscript,
                                                   String filePath,
                                                   String fileType) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 1. 字数检查
            Map<String, Object> wordCountResult = TechCheckUtil.checkWordCountFromDatabase(
                msId, filePath, fileType);
            
            // 2. 格式检查
            Map<String, Object> formatResult = TechCheckUtil.checkFormatFromDatabase(
                msId, filePath, fileType);
            
            // 3. 查重检查
            Map<String, Object> plagiarismResult = TechCheckUtil.checkPlagiarismFromDatabase(
                msId, filePath, fileType);
            
            // 4. 综合评估
            String overallResult = determineOverallResult(wordCountResult, formatResult, plagiarismResult);
            
            // 5. 保存检查记录到数据库
            saveTechnicalCheckToDatabase(msId, wordCountResult, formatResult, 
                                       plagiarismResult, overallResult, fileType);
            
            // 6. 返回结果
            result.put("success", true);
            result.put("wordCount", wordCountResult);
            result.put("format", formatResult);
            result.put("plagiarism", plagiarismResult);
            result.put("overallResult", overallResult);
            result.put("manuscript", manuscript);
            result.put("fileType", fileType);
            result.put("checkMethod", "数据库文件检查");
            
            logger.info("稿件 {} 审查完成，总体结果: {}", msId, overallResult);
            
        } catch (Exception e) {
            logger.error("执行文件检查失败: {}", e.getMessage(), e);
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 从versions表获取文件路径
     */
    private String getFilePathFromVersions(String msId) {
        try {
            String sql = "SELECT original_file_path FROM versions " +
                        "WHERE ms_id = ? ORDER BY version_num DESC LIMIT 1";
            return jdbcTemplate.queryForObject(sql, String.class, msId);
        } catch (Exception e) {
            logger.warn("从versions表获取文件路径失败: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * 保存技术检查记录到数据库
     */
    private void saveTechnicalCheckToDatabase(String msId,
                                            Map<String, Object> wordCountResult,
                                            Map<String, Object> formatResult,
                                            Map<String, Object> plagiarismResult,
                                            String overallResult,
                                            String fileType) {
        try {
            // 1. 创建技术检查记录
            TechnicalCheck technicalCheck = new TechnicalCheck();
            technicalCheck.setMsId(msId);
            technicalCheck.setCheckerId(1L); // 假设当前检查者ID为1
            
            if (wordCountResult != null) {
                Integer wordCount = (Integer) wordCountResult.get("wordCount");
                String wordCountStatus = (String) wordCountResult.get("status");
                technicalCheck.setWordCount(wordCount);
                technicalCheck.setWordCountStatus(wordCountStatus);
            }
            
            if (formatResult != null) {
                String formatStatus = (String) formatResult.get("status");
                technicalCheck.setFormatStatus(formatStatus);
            }
            
            if (plagiarismResult != null) {
                Double plagiarismRate = (Double) plagiarismResult.get("similarityRate");
                String plagiarismStatus = (String) plagiarismResult.get("status");
                technicalCheck.setPlagiarismRate(plagiarismRate);
                technicalCheck.setPlagiarismStatus(plagiarismStatus);
            }
            
            technicalCheck.setOverallResult(overallResult);
            
            // 构建详细的检查数据JSON
            Map<String, Object> checkData = new HashMap<>();
            checkData.put("wordCountDetails", wordCountResult);
            checkData.put("formatDetails", formatResult);
            checkData.put("plagiarismDetails", plagiarismResult);
            checkData.put("fileType", fileType);
            checkData.put("checkTime", new Date());
            
            technicalCheck.setCheckData(objectMapper.writeValueAsString(checkData));
            
            // 2. 保存到数据库
            technicalCheckMapper.insert(technicalCheck);
            
            // 3. 保存格式问题到数据库
            if (formatResult != null && formatResult.containsKey("issues")) {
                Object issuesObj = formatResult.get("issues");
                if (issuesObj instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> issues = (List<Map<String, Object>>) issuesObj;
                    
                    for (Map<String, Object> issue : issues) {
                        FormatIssues formatIssue = new FormatIssues();
                        formatIssue.setMsId(msId);
                        formatIssue.setCheckId(technicalCheck.getCheckId());
                        formatIssue.setIssueType((String) issue.get("type"));
                        formatIssue.setIssueDescription((String) issue.get("description"));
                        formatIssue.setSeverity((String) issue.get("severity"));
                        formatIssue.setStatus("OPEN");
                        
                        formatIssuesMapper.insert(formatIssue);
                    }
                }
            }
            
            logger.info("技术检查记录保存成功，检查ID: {}", technicalCheck.getCheckId());
            
        } catch (Exception e) {
            logger.error("保存技术检查记录失败: {}", e.getMessage(), e);
        }
    }
    
    /**
     * 确定总体审查结果
     */
    private String determineOverallResult(Map<String, Object> wordCountResult,
                                         Map<String, Object> formatResult,
                                         Map<String, Object> plagiarismResult) {
        boolean wordCountPass = "PASS".equals(wordCountResult.get("status"));
        boolean formatPass = "PASS".equals(formatResult.get("status"));
        boolean plagiarismPass = "PASS".equals(plagiarismResult.get("status"));
        
        if (wordCountPass && formatPass && plagiarismPass) {
            return "PASS";
        } else if (!wordCountPass && !formatPass && !plagiarismPass) {
            return "REJECT";
        } else {
            return "NEED_REVISION";
        }
    }
    
    @Override
    public List<Map<String, Object>> getCheckHistory(String msId) {
        try {
            // 1. 获取技术检查记录
            String sql = "SELECT tc.*, u.full_name as checker_name " +
                        "FROM technical_check tc " +
                        "LEFT JOIN user u ON tc.checker_id = u.user_id " +
                        "WHERE tc.ms_id = ? " +
                        "ORDER BY tc.check_time DESC";
            
            List<Map<String, Object>> technicalChecks = jdbcTemplate.queryForList(sql, msId);
            
            // 2. 为每条记录添加格式问题
            for (Map<String, Object> check : technicalChecks) {
                Long checkId = (Long) check.get("check_id");
                
                String issueSql = "SELECT * FROM format_issues " +
                                "WHERE check_id = ? " +
                                "ORDER BY severity DESC";
                
                List<Map<String, Object>> issues = jdbcTemplate.queryForList(issueSql, checkId);
                check.put("formatIssues", issues);
                
                // 解析JSON格式的检查数据
                String checkData = (String) check.get("check_data");
                if (checkData != null && !checkData.isEmpty()) {
                    try {
                        Map<String, Object> parsedData = objectMapper.readValue(checkData, Map.class);
                        check.put("detailedData", parsedData);
                    } catch (Exception e) {
                        logger.warn("解析检查数据失败: {}", e.getMessage());
                    }
                }
            }
            
            return technicalChecks;
            
        } catch (Exception e) {
            logger.error("获取审查历史失败: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }
    
    /**
     * 根据文件类型进行字数检查
     */
    private Map<String, Object> performWordCountCheck(int wordCount, String fileType) {
        Map<String, Object> result = new HashMap<>();
        
        result.put("wordCount", wordCount);
        result.put("fileType", fileType);
        result.put("checkMethod", "基于" + fileType + "文件内容统计");
        
        // 根据文件类型调整字数要求
        int minWords = 5000;
        int maxWords = 8000;
        
        // PDF文件可能包含图片等，对字数要求可以放宽
        if ("PDF".equalsIgnoreCase(fileType)) {
            minWords = 4500;
            maxWords = 8500;
        }
        
        boolean isPass = wordCount >= minWords && wordCount <= maxWords;
        result.put("status", isPass ? "PASS" : "FAIL");
        result.put("requirement", minWords + "-" + maxWords + "字");
        
        if (wordCount < minWords) {
            result.put("message", String.format("字数不足：当前%d字，要求%d-%d字", 
                                               wordCount, minWords, maxWords));
        } else if (wordCount > maxWords) {
            result.put("message", String.format("字数超限：当前%d字，要求%d-%d字", 
                                               wordCount, minWords, maxWords));
        } else {
            result.put("message", String.format("字数符合要求：当前%d字", wordCount));
        }
        
        return result;
    }

    /**
     * 根据文件类型进行格式检查
     */
    private Map<String, Object> performFormatCheck(String content, String fileType) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> issues = new ArrayList<>();
        
        result.put("fileType", fileType);
        
        // 基础格式检查
        if (content == null || content.trim().isEmpty()) {
            issues.add(createIssue("CONTENT", "文件内容为空", "HIGH"));
        }
        
        // 根据文件类型进行特定检查
        if ("PDF".equalsIgnoreCase(fileType)) {
            // PDF特定检查
            if (!content.contains("摘要") || !content.contains("关键词")) {
                issues.add(createIssue("STRUCTURE", "PDF文件缺少必要章节", "MEDIUM"));
            }
        } else if (fileType.toLowerCase().contains("doc")) {
            // Word文档特定检查
            if (content.length() < 1000) {
                issues.add(createIssue("CONTENT", "Word文档内容过短", "MEDIUM"));
            }
        }
        
        // 通用格式检查
        if (!content.contains("参考文献")) {
            issues.add(createIssue("REFERENCE", "缺少参考文献部分", "MEDIUM"));
        }
        
        result.put("issues", issues);
        result.put("issueCount", issues.size());
        result.put("status", issues.isEmpty() ? "PASS" : "FAIL");
        result.put("score", Math.max(0, 100 - issues.size() * 10));
        result.put("message", issues.isEmpty() ? "格式检查通过" : 
                    String.format("发现%d个格式问题", issues.size()));
        
        return result;
    }
    
    /**
     * 进行查重检查
     */
    private Map<String, Object> performPlagiarismCheck(String content, Manuscript manuscript) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 这里可以集成第三方查重API
            // 以下是模拟实现
            Random random = new Random();
            double similarityRate = random.nextDouble() * 20; // 0-20%
            
            // 根据标题长度和内容调整（简单模拟）
            if (manuscript.getTitle() != null && manuscript.getTitle().length() > 30) {
                similarityRate += 5; // 长标题可能重复率高
            }
            
            similarityRate = Math.min(similarityRate, 30.0);
            
            result.put("similarityRate", similarityRate);
            result.put("status", similarityRate <= 20 ? "PASS" : "FAIL");
            result.put("sourceCount", (int) (similarityRate / 5) + 1);
            
            if (similarityRate <= 20) {
                result.put("message", String.format("查重率%.2f%%符合要求(≤20%%)", similarityRate));
            } else if (similarityRate <= 30) {
                result.put("message", String.format("查重率%.2f%%略高，建议修改", similarityRate));
            } else {
                result.put("message", String.format("查重率%.2f%%过高，需要大幅修改", similarityRate));
            }
            
            // 模拟相似来源
            result.put("topSources", Arrays.asList(
                "Zhang et al. (2023) - Similarity " + String.format("%.1f%%", similarityRate/2),
                "Li et al. (2022) - Similarity " + String.format("%.1f%%", similarityRate/3),
                "Wang et al. (2021) - Similarity " + String.format("%.1f%%", similarityRate/4)
            ));
            
        } catch (Exception e) {
            logger.error("查重检查失败: {}", e.getMessage());
            result.put("similarityRate", 0.0);
            result.put("status", "FAIL");
            result.put("message", "查重检查失败: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 创建问题记录
     */
    private Map<String, Object> createIssue(String type, String description, String severity) {
        Map<String, Object> issue = new HashMap<>();
        issue.put("type", type);
        issue.put("description", description);
        issue.put("severity", severity);
        issue.put("time", new Date());
        return issue;
    }
    
    /**
     * 保存审查记录到数据库
     */
    private void saveTechnicalCheck(String msId, 
                                   Map<String, Object> wordCountResult,
                                   Map<String, Object> formatResult,
                                   Map<String, Object> plagiarismResult,
                                   String overallResult,
                                   String fileType) {
        try {
            // 获取检查者ID
            Long checkerId = 1L; // 实际应从session获取
            
            String sql = "INSERT INTO technical_check " +
                        "(ms_id, checker_id, word_count, word_count_status, " +
                        "format_status, plagiarism_rate, plagiarism_status, " +
                        "overall_result, file_type, check_data, check_time) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
            
            int wordCount = (int) wordCountResult.getOrDefault("wordCount", 0);
            String wordCountStatus = (String) wordCountResult.getOrDefault("status", "FAIL");
            String formatStatus = (String) formatResult.getOrDefault("status", "FAIL");
            double plagiarismRate = 0.0;
            if (plagiarismResult.get("similarityRate") instanceof Number) {
                plagiarismRate = ((Number) plagiarismResult.get("similarityRate")).doubleValue();
            }
            String plagiarismStatus = (String) plagiarismResult.getOrDefault("status", "FAIL");
            
            // 构建检查数据JSON
            Map<String, Object> checkData = new HashMap<>();
            checkData.put("wordCountDetails", wordCountResult);
            checkData.put("formatDetails", formatResult);
            checkData.put("plagiarismDetails", plagiarismResult);
            checkData.put("fileType", fileType);
            checkData.put("checkTime", new Date());
            
            String checkDataJson;
            try {
                checkDataJson = new com.fasterxml.jackson.databind.ObjectMapper()
                    .writeValueAsString(checkData);
            } catch (Exception e) {
                checkDataJson = "{\"error\": \"数据序列化失败\"}";
            }
            
            jdbcTemplate.update(sql, msId, checkerId, wordCount, wordCountStatus,
                              formatStatus, plagiarismRate, plagiarismStatus,
                              overallResult, fileType, checkDataJson);
            
            logger.info("保存审查记录成功，文件类型: {}", fileType);
            
        } catch (Exception e) {
            logger.error("保存审查记录失败: {}", e.getMessage(), e);
        }
    }
    
    /**
     * 发送详细反馈给作者
     */
    private void sendDetailedFeedbackToAuthor(String msId, 
                                             Map<String, Object> checkData,
                                             String comment) {
        try {
            // 获取作者信息
            String sql = "SELECT u.email, u.full_name, m.title " +
                        "FROM manuscript m " +
                        "JOIN user u ON m.submitter_id = u.user_id " +
                        "WHERE m.ms_id = ?";
            
            Map<String, Object> authorInfo = jdbcTemplate.queryForMap(sql, msId);
            String email = (String) authorInfo.get("email");
            String authorName = (String) authorInfo.get("full_name");
            String title = (String) authorInfo.get("title");
            
            // 构建反馈内容
            StringBuilder feedback = new StringBuilder();
            feedback.append("尊敬的").append(authorName).append("：\n\n");
            feedback.append("您的稿件《").append(title).append("》(").append(msId).append(")");
            feedback.append("形式审查未通过，具体问题如下：\n\n");
            
            // 添加字数问题
            if (checkData.containsKey("wordCount")) {
                Map<String, Object> wordCount = (Map<String, Object>) checkData.get("wordCount");
                if ("FAIL".equals(wordCount.get("status"))) {
                    feedback.append("1. 字数问题：").append(wordCount.get("message")).append("\n");
                }
            }
            
            // 添加格式问题
            if (checkData.containsKey("format")) {
                Map<String, Object> format = (Map<String, Object>) checkData.get("format");
                if (format.containsKey("issues")) {
                    Object issuesObj = format.get("issues");
                    if (issuesObj instanceof List) {
                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> issues = (List<Map<String, Object>>) issuesObj;
                        if (!issues.isEmpty()) {
                            feedback.append("2. 格式问题：\n");
                            for (Map<String, Object> issue : issues) {
                                feedback.append("   - ").append(issue.get("description")).append("\n");
                            }
                        }
                    }
                }
            }
            
            // 添加查重问题
            if (checkData.containsKey("plagiarism")) {
                Map<String, Object> plagiarism = (Map<String, Object>) checkData.get("plagiarism");
                if ("FAIL".equals(plagiarism.get("status"))) {
                    feedback.append("3. 查重问题：").append(plagiarism.get("message")).append("\n");
                }
            }
            
            feedback.append("\n编辑意见：").append(comment).append("\n\n");
            feedback.append("请根据以上问题修改后重新提交。\n");
            feedback.append("系统邮件，请勿回复。");
            
            // 记录发送日志（实际项目中应该真正发送邮件）
            logger.info("发送反馈给作者 {} ({})，内容：\n{}", 
                       authorName, email, feedback.toString());
            
        } catch (Exception e) {
            logger.error("发送反馈失败: {}", e.getMessage());
        }
    }
}