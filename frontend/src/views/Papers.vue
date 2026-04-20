<template>
  <main class="papers-page">
    <div class="container">
      <!-- 页面标题 -->
      <section class="page-header">
        <h1>研究论文</h1>
        <p class="subtitle">浏览《国际人工智能研究》期刊的最新出版物</p>
        <div class="header-actions">
          <router-link to="/submit" class="btn btn-primary">提交您的论文</router-link>
          <a href="#" class="btn btn-outline">下载作者指南</a>
        </div>
      </section>

      <!-- 筛选部分 -->
      <section class="filter-section">
        <div class="filter-bar">
          <div class="search-box">
            <input 
              type="text" 
              v-model="searchQuery" 
              placeholder="通过标题、作者或关键词搜索论文..."
              @input="filterPapers"
            />
            <span class="search-icon">🔍</span>
          </div>
          
          <div class="filter-controls">
            <select v-model="selectedCategory" @change="filterPapers" class="filter-select">
              <option value="">所有类别</option>
              <option value="research">研究论文</option>
              <option value="review">综述论文</option>
              <option value="survey">调查报告</option>
              <option value="application">应用论文</option>
            </select>
            
            <select v-model="selectedYear" @change="filterPapers" class="filter-select">
              <option value="">所有年份</option>
              <option v-for="year in years" :key="year" :value="year">{{ year }}</option>
            </select>
            
            <button @click="resetFilters" class="btn btn-outline">重置筛选</button>
          </div>
        </div>
      </section>

      <!-- 论文网格 -->
      <section class="papers-section">
        <div class="papers-header">
          <h2>最新出版物</h2>
          <div class="sort-controls">
            <span>排序方式:</span>
            <select v-model="sortBy" @change="sortPapers" class="sort-select">
              <option value="date">出版日期</option>
              <option value="downloads">下载最多</option>
              <option value="citations">引用最多</option>
              <option value="title">标题(A-Z)</option>
            </select>
          </div>
        </div>

        <div v-if="filteredPapers.length === 0" class="no-results">
          <p>未找到符合筛选条件的论文。</p>
        </div>

        <div v-else class="papers-grid">
          <div v-for="paper in filteredPapers" :key="paper.id" class="paper-card">
            <div class="paper-header">
              <div class="paper-badge" :class="paper.category">
                {{ getCategoryLabel(paper.category) }}
              </div>
              <div class="paper-stats">
                <span class="stat-item">📥 {{ paper.downloads }}</span>
                <span class="stat-item">📚 {{ paper.citations }}</span>
              </div>
            </div>
            
            <h3 class="paper-title">{{ paper.title }}</h3>
            <p class="paper-authors">{{ paper.authors }}</p>
            
            <div class="paper-abstract">
              <p>{{ paper.abstract.substring(0, 200) }}...</p>
            </div>
            
            <div class="paper-meta">
              <span class="meta-item">
                <strong>发表时间:</strong> {{ formatDate(paper.publishedDate) }}
              </span>
              <span class="meta-item">
                <strong>DOI:</strong> {{ paper.doi }}
              </span>
              <span class="meta-item">
                <strong>卷号:</strong> {{ paper.volume }}({{ paper.issue }})
              </span>
            </div>
            
            <div class="paper-keywords">
              <span v-for="keyword in paper.keywords.slice(0, 3)" :key="keyword" class="keyword">
                {{ keyword }}
              </span>
              <span v-if="paper.keywords.length > 3" class="more-keywords">
                +{{ paper.keywords.length - 3 }} 更多
              </span>
            </div>
            
            <div class="paper-actions">
              <button @click="downloadPaper(paper)" class="btn btn-outline btn-small">
                下载PDF
              </button>
              <button @click="viewAbstract(paper)" class="btn btn-outline btn-small">
                查看摘要
              </button>
              <router-link :to="`/paper/${paper.id}`" class="btn btn-primary btn-small">
                阅读全文
              </router-link>
            </div>
          </div>
        </div>

        <!-- 分页 -->
        <div v-if="filteredPapers.length > 0" class="pagination">
          <button 
            @click="prevPage" 
            :disabled="currentPage === 1" 
            class="pagination-btn"
          >
            上一页
          </button>
          
          <div class="page-numbers">
            <span 
              v-for="page in totalPages" 
              :key="page"
              :class="{ 'active': page === currentPage }"
              @click="goToPage(page)"
              class="page-number"
            >
              {{ page }}
            </span>
          </div>
          
          <button 
            @click="nextPage" 
            :disabled="currentPage === totalPages" 
            class="pagination-btn"
          >
            下一页
          </button>
        </div>
      </section>

      <!-- 统计部分 -->
      <section class="stats-section">
        <h2>期刊统计</h2>
        <div class="stats-cards">
          <div class="stat-card">
            <div class="stat-icon">📈</div>
            <div class="stat-value">{{ totalPapers }}</div>
            <div class="stat-label">已发表论文总数</div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">📥</div>
            <div class="stat-value">{{ formatNumber(totalDownloads) }}</div>
            <div class="stat-label">总下载量</div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">🌍</div>
            <div class="stat-value">{{ countries }}</div>
            <div class="stat-label">国家地区</div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">👥</div>
            <div class="stat-value">{{ formatNumber(totalAuthors) }}</div>
            <div class="stat-label">作者总数</div>
          </div>
        </div>
      </section>

      <!-- 最受欢迎的论文 -->
      <section class="popular-section">
        <h2>最受欢迎的论文</h2>
        <div class="popular-list">
          <div v-for="paper in popularPapers" :key="paper.id" class="popular-item">
            <div class="popular-rank">{{ paper.rank }}</div>
            <div class="popular-content">
              <h3>{{ paper.title }}</h3>
              <p class="popular-authors">{{ paper.authors }}</p>
              <div class="popular-stats">
                <span>📥 {{ paper.downloads }} 次下载</span>
                <span>📚 {{ paper.citations }} 次引用</span>
              </div>
            </div>
            <button @click="downloadPaper(paper)" class="btn btn-outline btn-small">
              下载
            </button>
          </div>
        </div>
      </section>
    </div>
  </main>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'

