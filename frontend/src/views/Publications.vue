<template>
  <main class="publications-page">
    <div class="container">
      <!-- 页面标题 -->
      <section class="page-header">
        <h1>出版物</h1>
        <p class="subtitle">浏览期刊卷期、特刊和已发表文章</p>
        <div class="header-tabs">
          <button 
            v-for="tab in tabs" 
            :key="tab.id"
            :class="['tab-btn', { 'active': activeTab === tab.id }]"
            @click="switchTab(tab.id)"
          >
            {{ tab.label }}
          </button>
        </div>
      </section>

      <!-- 最新卷期标签页 -->
      <section v-if="activeTab === 'latest'" class="tab-content">
        <div class="issues-section">
          <div class="section-header">
            <h2>最新卷期</h2>
            <div class="volume-select">
              <label for="volume">卷号:</label>
              <select v-model="selectedVolume" @change="filterByVolume" id="volume" class="volume-dropdown">
                <option value="all">所有卷号</option>
                <option v-for="volume in volumes" :key="volume" :value="volume">
                  卷 {{ volume }}
                </option>
              </select>
            </div>
          </div>

          <div class="issues-grid">
            <div v-for="issue in filteredIssues" :key="issue.id" class="issue-card">
              <div class="issue-header">
                <div class="issue-badge">
                  {{ issue.type === 'special' ? '特刊' : '常规刊' }}
                </div>
                <div class="issue-date">{{ formatDate(issue.publishedDate) }}</div>
              </div>
              
              <h3 class="issue-title">{{ issue.title }}</h3>
              
              <div class="issue-meta">
                <span class="meta-item">
                  <strong>卷号:</strong> {{ issue.volume }}({{ issue.issue }})
                </span>
                <span class="meta-item">
                  <strong>页码:</strong> {{ issue.pages }}
                </span>
                <span class="meta-item">
                  <strong>文章数:</strong> {{ issue.articleCount }}
                </span>
              </div>
              
              <p class="issue-description">{{ issue.description }}</p>
              
              <div class="issue-articles">
                <h4>特色文章:</h4>
                <ul class="articles-list">
                  <li v-for="article in issue.featuredArticles" :key="article.id" class="article-item">
                    <router-link :to="`/paper/${article.id}`" class="article-link">
                      {{ article.title }}
                    </router-link>
                    <span class="article-authors">{{ article.authors }}</span>
                  </li>
                </ul>
              </div>
              
              <div class="issue-actions">
                <button @click="downloadIssue(issue)" class="btn btn-outline">
                  下载完整卷期
                </button>
                <router-link :to="`/issue/${issue.id}`" class="btn btn-primary">
                  浏览卷期
                </router-link>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- 特刊标签页 -->
      <section v-if="activeTab === 'special'" class="tab-content">
        <div class="special-issues-section">
          <h2>特刊</h2>
          
          <div class="special-issues-grid">
            <div v-for="specialIssue in specialIssues" :key="specialIssue.id" class="special-issue-card">
              <div class="special-issue-header">
                <h3>{{ specialIssue.title }}</h3>
                <div :class="['special-issue-status', specialIssue.status]">
                  {{ getStatusLabel(specialIssue.status) }}
                </div>
              </div>
              
              <p class="special-issue-description">{{ specialIssue.description }}</p>
              
              <div class="special-issue-details">
                <div class="detail-item">
                  <strong>客座编辑:</strong> {{ specialIssue.guestEditor }}
                </div>
                <div class="detail-item">
                  <strong>投稿截止日期:</strong> {{ formatDate(specialIssue.deadline) }}
                </div>
                <div class="detail-item">
                  <strong>预计出版时间:</strong> {{ formatDate(specialIssue.expectedPublication) }}
                </div>
                <div v-if="specialIssue.publishedDate" class="detail-item">
                  <strong>已出版:</strong> {{ formatDate(specialIssue.publishedDate) }}
                </div>
              </div>
              
              <div class="special-issue-keywords">
                <span v-for="keyword in specialIssue.keywords" :key="keyword" class="keyword">
                  {{ keyword }}
                </span>
              </div>
              
              <div class="special-issue-actions">
                <a v-if="specialIssue.status === 'open'" :href="specialIssue.cfpLink" class="btn btn-primary">
                  提交论文
                </a>
                <router-link v-if="specialIssue.status === 'published'" :to="`/issue/${specialIssue.id}`" class="btn btn-primary">
                  查看卷期
                </router-link>
                <a :href="specialIssue.guidelinesLink" class="btn btn-outline">
                  投稿指南
                </a>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- 所有卷期标签页 -->
      <section v-if="activeTab === 'all'" class="tab-content">
        <div class="all-issues-section">
          <h2>所有卷期</h2>
          
          <div class="archive-controls">
            <div class="search-archive">
              <input 
                type="text" 
                v-model="archiveSearch" 
                placeholder="通过标题、卷号或年份搜索卷期..."
                class="archive-search-input"
              />
            </div>
            
            <div class="archive-filters">
              <select v-model="archiveYear" @change="filterArchive" class="archive-filter">
                <option value="all">所有年份</option>
                <option v-for="year in archiveYears" :key="year" :value="year">{{ year }}</option>
              </select>
              
              <select v-model="archiveType" @change="filterArchive" class="archive-filter">
                <option value="all">所有类型</option>
                <option value="regular">常规刊</option>
                <option value="special">特刊</option>
              </select>
            </div>
          </div>
          
          <div class="archive-table">
            <div class="table-header">
              <div class="table-col col-title">卷期标题</div>
              <div class="table-col col-volume">卷号/期号</div>
              <div class="table-col col-date">出版日期</div>
              <div class="table-col col-type">类型</div>
              <div class="table-col col-articles">文章数</div>
              <div class="table-col col-actions">操作</div>
            </div>
            
            <div v-if="filteredArchive.length === 0" class="no-results">
              未找到符合筛选条件的卷期。
            </div>
            
            <div v-else class="table-body">
              <div v-for="issue in paginatedArchive" :key="issue.id" class="table-row">
                <div class="table-col col-title">
                  <router-link :to="`/issue/${issue.id}`" class="issue-link">
                    {{ issue.title }}
                  </router-link>
                  <div v-if="issue.type === 'special'" class="special-tag">特刊</div>
                </div>
                <div class="table-col col-volume">卷 {{ issue.volume }}({{ issue.issue }})</div>
                <div class="table-col col-date">{{ formatDate(issue.publishedDate) }}</div>
                <div class="table-col col-type">
                  <span :class="['type-badge', issue.type]">
                    {{ issue.type === 'special' ? '特刊' : '常规' }}
                  </span>
                </div>
                <div class="table-col col-articles">{{ issue.articleCount }}</div>
                <div class="table-col col-actions">
                  <div class="action-buttons">
                    <button @click="viewIssue(issue)" class="action-btn view-btn">
                      查看
                    </button>
                    <button @click="downloadIssue(issue)" class="action-btn download-btn">
                      下载
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          
          <div class="archive-pagination">
            <button 
              @click="prevArchivePage" 
              :disabled="archiveCurrentPage === 1" 
              class="pagination-btn"
            >
              上一页
            </button>
            
            <div class="page-info">
              显示 {{ archiveStartIndex + 1 }}-{{ Math.min(archiveEndIndex, filteredArchive.length) }} 
              共 {{ filteredArchive.length }} 个卷期
            </div>
            
            <button 
              @click="nextArchivePage" 
              :disabled="archiveCurrentPage === archiveTotalPages" 
              class="pagination-btn"
            >
              下一页
            </button>
          </div>
        </div>
      </section>

      <!-- 影响指标部分 -->
      <section class="metrics-section">
        <h2>期刊影响指标</h2>
        <div class="metrics-grid">
          <div class="metric-card">
            <div class="metric-value">4.5</div>
            <div class="metric-label">2024影响因子</div>
            <div class="metric-trend trend-up">↑ 比2023年提升0.3</div>
          </div>
          <div class="metric-card">
            <div class="metric-value">3.8</div>
            <div class="metric-label">5年影响因子</div>
            <div class="metric-trend trend-up">↑ 比2022年提升0.5</div>
          </div>
          <div class="metric-card">
            <div class="metric-value">15 天</div>
            <div class="metric-label">平均初审时间</div>
            <div class="metric-trend trend-down">↓ 缩短2天</div>
          </div>
          <div class="metric-card">
            <div class="metric-value">85%</div>
            <div class="metric-label">接收率</div>
            <div class="metric-trend trend-up">↑ 比2023年提升5%</div>
          </div>
        </div>
      </section>

      <!-- 下载最多的文章 -->
      <section class="top-downloads-section">
        <h2>下载最多的文章</h2>
        <div class="downloads-list">
          <div v-for="(article, index) in topDownloads" :key="article.id" class="download-item">
            <div class="download-rank">{{ index + 1 }}</div>
            <div class="download-info">
              <h3>
                <router-link :to="`/paper/${article.id}`" class="article-title">
                  {{ article.title }}
                </router-link>
              </h3>
              <p class="article-meta">
                {{ article.authors }} · 
                {{ formatDate(article.publishedDate) }} · 
                卷 {{ article.volume }}({{ article.issue }})
              </p>
            </div>
            <div class="download-stats">
              <div class="download-count">📥 {{ formatNumber(article.downloads) }}</div>
              <div :class="['download-trend', article.trend > 0 ? 'trend-up' : 'trend-down']">
                {{ article.trend > 0 ? '↑' : '↓' }} {{ Math.abs(article.trend) }}%
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  </main>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 标签页配置
const tabs = [
  { id: 'latest', label: '最新卷期' },
  { id: 'special', label: '特刊' },
  { id: 'all', label: '所有卷期' }
]

