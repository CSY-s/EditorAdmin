<template>
  <main class="guide-page">
    <!-- 页面标题 -->
    <section class="page-header">
      <h1>用户指南</h1>
      <p class="subtitle">为作者、审稿人和编辑提供的资源和指南</p>
    </section>

    <!-- 快速导航 -->
    <section class="quick-nav">
      <div class="nav-cards">
        <div 
          v-for="section in guideSections" 
          :key="section.id"
          class="nav-card"
          @click="scrollToSection(section.id)"
        >
          <div class="nav-icon">{{ section.icon }}</div>
          <h3>{{ section.title }}</h3>
          <p>{{ section.description }}</p>
        </div>
      </div>
    </section>

    <!-- 内容部分 -->
    <div class="guide-content">
      <!-- 作者指南 -->
      <section id="authors" class="guide-section">
        <h2 class="section-title">作者指南</h2>
        
        <div class="section-content">
          <div class="subsections">
            <!-- 投稿流程 -->
            <div class="subsection">
              <h3>📝 投稿流程</h3>
              <div class="process-steps">
                <div v-for="(step, index) in authorProcess" :key="index" class="process-step">
                  <div class="step-number">{{ index + 1 }}</div>
                  <div class="step-content">
                    <h4>{{ step.title }}</h4>
                    <p>{{ step.description }}</p>
                    <ul v-if="step.details">
                      <li v-for="(detail, i) in step.details" :key="i">{{ detail }}</li>
                    </ul>
                  </div>
                </div>
              </div>
            </div>

            <!-- 稿件准备 -->
            <div class="subsection">
              <h3>📄 稿件准备</h3>
              <div class="preparation-grid">
                <div class="prep-card">
                  <h4>格式要求</h4>
                  <ul>
                    <li>使用我们的 <a href="#">Microsoft Word模板</a> 或 <a href="#">LaTeX模板</a></li>
                    <li>最大长度：8000字（含参考文献）</li>
                    <li>摘要：150-250字</li>
                    <li>关键词：4-6个相关术语</li>
                  </ul>
                </div>
                <div class="prep-card">
                  <h4>文件准备</h4>
                  <ul>
                    <li>提交PDF格式稿件以供评审</li>
                    <li>图表：最小300 DPI</li>
                    <li>表格：可编辑格式（Excel/Word）</li>
                    <li>补充材料：单独文件</li>
                  </ul>
                </div>
                <div class="prep-card">
                  <h4>结构指南</h4>
                  <ul>
                    <li>包含作者信息的标题页</li>
                    <li>结构化摘要</li>
                    <li>引言、方法、结果、讨论</li>
                    <li>参考文献使用APA第7版格式</li>
                  </ul>
                </div>
              </div>
            </div>

            <!-- 出版政策 -->
            <div class="subsection">
              <h3>⚖️ 出版政策</h3>
              <div class="policies-grid">
                <div class="policy-card">
                  <h4>开放获取政策</h4>
                  <p>所有文章均在知识共享CC-BY许可下发表。</p>
                  <ul>
                    <li>文章处理费：1800美元</li>
                    <li>发展中国家可申请豁免</li>
                    <li>发表后立即开放获取</li>
                  </ul>
                </div>
                <div class="policy-card">
                  <h4>伦理指南</h4>
                  <p>我们遵守COGE出版伦理准则。</p>
                  <ul>
                    <li>作者署名须符合ICMJE标准</li>
                    <li>利益冲突声明</li>
                    <li>需要数据可用性声明</li>
                  </ul>
                </div>
                <div class="policy-card">
                  <h4>版权政策</h4>
                  <p>作者保留其作品的版权。</p>
                  <ul>
                    <li>作者授予出版商独家许可</li>
                    <li>有权存入机构知识库</li>
                    <li>对作者再利用无限制</li>
                  </ul>
                </div>
              </div>
            </div>

            <!-- 常见问题 -->
            <div class="subsection">
              <h3>❓ 常见问题</h3>
              <div class="faq-list">
                <div v-for="(faq, index) in authorFAQs" :key="index" class="faq-item">
                  <button class="faq-question" @click="toggleFAQ(index)">
                    <span>{{ faq.question }}</span>
                    <span class="faq-icon">{{ expandedFAQ === index ? '−' : '+' }}</span>
                  </button>
                  <div v-if="expandedFAQ === index" class="faq-answer">
                    <p>{{ faq.answer }}</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- 审稿人指南 -->
      <section id="reviewers" class="guide-section">
        <h2 class="section-title">审稿人指南</h2>
        
        <div class="section-content">
          <div class="subsections">
            <!-- 评审流程 -->
            <div class="subsection">
              <h3>👁️ 评审流程概述</h3>
              <div class="review-process">
                <p>我们期刊采用双盲同行评审系统。审稿人应在4周内提供建设性反馈。</p>
                
                <div class="timeline">
                  <div class="timeline-step">
                    <div class="timeline-marker">1</div>
                    <div class="timeline-content">
                      <h4>邀请</h4>
                      <p>包含稿件摘要和截止日期的审稿邀请</p>
                    </div>
                  </div>
                  <div class="timeline-step">
                    <div class="timeline-marker">2</div>
                    <div class="timeline-content">
                      <h4>接受/拒绝</h4>
                      <p>7天内回复。如拒绝请推荐替代审稿人。</p>
                    </div>
                  </div>
                  <div class="timeline-step">
                    <div class="timeline-marker">3</div>
                    <div class="timeline-content">
                      <h4>提交评审</h4>
                      <p>提交详细评论和建议</p>
                    </div>
                  </div>
                  <div class="timeline-step">
                    <div class="timeline-marker">4</div>
                    <div class="timeline-content">
                      <h4>编辑决定</h4>
                      <p>编辑根据所有评审意见做出决定</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- 评审指南 -->
            <div class="subsection">
              <h3>📋 评审指南</h3>
              <div class="guidelines-grid">
                <div class="guideline-card">
                  <h4>给编辑的保密意见</h4>
                  <ul>
                    <li>对稿件的整体评估</li>
                    <li>保密关切或利益冲突</li>
                    <li>建议（接受/拒绝/修改）</li>
                    <li>评分表评估</li>
                  </ul>
                </div>
                <div class="guideline-card">
                  <h4>给作者的意见</h4>
                  <ul>
                    <li>建设性和具体的反馈</li>
                    <li>主要和次要问题</li>
                    <li>改进建议</li>
                    <li>引用具体章节</li>
                  </ul>
                </div>
                <div class="guideline-card">
                  <h4>评估标准</h4>
                  <ul>
                    <li>原创性和重要性</li>
                    <li>方法严谨性</li>
                    <li>清晰度和组织性</li>
                    <li>参考文献恰当性</li>
                    <li>伦理考虑</li>
                  </ul>
                </div>
              </div>
            </div>

            <!-- 审稿人福利 -->
            <div class="subsection">
              <h3>🎁 审稿人福利</h3>
              <div class="benefits-grid">
                <div class="benefit-card">
                  <div class="benefit-icon">📜</div>
                  <h4>审稿证书</h4>
                  <p>每完成一次评审可获得官方证书</p>
                </div>
                <div class="benefit-card">
                  <div class="benefit-icon">📚</div>
                  <h4>期刊访问</h4>
                  <p>活跃审稿人免费访问期刊</p>
                </div>
                <div class="benefit-card">
                  <div class="benefit-icon">🤝</div>
                  <h4>社区认可</h4>
                  <p>在年度审稿人名单中获得认可</p>
                </div>
                <div class="benefit-card">
                  <div class="benefit-icon">💳</div>
                  <h4>折扣优惠</h4>
                  <p>文章处理费50%折扣</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- 编辑指南 -->
      <section id="editors" class="guide-section">
        <h2 class="section-title">编辑指南</h2>
        
        <div class="section-content">
          <div class="subsections">
            <!-- 编辑责任 -->
            <div class="subsection">
              <h3>🎯 编辑责任</h3>
              <div class="responsibilities-grid">
                <div class="responsibility-card">
                  <h4>稿件处理</h4>
                  <ul>
                    <li>初审范围和质量</li>
                    <li>分配合适的审稿人</li>
                    <li>管理评审时间线</li>
                    <li>做出编辑决定</li>
                  </ul>
                </div>
                <div class="responsibility-card">
                  <h4>质量控制</h4>
                  <ul>
                    <li>确保评审质量</li>
                    <li>检查伦理合规性</li>
                    <li>核实利益冲突</li>
                    <li>维护评审标准</li>
                  </ul>
                </div>
                <div class="responsibility-card">
                  <h4>沟通</h4>
                  <ul>
                    <li>及时与作者沟通</li>
                    <li>清晰的决策信</li>
                    <li>向审稿人提供反馈</li>
                    <li>执行期刊政策</li>
                  </ul>
                </div>
              </div>
            </div>

            <!-- 决策指南 -->
            <div class="subsection">
              <h3>🤔 决策指南</h3>
              <div class="decision-criteria">
                <div class="criteria-card">
                  <h4>接受</h4>
                  <p>稿件符合所有发表标准</p>
                  <ul>
                    <li>所有审稿人推荐接受</li>
                    <li>无需重大修改</li>
                    <li>优秀的科学贡献</li>
                  </ul>
                </div>
                <div class="criteria-card">
                  <h4>小修</h4>
                  <p>稿件需要小幅改进</p>
                  <ul>
                    <li>审稿人建议小幅修改</li>
                    <li>核心贡献很强</li>
                    <li>预计2个月内完成修改</li>
                  </ul>
                </div>
                <div class="criteria-card">
                  <h4>大修</h4>
                  <p>需要实质性修改</p>
                  <ul>
                    <li>需要显著改进</li>
                    <li>需要重新评审</li>
                    <li>预计4个月内完成修改</li>
                  </ul>
                </div>
                <div class="criteria-card">
                  <h4>拒绝</h4>
                  <p>稿件不符合发表标准</p>
                  <ul>
                    <li>重大科学缺陷</li>
                    <li>超出期刊范围</li>
                    <li>写作质量差</li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- 工具与资源 -->
      <section id="resources" class="guide-section">
        <h2 class="section-title">工具与资源</h2>
        
        <div class="section-content">
          <div class="resources-grid">
            <div class="resource-card">
              <h3>📝 模板与格式</h3>
              <ul>
                <li><a href="#">Microsoft Word模板</a></li>
                <li><a href="#">LaTeX模板</a></li>
                <li><a href="#">参考文献样式指南</a></li>
                <li><a href="#">图表准备指南</a></li>
              </ul>
            </div>
            
            <div class="resource-card">
              <h3>🔍 检查工具</h3>
              <ul>
                <li><a href="#">抄袭检查工具</a></li>
                <li><a href="#">语法检查工具</a></li>
                <li><a href="#">参考文献验证器</a></li>
                <li><a href="#">图表质量检查</a></li>
              </ul>
            </div>
            
            <div class="resource-card">
              <h3>📚 参考文献管理</h3>
              <ul>
                <li><a href="#">EndNote样式</a></li>
                <li><a href="#">Mendeley指南</a></li>
                <li><a href="#">Zotero设置</a></li>
                <li><a href="#">BibTeX格式</a></li>
              </ul>
            </div>
            
            <div class="resource-card">
              <h3>📞 联系与支持</h3>
              <ul>
                <li><strong>编辑部：</strong> editor@iair-journal.org</li>
                <li><strong>技术支持：</strong> support@iair-journal.org</li>
                <li><strong>电话：</strong> +1 (234) 567-8900</li>
                <li><strong>响应时间：</strong> 24-48小时</li>
              </ul>
            </div>
          </div>
          
          <!-- 下载中心 -->
          <div class="download-center">
            <h3>📥 下载中心</h3>
            <div class="download-list">
              <div v-for="(doc, index) in downloadableDocs" :key="index" class="download-item">
                <div class="doc-icon">{{ doc.icon }}</div>
                <div class="doc-info">
                  <h4>{{ doc.title }}</h4>
                  <p>{{ doc.description }}</p>
                </div>
                <a :href="doc.link" class="btn btn-outline btn-small">下载</a>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>

    <!-- 返回顶部 -->
    <button @click="scrollToTop" class="back-to-top" v-show="showBackToTop">
      ↑ 返回顶部
    </button>
  </main>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'

