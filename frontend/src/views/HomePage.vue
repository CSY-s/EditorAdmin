<template>
  <div class="home-page">
    <!-- 英雄区域 -->
    <section class="hero-section">
      <div class="container">
        <div class="hero-content">
          <h1 class="hero-title">推动全球人工智能研究</h1>
          <p class="hero-subtitle">开源期刊出版人工智能领域前沿研究成果</p>
          <div class="hero-buttons">
            <router-link to="/submit" class="btn btn-primary btn-large">提交您的研究</router-link>
            <router-link to="/papers" class="btn btn-outline btn-large">浏览论文</router-link>
          </div>
        </div>
      </div>
    </section>

    <!-- 期刊统计 -->
    <section class="stats-section">
      <div class="container">
        <div class="stats-grid">
          <div class="stat-item">
            <h3>4.5</h3>
            <p>影响因子</p>
          </div>
          <div class="stat-item">
            <h3>15 天</h3>
            <p>平均初审时间</p>
          </div>
          <div class="stat-item">
            <h3>100%</h3>
            <p>开源访问</p>
          </div>
          <div class="stat-item">
            <h3>2 周</h3>
            <p>在线出版时间</p>
          </div>
        </div>
      </div>
    </section>

    <!-- 编委介绍 -->
    <section style="padding: 80px 0; background-color: #f8f9fa;">
      <div style="max-width: 1200px; margin: 0 auto; padding: 0 15px;">
        <h2 style="font-size: 2.5rem; color: #2c3e50; margin-bottom: 40px; text-align: center; position: relative;">
          期刊编委
          <div style="position: absolute; bottom: -10px; left: 50%; transform: translateX(-50%); width: 80px; height: 4px; background-color: #3498db;"></div>
        </h2>
        
        <!-- 编委网格 -->
        <div style="display: flex; flex-wrap: wrap; gap: 20px; justify-content: center;">
          <div 
            v-for="editor in editors" 
            :key="editor.id"
            style="flex: 0 0 calc(25% - 20px); max-width: calc(25% - 20px); min-width: 220px;"
          >
            <div style="
              background: white;
              border-radius: 10px;
              overflow: hidden;
              box-shadow: 0 5px 15px rgba(0,0,0,0.08);
              transition: all 0.3s;
              height: 100%;
            "
            @mouseenter="e => e.currentTarget.style.transform = 'translateY(-10px)'"
            @mouseleave="e => e.currentTarget.style.transform = 'translateY(0)'"
            >
              <img 
                :src="editor.image" 
                :alt="editor.name"
                style="width: 100%; height: 180px; object-fit: cover;"
              >
              <div style="padding: 20px;">
                <h4 style="margin: 0 0 8px 0; color: #2c3e50; font-size: 1.1rem;">{{ editor.name }}</h4>
                <p style="color: #666; margin: 0 0 10px 0; font-size: 0.9rem; font-weight: 500;">{{ editor.position }}</p>
                <p style="color: #777; font-size: 0.85rem; line-height: 1.4; margin: 0;">{{ editor.description }}</p>
              </div>
            </div>
          </div>
        </div>
        
        <!-- 按钮 -->
        <div style="text-align: center; margin-top: 40px;">
          <a 
            href="/front/about"
            style="
              display: inline-block;
              padding: 12px 30px;
              background: #3498db;
              color: white;
              text-decoration: none;
              border-radius: 5px;
              font-weight: 500;
              transition: all 0.3s;
            "
            @mouseenter="e => e.currentTarget.style.background = '#2980b9'"
            @mouseleave="e => e.currentTarget.style.background = '#3498db'"
          >
            <i class="fas fa-users" style="margin-right: 8px;"></i>查看所有编委
          </a>
        </div>
      </div>
    </section>

    <!-- 论文列表 -->
    <section id="papers" class="papers-section">
      <div class="container">
        <div class="section-header">
          <h2 class="section-title">
            <i class="fas fa-file-alt me-3"></i>
            最新研究论文
          </h2>
          <p class="section-subtitle">探索人工智能领域前沿研究成果</p>
        </div>
        
        <!-- 论文分类标签 -->
        <div class="paper-tabs">
          <button 
            v-for="tab in paperTabs" 
            :key="tab.id"
            class="paper-tab" 
            :class="{ active: activePaperTab === tab.id }"
            @click="switchPaperTab(tab.id)"
          >
            <i :class="tab.icon" class="me-2"></i>
            {{ tab.label }}
            <span class="tab-badge">{{ getPaperCount(tab.id) }}</span>
          </button>
        </div>
        
        <!-- 搜索框 -->
        <div class="search-container">
          <div class="search-box">
            <i class="fas fa-search search-icon"></i>
            <input 
              type="text" 
              v-model="paperSearch" 
              placeholder="搜索论文标题、作者、关键词..."
              class="search-input"
              @keyup.enter="handleSearch"
            >
            <button v-if="paperSearch" class="clear-search" @click="paperSearch = ''">
              <i class="fas fa-times"></i>
            </button>
          </div>
        </div>
        
        <!-- 论文网格 -->
        <div class="paper-grid">
          <transition-group name="fade-list" tag="div" class="paper-grid-content">
            <article 
              v-for="paper in filteredPapers" 
              :key="paper.id" 
              class="paper-card"
              :class="{
                'featured': paper.isFeatured,
                'hot': paper.citationCount > 100,
                'open-access': paper.isOpenAccess
              }"
            >
              <!-- 论文头部 -->
              <header class="paper-header">
                <div class="paper-meta">
                  <span class="paper-category" :style="getCategoryStyle(paper.category)">
                    {{ getCategoryText(paper.category) }}
                  </span>
                  <div class="paper-badges">
                    <span v-if="paper.isOpenAccess" class="badge badge-oa" title="开放获取">
                      <i class="fas fa-lock-open"></i> OA
                    </span>
                    <span v-if="paper.isFeatured" class="badge badge-featured" title="特色论文">
                      <i class="fas fa-star"></i>
                    </span>
                  </div>
                </div>
                
                <h3 class="paper-title">
                  <a @click="viewPaper(paper.id)" class="paper-title-link">
                    {{ paper.title }}
                  </a>
                </h3>
                
                <div class="paper-authors">
                  <i class="fas fa-user-circle author-icon"></i>
                  <div class="author-info">
                    <span class="author-names">{{ paper.authorList }}</span>
                    <span class="author-affiliation">{{ paper.authorUnit }}</span>
                  </div>
                </div>
              </header>
              
              <!-- 论文摘要 -->
              <div class="paper-body">
                <p class="paper-abstract">
                  {{ truncateAbstract(paper.abstractText, 200) }}
                </p>
                
                <!-- 关键词 -->
                <div class="paper-keywords" v-if="paper.keywords && paper.keywords.length">
                  <div class="keywords-header">
                    <i class="fas fa-tags"></i>
                    <span>关键词：</span>
                  </div>
                  <div class="keywords-list">
                    <span 
                      v-for="(keyword, index) in paper.keywords.slice(0, 4)" 
                      :key="index"
                      class="keyword"
                      @click="searchKeyword(keyword)"
                    >
                      {{ keyword }}
                    </span>
                    <span v-if="paper.keywords.length > 4" class="keyword-more">
                      +{{ paper.keywords.length - 4 }}
                    </span>
                  </div>
                </div>
              </div>
              
              <!-- 论文指标 -->
              <div class="paper-metrics">
                <div class="metric-item" @click="showMetricDetails('citation', paper)">
                  <div class="metric-icon citation">
                    <i class="fas fa-quote-right"></i>
                  </div>
                  <div class="metric-content">
                    <div class="metric-value">{{ formatNumber(paper.citationCount || 0) }}</div>
                    <div class="metric-label">引用</div>
                  </div>
                </div>
                
                <div class="metric-item" @click="showMetricDetails('download', paper)">
                  <div class="metric-icon download">
                    <i class="fas fa-download"></i>
                  </div>
                  <div class="metric-content">
                    <div class="metric-value">{{ formatNumber(paper.downloadCount || 0) }}</div>
                    <div class="metric-label">下载</div>
                  </div>
                </div>
                
                <div class="metric-item" @click="showMetricDetails('like', paper)">
                  <div class="metric-icon like">
                    <i class="fas fa-heart"></i>
                  </div>
                  <div class="metric-content">
                    <div class="metric-value">{{ formatNumber(paper.likesCount || 0) }}</div>
                    <div class="metric-label">点赞</div>
                  </div>
                </div>
                
                <div class="metric-item" @click="showMetricDetails('view', paper)">
                  <div class="metric-icon view">
                    <i class="fas fa-eye"></i>
                  </div>
                  <div class="metric-content">
                    <div class="metric-value">{{ formatNumber(paper.viewsCount || 0) }}</div>
                    <div class="metric-label">阅读</div>
                  </div>
                </div>
              </div>
              
              <!-- 论文底部 -->
              <footer class="paper-footer">
                <div class="paper-info">
                  <span class="info-item">
                    <i class="far fa-calendar-alt"></i>
                    {{ formatPaperDate(paper.publishDate) }}
                  </span>
                  <span class="info-item" v-if="paper.pages">
                    <i class="fas fa-file-alt"></i>
                    {{ paper.pages }} 页
                  </span>
                  <span class="info-item" v-if="paper.doi">
                    <i class="fas fa-hashtag"></i>
                    {{ paper.doi }}
                  </span>
                </div>
                
                <div class="paper-actions">
                  <button class="btn btn-primary btn-sm" @click="viewPaper(paper.id)">
                    <i class="fas fa-eye"></i>
                    阅读全文
                  </button>
                  <button class="btn btn-outline btn-sm" @click="downloadPaper(paper.id)">
                    <i class="fas fa-download"></i>
                    下载PDF
                  </button>
                  <button 
                    class="btn btn-like btn-sm" 
                    @click="likePaper(paper.id)"
                    :class="{ 'liked': paper.liked }"
                  >
                    <i class="fas fa-heart"></i>
                    {{ paper.likesCount || 0 }}
                  </button>
                </div>
              </footer>
            </article>
          </transition-group>
        </div>
        
        <!-- 空状态 -->
        <div v-if="filteredPapers.length === 0" class="empty-state">
          <div class="empty-icon">
            <i class="fas fa-search"></i>
          </div>
          <h3 class="empty-title">未找到相关论文</h3>
          <p class="empty-description">请尝试其他搜索关键词或选择不同的分类</p>
          <button class="btn btn-outline-primary" @click="clearSearch">
            <i class="fas fa-redo me-2"></i>清空搜索
          </button>
        </div>
        
        <!-- 查看更多 -->
        <div class="text-center mt-5">
          <div class="papers-summary mb-4">
            <span class="summary-item">
              <i class="fas fa-file-alt me-2"></i>
              共 {{ totalPapersCount }} 篇论文
            </span>
            <span class="summary-item">
              <i class="fas fa-quote-right me-2"></i>
              {{ totalCitations }} 引用
            </span>
            <span class="summary-item">
              <i class="fas fa-download me-2"></i>
              {{ totalDownloads }} 下载
            </span>
          </div>
          
          <router-link to="/papers" class="btn btn-primary btn-lg">
            <i class="fas fa-list me-2"></i>浏览所有论文
          </router-link>
          <router-link to="/submit" class="btn btn-outline-primary btn-lg ms-3">
            <i class="fas fa-paper-plane me-2"></i>提交论文
          </router-link>
        </div>
      </div>
    </section>

    <!-- 新闻与公告 -->
  <section class="news-section" style="padding: 80px 0; background-color: #f8f9fa;">
    <div class="container" style="max-width: 1400px; margin: 0 auto; padding: 0 15px;">
      <h2 style="font-size: 2.5rem; color: #2c3e50; margin-bottom: 40px; text-align: center; position: relative;">
        新闻与公告
        <div style="position: absolute; bottom: -10px; left: 50%; transform: translateX(-50%); width: 80px; height: 4px; background-color: #3498db;"></div>
      </h2>
      
      <!-- 新闻分类标签 -->
      <div class="text-center mb-4">
        <button 
          v-for="tab in newsTabs" 
          :key="tab.id"
          class="news-tab" 
          :class="{ active: activeNewsTab === tab.id }"
          @click="switchNewsTab(tab.id)"
        >
          {{ tab.label }}
        </button>
      </div>
      
      <!-- 加载状态 -->
      <div v-if="newsLoading" style="text-align: center; padding: 60px 0;">
        <div style="
          width: 50px;
          height: 50px;
          border: 3px solid #f3f3f3;
          border-top: 3px solid #3498db;
          border-radius: 50%;
          animation: spin 1s linear infinite;
          margin: 0 auto 20px;
        "></div>
        <p style="color: #666;">正在加载数据...</p>
      </div>
      
      <!-- 数据内容 -->
      <div v-else-if="displayData.length > 0" style="display: flex; flex-wrap: wrap; gap: 20px; justify-content: center;">
        <div 
          v-for="item in displayData" 
          :key="item.id"
          style="flex: 0 0 calc(33.333% - 20px); max-width: calc(33.333% - 20px); min-width: 300px;"
        >
          <div style="
            background: white;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
            transition: all 0.3s;
            height: 100%;
            display: flex;
            flex-direction: column;
          "
          @mouseenter="e => e.currentTarget.style.transform = 'translateY(-5px)'"
          @mouseleave="e => e.currentTarget.style.transform = 'translateY(0)'"
          >
            <!-- 新闻类型标签 -->
            <div style="display: flex; justify-content: space-between; align-items: center; padding: 15px 20px 0 20px;">
              <span v-if="activeNewsTab === 'news'" :style="{
                display: 'inline-block',
                padding: '4px 12px',
                borderRadius: '12px',
                fontSize: '12px',
                fontWeight: '600',
                background: getNewsTypeStyle(item.newsType).background,
                color: getNewsTypeStyle(item.newsType).color
              }">
                {{ getNewsTypeText(item.newsType) }}
              </span>
              
              <span v-if="activeNewsTab === 'announcements'" :style="{
                display: 'inline-block',
                padding: '4px 12px',
                borderRadius: '12px',
                fontSize: '12px',
                fontWeight: '600',
                background: getAnnouncementTypeStyle(item.announcementType).background,
                color: getAnnouncementTypeStyle(item.announcementType).color
              }">
                {{ getAnnouncementTypeText(item.announcementType) }}
              </span>
              
              <!-- 如果是新闻，显示是否公告 -->
              <span v-if="activeNewsTab === 'news' && item.isNotice == 1" style="
                display: inline-block;
                padding: 4px 8px;
                border-radius: 12px;
                font-size: 12px;
                font-weight: 600;
                background: #fff3e0;
                color: #ef6c00;
              ">
                <i class="fas fa-bullhorn" style="margin-right: 4px;"></i>公告
              </span>
            </div>
            
            <!-- 内容 -->
            <div style="padding: 15px 20px; flex-grow: 1;">
              <h4 style="
                margin: 0 0 12px 0;
                color: #2c3e50;
                font-size: 1.2rem;
                line-height: 1.4;
                cursor: pointer;
                transition: color 0.2s;
              "
              @mouseenter="e => e.target.style.color = '#3498db'"
              @mouseleave="e => e.target.style.color = '#2c3e50'"
              @click="showItemDetail(item)">
                {{ item.title }}
              </h4>
              
              <!-- 摘要 -->
              <div style="
                color: #666;
                font-size: 0.95rem;
                line-height: 1.5;
                margin-bottom: 15px;
                overflow: hidden;
                display: -webkit-box;
                -webkit-line-clamp: 3;
                -webkit-box-orient: vertical;
              " v-html="getContentSummary(item)"></div>
              
              <!-- 发布时间 -->
              <div style="
                color: #888;
                font-size: 0.85rem;
                display: flex;
                align-items: center;
                gap: 8px;
              ">
                <i class="fas fa-calendar-alt"></i>
                {{ formatDate(item.publishDate) }}
              </div>
            </div>
            
            <!-- 操作按钮 -->
            <div style="
              padding: 15px 20px;
              border-top: 1px solid #eee;
              display: flex;
              gap: 10px;
            ">
              <button 
                style="
                  flex: 1;
                  padding: 8px 16px;
                  background: #3498db;
                  color: white;
                  border: none;
                  border-radius: 5px;
                  cursor: pointer;
                  display: flex;
                  align-items: center;
                  justify-content: center;
                  gap: 8px;
                  transition: all 0.3s;
                "
                @click="showItemDetail(item)"
                @mouseenter="e => e.currentTarget.style.background = '#2980b9'"
                @mouseleave="e => e.currentTarget.style.background = '#3498db'"
              >
                <i class="fas fa-eye"></i>查看详情
              </button>
              
              <button 
                v-if="item.attachmentPath || item.attachment_path"
                style="
                  padding: 8px 16px;
                  background: #f5f5f5;
                  color: #666;
                  border: 1px solid #ddd;
                  border-radius: 5px;
                  cursor: pointer;
                  display: flex;
                  align-items: center;
                  gap: 8px;
                  transition: all 0.3s;
                "
                @click="downloadAttachment(item.attachmentPath || item.attachment_path)"
                @mouseenter="e => { e.currentTarget.style.background = '#e0e0e0'; e.currentTarget.style.color = '#333'; }"
                @mouseleave="e => { e.currentTarget.style.background = '#f5f5f5'; e.currentTarget.style.color = '#666'; }"
              >
                <i class="fas fa-download"></i>
              </button>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 空状态 -->
      <div v-else style="text-align: center; padding: 80px 0;">
        <i class="fas fa-newspaper" style="font-size: 60px; color: #ddd; margin-bottom: 20px;"></i>
        <h4 style="color: #999; margin-bottom: 10px;">暂无数据</h4>
        <p style="color: #aaa;">暂时没有已发布的内容</p>
      </div>
      
      <!-- 查看更多按钮 -->
      <div style="text-align: center; margin-top: 40px;">
        <a 
          v-if="activeNewsTab === 'news'"
          href="/front/"
          style="
            display: inline-block;
            padding: 12px 30px;
            background: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: 500;
            transition: all 0.3s;
          "
          @mouseenter="e => e.currentTarget.style.background = '#2980b9'"
          @mouseleave="e => e.currentTarget.style.background = '#3498db'"
        >
          <i class="fas fa-newspaper" style="margin-right: 8px;"></i>查看所有新闻
        </a>
        <a 
          v-if="activeNewsTab === 'announcements'"
          href="/front/"
          style="
            display: inline-block;
            padding: 12px 30px;
            background: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: 500;
            transition: all 0.3s;
          "
          @mouseenter="e => e.currentTarget.style.background = '#2980b9'"
          @mouseleave="e => e.currentTarget.style.background = '#3498db'"
        >
          <i class="fas fa-bullhorn" style="margin-right: 8px;"></i>查看所有公告
        </a>
      </div>
    </div>
  </section>

  <!-- 详情模态框 -->
  <div 
    v-if="selectedItem" 
    style="
      position: fixed;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background: rgba(0,0,0,0.5);
      display: flex;
      align-items: center;
      justify-content: center;
      z-index: 1000;
      padding: 20px;
      animation: fadeIn 0.3s ease;
    "
    @click="closeItemDetail"
  >
    <div 
      style="
        background: white;
        border-radius: 12px;
        max-width: 800px;
        width: 100%;
        max-height: 90vh;
        overflow-y: auto;
        animation: slideUp 0.3s ease;
      "
      @click.stop
    >
      <!-- 模态框头部 -->
      <div style="
        padding: 25px 30px;
        border-bottom: 1px solid #eee;
        position: sticky;
        top: 0;
        background: white;
        z-index: 10;
        border-radius: 12px 12px 0 0;
        display: flex;
        justify-content: space-between;
        align-items: center;
      ">
        <h2 style="margin: 0; color: #333; font-size: 24px; line-height: 1.4;">{{ selectedItem.title }}</h2>
        <button 
          @click="closeItemDetail"
          style="
            background: none;
            border: none;
            font-size: 24px;
            color: #999;
            cursor: pointer;
            padding: 5px;
            transition: color 0.2s;
          "
          @mouseenter="e => e.target.style.color = '#333'"
          @mouseleave="e => e.target.style.color = '#999'"
        >
          <i class="fas fa-times"></i>
        </button>
      </div>
      
      <!-- 模态框内容 -->
      <div style="padding: 30px;">
        <!-- 标签 -->
        <div style="display: flex; gap: 10px; margin-bottom: 25px; flex-wrap: wrap;">
          <!-- 如果是新闻 -->
          <span v-if="selectedItem.newsType" :style="{
            display: 'inline-flex',
            alignItems: 'center',
            gap: '6px',
            padding: '6px 12px',
            borderRadius: '6px',
            fontSize: '14px',
            background: getNewsTypeStyle(selectedItem.newsType).background,
            color: getNewsTypeStyle(selectedItem.newsType).color
          }">
            <i class="fas fa-tag"></i> {{ getNewsTypeText(selectedItem.newsType) }}
          </span>
          
          <!-- 如果是公告 -->
          <span v-if="selectedItem.announcementType" :style="{
            display: 'inline-flex',
            alignItems: 'center',
            gap: '6px',
            padding: '6px 12px',
            borderRadius: '6px',
            fontSize: '14px',
            background: getAnnouncementTypeStyle(selectedItem.announcementType).background,
            color: getAnnouncementTypeStyle(selectedItem.announcementType).color
          }">
            <i class="fas fa-tag"></i> {{ getAnnouncementTypeText(selectedItem.announcementType) }}
          </span>
          
          <!-- 新闻的公告标签 -->
          <span v-if="selectedItem.newsType && selectedItem.isNotice == 1" style="
            display: inline-flex;
            align-items: center;
            gap: 6px;
            padding: 6px 12px;
            border-radius: 6px;
            font-size: 14px;
            background: #fff3e0;
            color: #ef6c00;
          ">
            <i class="fas fa-bullhorn"></i> 公告
          </span>
          
          <span style="
            display: inline-flex;
            align-items: center;
            gap: 6px;
            padding: 6px 12px;
            border-radius: 6px;
            font-size: 14px;
            background: #f5f5f5;
            color: #666;
          ">
            <i class="fas fa-calendar-alt"></i> {{ formatDateTime(selectedItem.publishDate) }}
          </span>
        </div>
        
        <!-- 内容 -->
        <div style="line-height: 1.8; color: #333; font-size: 16px;" v-html="selectedItem.content"></div>
        
        <!-- 附件 -->
        <div v-if="selectedItem.attachmentPath || selectedItem.attachment_path" style="margin-top: 30px; padding: 20px; background: #f9f9f9; border-radius: 8px;">
          <h4 style="margin: 0 0 15px 0; color: #333; display: flex; align-items: center; gap: 10px;">
            <i class="fas fa-paperclip"></i> 附件
          </h4>
          <div style="
            display: flex;
            align-items: center;
            gap: 15px;
            padding: 12px;
            background: white;
            border-radius: 6px;
            border: 1px solid #eee;
          ">
            <i class="fas fa-file" style="color: #3498db; font-size: 20px;"></i>
            <span style="flex: 1; color: #333;">{{ getFileName(selectedItem.attachmentPath || selectedItem.attachment_path) }}</span>
            <div style="display: flex; gap: 10px;">
              <button 
                @click="downloadAttachment(selectedItem.attachmentPath || selectedItem.attachment_path)"
                style="
                  padding: 6px 12px;
                  border: 1px solid #ddd;
                  background: white;
                  border-radius: 4px;
                  cursor: pointer;
                  display: flex;
                  align-items: center;
                  gap: 6px;
                  transition: all 0.2s;
                "
                @mouseenter="e => e.currentTarget.style.background = '#f5f5f5'"
                @mouseleave="e => e.currentTarget.style.background = 'white'"
              >
                <i class="fas fa-download"></i> 下载
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

    <!-- 征稿通知 -->
    <section class="cfp-section">
      <div class="container">
        <div class="cfp-card">
          <h2 class="text-center mb-4">征稿通知</h2>
          
          <!-- 征稿通知分类标签 -->
          <div class="cfp-tabs text-center mb-4">
            <button 
              v-for="tab in cfpTabs" 
              :key="tab.id"
              class="cfp-tab" 
              :class="{ active: activeCfpTab === tab.id }"
              @click="switchCfpTab(tab.id)"
            >
              {{ tab.label }}
            </button>
          </div>
          
          <!-- 征稿通知内容 -->
          <div class="cfp-list" v-if="displayCfpData.length > 0">
            <div v-for="cfp in displayCfpData" :key="cfp.id" class="cfp-item">
              <div class="cfp-header">
                <span class="cfp-badge" :style="getCfpBadgeStyle(cfp.type)">
                  <i :class="cfp.icon" class="me-1"></i>{{ cfp.typeText }}
                </span>
                <span class="cfp-deadline">
                  <i class="fas fa-calendar-check me-1"></i>
                  截止日期：<strong>{{ cfp.deadline }}</strong>
                </span>
              </div>
              
              <h4 class="cfp-title">{{ cfp.title }}</h4>
              
              <div class="cfp-description">
                <p>{{ cfp.description }}</p>
                
                <div class="cfp-details">
                  <div class="detail-item">
                    <i class="fas fa-calendar-alt"></i>
                    <div>
                      <h6>重要日期</h6>
                      <p v-html="cfp.importantDates"></p>
                    </div>
                  </div>
                  
                  <div class="detail-item">
                    <i class="fas fa-bullseye"></i>
                    <div>
                      <h6>征稿主题</h6>
                      <ul>
                        <li v-for="(topic, index) in cfp.topics" :key="index">{{ topic }}</li>
                      </ul>
                    </div>
                  </div>
                  
                  <div class="detail-item">
                    <i class="fas fa-users"></i>
                    <div>
                      <h6>客座编辑</h6>
                      <p>{{ cfp.guestEditors }}</p>
                    </div>
                  </div>
                </div>
              </div>
              
              <div class="cfp-footer">
                <div class="cfp-meta">
                  <span class="meta-item">
                    <i class="fas fa-book me-1"></i>
                    预计出版：{{ cfp.expectedPublication }}
                  </span>
                  <span class="meta-item">
                    <i class="fas fa-paper-plane me-1"></i>
                    已投稿数：{{ cfp.submissionCount }}
                  </span>
                  <span class="meta-item">
                    <i class="fas fa-star me-1"></i>
                    影响因子：{{ cfp.impactFactor }}
                  </span>
                </div>
                
                <div class="cfp-actions">
                  <button class="btn btn-outline-light me-2" @click="viewCfpDetail(cfp.id)">
                    <i class="fas fa-info-circle me-1"></i> 查看详情
                  </button>
                  <button class="btn btn-light me-2" @click="submitToCfp(cfp.id)">
                    <i class="fas fa-paper-plane me-1"></i> 立即投稿
                  </button>
                </div>
              </div>
            </div>
          </div>
          
          <div class="text-center mt-5">
            <p class="lead">投稿您的论文，加入我们的学术社区！</p>
            <router-link to="/submit" class="btn btn-light btn-lg">
              <i class="fas fa-paper-plane me-2"></i>立即投稿
            </router-link>
          </div>
        </div>
      </div>
    </section>

  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'