const activeTab = ref('latest')
const selectedVolume = ref('all')
const archiveSearch = ref('')
const archiveYear = ref('all')
const archiveType = ref('all')
const archiveCurrentPage = ref(1)
const archiveItemsPerPage = 10

// 模拟卷期数据
const issues = ref([
  {
    id: 1,
    title: '深度学习进展',
    volume: 15,
    issue: 3,
    type: 'regular',
    publishedDate: '2024-11-15',
    pages: '1-150',
    articleCount: 12,
    description: '本期展示深度学习算法与应用的前沿研究。',
    featuredArticles: [
      { id: 101, title: '多模态学习的Transformer模型', authors: '张 等' },
      { id: 102, title: '差分隐私联邦学习', authors: 'Smith 等' }
    ]
  },
  {
    id: 2,
    title: '人工智能促进可持续发展',
    volume: 15,
    issue: 2,
    type: 'special',
    publishedDate: '2024-10-28',
    pages: '151-300',
    articleCount: 8,
    description: '特刊聚焦人工智能实现可持续发展目标的应用。',
    featuredArticles: [
      { id: 103, title: '用于气候预测的人工智能', authors: '陈 等' },
      { id: 104, title: '机器学习在智慧农业中的应用', authors: '金 等' }
    ]
  },
  {
    id: 3,
    title: '量子机器学习',
    volume: 15,
    issue: 1,
    type: 'regular',
    publishedDate: '2024-09-15',
    pages: '1-120',
    articleCount: 10,
    description: '探索量子计算与机器学习的交叉领域。',
    featuredArticles: [
      { id: 105, title: '量子神经网络', authors: 'Patel 等' },
      { id: 106, title: '量子优化算法', authors: '王 等' }
    ]
  }
])