// 导航部分
const guideSections = ref([
  {
    id: 'authors',
    title: '作者指南',
    icon: '✍️',
    description: '投稿指南、格式要求和政策'
  },
  {
    id: 'reviewers',
    title: '审稿人指南',
    icon: '👁️',
    description: '评审流程、指南和福利'
  },
  {
    id: 'editors',
    title: '编辑指南',
    icon: '📝',
    description: '编辑责任和决策指南'
  },
  {
    id: 'resources',
    title: '工具与资源',
    icon: '🔧',
    description: '模板、工具和联系信息'
  }
])

// 作者流程步骤
const authorProcess = ref([
  {
    title: '准备稿件',
    description: '确保您的稿件符合我们的格式和伦理指南。',
    details: [
      '使用提供的模板',
      '检查字数限制',
      '准备图表',
      '声明利益冲突'
    ]
  },
  {
    title: '在线提交',
    description: '使用我们的在线投稿系统上传稿件。',
    details: [
      '创建或登录账户',
      '完成投稿表格',
      '上传稿件和文件',
      '添加作者信息'
    ]
  },
  {
    title: '同行评审',
    description: '您的稿件将接受双盲同行评审。',
    details: [
      '初步编辑检查',
      '分配给审稿人',
      '评审周期（4-6周）',
      '编辑决定'
    ]
  },
  {
    title: '修改与发表',
    description: '回复审稿意见并准备最终版本。',
    details: [
      '提交修改稿',
      '格式化最终版本',
      '校对和排版',
      '在线发表'
    ]
  }
])