// 响应式数据
const activePaperTab = ref('latest')
const activeNewsTab = ref('news')
const activeCfpTab = ref('special')
const subscribeEmail = ref('')
const newsList = ref([])
const newsLoading = ref(false)
const selectedItem = ref(null)
const journalAnnouncements = ref([])
const paperSearch = ref('')
const viewMode = ref('grid')

// 新闻分类标签
const newsTabs = ref([
  { id: 'news', label: '新闻' },
  { id: 'announcements', label: '公告' }
])

// 论文标签
const paperTabs = ref([
  { id: 'latest', label: '最新发表', icon: 'fas fa-clock' },
  { id: 'cited', label: '高被引论文', icon: 'fas fa-quote-right' },
  { id: 'downloaded', label: '最多下载', icon: 'fas fa-download' },
  { id: 'popular', label: '最受欢迎', icon: 'fas fa-heart' },
  { id: 'recommended', label: '推荐阅读', icon: 'fas fa-thumbs-up' }
])

// 征稿通知分类标签
const cfpTabs = ref([
  { id: 'special', label: '特刊征稿' },
  { id: 'regular', label: '常规征稿' },
  { id: 'hot', label: '热点专题' },
  { id: 'upcoming', label: '即将截止' }
])

// 编委数据
const editors = ref([
  {
    id: 1,
    name: '张明 教授',
    position: '主编',
    description: '清华大学人工智能研究院院长，IEEE Fellow，在机器学习领域有超过20年的研究经验。',
    image: 'https://randomuser.me/api/portraits/men/32.jpg'
  },
  {
    id: 2,
    name: '李芳 教授',
    position: '副主编',
    description: '北京大学计算机科学系教授，主要研究方向为自然语言处理和知识图谱。',
    image: 'https://randomuser.me/api/portraits/women/44.jpg'
  },
  {
    id: 3,
    name: '王强 教授',
    position: '编委',
    description: '斯坦福大学客座教授，在计算机视觉和深度学习领域发表了多篇重要论文。',
    image: 'https://randomuser.me/api/portraits/men/67.jpg'
  },
  {
    id: 4,
    name: '陈静 教授',
    position: '编委',
    description: '麻省理工学院研究员，专注于强化学习和机器人技术的交叉研究。',
    image: 'https://randomuser.me/api/portraits/women/68.jpg'
  }
])