// 模拟论文数据
const papers = ref([
  {
    id: 1,
    title: '多模态学习的Transformer模型：全面综述',
    authors: '张, L.; 王, Q.; 陈, Y. 等.',
    abstract: '本文全面综述了用于多模态学习任务的Transformer架构，包括视觉语言模型、语音识别和多模态融合技术。',
    category: 'survey',
    publishedDate: '2024-11-15',
    downloads: 1243,
    citations: 45,
    doi: '10.1234/iair.2024.001',
    volume: '15',
    issue: '3',
    keywords: ['Transformer', '多模态学习', '深度学习', '计算机视觉', '自然语言处理']
  },
  {
    id: 2,
    title: '面向医疗应用的差分隐私联邦学习',
    authors: 'Smith, J.; Johnson, R.; Brown, A.',
    abstract: '我们提出了一种专门为敏感医疗数据应用设计的新型差分隐私联邦学习框架。',
    category: 'research',
    publishedDate: '2024-11-10',
    downloads: 987,
    citations: 32,
    doi: '10.1234/iair.2024.002',
    volume: '15',
    issue: '3',
    keywords: ['联邦学习', '差分隐私', '医疗健康', '机器学习']
  },
  {
    id: 3,
    title: '用于金融欺诈检测的可解释人工智能',
    authors: '金, S.; 李, H.; 朴, J.',
    abstract: '一种用于检测金融欺诈交易的可解释机器学习方法。',
    category: 'application',
    publishedDate: '2024-11-05',
    downloads: 856,
    citations: 28,
    doi: '10.1234/iair.2024.003',
    volume: '15',
    issue: '3',
    keywords: ['可解释人工智能', '欺诈检测', '金融机器学习', '可解释性']
  },
  {
    id: 4,
    title: '量子机器学习：算法与应用',
    authors: 'Patel, R.; Gupta, S.; Kumar, M.',
    abstract: '本文探讨了量子计算与机器学习的交叉领域，讨论了量子算法及其潜在应用。',
    category: 'review',
    publishedDate: '2024-10-28',
    downloads: 1120,
    citations: 52,
    doi: '10.1234/iair.2024.004',
    volume: '15',
    issue: '2',
    keywords: ['量子计算', '机器学习', '量子算法', '量子机器学习']
  },
  {
    id: 5,
    title: '用于医学图像分析的自监督学习',
    authors: '陈, W.; 刘, X.; 张, Y.',
    abstract: '一种减少对标记数据依赖的医学图像分析新型自监督学习框架。',
    category: 'research',
    publishedDate: '2024-10-20',
    downloads: 743,
    citations: 31,
    doi: '10.1234/iair.2024.005',
    volume: '15',
    issue: '2',
    keywords: ['自监督学习', '医学影像', '计算机视觉', '医疗人工智能']
  }
])

// 筛选和排序
const searchQuery = ref('')
const selectedCategory = ref('')
const selectedYear = ref('')
const sortBy = ref('date')
const currentPage = ref(1)
const papersPerPage = 5

const years = computed(() => {
  const uniqueYears = [...new Set(papers.value.map(p => p.publishedDate.split('-')[0]))]
  return uniqueYears.sort((a, b) => b - a)
})