// 模拟特刊数据
const specialIssues = ref([
  {
    id: 1,
    title: '人工智能促进可持续发展',
    status: 'published',
    description: '本特刊探讨人工智能如何促进实现联合国可持续发展目标。',
    guestEditor: 'Maria Rodriguez 教授, 剑桥大学',
    deadline: '2024-06-30',
    expectedPublication: '2024-12-15',
    publishedDate: '2024-10-28',
    keywords: ['可持续发展', '人工智能应用', '气候变化', '智慧城市'],
    cfpLink: '#',
    guidelinesLink: '#'
  },
  {
    id: 2,
    title: '医疗健康中的可解释人工智能',
    status: 'open',
    description: '专注于用于医疗诊断和治疗的可靠且可解释的人工智能模型。',
    guestEditor: 'Sarah Johnson 博士, 斯坦福大学',
    deadline: '2025-03-31',
    expectedPublication: '2025-09-30',
    publishedDate: null,
    keywords: ['可解释人工智能', '医疗健康', '医学影像', '可信人工智能'],
    cfpLink: '#',
    guidelinesLink: '#'
  },
  {
    id: 3,
    title: '多模态人工智能系统',
    status: 'in-progress',
    description: '关于能处理和整合多种模态(文本、图像、音频等)的人工智能系统的研究。',
    guestEditor: '张伟 教授, 清华大学',
    deadline: '2024-12-31',
    expectedPublication: '2025-06-30',
    publishedDate: null,
    keywords: ['多模态学习', '跨模态', '融合', '深度学习'],
    cfpLink: '#',
    guidelinesLink: '#'
  }
])