// 作者常见问题
const authorFAQs = ref([
  {
    question: '投稿费用是多少？',
    answer: '没有投稿费。文章处理费仅在文章被接受发表后收取。'
  },
  {
    question: '评审过程需要多长时间？',
    answer: '从投稿到首次决定的平均时间为4-6周。'
  },
  {
    question: '我可以推荐审稿人吗？',
    answer: '可以，您在投稿时可以推荐最多3位潜在审稿人。'
  },
  {
    question: '字数限制是多少？',
    answer: '研究文章不应超过8000字（含参考文献）。'
  },
  {
    question: '如何跟踪投稿状态？',
    answer: '您可以通过作者仪表板跟踪投稿状态。'
  }
])

// 可下载文档
const downloadableDocs = ref([
  {
    title: '作者指南PDF',
    description: '稿件准备的完整指南',
    icon: '📄',
    link: '#'
  },
  {
    title: 'Word模板',
    description: 'Microsoft Word稿件模板',
    icon: '📝',
    link: '#'
  },
  {
    title: 'LaTeX模板',
    description: '包含期刊样式的LaTeX模板',
    icon: '🔤',
    link: '#'
  },
  {
    title: '审稿人表格',
    description: '审稿人评估表格',
    icon: '📋',
    link: '#'
  }
])