const filteredPapers = computed(() => {
  let filtered = papers.value
  
  // 应用搜索筛选
  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase()
    filtered = filtered.filter(paper => 
      paper.title.toLowerCase().includes(query) ||
      paper.authors.toLowerCase().includes(query) ||
      paper.keywords.some(kw => kw.toLowerCase().includes(query))
    )
  }
  
  // 应用类别筛选
  if (selectedCategory.value) {
    filtered = filtered.filter(paper => paper.category === selectedCategory.value)
  }
  
  // 应用年份筛选
  if (selectedYear.value) {
    filtered = filtered.filter(paper => 
      paper.publishedDate.startsWith(selectedYear.value)
    )
  }
  
  // 应用排序
  filtered = [...filtered].sort((a, b) => {
    switch (sortBy.value) {
      case 'date':
        return new Date(b.publishedDate) - new Date(a.publishedDate)
      case 'downloads':
        return b.downloads - a.downloads
      case 'citations':
        return b.citations - a.citations
      case 'title':
        return a.title.localeCompare(b.title)
      default:
        return 0
    }
  })
  
  return filtered
})

const totalPages = computed(() => {
  return Math.ceil(filteredPapers.value.length / papersPerPage)
})

const paginatedPapers = computed(() => {
  const start = (currentPage.value - 1) * papersPerPage
  const end = start + papersPerPage
  return filteredPapers.value.slice(start, end)
})

// 统计
const totalPapers = computed(() => papers.value.length)
const totalDownloads = computed(() => 
  papers.value.reduce((sum, paper) => sum + paper.downloads, 0)
)
const totalAuthors = computed(() => {
  const authors = new Set()
  papers.value.forEach(paper => {
    paper.authors.split(';').forEach(author => {
      authors.add(author.trim())
    })
  })
  return authors.size
})
const countries = computed(() => 45) // 模拟数据

// 最受欢迎的论文
const popularPapers = computed(() => {
  return [...papers.value]
    .sort((a, b) => b.downloads - a.downloads)
    .slice(0, 5)
    .map((paper, index) => ({
      ...paper,
      rank: index + 1
    }))
})

// 方法
const filterPapers = () => {
  currentPage.value = 1
}

const resetFilters = () => {
  searchQuery.value = ''
  selectedCategory.value = ''
  selectedYear.value = ''
  sortBy.value = 'date'
  filterPapers()
}

const getCategoryLabel = (category) => {
  const labels = {
    research: '研究论文',
    review: '综述论文',
    survey: '调查报告',
    application: '应用论文'
  }
  return labels[category] || category
}

const formatDate = (dateString) => {
  return new Date(dateString).toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

const formatNumber = (num) => {
  if (num >= 1000000) return (num / 1000000).toFixed(1) + '百万'
  if (num >= 1000) return (num / 1000).toFixed(1) + '千'
  return num.toString()
}

const downloadPaper = (paper) => {
  alert(`正在下载: ${paper.title}`)
  // 实际实现中，这里会触发文件下载
}

const viewAbstract = (paper) => {
  alert(`摘要: ${paper.abstract}`)
}

// 分页方法
const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
  }
}

const goToPage = (page) => {
  currentPage.value = page
}
</script>

<!-- 样式保持不变 -->
<style scoped>
.papers-page {
  padding: 40px 0 60px;
  background-color: #f8f9fa;
}

.page-header {
  text-align: center;
  margin-bottom: 40px;
  padding-bottom: 30px;
  border-bottom: 1px solid #e0e0e0;
}

.page-header h1 {
  font-size: 42px;
  color: #2c3e50;
  margin-bottom: 15px;
}

.subtitle {
  font-size: 18px;
  color: #7f8c8d;
  margin-bottom: 25px;
}

.header-actions {
  display: flex;
  gap: 15px;
  justify-content: center;
  flex-wrap: wrap;
}

.filter-section {
  background: white;
  padding: 25px;
  border-radius: 10px;
  box-shadow: 0 2px 15px rgba(0,0,0,0.05);
  margin-bottom: 30px;
}

.filter-bar {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.search-box {
  position: relative;
  max-width: 600px;
  margin: 0 auto;
}

.search-box input {
  width: 100%;
  padding: 15px 20px 15px 50px;
  border: 2px solid #e0e0e0;
  border-radius: 8px;
  font-size: 16px;
  transition: border-color 0.3s ease;
}

.search-box input:focus {
  outline: none;
  border-color: #2c3e50;
}

.search-icon {
  position: absolute;
  left: 20px;
  top: 50%;
  transform: translateY(-50%);
  color: #7f8c8d;
}

.filter-controls {
  display: flex;
  gap: 15px;
  justify-content: center;
  flex-wrap: wrap;
}

.filter-select {
  padding: 10px 15px;
  border: 2px solid #e0e0e0;
  border-radius: 6px;
  background: white;
  color: #2c3e50;
  font-size: 14px;
  min-width: 180px;
}

.papers-section {
  background: white;
  padding: 30px;
  border-radius: 10px;
  margin-bottom: 40px;
  box-shadow: 0 2px 15px rgba(0,0,0,0.05);
}

.papers-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  flex-wrap: wrap;
  gap: 15px;
}