// 模拟存档数据
const archiveIssues = ref([
  ...issues.value,
  // 添加更多历史卷期
  {
    id: 4,
    title: '自然语言处理进展',
    volume: 14,
    issue: 4,
    type: 'regular',
    publishedDate: '2024-08-15',
    articleCount: 11
  },
  {
    id: 5,
    title: '计算机视觉与机器人技术',
    volume: 14,
    issue: 3,
    type: 'special',
    publishedDate: '2024-07-30',
    articleCount: 9
  },
  {
    id: 6,
    title: '机器学习理论',
    volume: 14,
    issue: 2,
    type: 'regular',
    publishedDate: '2024-06-15',
    articleCount: 10
  }
])

// 计算属性
const volumes = computed(() => {
  return [...new Set(issues.value.map(issue => issue.volume))].sort((a, b) => b - a)
})

const filteredIssues = computed(() => {
  if (selectedVolume.value === 'all') return issues.value
  return issues.value.filter(issue => issue.volume.toString() === selectedVolume.value)
})

const filteredArchive = computed(() => {
  let filtered = archiveIssues.value
  
  // 搜索筛选
  if (archiveSearch.value) {
    const query = archiveSearch.value.toLowerCase()
    filtered = filtered.filter(issue => 
      issue.title.toLowerCase().includes(query) ||
      issue.volume.toString().includes(query) ||
      issue.publishedDate.includes(query)
    )
  }
  
  // 年份筛选
  if (archiveYear.value !== 'all') {
    filtered = filtered.filter(issue => 
      issue.publishedDate.startsWith(archiveYear.value)
    )
  }
  
  // 类型筛选
  if (archiveType.value !== 'all') {
    filtered = filtered.filter(issue => issue.type === archiveType.value)
  }
  
  // 按日期排序(最新的在前)
  return filtered.sort((a, b) => 
    new Date(b.publishedDate) - new Date(a.publishedDate)
  )
})

const archiveYears = computed(() => {
  const years = [...new Set(archiveIssues.value.map(issue => 
    issue.publishedDate.split('-')[0]
  ))]
  return years.sort((a, b) => b - a)
})

const archiveTotalPages = computed(() => {
  return Math.ceil(filteredArchive.value.length / archiveItemsPerPage)
})

const archiveStartIndex = computed(() => {
  return (archiveCurrentPage.value - 1) * archiveItemsPerPage
})

const archiveEndIndex = computed(() => {
  return archiveStartIndex.value + archiveItemsPerPage
})

const paginatedArchive = computed(() => {
  return filteredArchive.value.slice(archiveStartIndex.value, archiveEndIndex.value)
})

// 下载最多的数据
const topDownloads = computed(() => {
  // 模拟下载最多的数据
  return [
    {
      id: 1,
      title: '多模态学习的Transformer模型：全面综述',
      authors: '张, L.; 王, Q.; 陈, Y. 等.',
      publishedDate: '2024-11-15',
      volume: 15,
      issue: 3,
      downloads: 1243,
      trend: 15
    },
    {
      id: 2,
      title: '量子机器学习：算法与应用',
      authors: 'Patel, R.; Gupta, S.; Kumar, M.',
      publishedDate: '2024-10-28',
      volume: 15,
      issue: 2,
      downloads: 1120,
      trend: 8
    },
    {
      id: 3,
      title: '面向医疗应用的差分隐私联邦学习',
      authors: 'Smith, J.; Johnson, R.; Brown, A.',
      publishedDate: '2024-11-10',
      volume: 15,
      issue: 3,
      downloads: 987,
      trend: -2
    }
  ]
})

// 方法
const switchTab = (tabId) => {
  activeTab.value = tabId
  archiveCurrentPage.value = 1
}

const filterByVolume = () => {
  // 卷号筛选逻辑已在计算属性中处理
}

const filterArchive = () => {
  archiveCurrentPage.value = 1
}

const getStatusLabel = (status) => {
  const labels = {
    'open': '开放投稿',
    'in-progress': '进行中',
    'published': '已出版'
  }
  return labels[status] || status
}

const formatDate = (dateString) => {
  if (!dateString) return '待定'
  return new Date(dateString).toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  })
}

const formatNumber = (num) => {
  if (num >= 1000000) return (num / 1000000).toFixed(1) + '百万'
  if (num >= 1000) return (num / 1000).toFixed(1) + '千'
  return num.toString()
}