// 论文数据 - 静态数据（保持原有字段名称不变）
const papersData = ref({
  latest: [
    {
      id: 1,
      title: '基于Transformer的多模态情感分析模型研究',
      authorList: '张伟, 李娜, 王明',
      authorUnit: '清华大学人工智能研究院',
      abstractText: '本文提出了一种基于Transformer架构的多模态情感分析模型，通过融合文本、语音和视觉信息，实现了对用户情感的精准识别。实验结果表明，该模型在公开数据集上的准确率达到92.3%，比现有最佳方法提高了5.7%。',
      publishDate: '2024-11-15',
      citationCount: 128,
      downloadCount: 3421,
      likesCount: 245,
      viewsCount: 12500,
      category: 'nlp',
      keywords: ['Transformer', '多模态', '情感分析', '深度学习', '神经网络'],
      doi: '10.1234/ai.2024.001',
      pages: 15,
      isFeatured: true,
      isOpenAccess: true,
      liked: false,
      publicationType: 'Research'
    },
    {
      id: 2,
      title: '面向边缘计算的轻量级目标检测算法',
      authorList: '陈浩, 赵静, 孙强',
      authorUnit: '上海交通大学计算机科学与技术系',
      abstractText: '本文设计了一种适用于边缘设备的轻量级目标检测算法，在保持高检测精度的同时，将模型参数量减少了75%。该算法在移动设备上的推理速度达到30FPS，满足了实时检测的需求。',
      publishDate: '2024-11-10',
      citationCount: 95,
      downloadCount: 2987,
      likesCount: 189,
      viewsCount: 9800,
      category: 'vision',
      keywords: ['目标检测', '边缘计算', '轻量级模型', '实时检测'],
      doi: '10.1234/ai.2024.002',
      pages: 12,
      isFeatured: false,
      isOpenAccess: true,
      liked: true,
      publicationType: 'Research'
    },
    {
      id: 3,
      title: '基于强化学习的机器人路径规划算法优化',
      authorList: '刘洋, 周婷',
      authorUnit: '中国科学院自动化研究所',
      abstractText: '本研究提出了一种基于深度强化学习的机器人路径规划算法，通过引入自适应奖励机制和环境建模技术，显著提升了机器人在复杂环境中的导航能力。与传统方法相比，路径规划效率提高了40%。',
      publishDate: '2024-11-05',
      citationCount: 67,
      downloadCount: 2310,
      likesCount: 156,
      viewsCount: 8500,
      category: 'robotics',
      keywords: ['强化学习', '路径规划', '机器人', '深度强化学习'],
      doi: '10.1234/ai.2024.003',
      pages: 18,
      isFeatured: true,
      isOpenAccess: true,
      liked: false,
      publicationType: 'Research'
    }
  ],
  cited: [
    {
      id: 4,
      title: '深度学习在医学影像诊断中的应用综述',
      authorList: '王晓, 李明, 赵华',
      authorUnit: '北京大学医学院',
      abstractText: '本文系统综述了深度学习在医学影像诊断领域的最新研究进展，涵盖了CT、MRI、X光等多种影像模态，详细分析了不同算法的性能表现和临床应用价值。',
      publishDate: '2024-10-20',
      citationCount: 312,
      downloadCount: 5210,
      likesCount: 421,
      viewsCount: 21500,
      category: 'medical',
      keywords: ['医学影像', '深度学习', '诊断', '医疗AI'],
      doi: '10.1234/ai.2024.004',
      pages: 25,
      isFeatured: false,
      isOpenAccess: true,
      liked: false,
      publicationType: 'Review'
    }
  ],
  downloaded: [
    {
      id: 5,
      title: '大语言模型在代码生成中的应用研究',
      authorList: '吴刚, 郑丽',
      authorUnit: '浙江大学软件学院',
      abstractText: '本文探讨了大语言模型在代码生成任务中的应用，提出了基于GPT架构的专用代码生成模型，在多个编程语言基准测试中取得了state-of-the-art的性能。',
      publishDate: '2024-10-15',
      citationCount: 245,
      downloadCount: 4321,
      likesCount: 278,
      viewsCount: 18700,
      category: 'nlp',
      keywords: ['大语言模型', '代码生成', 'GPT', '程序生成'],
      doi: '10.1234/ai.2024.005',
      pages: 20,
      isFeatured: true,
      isOpenAccess: true,
      liked: false,
      publicationType: 'Research'
    }
  ],
  popular: [
    {
      id: 6,
      title: '可解释人工智能：方法与应用',
      authorList: '周敏, 杨帆, 胡伟',
      authorUnit: '复旦大学计算机科学与技术学院',
      abstractText: '本文系统分析了可解释人工智能的技术框架和应用场景，提出了一种统一的评估体系，为构建可信赖的AI系统提供了理论指导和技术路径。',
      publishDate: '2024-10-10',
      citationCount: 187,
      downloadCount: 3890,
      likesCount: 334,
      viewsCount: 15600,
      category: 'theory',
      keywords: ['可解释AI', '可信人工智能', 'XAI', '模型解释'],
      doi: '10.1234/ai.2024.006',
      pages: 18,
      isFeatured: false,
      isOpenAccess: true,
      liked: true,
      publicationType: 'Review'
    }
  ],
  recommended: [
    {
      id: 7,
      title: '联邦学习中的隐私保护技术研究',
      authorList: '赵刚, 钱芳, 孙伟',
      authorUnit: '华中科技大学计算机学院',
      abstractText: '本研究深入探讨了联邦学习中的隐私保护技术，提出了一种基于差分隐私和同态加密的混合保护方案，在保证模型性能的同时显著提升了隐私保护水平。',
      publishDate: '2024-10-05',
      citationCount: 142,
      downloadCount: 3210,
      likesCount: 198,
      viewsCount: 12300,
      category: 'security',
      keywords: ['联邦学习', '隐私保护', '差分隐私', '安全计算'],
      doi: '10.1234/ai.2024.007',
      pages: 16,
      isFeatured: true,
      isOpenAccess: true,
      liked: false,
      publicationType: 'Research'
    }
  ]
})