.papers-header h2 {
  font-size: 28px;
  color: #2c3e50;
}

.sort-controls {
  display: flex;
  align-items: center;
  gap: 10px;
}

.sort-select {
  padding: 8px 12px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  background: white;
}

.no-results {
  text-align: center;
  padding: 50px;
  color: #7f8c8d;
  font-size: 18px;
}

.papers-grid {
  display: flex;
  flex-direction: column;
  gap: 25px;
}

.paper-card {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 25px;
  transition: all 0.3s ease;
  background: white;
}

.paper-card:hover {
  border-color: #2c3e50;
  box-shadow: 0 5px 20px rgba(0,0,0,0.08);
  transform: translateY(-2px);
}

.paper-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.paper-badge {
  padding: 5px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
  text-transform: uppercase;
}

.paper-badge.research {
  background-color: #e8f4fd;
  color: #3498db;
}

.paper-badge.review {
  background-color: #e8f8f0;
  color: #27ae60;
}

.paper-badge.survey {
  background-color: #fef5e7;
  color: #f39c12;
}

.paper-badge.application {
  background-color: #f4ecf7;
  color: #8e44ad;
}

.paper-stats {
  display: flex;
  gap: 15px;
}

.stat-item {
  font-size: 14px;
  color: #7f8c8d;
}

.paper-title {
  font-size: 22px;
  color: #2c3e50;
  margin-bottom: 10px;
  line-height: 1.3;
}

.paper-authors {
  color: #3498db;
  font-size: 15px;
  margin-bottom: 15px;
  font-style: italic;
}

.paper-abstract {
  color: #555;
  margin-bottom: 20px;
  line-height: 1.6;
}

.paper-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
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

.paper-keywords {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 20px;
}

.keyword {
  background-color: #f8f9fa;
  padding: 4px 10px;
  border-radius: 15px;
  font-size: 12px;
  color: #2c3e50;
}

.more-keywords {
  font-size: 12px;
  color: #7f8c8d;
  align-self: center;
}

.paper-actions {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.btn-small {
  padding: 6px 12px;
  font-size: 14px;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px;
  margin-top: 40px;
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

.pagination-btn:hover:not(:disabled) {
  border-color: #2c3e50;
  background-color: #f8f9fa;
}

.pagination-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.page-numbers {
  display: flex;
  gap: 5px;
}

.page-number {
  padding: 8px 12px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.page-number:hover {
  background-color: #f8f9fa;
}

.page-number.active {
  background-color: #2c3e50;
  color: white;
  border-color: #2c3e50;
}

.stats-section {
  margin-bottom: 40px;
}

.stats-section h2 {
  text-align: center;
  font-size: 32px;
  color: #2c3e50;
  margin-bottom: 30px;
}

.stats-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
}

.stat-card {
  background: white;
  padding: 30px 20px;
  border-radius: 10px;
  text-align: center;
  box-shadow: 0 3px 15px rgba(0,0,0,0.05);
  transition: transform 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-5px);
}

.stat-icon {
  font-size: 36px;
  margin-bottom: 15px;
}

.stat-value {
  font-size: 36px;
  font-weight: 700;
  color: #2c3e50;
  margin-bottom: 10px;
}

.stat-label {
  color: #7f8c8d;
  font-size: 14px;
}

.popular-section {
  background: white;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 2px 15px rgba(0,0,0,0.05);
}

.popular-section h2 {
  font-size: 32px;
  color: #2c3e50;
  margin-bottom: 30px;
  text-align: center;
}

.popular-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.popular-item {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.popular-item:hover {
  border-color: #2c3e50;
  background-color: #f8f9fa;
}

.popular-rank {
  font-size: 24px;
  font-weight: 700;
  color: #2c3e50;
  min-width: 40px;
}

.popular-content {
  flex: 1;
}

.popular-content h3 {
  font-size: 18px;
  color: #2c3e50;
  margin-bottom: 8px;
}

.popular-authors {
  color: #3498db;
  font-size: 14px;
  margin-bottom: 10px;
}

.popular-stats {
  display: flex;
  gap: 20px;
  font-size: 14px;
  color: #7f8c8d;
}

@media (max-width: 768px) {
  .papers-header {
    flex-direction: column;
    align-items: flex-start;
  }
  
  .paper-actions {
    flex-direction: column;
  }
  
  .popular-item {
    flex-direction: column;
    text-align: center;
    gap: 15px;
  }
  
  .stats-cards {
    grid-template-columns: 1fr;
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