const downloadIssue = (issue) => {
  alert(`正在下载卷期: ${issue.title}`)
  // 实际实现中，这里会触发PDF下载
}

const viewIssue = (issue) => {
  // 实际实现中，导航将通过router-link处理
  console.log('查看卷期:', issue.id)
}

const prevArchivePage = () => {
  if (archiveCurrentPage.value > 1) {
    archiveCurrentPage.value--
  }
}

const nextArchivePage = () => {
  if (archiveCurrentPage.value < archiveTotalPages.value) {
    archiveCurrentPage.value++
  }
}

onMounted(() => {
  console.log('出版物页面已加载')
})
</script>

<!-- 样式保持不变 -->
<style scoped>
.publications-page {
  padding: 40px 0 60px;
  background-color: #f8f9fa;
}

.page-header {
  text-align: center;
  margin-bottom: 40px;
}

.page-header h1 {
  font-size: 42px;
  color: #2c3e50;
  margin-bottom: 15px;
}

.subtitle {
  font-size: 18px;
  color: #7f8c8d;
  margin-bottom: 30px;
}

.header-tabs {
  display: flex;
  justify-content: center;
  gap: 10px;
  border-bottom: 2px solid #e0e0e0;
  max-width: 600px;
  margin: 0 auto;
}

.tab-btn {
  padding: 15px 30px;
  background: none;
  border: none;
  border-bottom: 3px solid transparent;
  font-size: 16px;
  color: #7f8c8d;
  cursor: pointer;
  transition: all 0.3s ease;
}

.tab-btn:hover {
  color: #2c3e50;
}

.tab-btn.active {
  color: #2c3e50;
  border-bottom-color: #2c3e50;
  font-weight: 600;
}

.tab-content {
  background: white;
  padding: 30px;
  border-radius: 10px;
  margin-bottom: 40px;
  box-shadow: 0 2px 15px rgba(0,0,0,0.05);
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  flex-wrap: wrap;
  gap: 15px;
}

.section-header h2 {
  font-size: 28px;
  color: #2c3e50;
}

.volume-select {
  display: flex;
  align-items: center;
  gap: 10px;
}

.volume-dropdown {
  padding: 8px 15px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  background: white;
  color: #2c3e50;
}

.issues-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
  gap: 25px;
}

.issue-card {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 25px;
  transition: all 0.3s ease;
}

.issue-card:hover {
  border-color: #2c3e50;
  box-shadow: 0 5px 20px rgba(0,0,0,0.08);
}

.issue-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.issue-badge {
  padding: 5px 12px;
  background-color: #e8f4fd;
  color: #3498db;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
}

.issue-date {
  font-size: 14px;
  color: #7f8c8d;
}

.issue-title {
  font-size: 22px;
  color: #2c3e50;
  margin-bottom: 15px;
}

.issue-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  margin-bottom: 15px;
  font-size: 14px;
}

.meta-item {
  color: #7f8c8d;
}

.meta-item strong {
  color: #2c3e50;
  margin-right: 5px;
}

.issue-description {
  color: #555;
  line-height: 1.6;
  margin-bottom: 20px;
}

.issue-articles {
  margin-bottom: 20px;
}

.issue-articles h4 {
  font-size: 16px;
  color: #2c3e50;
  margin-bottom: 10px;
}

.articles-list {
  list-style: none;
}

.article-item {
  padding: 8px 0;
  border-bottom: 1px solid #eee;
}

.article-item:last-child {
  border-bottom: none;
}

.article-link {
  color: #3498db;
  text-decoration: none;
  font-weight: 500;
}

.article-link:hover {
  text-decoration: underline;
}

.article-authors {
  display: block;
  font-size: 12px;
  color: #7f8c8d;
  margin-top: 4px;
}

.issue-actions {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.btn {
  display: inline-block;
  padding: 10px 20px;
  border-radius: 4px;
  border: none;
  cursor: pointer;
  font-size: 16px;
  transition: all 0.3s ease;
  text-decoration: none;
  text-align: center;
}

.btn-primary {
  background-color: #2c3e50;
  color: white;
}

.btn-primary:hover {
  background-color: #1a252f;
}

.btn-outline {
  background-color: transparent;
  border: 1px solid #2c3e50;
  color: #2c3e50;
}

.btn-outline:hover {
  background-color: #2c3e50;
  color: white;
}

.special-issues-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
  gap: 25px;
}

.special-issue-card {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 25px;
  transition: all 0.3s ease;
}