// 计算属性
const filteredPapers = computed(() => {
  const papers = papersData.value[activePaperTab.value] || []
  
  if (!paperSearch.value.trim()) {
    return papers
  }
  
  const searchTerm = paperSearch.value.toLowerCase()
  return papers.filter(paper => {
    return (
      paper.title.toLowerCase().includes(searchTerm) ||
      paper.authorList.toLowerCase().includes(searchTerm) ||
      paper.authorUnit.toLowerCase().includes(searchTerm) ||
      (paper.keywords && paper.keywords.some(keyword => 
        keyword.toLowerCase().includes(searchTerm)
      )) ||
      paper.abstractText.toLowerCase().includes(searchTerm)
    )
  })
})

const totalPapersCount = computed(() => {
  return Object.values(papersData.value)
    .reduce((total, papers) => total + papers.length, 0)
})

const totalCitations = computed(() => {
  return Object.values(papersData.value)
    .flat()
    .reduce((total, paper) => total + (paper.citationCount || 0), 0)
})

const totalDownloads = computed(() => {
  return Object.values(papersData.value)
    .flat()
    .reduce((total, paper) => total + (paper.downloadCount || 0), 0)
})

// 征稿通知数据 - 静态数据
const callForPapers = ref({
  special: [
    {
      id: 1,
      type: 'special',
      typeText: '特刊征稿',
      icon: 'fas fa-star',
      title: '人工智能促进可持续发展（AI for Sustainable Development）',
      description: '本特刊旨在探索人工智能技术在实现联合国可持续发展目标（SDGs）中的创新应用，包括但不限于环境保护、能源管理、城市规划和农业智能化等领域的AI解决方案。',
      deadline: '2025年6月30日',
      importantDates: `
        征稿开放：2024年12月1日<br>
        投稿截止：2025年6月30日<br>
        一审通知：2025年8月15日<br>
        最终提交：2025年9月30日<br>
        预计出版：2025年12月
      `,
      topics: [
        'AI在气候变化预测与应对中的应用',
        '智能能源管理与优化',
        '可持续城市与智能交通',
        '精准农业与粮食安全',
        '环境监测与生态保护'
      ],
      guestEditors: '张明 教授（清华大学）、Maria Rodriguez 教授（MIT）、王强 教授（斯坦福大学）',
      expectedPublication: '2025年12月',
      submissionCount: 45,
      impactFactor: '预计4.8'
    },
    {
      id: 2,
      type: 'special',
      typeText: '特刊征稿',
      icon: 'fas fa-star',
      title: '医疗人工智能：从研究到临床实践',
      description: '聚焦人工智能技术在医疗健康领域的转化应用，包括医学影像分析、疾病预测、药物研发、个性化治疗等前沿研究方向，促进AI技术与临床需求的深度融合。',
      deadline: '2025年7月15日',
      importantDates: `
        征稿开放：2024年12月15日<br>
        投稿截止：2025年7月15日<br>
        一审通知：2025年8月31日<br>
        最终提交：2025年10月15日<br>
        预计出版：2026年1月
      `,
      topics: [
        '医学影像智能诊断',
        '疾病风险预测模型',
        '药物发现与开发',
        '个性化治疗推荐',
        '医疗机器人技术'
      ],
      guestEditors: '李芳 教授（北京大学）、John Smith 教授（哈佛医学院）、陈静 教授（MIT）',
      expectedPublication: '2026年1月',
      submissionCount: 38,
      impactFactor: '预计5.2'
    },
    {
      id: 3,
      type: 'special',
      typeText: '特刊征稿',
      icon: 'fas fa-star',
      title: '可解释AI与可信人工智能',
      description: '关注人工智能系统的可解释性、透明度和可信度研究，包括模型解释方法、公平性分析、安全验证等关键技术，推动建立负责任的人工智能体系。',
      deadline: '2025年8月1日',
      importantDates: `
        征稿开放：2025年1月1日<br>
        投稿截止：2025年8月1日<br>
        一审通知：2025年9月15日<br>
        最终提交：2025年10月31日<br>
        预计出版：2026年2月
      `,
      topics: [
        '模型可解释性方法',
        '算法公平性评估',
        'AI系统安全验证',
        '伦理框架与治理',
        '可信AI应用案例'
      ],
      guestEditors: '周敏 教授（复旦大学）、杨帆 教授（华东师范大学）、胡伟 教授（北京大学）',
      expectedPublication: '2026年2月',
      submissionCount: 29,
      impactFactor: '预计4.9'
    }
  ],
  regular: [
    {
      id: 4,
      type: 'regular',
      typeText: '常规征稿',
      icon: 'fas fa-file-alt',
      title: '深度学习理论与算法创新',
      description: '本期刊持续征集深度学习领域的基础理论研究与算法创新论文，包括但不限于神经网络架构设计、优化算法、理论分析等方面的原创性研究成果。',
      deadline: '持续征稿',
      importantDates: `
        持续征稿，滚动审稿<br>
        平均审稿周期：45天<br>
        在线优先出版：录用后2周内<br>
        正式出版：按卷期安排
      `,
      topics: [
        '神经网络架构创新',
        '优化算法与训练技术',
        '深度学习理论分析',
        '模型压缩与加速',
        '无监督与自监督学习'
      ],
      guestEditors: '期刊编委会',
      expectedPublication: '滚动出版',
      submissionCount: 120,
      impactFactor: '4.5'
    },
    {
      id: 5,
      type: 'regular',
      typeText: '常规征稿',
      icon: 'fas fa-file-alt',
      title: '自然语言处理前沿技术',
      description: '征集自然语言处理领域的最新研究成果，包括语言理解、生成、翻译、问答等技术突破和应用创新。',
      deadline: '持续征稿',
      importantDates: `
        滚动征稿，快速审稿<br>
        平均审稿周期：40天<br>
        优先出版通道：优秀论文加速出版<br>
        开放获取：所有录用论文立即开放
      `,
      topics: [
        '大语言模型技术',
        '多语言处理',
        '语义理解与推理',
        '对话系统与问答',
        '文本生成与摘要'
      ],
      guestEditors: '吴刚 教授（浙江大学）、刘洋 教授（中国科学院）',
      expectedPublication: '滚动出版',
      submissionCount: 87,
      impactFactor: '4.7'
    }
  ],
  hot: [
    {
      id: 6,
      type: 'hot',
      typeText: '热点专题',
      icon: 'fas fa-fire',
      title: '大语言模型与生成式AI',
      description: '关注大语言模型的技术突破、应用创新及社会影响，包括模型架构、训练方法、评估基准、多模态生成、负责任AI等研究方向。',
      deadline: '2025年5月31日',
      importantDates: `
        征稿开放：2024年11月1日<br>
        投稿截止：2025年5月31日<br>
        加速审稿：1个月内返回<br>
        预计出版：2025年9月
      `,
      topics: [
        '大语言模型架构创新',
        '多模态生成技术',
        '高效训练与推理',
        '负责任AI与安全性',
        '行业应用与评估'
      ],
      guestEditors: '吴刚 教授（清华大学）、刘洋 教授（北京大学）、周敏 教授（MIT）',
      expectedPublication: '2025年9月',
      submissionCount: 67,
      impactFactor: '预计5.5'
    },
    {
      id: 7,
      type: 'hot',
      typeText: '热点专题',
      icon: 'fas fa-fire',
      title: '边缘智能与物联网AI',
      description: '探索人工智能在边缘计算和物联网设备中的应用，包括模型轻量化、分布式学习、实时推理、资源优化等关键技术研究。',
      deadline: '2025年6月15日',
      importantDates: `
        征稿开放：2024年11月15日<br>
        投稿截止：2025年6月15日<br>
        一审通知：2025年7月31日<br>
        最终提交：2025年8月31日<br>
        预计出版：2025年11月
      `,
      topics: [
        '边缘AI芯片与硬件',
        '模型压缩与部署',
        '联邦学习与隐私保护',
        '实时智能决策',
        '物联网应用场景'
      ],
      guestEditors: '陈浩 教授（上海交通大学）、赵静 教授（浙江大学）、孙强 教授（中科院）',
      expectedPublication: '2025年11月',
      submissionCount: 52,
      impactFactor: '预计4.9'
    }
  ],
  upcoming: [
    {
      id: 8,
      type: 'upcoming',
      typeText: '即将截止',
      icon: 'fas fa-clock',
      title: '人工智能教育应用专题',
      description: '聚焦AI技术在教育领域的创新应用，包括个性化学习、智能辅导、教育评估、教育机器人等方面的研究成果。',
      deadline: '2024年12月31日',
      importantDates: `
        投稿截止：2024年12月31日（即将截止）<br>
        紧急审稿通道已开启<br>
        加速出版流程<br>
        预计出版：2025年3月
      `,
      topics: [
        '个性化学习系统',
        '智能教学助手',
        '学习行为分析',
        '教育机器人',
        '教育公平与AI'
      ],
      guestEditors: '郑丽 教授（北京师范大学）、杨帆 教授（华东师范大学）、胡伟 教授（北京大学）',
      expectedPublication: '2025年3月',
      submissionCount: 28,
      impactFactor: '预计4.3'
    },
    {
      id: 9,
      type: 'upcoming',
      typeText: '即将截止',
      icon: 'fas fa-clock',
      title: 'AI赋能智慧城市',
      description: '探讨人工智能技术在智慧城市建设中的应用，包括智能交通、能源管理、公共安全、城市治理等方向。',
      deadline: '2025年1月15日',
      importantDates: `
        投稿截止：2025年1月15日（即将截止）<br>
        快速审稿：2周内返回初审意见<br>
        优先出版：录用后立即在线出版<br>
        预计见刊：2025年5月
      `,
      topics: [
        '智能交通系统',
        '城市能源管理',
        '公共安全监控',
        '智慧社区服务',
        '城市治理优化'
      ],
      guestEditors: '张伟 教授（清华大学）、李娜 教授（北京大学）、王明 教授（上海交通大学）',
      expectedPublication: '2025年5月',
      submissionCount: 34,
      impactFactor: '预计4.6'
    }
  ]
})