// 响应式变量
const expandedFAQ = ref(null)
const showBackToTop = ref(false)

// 方法
const toggleFAQ = (index) => {
  expandedFAQ.value = expandedFAQ.value === index ? null : index
}

const scrollToSection = (sectionId) => {
  const element = document.getElementById(sectionId)
  if (element) {
    element.scrollIntoView({ behavior: 'smooth' })
  }
}

const scrollToTop = () => {
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

const handleScroll = () => {
  showBackToTop.value = window.scrollY > 500
}

// 生命周期钩子
onMounted(() => {
  window.addEventListener('scroll', handleScroll)
})

onBeforeUnmount(() => {
  window.removeEventListener('scroll', handleScroll)
})
</script>

<style scoped>
/* 基础重置 */
.guide-page {
  min-height: 100vh;
  background-color: #f8f9fa;
  padding: 20px 8px 40px;
  width: 100%;
}

/* 页面标题 - 更紧凑 */
.page-header {
  text-align: center;
  margin-bottom: 30px;
  padding: 0 4px;
}

.page-header h1 {
  font-size: 32px;
  color: #2c3e50;
  margin-bottom: 10px;
  font-weight: 700;
}

.subtitle {
  font-size: 16px;
  color: #7f8c8d;
  line-height: 1.4;
}

/* 快速导航 - 更紧凑 */
.quick-nav {
  margin-bottom: 35px;
  padding: 0 2px;
}

.nav-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 12px;
  max-width: 1200px;
  margin: 0 auto;
}

.nav-card {
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 18px;
  text-align: center;
  cursor: pointer;
  transition: all 0.2s ease;
}

.nav-card:hover {
  border-color: #2c3e50;
  transform: translateY(-3px);
  box-shadow: 0 5px 15px rgba(0,0,0,0.08);
}

.nav-icon {
  font-size: 32px;
  margin-bottom: 10px;
}

.nav-card h3 {
  font-size: 16px;
  color: #2c3e50;
  margin-bottom: 6px;
  font-weight: 600;
}

.nav-card p {
  color: #7f8c8d;
  font-size: 13px;
  line-height: 1.4;
}