.special-issue-card:hover {
  border-color: #2c3e50;
  box-shadow: 0 5px 20px rgba(0,0,0,0.08);
}

.special-issue-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 15px;
  gap: 15px;
}

.special-issue-header h3 {
  font-size: 22px;
  color: #2c3e50;
  flex: 1;
}

.special-issue-status {
  padding: 5px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
}

.special-issue-status.open {
  background-color: #e8f8f0;
  color: #27ae60;
}

.special-issue-status.in-progress {
  background-color: #fef5e7;
  color: #f39c12;
}

.special-issue-status.published {
  background-color: #e8f4fd;
  color: #3498db;
}

.special-issue-description {
  color: #555;
  line-height: 1.6;
  margin-bottom: 20px;
}

.special-issue-details {
  margin-bottom: 20px;
}

.detail-item {
  font-size: 14px;
  color: #7f8c8d;
  margin-bottom: 8px;
}

.detail-item strong {
  color: #2c3e50;
  margin-right: 5px;
}

.special-issue-keywords {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 20px;
}

.special-issue-keywords .keyword {
  background-color: #f8f9fa;
  padding: 4px 10px;
  border-radius: 15px;
  font-size: 12px;
  color: #2c3e50;
}

.special-issue-actions {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.archive-controls {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 25px;
  flex-wrap: wrap;
  gap: 15px;
}

.search-archive {
  flex: 1;
  min-width: 300px;
}

.archive-search-input {
  width: 100%;
  padding: 12px 20px;
  border: 2px solid #e0e0e0;
  border-radius: 8px;
  font-size: 16px;
}

.archive-search-input:focus {
  outline: none;
  border-color: #2c3e50;
}

.archive-filters {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.archive-filter {
  padding: 10px 15px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  background: white;
  color: #2c3e50;
  min-width: 150px;
}

.archive-table {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  overflow: hidden;
  margin-bottom: 25px;
}

.table-header {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 0.8fr 0.8fr 1fr;
  background-color: #2c3e50;
  color: white;
  padding: 15px 20px;
  font-weight: 600;
}

.table-col {
  padding: 0 10px;
}

.table-body {
  max-height: 500px;
  overflow-y: auto;
}

.table-row {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 0.8fr 0.8fr 1fr;
  padding: 15px 20px;
  border-bottom: 1px solid #e0e0e0;
  align-items: center;
}

.table-row:last-child {
  border-bottom: none;
}

.table-row:hover {
  background-color: #f8f9fa;
}

.issue-link {
  color: #3498db;
  text-decoration: none;
  font-weight: 500;
}

.issue-link:hover {
  text-decoration: underline;
}

.special-tag {
  display: inline-block;
  background-color: #f4ecf7;
  color: #8e44ad;
  padding: 2px 8px;
  border-radius: 12px;
  font-size: 11px;
  margin-top: 5px;
}

.type-badge {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
}

.type-badge.regular {
  background-color: #e8f4fd;
  color: #3498db;
}

.type-badge.special {
  background-color: #f4ecf7;
  color: #8e44ad;
}

.action-buttons {
  display: flex;
  gap: 8px;
}

.action-btn {
  padding: 6px 12px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  background: white;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s ease;
}

.view-btn:hover {
  background-color: #e8f4fd;
  border-color: #3498db;
  color: #3498db;
}

.download-btn:hover {
  background-color: #e8f8f0;
  border-color: #27ae60;
  color: #27ae60;
}

.no-results {
  text-align: center;
  padding: 40px;
  color: #7f8c8d;
  font-size: 16px;
  grid-column: 1 / -1;
}

.archive-pagination {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 20px;
  border-top: 1px solid #e0e0e0;
}

.pagination-btn {
  padding: 8px 16px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  background: white;
  cursor: pointer;
  transition: all 0.3s ease;
}

.pagination-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.pagination-btn:not(:disabled):hover {
  background-color: #2c3e50;
  color: white;
  border-color: #2c3e50;
}

.page-info {
  color: #7f8c8d;
  font-size: 14px;
}

.metrics-section {
  margin-bottom: 40px;
}

.metrics-section h2 {
  text-align: center;
  font-size: 32px;
  color: #2c3e50;
  margin-bottom: 30px;
}

.metrics-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
}

.metric-card {
  background: white;
  padding: 30px 20px;
  border-radius: 10px;
  text-align: center;
  box-shadow: 0 3px 15px rgba(0,0,0,0.05);
}

.metric-value {
  font-size: 48px;
  font-weight: 700;
  color: #2c3e50;
  margin-bottom: 10px;
}

.metric-label {
  color: #7f8c8d;
  font-size: 16px;
  margin-bottom: 10px;
}

.metric-trend {
  font-size: 14px;
  font-weight: 500;
}

.trend-up {
  color: #27ae60;
}

.trend-down {
  color: #e74c3c;
}

.top-downloads-section {
  background: white;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 2px 15px rgba(0,0,0,0.05);
}

.top-downloads-section h2 {
  font-size: 32px;
  color: #2c3e50;
  margin-bottom: 30px;
  text-align: center;
}

.downloads-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.download-item {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.download-item:hover {
  border-color: #2c3e50;
  background-color: #f8f9fa;
}

.download-rank {
  font-size: 24px;
  font-weight: 700;
  color: #2c3e50;
  min-width: 40px;
  text-align: center;
}

.download-info {
  flex: 1;
}

.article-title {
  color: #2c3e50;
  text-decoration: none;
  font-size: 18px;
  font-weight: 500;
}

.article-title:hover {
  text-decoration: underline;
}

.article-meta {
  color: #7f8c8d;
  font-size: 14px;
  margin-top: 5px;
}

.download-stats {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 5px;
}

.download-count {
  font-size: 20px;
  font-weight: 600;
  color: #2c3e50;
}

.download-trend {
  font-size: 14px;
  font-weight: 500;
}

@media (max-width: 1024px) {
  .table-header,
  .table-row {
    grid-template-columns: repeat(6, 1fr);
    font-size: 14px;
  }
  
  .table-col {
    word-break: break-word;
  }
}

@media (max-width: 768px) {
  .header-tabs {
    flex-direction: column;
    border-bottom: none;
  }
  
  .tab-btn {
    border-bottom: none;
    border-left: 3px solid transparent;
    text-align: left;
  }
  
  .tab-btn.active {
    border-left-color: #2c3e50;
    border-bottom: none;
  }
  
  .issues-grid,
  .special-issues-grid {
    grid-template-columns: 1fr;
  }
  
  .archive-controls {
    flex-direction: column;
    align-items: stretch;
  }
  
  .search-archive {
    min-width: 100%;
  }
  
  .archive-filters {
    flex-direction: column;
  }
  
  .table-header {
    display: none;
  }
  
  .table-row {
    grid-template-columns: 1fr;
    gap: 10px;
  }
  
  .table-col {
    display: flex;
    justify-content: space-between;
    padding: 5px 0;
  }
  
  .table-col::before {
    content: attr(data-label);
    font-weight: 600;
    color: #2c3e50;
  }
  
  .archive-pagination {
    flex-direction: column;
    gap: 15px;
  }
  
  .download-item {
    flex-direction: column;
    text-align: center;
    gap: 15px;
  }
  
  .download-stats {
    align-items: center;
  }
}

/* 在所有Vue文件的样式部分添加或更新 */

/* 大幅减少页面两侧空白 - 通用调整 */
.container {
  max-width: 1600px !important;  /* 增加最大宽度 */
  width: 98% !important;         /* 增加宽度百分比 */
  margin: 0 auto !important;
  padding: 0 10px !important;    /* 减少内边距 */
}

/* 特定页面的容器调整 */
.home-page .container,
.papers-page .container,
.publications-page .container,
.guide-page .container,
.submit-page .container,
.journal-page .container {
  max-width: 1700px !important;  /* 进一步增加最大宽度 */
  width: 99% !important;         /* 几乎占满宽度 */
  padding: 0 15px !important;    /* 非常小的两侧边距 */
}

/* 内容卡片进一步加宽 */
.step-content,
.tab-content,
.guide-section,
.info-card,
.paper-card,
.issue-card,
.special-issue-card,
.article-type-card {
  max-width: 100% !important;
  margin: 0 auto 15px !important;
  padding: 15px 20px !important;  /* 减少内边距 */
}

/* 网格布局更紧凑 */
.papers-grid,
.issues-grid,
.info-grid,
.policies-grid,
.publications-grid,
.news-grid,
.preparation-grid,
.guidelines-grid,
.benefits-grid {
  gap: 15px !important;           /* 减少间隙 */
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr)) !important; /* 更小最小宽度 */
}