// 计算属性
const displayData = computed(() => {
  if (activeNewsTab.value === 'news') {
    return newsList.value
  } else if (activeNewsTab.value === 'announcements') {
    return journalAnnouncements.value
  }
  return []
})

const displayCfpData = computed(() => {
  return callForPapers.value[activeCfpTab.value] || []
})

// 方法
const switchPaperTab = (tabId) => {
  activePaperTab.value = tabId
  paperSearch.value = ''
}

const getPaperCount = (tabId) => {
  return papersData.value[tabId]?.length || 0
}

const formatPaperDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  const now = new Date()
  const diffTime = now - date
  const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24))
  
  if (diffDays === 0) return '今天'
  if (diffDays === 1) return '昨天'
  if (diffDays < 7) return `${diffDays}天前`
  if (diffDays < 30) return `${Math.floor(diffDays / 7)}周前`
  
  return date.toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  })
}

const truncateAbstract = (abstract, maxLength) => {
  if (!abstract) return ''
  if (abstract.length <= maxLength) return abstract
  return abstract.substring(0, maxLength) + '...'
}

const formatNumber = (num) => {
  if (num >= 1000000) {
    return (num / 1000000).toFixed(1) + 'M'
  }
  if (num >= 1000) {
    return (num / 1000).toFixed(1) + 'k'
  }
  return num.toString()
}

const getCategoryText = (category) => {
  const categories = {
    'nlp': '自然语言处理',
    'vision': '计算机视觉',
    'robotics': '机器人',
    'medical': '医疗AI',
    'theory': '理论基础',
    'security': '安全隐私',
    'application': '应用研究'
  }
  return categories[category] || '其他'
}

const getCategoryStyle = (category) => {
  const styles = {
    'nlp': { 
      backgroundColor: '#e3f2fd',
      color: '#1976d2',
      borderColor: '#bbdefb'
    },
    'vision': { 
      backgroundColor: '#fce4ec',
      color: '#c2185b',
      borderColor: '#f8bbd9'
    },
    'robotics': { 
      backgroundColor: '#e8f5e9',
      color: '#388e3c',
      borderColor: '#c8e6c9'
    },
    'medical': { 
      backgroundColor: '#fff3e0',
      color: '#f57c00',
      borderColor: '#ffe0b2'
    },
    'theory': { 
      backgroundColor: '#f3e5f5',
      color: '#7b1fa2',
      borderColor: '#e1bee7'
    },
    'security': { 
      backgroundColor: '#e0f2f1',
      color: '#00796b',
      borderColor: '#b2dfdb'
    }
  }
  return styles[category] || { 
    backgroundColor: '#f5f5f5',
    color: '#616161',
    borderColor: '#e0e0e0'
  }
}