/* 内容部分 */
.guide-content {
  display: flex;
  flex-direction: column;
  gap: 35px;
  max-width: 1300px;
  margin: 0 auto;
  padding: 0 6px;
  width: 100%;
}

.guide-section {
  background: white;
  border-radius: 8px;
  padding: 24px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.04);
  border: 1px solid #e8ecef;
}

.section-title {
  font-size: 26px;
  color: #2c3e50;
  margin-bottom: 20px;
  padding-bottom: 12px;
  border-bottom: 1px solid #e0e0e0;
  font-weight: 600;
}

.section-content {
  margin-top: 15px;
}

.subsections {
  display: flex;
  flex-direction: column;
  gap: 28px;
}

.subsection {
  margin-bottom: 20px;
}

.subsection h3 {
  font-size: 20px;
  color: #2c3e50;
  margin-bottom: 15px;
  font-weight: 600;
}

/* Process Steps - 更紧凑 */
.process-steps {
  display: flex;
  flex-direction: column;
  gap: 20px;
  position: relative;
  padding-left: 25px;
}

.process-steps::before {
  content: '';
  position: absolute;
  left: 15px;
  top: 0;
  bottom: 0;
  width: 2px;
  background-color: #e0e0e0;
}

.process-step {
  display: flex;
  gap: 15px;
  position: relative;
}

.step-number {
  width: 32px;
  height: 32px;
  background: #2c3e50;
  color: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
  font-size: 14px;
  flex-shrink: 0;
  z-index: 1;
}

.step-content {
  flex: 1;
}

.step-content h4 {
  font-size: 18px;
  color: #2c3e50;
  margin-bottom: 6px;
  font-weight: 600;
}

.step-content p {
  color: #555;
  margin-bottom: 8px;
  font-size: 14px;
  line-height: 1.5;
}

.step-content ul {
  list-style-type: disc;
  margin-left: 18px;
  color: #7f8c8d;
  font-size: 13px;
}

.step-content li {
  margin-bottom: 4px;
  line-height: 1.4;
}

/* 网格布局 - 更紧凑 */
.preparation-grid,
.policies-grid,
.guidelines-grid,
.responsibilities-grid,
.resources-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 16px;
}

.prep-card,
.policy-card,
.guideline-card,
.responsibility-card,
.resource-card {
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  padding: 16px;
  transition: all 0.2s ease;
}

.prep-card:hover,
.policy-card:hover,
.guideline-card:hover,
.responsibility-card:hover,
.resource-card:hover {
  border-color: #2c3e50;
  box-shadow: 0 3px 10px rgba(0,0,0,0.05);
}

.prep-card h4,
.policy-card h4,
.guideline-card h4,
.responsibility-card h4,
.resource-card h3 {
  font-size: 16px;
  color: #2c3e50;
  margin-bottom: 12px;
  font-weight: 600;
}

.prep-card ul,
.policy-card ul,
.guideline-card ul,
.responsibility-card ul,
.resource-card ul {
  list-style-type: disc;
  margin-left: 18px;
  color: #555;
  font-size: 13px;
}

.prep-card li,
.policy-card li,
.guideline-card li,
.responsibility-card li,
.resource-card li {
  margin-bottom: 6px;
  line-height: 1.4;
}

.policy-card p {
  color: #555;
  font-size: 13px;
  margin-bottom: 10px;
  line-height: 1.4;
}

.prep-card a,
.resource-card a {
  color: #3498db;
  text-decoration: none;
  font-size: 13px;
}

.prep-card a:hover,
.resource-card a:hover {
  text-decoration: underline;
}

/* FAQ Section - 更紧凑 */
.faq-list {
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  overflow: hidden;
}

.faq-item {
  border-bottom: 1px solid #e0e0e0;
}

.faq-item:last-child {
  border-bottom: none;
}

.faq-question {
  width: 100%;
  padding: 14px 16px;
  text-align: left;
  background: none;
  border: none;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
  font-weight: 500;
  color: #2c3e50;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.faq-question:hover {
  background-color: #f8f9fa;
}

.faq-icon {
  font-size: 18px;
  font-weight: 600;
  color: #2c3e50;
}

.faq-answer {
  padding: 0 16px 14px;
  color: #555;
  font-size: 13px;
  line-height: 1.5;
}

/* Review Process Timeline - 更紧凑 */
.review-process > p {
  color: #555;
  font-size: 14px;
  line-height: 1.5;
  margin-bottom: 20px;
}