/* 提交页面特别紧凑 */
.submission-wizard {
  padding: 15px !important;
  margin: 0 auto 15px !important;
}

.step-content {
  padding: 20px !important;
  margin: 0 auto 15px !important;
}

.form-grid {
  gap: 12px !important;           /* 减少表单间隙 */
}

/* 表格内容紧凑 */
.archive-table .table-header,
.archive-table .table-row {
  padding: 8px 12px !important;   /* 减少表格内边距 */
}

/* 英雄区域占满宽度 */
.hero-section {
  padding: 60px 0 !important;     /* 减少垂直内边距 */
}

.hero-section .container {
  max-width: 100% !important;
  width: 100% !important;
  padding: 0 10px !important;
}

.hero-content {
  max-width: 1400px !important;
  margin: 0 auto !important;
  padding: 0 15px !important;
}

/* 论文列表更紧凑 */
.paper-card {
  padding: 15px !important;       /* 减少卡片内边距 */
  margin: 0 0 12px 0 !important;  /* 减少卡片间距 */
}

.paper-title {
  font-size: 18px !important;     /* 稍小字体 */
  line-height: 1.3 !important;
}

/* 期刊信息页面紧凑 */
.journal-hero {
  padding: 30px 0 !important;     /* 减少内边距 */
}