const searchKeyword = (keyword) => {
  paperSearch.value = keyword
}

const handleSearch = () => {
  console.log('搜索关键词:', paperSearch.value)
}

const clearSearch = () => {
  paperSearch.value = ''
}

const viewPaper = (paperId) => {
  console.log('查看论文详情:', paperId)
}

const downloadPaper = (paperId) => {
  console.log('下载论文:', paperId)
}

const likePaper = (paperId) => {
  const papers = Object.values(papersData.value).flat()
  const paper = papers.find(p => p.id === paperId)
  if (paper) {
    paper.liked = !paper.liked
    paper.likesCount += paper.liked ? 1 : -1
  }
}

const showMetricDetails = (type, paper) => {
  console.log(`查看${type}详情:`, paper)
}

const switchNewsTab = (tabId) => {
  activeNewsTab.value = tabId
  // 切换时加载对应数据
  if (tabId === 'news' && newsList.value.length === 0) {
    loadNewsData()
  } else if (tabId === 'announcements' && journalAnnouncements.value.length === 0) {
    loadJournalAnnouncements()
  }
}

const switchCfpTab = (tabId) => {
  activeCfpTab.value = tabId
}

// 征稿通知徽章样式
const getCfpBadgeStyle = (type) => {
  const styles = {
    'special': { background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)', color: 'white' },
    'regular': { background: 'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)', color: 'white' },
    'hot': { background: 'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)', color: 'white' },
    'upcoming': { background: 'linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)', color: 'white' }
  }
  return styles[type] || { background: '#f5f5f5', color: '#666' }
}

// 征稿通知相关方法
const viewCfpDetail = (cfpId) => {
  console.log('查看征稿详情:', cfpId)
  // 这里可以添加查看征稿详情的逻辑
}

const submitToCfp = (cfpId) => {
  console.log('投稿到征稿:', cfpId)
  // 这里可以添加投稿的逻辑
}

// 保持原有的新闻相关方法不变
const getNewsTypeText = (type) => {
  const types = {
    'REGULAR_NEWS': '普通新闻',
    'CALL_FOR_PAPERS': '征稿通知',
    'SPECIAL_ISSUE': '特刊公告'
  }
  return types[type] || type || '新闻'
}

const getNewsTypeStyle = (type) => {
  const styles = {
    'REGULAR_NEWS': { background: '#e3f2fd', color: '#1565c0' },
    'CALL_FOR_PAPERS': { background: '#fff3e0', color: '#ef6c00' },
    'SPECIAL_ISSUE': { background: '#e8f5e9', color: '#2e7d32' }
  }
  return styles[type] || { background: '#f5f5f5', color: '#616161' }
}

const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  const now = new Date()
  const diffTime = Math.abs(now - date)
  const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24))
  
  // 如果是今天，显示"今天"
  if (diffDays === 0) {
    return '今天'
  }
  // 如果是昨天，显示"昨天"
  if (diffDays === 1) {
    return '昨天'
  }
  // 如果是一周内，显示"X天前"
  if (diffDays < 7) {
    return `${diffDays}天前`
  }
  
  // 否则显示具体日期
  return date.toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
  })
}

const formatDateTime = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const getContentSummary = (item) => {
  const content = item.content || ''
  if (!content) return '暂无内容'
  // 移除HTML标签
  const text = content.replace(/<[^>]*>/g, '')
  // 限制长度
  if (text.length <= 120) return text
  return text.substring(0, 120) + '...'
}

const showItemDetail = (item) => {
  selectedItem.value = item
  // 阻止背景滚动
  document.body.style.overflow = 'hidden'
}

const closeItemDetail = () => {
  selectedItem.value = null
  // 恢复背景滚动
  document.body.style.overflow = 'auto'
}

const downloadAttachment = (path) => {
  if (!path) return
  const fileName = getFileName(path)
  // 使用代理路径
  const downloadUrl = `/download?path=${encodeURIComponent(path)}&filename=${encodeURIComponent(fileName)}`
  
  // 创建临时链接进行下载
  const link = document.createElement('a')
  link.href = downloadUrl
  link.download = fileName
  document.body.appendChild(link)
  link.click()
  document.body.removeChild(link)
}

const getFileName = (path) => {
  if (!path) return ''
  return path.split('/').pop()
}

// 加载期刊公告数据
const loadJournalAnnouncements = async () => {
  newsLoading.value = true
  try {
    console.log('开始加载期刊公告数据...')
    
    // 调用你的Spring Boot后端API
    const response = await fetch('/editorial-office/journal-announcement/list?page=1&pageSize=6&status=Published')
    
    console.log('期刊公告API响应状态:', response.status)
    
    if (response.ok) {
      const result = await response.json()
      console.log('期刊公告API响应数据:', result)
      
      if (result.code === 200 && result.data && result.data.data) {
        // 确保只处理已发布的公告，且只显示前6条
        journalAnnouncements.value = result.data.data
          .filter(item => {
            return item.publishStatus === 'Published' || item.publish_status === 'Published'
          })
          .slice(0, 6) // 只取前6条
          .map(item => {
            return {
              id: item.id,
              title: item.title,
              content: item.content || '',
              publishDate: item.publishDate || item.publish_date,
              announcementType: item.announcementType || item.announcement_type || 'OTHER',
              effectiveDate: item.effectiveDate || item.effective_date,
              expireDate: item.expireDate || item.expire_date,
              priority: item.priority,
              isTop: item.isTop || item.is_top,
              viewCount: item.viewCount || item.view_count,
              attachmentPath: item.attachmentPath || item.attachment_path,
              publishStatus: item.publishStatus || item.publish_status
            }
          })
        console.log('处理后的公告列表:', journalAnnouncements.value)
      } else {
        console.error('公告API返回数据格式错误或没有数据:', result)
      }
    } else {
      console.error('公告API请求失败，状态码:', response.status)
    }
  } catch (error) {
    console.error('加载期刊公告数据失败:', error)
  } finally {
    newsLoading.value = false
  }
}

const scrollTo = (sectionId) => {
  const element = document.getElementById(sectionId)
  if (element) {
    element.scrollIntoView({ behavior: 'smooth' })
  }
}

const handleSubscribe = () => {
  if (subscribeEmail.value) {
    alert(`感谢订阅！我们将发送确认邮件到：${subscribeEmail.value}`)
    subscribeEmail.value = ''
  } else {
    alert('请输入邮箱地址')
  }
}

// 公告类型相关方法
const getAnnouncementTypeText = (type) => {
  const types = {
    'JOURNAL_INFO': '期刊信息',
    'IMPACT_FACTOR': '影响因子',
    'POLICY_GUIDE': '政策指南',
    'IMPORTANT_NOTICE': '重要通知',
    'OTHER': '其他'
  }
  return types[type] || type || '公告'
}

const getAnnouncementTypeStyle = (type) => {
  const styles = {
    'JOURNAL_INFO': { background: '#e3f2fd', color: '#1565c0' },
    'IMPACT_FACTOR': { background: '#e8f5e9', color: '#2e7d32' },
    'POLICY_GUIDE': { background: '#fff3e0', color: '#ef6c00' },
    'IMPORTANT_NOTICE': { background: '#ffebee', color: '#c62828' },
    'OTHER': { background: '#f3e5f5', color: '#7b1fa2' }
  }
  return styles[type] || { background: '#f5f5f5', color: '#616161' }
}

// 生命周期钩子
onMounted(() => {
  // 初始化时加载新闻数据
  loadNewsData()
})

// 加载新闻数据（连接到后端API）
const loadNewsData = async () => {
  newsLoading.value = true
  try {
    // 正确的后端API路径 - 应该调用新闻列表接口
    console.log('开始加载新闻数据...')
    const response = await fetch('/editorial-office/news/list?page=1&pageSize=6&status=Published')
    
    console.log('API响应状态:', response.status)
    
    if (response.ok) {
      const result = await response.json()
      console.log('API响应数据:', result)
      
      if (result.code === 200 && result.data && result.data.data) {
        // 确保只处理已发布的新闻
        newsList.value = result.data.data
          .filter(item => {
            console.log('检查新闻项目:', item)
            return item.publishStatus === 'Published'
          })
          .map(item => {
            console.log('处理新闻项目:', item)
            return {
              id: item.id,
              title: item.title,
              content: item.content || '',
              publishDate: item.publishDate || item.publish_date,
              newsType: item.newsType || item.news_type || 'REGULAR_NEWS',
              isNotice: item.isNotice || item.is_notice || 0,
              attachmentPath: item.attachmentPath,
              publishStatus: item.publishStatus || item.publish_status
            }
          })
        console.log('处理后的新闻列表:', newsList.value)
      } else {
        console.error('API返回数据格式错误或没有数据:', result)
        // 使用模拟数据
        newsList.value = getMockNewsData()
      }
    } else {
      console.error('API请求失败，状态码:', response.status)
      // 尝试另一个API路径
      console.log('尝试备用API路径...')
      try {
        const fallbackResponse = await fetch('/editorial-office/news/list-all')
        if (fallbackResponse.ok) {
          const fallbackResult = await fallbackResponse.json()
          console.log('备用API响应:', fallbackResult)
          
          if (fallbackResult.code === 200 && fallbackResult.data) {
            newsList.value = fallbackResult.data
              .filter(item => item.publishStatus === 'Published')
              .map(item => ({
                id: item.id,
                title: item.title,
                content: item.content || '',
                publishDate: item.publishDate,
                newsType: item.newsType || 'REGULAR_NEWS',
                isNotice: item.isNotice || 0,
                attachmentPath: item.attachmentPath,
                publishStatus: item.publishStatus
              }))
            console.log('备用API处理后的新闻列表:', newsList.value)
          } else {
            throw new Error('备用API返回数据格式错误')
          }
        } else {
          throw new Error(`备用API请求失败: ${fallbackResponse.status}`)
        }
      } catch (fallbackError) {
        console.error('备用API也失败:', fallbackError)
        // 使用模拟数据
        newsList.value = getMockNewsData()
      }
    }
  } catch (error) {
    console.error('加载新闻数据失败:', error)
    // 使用模拟数据
    newsList.value = getMockNewsData()
  } finally {
    newsLoading.value = false
  }
}