.timeline {
  display: flex;
  flex-direction: column;
  gap: 20px;
  position: relative;
  padding-left: 35px;
}

.timeline::before {
  content: '';
  position: absolute;
  left: 15px;
  top: 0;
  bottom: 0;
  width: 2px;
  background-color: #e0e0e0;
}

.timeline-step {
  display: flex;
  gap: 15px;
  position: relative;
}

.timeline-marker {
  width: 30px;
  height: 30px;
  background: #3498db;
  color: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
  font-size: 13px;
  flex-shrink: 0;
  z-index: 1;
}

.timeline-content h4 {
  font-size: 16px;
  color: #2c3e50;
  margin-bottom: 4px;
  font-weight: 600;
}

.timeline-content p {
  color: #7f8c8d;
  font-size: 13px;
  line-height: 1.4;
}

/* Benefits Grid - 更紧凑 */
.benefits-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
  gap: 16px;
}

.benefit-card {
  text-align: center;
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  transition: all 0.2s ease;
}

.benefit-card:hover {
  border-color: #27ae60;
  transform: translateY(-3px);
}

.benefit-icon {
  font-size: 28px;
  margin-bottom: 10px;
}

.benefit-card h4 {
  font-size: 16px;
  color: #2c3e50;
  margin-bottom: 8px;
  font-weight: 600;
}

.benefit-card p {
  color: #7f8c8d;
  font-size: 12px;
  line-height: 1.4;
}

/* Decision Criteria - 更紧凑 */
.decision-criteria {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 16px;
}

.criteria-card {
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  padding: 18px;
  transition: all 0.2s ease;
  border-top: 3px solid #3498db;
}

.criteria-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 3px 10px rgba(0,0,0,0.05);
}