.info-card {
  padding: 20px !important;       /* 减少内边距 */
}

/* 用户指南页面紧凑 */
.nav-cards {
  gap: 12px !important;
  grid-template-columns: repeat(auto-fit, minmax(230px, 1fr)) !important;
}

.guide-section {
  padding: 25px !important;       /* 减少内边距 */
}

/* 筛选控件紧凑 */
.filter-bar {
  gap: 15px !important;
}

.filter-controls {
  gap: 8px !important;
}

.filter-select {
  min-width: 140px !important;    /* 减少最小宽度 */
  padding: 8px 12px !important;   /* 减少内边距 */
}

/* 分页紧凑 */
.pagination {
  gap: 15px !important;
  margin-top: 20px !important;
  padding-top: 15px !important;
}

.page-numbers {
  gap: 3px !important;
}

.page-number {
  padding: 6px 10px !important;
}

/* 统计卡片紧凑 */
.stats-cards {
  gap: 15px !important;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr)) !important;
}

.stat-card {
  padding: 20px 15px !important;  /* 减少内边距 */
}

/* 登录页面紧凑 */
.login-container {
  max-width: 400px !important;
  width: 96% !important;
  margin: 10px auto !important;
}

.login-content {
  padding: 20px !important;       /* 减少内边距 */
}

/* 响应式调整 - 移动端 */
@media (max-width: 768px) {
  .container {
    width: 100% !important;
    padding: 0 8px !important;    /* 移动端更小边距 */
  }
  
  .step-content,
  .guide-section,
  .paper-card,
  .issue-card {
    padding: 12px !important;     /* 移动端更小内边距 */
    margin: 0 auto 10px !important;
  }
  
  .papers-grid,
  .issues-grid,
  .info-grid {
    grid-template-columns: 1fr !important;
    gap: 10px !important;         /* 移动端更小间隙 */
  }
  
  .hero-section {
    padding: 40px 0 !important;
  }
  
  .hero-title {
    font-size: 28px !important;
  }
  
  .hero-subtitle {
    font-size: 16px !important;
  }
}

/* 大屏幕进一步利用空间 */
@media (min-width: 1920px) {
  .container {
    max-width: 1800px !important;
    width: 98% !important;
    padding: 0 20px !important;
  }
  
  .home-page .container,
  .papers-page .container {
    max-width: 1900px !important;
    width: 99% !important;
  }
  
  .papers-grid,
  .issues-grid {
    grid-template-columns: repeat(auto-fit, minmax(320px, 1fr)) !important;
    gap: 20px !important;
  }
}

/* 极小屏幕适配 */
@media (max-width: 480px) {
  .container {
    padding: 0 5px !important;    /* 极小屏幕几乎无空白 */
  }
  
  .step-content,
  .paper-card {
    padding: 10px !important;
  }
  
  .form-grid,
  .form-row {
    gap: 8px !important;
  }
}

/* 按钮紧凑 */
.btn-small {
  padding: 5px 10px !important;
  font-size: 13px !important;
}

.btn {
  padding: 8px 16px !important;
  font-size: 14px !important;
}

.btn-large {
  padding: 10px 20px !important;
  font-size: 16px !important;
}
</style>