// 模拟数据函数
const getMockNewsData = () => {
  console.log('使用模拟数据')
  return [
    {
      id: 1,
      title: '期刊影响因子提升至4.5',
      content: '<p>我们很高兴地宣布，本刊2024年影响因子已提升至4.5，在人工智能领域期刊中排名显著提升。</p>',
      publishDate: '2024-11-20T10:30:00',
      newsType: 'REGULAR_NEWS',
      isNotice: 1,
      attachmentPath: null,
      publishStatus: 'Published'
    },
    {
      id: 2,
      title: '2025年特刊征稿：人工智能在医疗领域的应用',
      content: '<p>本刊将出版特刊"AI in Healthcare"，现面向全球学者征集高质量论文。投稿截止日期：2025年6月30日。</p>',
      publishDate: '2024-11-15T14:20:00',
      newsType: 'CALL_FOR_PAPERS',
      isNotice: 0,
      attachmentPath: '/upload/attachments/cfp_healthcare.pdf',
      publishStatus: 'Published'
    },
    {
      id: 3,
      title: '新增编委成员',
      content: '<p>欢迎来自斯坦福大学、麻省理工学院等顶尖研究机构的新编委成员加入我们的编委会。</p>',
      publishDate: '2024-11-10T09:15:00',
      newsType: 'REGULAR_NEWS',
      isNotice: 0,
      attachmentPath: null,
      publishStatus: 'Published'
    }
  ]
}
</script>

<style scoped>
/* 保持原有的CSS样式不变 */
.hero-section {
  background: linear-gradient(135deg, #2c3e50 0%, #4a6491 100%);
  color: white;
  padding: 100px 0;
  text-align: center;
}

.hero-title {
  font-size: 48px;
  margin-bottom: 20px;
  font-weight: 700;
}

.hero-subtitle {
  font-size: 20px;
  margin-bottom: 40px;
  opacity: 0.9;
}

.hero-buttons {
  display: flex;
  gap: 20px;
  justify-content: center;
  flex-wrap: wrap;
}

.btn-large {
  padding: 15px 30px;
  font-size: 18px;
}

.stats-section {
  padding: 60px 0;
  background-color: #f8f9fa;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 30px;
  text-align: center;
}

.stat-item h3 {
  font-size: 42px;
  color: #2c3e50;
  margin-bottom: 10px;
}

.stat-item p {
  color: #7f8c8d;
  font-size: 18px;
}

.section-title {
  font-size: 32px;
  color: #2c3e50;
  margin-bottom: 40px;
  text-align: center;
}

.papers-section {
  padding: 80px 0;
  background-color: #fff;
}

.paper-tab:hover {
  background: #f5f5f5;
}

.paper-tab.active {
  background: #3498db;
  color: white;
  border-color: #3498db;
}

.paper-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.paper-item {
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 25px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.paper-item:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 25px rgba(0,0,0,0.1);
}

.cfp-section {
  padding: 80px 0;
}

.cfp-card {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 60px;
  border-radius: 12px;
  text-align: center;
}

/* 新闻标签样式 */
.news-tab {
  padding: 8px 20px;
  background: white;
  color: #333;
  border: 1px solid #ddd;
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.3s;
  font-weight: 500;
  margin: 0 10px;
}

.news-tab:hover {
  background: #f5f5f5;
}

.news-tab.active {
  background: #3498db;
  color: white;
  border-color: #3498db;
}

/* 征稿通知样式 */
.cfp-tabs {
  display: flex;
  justify-content: center;
  gap: 15px;
  margin-bottom: 30px;
  flex-wrap: wrap;
}

.cfp-tab {
  padding: 8px 20px;
  background: rgba(255, 255, 255, 0.2);
  color: white;
  border: 1px solid rgba(255, 255, 255, 0.3);
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.3s;
  font-weight: 500;
}

.cfp-tab:hover {
  background: rgba(255, 255, 255, 0.3);
}

.cfp-tab.active {
  background: white;
  color: #667eea;
  border-color: white;
}

.cfp-list {
  display: flex;
  flex-direction: column;
  gap: 25px;
  margin-bottom: 30px;
}

.cfp-item {
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 12px;
  padding: 25px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.cfp-item:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 25px rgba(0,0,0,0.1);
}

.cfp-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  flex-wrap: wrap;
  gap: 10px;
}

.cfp-badge {
  padding: 6px 12px;
  border-radius: 15px;
  font-size: 14px;
  font-weight: 600;
}

.cfp-deadline {
  background: rgba(255, 255, 255, 0.2);
  padding: 6px 12px;
  border-radius: 15px;
  font-size: 14px;
}

.cfp-title {
  font-size: 20px;
  color: white;
  margin-bottom: 15px;
  line-height: 1.4;
}

.cfp-description {
  margin-bottom: 20px;
}

.cfp-description p {
  color: rgba(255, 255, 255, 0.9);
  line-height: 1.6;
  margin-bottom: 20px;
  font-size: 15px;
}

.cfp-details {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
  margin-top: 25px;
}

.detail-item {
  display: flex;
  gap: 15px;
  align-items: flex-start;
  background: rgba(255, 255, 255, 0.1);
  padding: 15px;
  border-radius: 8px;
}

.detail-item i {
  color: white;
  font-size: 18px;
  margin-top: 2px;
}

.detail-item h6 {
  color: white;
  margin-bottom: 8px;
  font-size: 16px;
}

.detail-item p, .detail-item ul {
  color: rgba(255, 255, 255, 0.8);
  font-size: 14px;
  line-height: 1.5;
}

.detail-item ul {
  padding-left: 20px;
  margin: 0;
}

.detail-item li {
  margin-bottom: 6px;
  color: rgba(255, 255, 255, 0.8);
}

.cfp-footer {
  border-top: 1px solid rgba(255, 255, 255, 0.2);
  padding-top: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 20px;
}

.cfp-meta {
  display: flex;
  gap: 20px;
  flex-wrap: wrap;
}

.meta-item {
  color: rgba(255, 255, 255, 0.8);
  font-size: 14px;
  display: flex;
  align-items: center;
}

.meta-item i {
  color: white;
  margin-right: 6px;
}