.criteria-card:nth-child(1) { border-top-color: #27ae60; }
.criteria-card:nth-child(2) { border-top-color: #f39c12; }
.criteria-card:nth-child(3) { border-top-color: #e74c3c; }
.criteria-card:nth-child(4) { border-top-color: #7f8c8d; }

.criteria-card h4 {
  font-size: 16px;
  color: #2c3e50;
  margin-bottom: 8px;
  font-weight: 600;
}

.criteria-card > p {
  color: #7f8c8d;
  margin-bottom: 10px;
  font-size: 13px;
  line-height: 1.4;
}

.criteria-card ul {
  list-style-type: disc;
  margin-left: 18px;
  color: #555;
  font-size: 13px;
}

.criteria-card li {
  margin-bottom: 5px;
  line-height: 1.4;
}

/* Resources Grid - 更紧凑 */
.resource-card h3 {
  font-size: 16px;
  color: #2c3e50;
  margin-bottom: 12px;
  font-weight: 600;
}

.resource-card ul {
  list-style: none;
  margin: 0;
  padding: 0;
}

.resource-card li {
  margin-bottom: 8px;
  line-height: 1.4;
}

.resource-card a {
  color: #3498db;
  text-decoration: none;
  font-size: 13px;
}

.resource-card a:hover {
  text-decoration: underline;
}

.resource-card strong {
  color: #2c3e50;
  font-size: 13px;
  margin-right: 4px;
}

/* Download Center - 更紧凑 */
.download-center {
  margin-top: 30px;
  padding: 20px;
  background-color: #f8f9fa;
  border-radius: 6px;
  border: 1px solid #e0e0e0;
}

.download-center h3 {
  font-size: 20px;
  color: #2c3e50;
  margin-bottom: 16px;
  font-weight: 600;
}

.download-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.download-item {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 14px;
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  transition: all 0.2s ease;
}

.download-item:hover {
  border-color: #2c3e50;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
}

.doc-icon {
  font-size: 20px;
}

.doc-info {
  flex: 1;
}

.doc-info h4 {
  font-size: 15px;
  color: #2c3e50;
  margin-bottom: 4px;
  font-weight: 600;
}

.doc-info p {
  color: #7f8c8d;
  font-size: 12px;
  line-height: 1.4;
}

/* 按钮样式 */
.btn-outline {
  background: transparent;
  border: 1px solid #3498db;
  color: #3498db;
  padding: 6px 12px;
  border-radius: 4px;
  font-size: 13px;
  cursor: pointer;
  text-decoration: none;
  transition: all 0.2s ease;
}

.btn-outline:hover {
  background: #3498db;
  color: white;
}

.btn-small {
  padding: 5px 10px !important;
  font-size: 12px !important;
}

/* Back to Top Button */
.back-to-top {
  position: fixed;
  bottom: 20px;
  right: 20px;
  padding: 8px 16px;
  background: #2c3e50;
  color: white;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  font-size: 13px;
  font-weight: 500;
  box-shadow: 0 2px 10px rgba(0,0,0,0.15);
  transition: all 0.2s ease;
  z-index: 100;
}

.back-to-top:hover {
  background: #1a252f;
  transform: translateY(-1px);
}

/* 响应式调整 - 移动端 */
@media (max-width: 768px) {
  .guide-page {
    padding: 15px 4px 30px;
  }
  
  .page-header {
    margin-bottom: 20px;
  }
  
  .page-header h1 {
    font-size: 26px;
  }
  
  .subtitle {
    font-size: 14px;
  }
  
  .quick-nav {
    margin-bottom: 25px;
  }
  
  .nav-cards {
    grid-template-columns: 1fr;
    gap: 10px;
  }
  
  .nav-card {
    padding: 16px;
  }
  
  .nav-icon {
    font-size: 28px;
  }
  
  .nav-card h3 {
    font-size: 15px;
  }
  
  .guide-content {
    gap: 25px;
    padding: 0 4px;
  }
  
  .guide-section {
    padding: 18px;
  }
  
  .section-title {
    font-size: 22px;
    margin-bottom: 16px;
    padding-bottom: 10px;
  }
  
  .subsections {
    gap: 22px;
  }
  
  .subsection h3 {
    font-size: 18px;
    margin-bottom: 12px;
  }
  
  .process-steps,
  .timeline {
    padding-left: 20px;
  }
  
  .process-steps::before,
  .timeline::before {
    left: 10px;
  }
  
  .step-number,
  .timeline-marker {
    width: 28px;
    height: 28px;
    font-size: 13px;
  }
  
  .step-content h4,
  .timeline-content h4 {
    font-size: 15px;
  }
  
  .preparation-grid,
  .policies-grid,
  .guidelines-grid,
  .responsibilities-grid,
  .resources-grid,
  .benefits-grid,
  .decision-criteria {
    grid-template-columns: 1fr;
    gap: 12px;
  }
  
  .prep-card,
  .policy-card,
  .guideline-card,
  .responsibility-card,
  .resource-card,
  .criteria-card,
  .benefit-card {
    padding: 14px;
  }
  
  .download-center {
    padding: 16px;
    margin-top: 24px;
  }
  
  .download-center h3 {
    font-size: 18px;
    margin-bottom: 12px;
  }
  
  .download-list {
    gap: 10px;
  }
  
  .download-item {
    padding: 12px;
    gap: 12px;
  }
  
  .back-to-top {
    bottom: 15px;
    right: 15px;
    padding: 6px 12px;
    font-size: 12px;
  }
}

@media (max-width: 480px) {
  .guide-page {
    padding: 10px 2px 20px;
  }
  
  .page-header h1 {
    font-size: 22px;
  }
  
  .subtitle {
    font-size: 13px;
  }
  
  .nav-card {
    padding: 14px;
  }
  
  .guide-section {
    padding: 16px;
  }
  
  .section-title {
    font-size: 20px;
  }
  
  .subsection h3 {
    font-size: 16px;
  }
  
  .process-step,
  .timeline-step {
    gap: 12px;
  }
  
  .step-number,
  .timeline-marker {
    width: 26px;
    height: 26px;
    font-size: 12px;
  }
  
  .faq-question {
    padding: 12px 14px;
    font-size: 13px;
  }
  
  .download-item {
    flex-direction: column;
    text-align: center;
    gap: 8px;
  }
}

/* 大屏幕优化 */
@media (min-width: 1400px) {
  .nav-cards {
    grid-template-columns: repeat(4, 1fr);
  }
  
  .guide-content {
    max-width: 1400px;
  }
  
  .preparation-grid,
  .policies-grid,
  .guidelines-grid,
  .responsibilities-grid,
  .resources-grid {
    grid-template-columns: repeat(3, 1fr);
  }
  
  .benefits-grid {
    grid-template-columns: repeat(4, 1fr);
  }
  
  .decision-criteria {
    grid-template-columns: repeat(4, 1fr);
  }
}

@media (min-width: 1800px) {
  .guide-content {
    max-width: 1700px;
  }
}
</style>