// src/main/java/com/bjfu/paper/mapper/FileMapper.java
package com.bjfu.paper.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bjfu.paper.model.entity.File;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper extends BaseMapper<File> {
    
    /**
     * 根据稿件ID获取所有文件
     */
    @Select("SELECT * FROM file WHERE ms_id = #{msId} ORDER BY upload_time DESC")
    List<File> selectByManuscriptId(@Param("msId") String msId);
    
    /**
     * 根据稿件ID和文件类型获取文件
     */
    @Select("SELECT * FROM file WHERE ms_id = #{msId} AND file_type = #{fileType} ORDER BY upload_time DESC")
    List<File> selectByManuscriptIdAndType(
        @Param("msId") String msId, 
        @Param("fileType") String fileType
    );
    
    /**
     * 获取稿件的最新稿件文件
     */
    @Select("SELECT * FROM file WHERE ms_id = #{msId} AND file_type = 'MANUSCRIPT' ORDER BY upload_time DESC LIMIT 1")
    File selectLatestManuscriptFile(@Param("msId") String msId);
    
    /**
     * 获取稿件的所有稿件文件（按类型）
     */
    @Select("SELECT * FROM file WHERE ms_id = #{msId} AND file_type IN ('MANUSCRIPT', 'PDF', 'DOC', 'DOCX') ORDER BY upload_time DESC")
    List<File> selectAllManuscriptFiles(@Param("msId") String msId);
}