.cfp-actions {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

@media (max-width: 768px) {
  .hero-title {
    font-size: 32px;
  }
  
  .hero-subtitle {
    font-size: 16px;
  }
  
  .section-title {
    font-size: 24px;
  }
  
  .cfp-card {
    padding: 40px 20px;
  }
  
  .paper-tabs, .cfp-tabs {
    gap: 10px;
  }
  
  .paper-tab, .cfp-tab, .news-tab {
    padding: 6px 15px;
    font-size: 14px;
  }
  
  .paper-item, .cfp-item {
    padding: 20px;
  }
  
  .cfp-footer {
    flex-direction: column;
    align-items: flex-start;
  }
  
  .cfp-actions {
    width: 100%;
    justify-content: flex-start;
  }
  
  .cfp-details {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 576px) {
  .cfp-header {
    flex-direction: column;
    align-items: flex-start;
  }
  
  .cfp-meta {
    flex-direction: column;
    gap: 10px;
  }
}

/* 页面布局调整 */
.container {
  max-width: 1600px;
  width: 98%;
  margin: 0 auto;
  padding: 0 10px;
}

.home-page .container {
  max-width: 1700px;
  width: 99%;
  padding: 0 15px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .container {
    width: 100%;
    padding: 0 8px;
  }
  
  .hero-section {
    padding: 40px 0;
  }
  
  .hero-title {
    font-size: 28px;
  }
  
  .hero-subtitle {
    font-size: 16px;
  }
}

/* 动画定义 */
@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 页脚样式 */
.footer {
  background: #2c3e50;
  color: white;
  padding: 60px 0 30px;
}

.footer h4, .footer h5 {
  color: white;
}

.footer a {
  transition: color 0.3s;
}

.footer a:hover {
  color: #3498db !important;
}

.social-links a {
  display: inline-block;
  transition: transform 0.3s;
}

.social-links a:hover {
  transform: translateY(-3px);
}

/* 论文区域样式 */
.papers-section {
  padding: 80px 0;
  background-color: #f8fafc;
}

.section-header {
  text-align: center;
  margin-bottom: 40px;
}

.section-title {
  font-size: 2.5rem;
  font-weight: 700;
  color: #2c3e50;
  margin-bottom: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.section-title i {
  color: #3498db;
}

.section-subtitle {
  font-size: 1.1rem;
  color: #7f8c8d;
  max-width: 600px;
  margin: 0 auto;
  line-height: 1.6;
}

/* 论文标签页 */
.paper-tabs {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-bottom: 30px;
  flex-wrap: wrap;
  padding: 20px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}

.paper-tab {
  display: flex;
  align-items: center;
  padding: 10px 20px;
  background: #f8f9fa;
  border: 2px solid #e9ecef;
  border-radius: 25px;
  color: #495057;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 0.95rem;
}

.paper-tab:hover {
  background: #e9ecef;
  border-color: #ced4da;
  transform: translateY(-2px);
}

.paper-tab.active {
  background: #3498db;
  border-color: #3498db;
  color: white;
  box-shadow: 0 4px 15px rgba(52, 152, 219, 0.3);
}

.paper-tab i {
  font-size: 0.9rem;
}

.tab-badge {
  background: rgba(255, 255, 255, 0.3);
  padding: 2px 8px;
  border-radius: 10px;
  font-size: 0.8rem;
  margin-left: 8px;
  font-weight: 700;
}

.paper-tab.active .tab-badge {
  background: rgba(255, 255, 255, 0.2);
}

/* 搜索框 */
.search-container {
  margin-bottom: 40px;
}

.search-box {
  position: relative;
  max-width: 600px;
  margin: 0 auto;
}

.search-icon {
  position: absolute;
  left: 20px;
  top: 50%;
  transform: translateY(-50%);
  color: #adb5bd;
  font-size: 1rem;
}

.search-input {
  width: 100%;
  padding: 15px 50px;
  border: 2px solid #e9ecef;
  border-radius: 30px;
  font-size: 1rem;
  transition: all 0.3s ease;
  background: white;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.search-input:focus {
  outline: none;
  border-color: #3498db;
  box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.1);
}

.clear-search {
  position: absolute;
  right: 15px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  color: #adb5bd;
  cursor: pointer;
  padding: 5px;
  font-size: 0.9rem;
}

.clear-search:hover {
  color: #495057;
}

/* 论文网格 */
.paper-grid {
  margin-bottom: 40px;
}

.paper-grid-content {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 30px;
}

/* 论文卡片 */
.paper-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
  display: flex;
  flex-direction: column;
  height: 100%;
  position: relative;
  border: 1px solid #f1f1f1;
}

.paper-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
}

.paper-card.featured {
  border-left: 4px solid #ffc107;
}

.paper-card.hot {
  border-left: 4px solid #e74c3c;
}

.paper-card.open-access::before {
  content: '';
  position: absolute;
  top: 0;
  right: 0;
  width: 0;
  height: 0;
  border-style: solid;
  border-width: 0 40px 40px 0;
  border-color: transparent #2ecc71 transparent transparent;
}

.paper-card.open-access::after {
  content: 'OA';
  position: absolute;
  top: 5px;
  right: 5px;
  color: white;
  font-size: 0.7rem;
  font-weight: 600;
  transform: rotate(45deg);
}

/* 论文头部 */
.paper-header {
  padding: 25px 25px 15px;
  border-bottom: 1px solid #f8f9fa;
}

.paper-meta {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 15px;
}

.paper-category {
  padding: 6px 12px;
  border-radius: 15px;
  font-size: 0.8rem;
  font-weight: 600;
  border: 1px solid;
  display: inline-block;
}

.paper-badges {
  display: flex;
  gap: 5px;
}

.badge {
  padding: 4px 8px;
  border-radius: 10px;
  font-size: 0.7rem;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 3px;
}

.badge-oa {
  background: #e8f5e9;
  color: #2e7d32;
}

.badge-featured {
  background: #fff3e0;
  color: #ef6c00;
}

.paper-title {
  margin: 0 0 15px 0;
}

.paper-title-link {
  font-size: 1.3rem;
  font-weight: 700;
  color: #2c3e50;
  text-decoration: none;
  line-height: 1.4;
  cursor: pointer;
  display: block;
  transition: color 0.2s;
}

.paper-title-link:hover {
  color: #3498db;
}

.paper-authors {
  display: flex;
  align-items: flex-start;
  gap: 12px;
}

.author-icon {
  color: #adb5bd;
  font-size: 1.2rem;
  margin-top: 2px;
  flex-shrink: 0;
}

.author-info {
  flex: 1;
  min-width: 0;
}

.author-names {
  display: block;
  font-weight: 600;
  color: #495057;
  font-size: 0.95rem;
  margin-bottom: 4px;
  line-height: 1.3;
}

.author-affiliation {
  display: block;
  color: #6c757d;
  font-size: 0.85rem;
  line-height: 1.4;
}

/* 论文主体 */
.paper-body {
  padding: 20px 25px;
  flex-grow: 1;
}

.paper-abstract {
  color: #495057;
  line-height: 1.7;
  margin-bottom: 20px;
  font-size: 0.95rem;
  display: -webkit-box;
  -webkit-line-clamp: 4;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.paper-keywords {
  margin-top: 20px;
}

.keywords-header {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #6c757d;
  font-size: 0.9rem;
  margin-bottom: 10px;
}

.keywords-header i {
  color: #3498db;
}

.keywords-list {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.keyword {
  padding: 6px 12px;
  background: #f8f9fa;
  border-radius: 15px;
  font-size: 0.85rem;
  color: #495057;
  cursor: pointer;
  transition: all 0.2s;
  border: 1px solid #e9ecef;
}

.keyword:hover {
  background: #3498db;
  color: white;
  border-color: #3498db;
  transform: translateY(-1px);
}

.keyword-more {
  padding: 6px 12px;
  background: #f8f9fa;
  border-radius: 15px;
  font-size: 0.85rem;
  color: #adb5bd;
  border: 1px solid #e9ecef;
}

/* 论文指标 */
.paper-metrics {
  padding: 15px 25px;
  background: #f8f9fa;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 15px;
  border-top: 1px solid #e9ecef;
  border-bottom: 1px solid #e9ecef;
}

.metric-item {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
  transition: all 0.2s;
  padding: 8px;
  border-radius: 8px;
}

.metric-item:hover {
  background: white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.metric-icon {
  width: 36px;
  height: 36px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1rem;
}

.metric-icon.citation {
  background: #e3f2fd;
  color: #1976d2;
}

.metric-icon.download {
  background: #e8f5e9;
  color: #388e3c;
}

.metric-icon.like {
  background: #ffebee;
  color: #e53935;
}

.metric-icon.view {
  background: #f3e5f5;
  color: #7b1fa2;
}

.metric-content {
  flex: 1;
  min-width: 0;
}

.metric-value {
  font-size: 1.2rem;
  font-weight: 700;
  color: #2c3e50;
  line-height: 1;
  margin-bottom: 2px;
}

.metric-label {
  font-size: 0.8rem;
  color: #6c757d;
  font-weight: 500;
}

/* 论文底部 */
.paper-footer {
  padding: 20px 25px;
}

.paper-info {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  margin-bottom: 20px;
  color: #6c757d;
  font-size: 0.85rem;
}

.info-item {
  display: flex;
  align-items: center;
  gap: 5px;
}

.info-item i {
  font-size: 0.9rem;
}

.paper-actions {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 10px 20px;
  font-size: 0.9rem;
  font-weight: 600;
  border-radius: 6px;
  border: 2px solid transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  text-decoration: none;
  white-space: nowrap;
}

.btn-sm {
  padding: 8px 16px;
  font-size: 0.85rem;
}

.btn-primary {
  background: #3498db;
  color: white;
  border-color: #3498db;
}

.btn-primary:hover {
  background: #2980b9;
  border-color: #2980b9;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(52, 152, 219, 0.2);
}

.btn-outline {
  background: white;
  color: #495057;
  border-color: #ced4da;
}

.btn-outline:hover {
  background: #f8f9fa;
  color: #2c3e50;
  border-color: #adb5bd;
  transform: translateY(-2px);
}

.btn-like {
  background: #f8f9fa;
  color: #6c757d;
  border-color: #e9ecef;
}

.btn-like:hover {
  background: #ffebee;
  color: #e53935;
  border-color: #ffcdd2;
}

.btn-like.liked {
  background: #ffebee;
  color: #e53935;
  border-color: #ffcdd2;
}

.btn-lg {
  padding: 12px 28px;
  font-size: 1rem;
}

/* 空状态 */
.empty-state {
  text-align: center;
  padding: 60px 20px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  margin: 40px 0;
}

.empty-icon {
  font-size: 4rem;
  color: #dee2e6;
  margin-bottom: 20px;
}

.empty-title {
  color: #495057;
  font-size: 1.5rem;
  margin-bottom: 10px;
}

.empty-description {
  color: #6c757d;
  margin-bottom: 30px;
}

/* 论文摘要 */
.papers-summary {
  display: flex;
  justify-content: center;
  gap: 30px;
  flex-wrap: wrap;
  margin-bottom: 20px;
}

.summary-item {
  display: flex;
  align-items: center;
  color: #6c757d;
  font-size: 0.95rem;
}

.summary-item i {
  color: #3498db;
  font-size: 1.1rem;
}

/* 动画效果 */
.fade-list-move,
.fade-list-enter-active,
.fade-list-leave-active {
  transition: all 0.5s ease;
}

.fade-list-enter-from,
.fade-list-leave-to {
  opacity: 0;
  transform: translateY(20px);
}

.fade-list-leave-active {
  position: absolute;
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .paper-grid-content {
    grid-template-columns: repeat(auto-fill, minmax(330px, 1fr));
  }
}

@media (max-width: 768px) {
  .paper-tabs {
    padding: 15px;
  }
  
  .paper-tab {
    padding: 8px 15px;
    font-size: 0.9rem;
  }
  
  .paper-grid-content {
    grid-template-columns: 1fr;
  }
  
  .paper-metrics {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .paper-info {
    flex-direction: column;
    gap: 10px;
  }
  
  .papers-summary {
    gap: 20px;
  }
}

@media (max-width: 576px) {
  .section-title {
    font-size: 2rem;
  }
  
  .paper-card {
    border-radius: 10px;
  }
  
  .paper-header,
  .paper-body,
  .paper-metrics,
  .paper-footer {
    padding: 20px;
  }
  
  .paper-actions {
    flex-direction: column;
  }
  
  .btn {
    width: 100%;
    justify-content: center;
  }
}
</style>
