<template>
  <main class="journal-page">
    <div class="container">
      <!-- 期刊横幅 -->
      <section class="journal-hero">
        <h1>关于《国际人工智能研究》期刊</h1>
        <p class="subtitle">出版高质量人工智能与机器学习研究的领先开源期刊</p>
      </section>

      <!-- 宗旨与投稿范围 -->
      <section class="section-card">
        <div class="section-header">
          <h2><i class="fas fa-book-open me-2"></i> 宗旨与投稿范围</h2>
          <router-link to="/submit" class="btn btn-outline">
            <i class="fas fa-paper-plane me-2"></i>立即投稿
          </router-link>
        </div>
        
        <div class="section-content">
          <div class="content-block">
            <h4>期刊主旨</h4>
            <p>《国际人工智能研究》(IAIR) 致力于推动人工智能领域的理论创新与应用实践，发表原创性强、影响力大的高水平研究成果，促进学术交流和技术进步。本刊旨在成为人工智能研究者交流最新发现、分享创新方法的重要平台，推动人工智能技术的理论发展和实际应用。</p>
          </div>
          
          <div class="content-block">
            <h4>主要研究方向</h4>
            <div class="research-grid">
              <div class="research-category">
                <h5><i class="fas fa-brain me-2"></i> 机器学习与深度学习</h5>
                <ul>
                  <li>监督学习与无监督学习</li>
                  <li>强化学习与元学习</li>
                  <li>神经网络架构设计</li>
                  <li>模型优化与训练技术</li>
                  <li>可解释机器学习</li>
                </ul>
              </div>
              
              <div class="research-category">
                <h5><i class="fas fa-comments me-2"></i> 自然语言处理</h5>
                <ul>
                  <li>机器翻译与文本生成</li>
                  <li>情感分析与意见挖掘</li>
                  <li>知识图谱与语义理解</li>
                  <li>对话系统与问答系统</li>
                  <li>信息检索与文本摘要</li>
                </ul>
              </div>
              
              <div class="research-category">
                <h5><i class="fas fa-eye me-2"></i> 计算机视觉</h5>
                <ul>
                  <li>图像识别与分类</li>
                  <li>目标检测与跟踪</li>
                  <li>三维视觉与重建</li>
                  <li>视频分析与理解</li>
                  <li>人脸识别与生物特征</li>
                </ul>
              </div>
              
              <div class="research-category">
                <h5><i class="fas fa-robot me-2"></i> 智能机器人</h5>
                <ul>
                  <li>自主导航与路径规划</li>
                  <li>人机交互与协作</li>
                  <li>多机器人系统</li>
                  <li>智能制造与工业4.0</li>
                  <li>服务机器人技术</li>
                </ul>
              </div>
              
              <div class="research-category">
                <h5><i class="fas fa-flask me-2"></i> AI理论方法</h5>
                <ul>
                  <li>联邦学习与隐私保护</li>
                  <li>小样本与零样本学习</li>
                  <li>迁移学习与领域自适应</li>
                  <li>生成对抗网络</li>
                  <li>因果推理与推断</li>
                </ul>
              </div>
              
              <div class="research-category">
                <h5><i class="fas fa-cogs me-2"></i> AI交叉应用</h5>
                <ul>
                  <li>医疗AI与生物信息学</li>
                  <li>金融科技与智能投顾</li>
                  <li>智慧城市与智能交通</li>
                  <li>教育技术与个性化学习</li>
                  <li>环境监测与可持续发展</li>
                </ul>
              </div>
            </div>
          </div>
          
          <div class="content-block">
            <h4>投稿内容要求</h4>
            <div class="submission-types">
              <div class="submission-type">
                <div class="type-icon">
                  <i class="fas fa-file-alt"></i>
                </div>
                <div class="type-content">
                  <h5>研究论文</h5>
                  <p>原创性研究，需包含创新方法、理论分析、实验验证和结果讨论。篇幅建议：8,000-12,000字。</p>
                </div>
              </div>
              
              <div class="submission-type">
                <div class="type-icon">
                  <i class="fas fa-book"></i>
                </div>
                <div class="type-content">
                  <h5>综述论文</h5>
                  <p>系统性评述某一领域的研究进展、发展趋势和挑战。篇幅建议：10,000-15,000字。</p>
                </div>
              </div>
              
              <!-- <div class="submission-type">
                <div class="type-icon">
                  <i class="fas fa-bolt"></i>
                </div>
                <div class="type-content">
                  <h5>短篇论文</h5>
                  <p>研究简报、技术说明、快速通信等。篇幅建议：3,000-5,000字。</p>
                </div>
              </div> -->
              
              <div class="submission-type">
                <div class="type-icon">
                  <i class="fas fa-database"></i>
                </div>
                <div class="type-content">
                  <h5>数据集论文</h5>
                  <p>高质量数据集的创建、标注、验证和应用。需提供数据可用性声明。</p>
                </div>
              </div>
              
              <div class="submission-type">
                <div class="type-icon">
                  <i class="fas fa-tools"></i>
                </div>
                <div class="type-content">
                  <h5>工具与系统论文</h5>
                  <p>软件工具、算法库、系统平台的设计、实现和应用案例。</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- 期刊信息 -->
      <section class="section-card">
        <div class="section-header">
          <h2><i class="fas fa-info-circle me-2"></i> 期刊信息</h2>
        </div>
        
        <div class="section-content">
          <div class="journal-overview">
            <div class="overview-grid">
              <div class="overview-item">
                <div class="overview-icon">
                  <i class="fas fa-hashtag"></i>
                </div>
                <div class="overview-content">
                  <h4>ISSN号</h4>
                  <p><strong>印刷版</strong>: 1234-5678</p>
                  <p><strong>在线版</strong>: 2345-6789</p>
                </div>
              </div>
              
              <div class="overview-item">
                <div class="overview-icon">
                  <i class="fas fa-calendar-alt"></i>
                </div>
                <div class="overview-content">
                  <h4>创刊年份</h4>
                  <p>2018年</p>
                  <p class="text-muted">已出版6卷</p>
                </div>
              </div>
              
              <div class="overview-item">
                <div class="overview-icon">
                  <i class="fas fa-globe"></i>
                </div>
                <div class="overview-content">
                  <h4>出版频率</h4>
                  <p>月刊</p>
                  <p class="text-muted">每年12期</p>
                </div>
              </div>
              
              <!-- <div class="overview-item">
                <div class="overview-icon">
                  <i class="fas fa-language"></i>
                </div>
                <div class="overview-content">
                  <h4>出版语言</h4>
                  <p>英语</p>
                  <p class="text-muted">全球发行</p>
                </div>
              </div> -->
              
              <div class="overview-item">
                <div class="overview-icon">
                  <i class="fas fa-unlock"></i>
                </div>
                <div class="overview-content">
                  <h4>访问模式</h4>
                  <p>开放获取</p>
                  <p class="text-muted">CC BY 4.0许可</p>
                </div>
              </div>
              
              <div class="overview-item">
                <div class="overview-icon">
                  <i class="fas fa-database"></i>
                </div>
                <div class="overview-content">
                  <h4>索引收录</h4>
                  <p>SCI-E, Scopus, EI</p>
                  <p class="text-muted">DOAJ, CNKI</p>
                </div>
              </div>
            </div>
          </div>
          
          <div class="journal-metrics">
            <h4 class="mb-4">期刊关键指标</h4>
            <div class="metrics-grid">
              <div class="metric-card primary">
                <div class="metric-value">4.5</div>
                <div class="metric-label">影响因子 (2024)</div>
                <div class="metric-trend">
                  <i class="fas fa-arrow-up me-1"></i>较去年提升18.4%
                </div>
              </div>
              
              <div class="metric-card success">
                <div class="metric-value">15 天</div>
                <div class="metric-label">平均初审时间</div>
                <div class="metric-trend">快速处理，高效反馈</div>
              </div>
              
              <div class="metric-card info">
                <div class="metric-value">100%</div>
                <div class="metric-label">开源访问</div>
                <div class="metric-trend">全球免费阅读下载</div>
              </div>
              
              <div class="metric-card warning">
                <div class="metric-value">2.5 月</div>
                <div class="metric-label">接收至出版时间</div>
                <div class="metric-trend">在线优先出版加速传播</div>
              </div>
<!--               
              <div class="metric-card secondary">
                <div class="metric-value">3.2</div>
                <div class="metric-label">5年影响因子</div>
                <div class="metric-trend">稳定增长趋势</div>
              </div> -->
              
              <div class="metric-card danger">
                <div class="metric-value">45%</div>
                <div class="metric-label">录用率</div>
                <div class="metric-trend">严格质量控制</div>
              </div>
            </div>
          </div>
          
          <div class="publishing-timeline">
            <h4 class="mb-4">出版时间线</h4>
            <div class="timeline">
              <div class="timeline-item">
                <div class="timeline-marker">
                  <div class="marker-number">1</div>
                </div>
                <div class="timeline-content">
                  <h5>投稿与格式审核</h5>
                  <p>作者在线提交稿件，系统自动查重，编辑部进行格式审核（1-3个工作日）</p>
                </div>
              </div>
              
              <div class="timeline-item">
                <div class="timeline-marker">
                  <div class="marker-number">2</div>
                </div>
                <div class="timeline-content">
                  <h5>主编初审</h5>
                  <p>主编快速评估论文质量、创新性和期刊契合度（1-2周内决定是否送审）</p>
                </div>
              </div>
              
              <div class="timeline-item">
                <div class="timeline-marker">
                  <div class="marker-number">3</div>
                </div>
                <div class="timeline-content">
                  <h5>同行评审</h5>
                  <p>双盲评审，3-4位领域专家审稿，提供详细评审意见（4-6周返回意见）</p>
                </div>
              </div>
              
              <div class="timeline-item">
                <div class="timeline-marker">
                  <div class="marker-number">4</div>
                </div>
                <div class="timeline-content">
                  <h5>修改与再审</h5>
                  <p>作者根据评审意见修改论文，审稿人评估修改质量（1-2周）</p>
                </div>
              </div>
              
              <div class="timeline-item">
                <div class="timeline-marker">
                  <div class="marker-number">5</div>
                </div>
                <div class="timeline-content">
                  <h5>最终决定</h5>
                  <p>主编基于评审意见做出最终录用决定，通知作者（1周内）</p>
                </div>
              </div>
              
              <div class="timeline-item">
                <div class="timeline-marker">
                  <div class="marker-number">6</div>
                </div>
                <div class="timeline-content">
                  <h5>录用与出版</h5>
                  <p>作者签署版权协议，支付处理费，论文在线优先出版，正式排版后加入卷期</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- 编委成员 -->
      <section class="section-card">
        <div class="section-header">
          <h2><i class="fas fa-users me-2"></i> 编委成员</h2>
          <router-link to="/editorial-board" class="btn btn-outline">
            <i class="fas fa-users me-2"></i>查看完整编委名单
          </router-link>
        </div>
        
        <div class="section-content">
          <div class="editorial-board">
            <!-- 主编 -->
            <div class="board-section">
              <h4 class="board-title">
                <i class="fas fa-crown me-2"></i>主编
                <span class="board-count">2位</span>
              </h4>
              <div class="board-members">
                <div v-for="member in editorialBoard.filter(m => m.role === '主编')" :key="member.id" class="board-member-card">
                  <div class="member-header">
                    <h5>{{ member.name }}</h5>
                    <span class="member-role">主编</span>
                  </div>
                  <div class="member-info">
                    <p class="member-affiliation">
                      <i class="fas fa-university me-2"></i>{{ member.affiliation }}
                    </p>
                    <p class="member-expertise">
                      <i class="fas fa-graduation-cap me-2"></i>研究方向：{{ member.expertise }}
                    </p>
                  </div>
                  <div class="member-achievements">
                    <span class="achievement-tag">IEEE Fellow</span>
                    <span class="achievement-tag">ACM Distinguished Member</span>
                  </div>
                </div>
              </div>
            </div>
            
            <!-- 副主编 -->
            <div class="board-section">
              <h4 class="board-title">
                <i class="fas fa-user-tie me-2"></i>副主编
                <span class="board-count">3位</span>
              </h4>
              <div class="board-members">
                <div v-for="member in editorialBoard.filter(m => m.role === '副主编')" :key="member.id" class="board-member-card">
                  <div class="member-header">
                    <h5>{{ member.name }}</h5>
                    <span class="member-role">副主编</span>
                  </div>
                  <div class="member-info">
                    <p class="member-affiliation">
                      <i class="fas fa-university me-2"></i>{{ member.affiliation }}
                    </p>
                    <p class="member-expertise">
                      <i class="fas fa-graduation-cap me-2"></i>研究方向：{{ member.expertise }}
                    </p>
                  </div>
                  <div class="member-achievements">
                    <span class="achievement-tag">国家杰青</span>
                    <span class="achievement-tag">IEEE Senior Member</span>
                  </div>
                </div>
              </div>
            </div>
            
            <!-- 编委 -->
            <div class="board-section">
              <h4 class="board-title">
                <i class="fas fa-user-edit me-2"></i>编委
                <span class="board-count">{{ editorialBoard.filter(m => m.role === '编委').length }}位</span>
              </h4>
              <div class="board-members grid">
                <div v-for="member in editorialBoard.filter(m => m.role === '编委')" :key="member.id" class="board-member-card compact">
                  <div class="member-header">
                    <h5>{{ member.name }}</h5>
                  </div>
                  <div class="member-info">
                    <p class="member-affiliation">
                      <i class="fas fa-university me-2"></i>{{ member.affiliation.split(',')[0] }}
                    </p>
                    <p class="member-expertise">
                      <i class="fas fa-graduation-cap me-2"></i>{{ member.expertise.split('、')[0] }}
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- 期刊新闻动态 -->
      <section class="section-card">
        <div class="section-header">
          <h2><i class="fas fa-newspaper me-2"></i> 期刊新闻动态</h2>
          <div class="section-actions">
            <button class="btn btn-outline me-2" @click="refreshNews">
              <i class="fas fa-sync-alt me-2"></i>刷新新闻
            </button>
            <router-link to="/news" class="btn btn-primary">
              <i class="fas fa-list me-2"></i>查看所有新闻
            </router-link>
          </div>
        </div>
        
        <div class="section-content">
          <!-- 加载状态 -->
          <div v-if="newsLoading" class="loading-state">
            <div class="spinner"></div>
            <p>正在加载新闻...</p>
          </div>
          
          <!-- 新闻列表 -->
          <div v-else-if="journalNews.length > 0" class="news-grid">
            <div v-for="news in journalNews" :key="news.id" class="news-card" @click="showNewsDetail(news)">
              <div class="news-card-header">
                <span class="news-type" :style="getNewsTypeStyle(news.type)">{{ news.type }}</span>
                <span class="news-date">{{ formatNewsDate(news.date) }}</span>
              </div>
              <div class="news-card-body">
                <h5 class="news-title">{{ news.title }}</h5>
                <p class="news-summary">{{ news.summary }}</p>
                <div class="news-attachment" v-if="news.attachment">
                  <i class="fas fa-paperclip me-2"></i>
                  <span>{{ news.attachment.name }}</span>
                </div>
              </div>
              <div class="news-card-footer">
                <span class="read-more">点击查看详情 →</span>
              </div>
            </div>
          </div>
          
          <!-- 空状态 -->
          <div v-else class="empty-state">
            <div class="empty-icon">
              <i class="fas fa-newspaper"></i>
            </div>
            <h5>暂无新闻动态</h5>
            <p class="text-muted">暂时没有发布的新闻，请稍后再试</p>
            <button class="btn btn-outline mt-3" @click="refreshNews">
              <i class="fas fa-sync-alt me-2"></i>刷新
            </button>
          </div>
        </div>
      </section>

      <!-- 政策与指南 -->
      <section class="section-card">
        <div class="section-header">
          <h2><i class="fas fa-book me-2"></i> 政策与指南</h2>
          <router-link to="/guide" class="btn btn-primary">
            <i class="fas fa-graduation-cap me-2"></i>查看完整作者指南
          </router-link>
        </div>
        
        <div class="section-content">
          <div class="policies-grid">
            <div class="policy-card" @click="showPolicyDetail('ethical')">
              <div class="policy-icon">
                <i class="fas fa-balance-scale"></i>
              </div>
              <div class="policy-content">
                <h4>伦理政策</h4>
                <p>遵循国际出版伦理标准，确保研究的诚信性和透明度。</p>
                <div class="policy-tags">
                  <span class="policy-tag">作者责任</span>
                  <span class="policy-tag">研究诚信</span>
                  <span class="policy-tag">利益冲突</span>
                </div>
              </div>
              <div class="policy-action">
                <i class="fas fa-chevron-right"></i>
              </div>
            </div>
            
            <div class="policy-card" @click="showPolicyDetail('review')">
              <div class="policy-icon">
                <i class="fas fa-clipboard-check"></i>
              </div>
              <div class="policy-content">
                <h4>同行评审政策</h4>
                <p>严格的双盲评审流程，确保论文质量与学术标准。</p>
                <div class="policy-tags">
                  <span class="policy-tag">双盲评审</span>
                  <span class="policy-tag">审稿标准</span>
                  <span class="policy-tag">时间框架</span>
                </div>
              </div>
              <div class="policy-action">
                <i class="fas fa-chevron-right"></i>
              </div>
            </div>
            
            <div class="policy-card" @click="showPolicyDetail('openaccess')">
              <div class="policy-icon">
                <i class="fas fa-unlock"></i>
              </div>
              <div class="policy-content">
                <h4>开放获取政策</h4>
                <p>所有文章采用知识共享许可，支持免费阅读和分享。</p>
                <div class="policy-tags">
                  <span class="policy-tag">CC-BY许可</span>
                  <span class="policy-tag">开放数据</span>
                  <span class="policy-tag">重用政策</span>
                </div>
              </div>
              <div class="policy-action">
                <i class="fas fa-chevron-right"></i>
              </div>
            </div>
            
            <div class="policy-card" @click="showPolicyDetail('copyright')">
              <div class="policy-icon">
                <i class="fas fa-copyright"></i>
              </div>
              <div class="policy-content">
                <h4>版权政策</h4>
                <p>作者保留著作权，授予期刊非独占的发表和分发权利。</p>
                <div class="policy-tags">
                  <span class="policy-tag">作者版权</span>
                  <span class="policy-tag">分发许可</span>
                  <span class="policy-tag">转载政策</span>
                </div>
              </div>
              <div class="policy-action">
                <i class="fas fa-chevron-right"></i>
              </div>
            </div>
            
            <div class="policy-card" @click="showPolicyDetail('data')">
              <div class="policy-icon">
                <i class="fas fa-database"></i>
              </div>
              <div class="policy-content">
                <h4>数据共享政策</h4>
                <p>鼓励研究数据的开放共享，提高研究的可重复性。</p>
                <div class="policy-tags">
                  <span class="policy-tag">数据可用性</span>
                  <span class="policy-tag">存储库要求</span>
                  <span class="policy-tag">引用规范</span>
                </div>
              </div>
              <div class="policy-action">
                <i class="fas fa-chevron-right"></i>
              </div>
            </div>
            
            <div class="policy-card" @click="showPolicyDetail('author')">
              <div class="policy-icon">
                <i class="fas fa-user-edit"></i>
              </div>
              <div class="policy-content">
                <h4>作者指南</h4>
                <p>详细的论文格式要求、投稿流程和写作规范。</p>
                <div class="policy-tags">
                  <span class="policy-tag">格式模板</span>
                  <span class="policy-tag">投稿流程</span>
                  <span class="policy-tag">写作规范</span>
                </div>
              </div>
              <div class="policy-action">
                <i class="fas fa-chevron-right"></i>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- 联系部分 -->
      <section class="section-card contact-section">
        <div class="section-header">
          <h2><i class="fas fa-address-card me-2"></i> 联系我们</h2>
        </div>
        
        <div class="section-content">
          <div class="contact-grid">
            <div class="contact-card">
              <div class="contact-icon">
                <i class="fas fa-envelope"></i>
              </div>
              <div class="contact-content">
                <h4>编辑部</h4>
                <p><strong>邮箱</strong>: editor@iair-journal.org</p>
                <p><strong>电话</strong>: +1 (234) 567-8900</p>
                <p><strong>工作时间</strong>: 周一至周五 9:00-17:00 (UTC+8)</p>
              </div>
            </div>
            
            <div class="contact-card">
              <div class="contact-icon">
                <i class="fas fa-question-circle"></i>
              </div>
              <div class="contact-content">
                <h4>投稿咨询</h4>
                <p><strong>投稿系统支持</strong>: submission@iair-journal.org</p>
                <p><strong>技术问题</strong>: support@iair-journal.org</p>
                <p><strong>格式审核</strong>: format@iair-journal.org</p>
              </div>
            </div>
            
            <div class="contact-card">
              <div class="contact-icon">
                <i class="fas fa-map-marker-alt"></i>
              </div>
              <div class="contact-content">
                <h4>办公地址</h4>
                <p>人工智能期刊编辑部</p>
                <p>北京市海淀区清华大学</p>
                <p>邮政编码: 100084</p>
                <p>中国</p>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- 新闻详情模态框 -->
      <div v-if="selectedNews" class="modal-overlay" @click="closeNewsDetail">
        <div class="modal-content" @click.stop>
          <div class="modal-header">
            <h3>{{ selectedNews.title }}</h3>
            <button class="modal-close" @click="closeNewsDetail">
              <i class="fas fa-times"></i>
            </button>
          </div>
          <div class="modal-body">
            <div class="news-meta">
              <span class="news-type-badge" :style="getNewsTypeStyle(selectedNews.type)">
                {{ selectedNews.type }}
              </span>
              <span class="news-date">{{ formatFullDate(selectedNews.date) }}</span>
            </div>
            <div class="news-content" v-html="selectedNews.content"></div>
            <div v-if="selectedNews.attachment" class="news-attachment">
              <h5><i class="fas fa-paperclip me-2"></i>附件</h5>
              <a :href="selectedNews.attachment.url" class="attachment-link">
                <i class="fas fa-file-pdf me-2"></i>{{ selectedNews.attachment.name }}
              </a>
            </div>
          </div>
        </div>
      </div>

      <!-- 政策详情模态框 -->
      <div v-if="selectedPolicy" class="modal-overlay" @click="closePolicyDetail">
        <div class="modal-content policy-modal" @click.stop>
          <div class="modal-header">
            <h3>{{ selectedPolicy.title }}</h3>
            <button class="modal-close" @click="closePolicyDetail">
              <i class="fas fa-times"></i>
            </button>
          </div>
          <div class="modal-body">
            <div class="policy-content" v-html="selectedPolicy.content"></div>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script setup>
import { ref, onMounted } from 'vue'

// 响应式数据
const newsLoading = ref(false)
const selectedNews = ref(null)
const selectedPolicy = ref(null)

// 编委数据
const editorialBoard = ref([
  {
    id: 1,
    name: 'Sarah Johnson 教授',
    role: '主编',
    affiliation: '斯坦福大学人工智能实验室, 美国',
    expertise: '机器学习、自然语言处理'
  },
  {
    id: 2,
    name: '张伟 教授',
    role: '主编',
    affiliation: '清华大学人工智能研究院, 中国',
    expertise: '计算机视觉、深度学习'
  },
  {
    id: 3,
    name: 'Maria Rodriguez 博士',
    role: '副主编',
    affiliation: '剑桥大学机器学习研究所, 英国',
    expertise: '强化学习、机器人技术'
  },
  {
    id: 4,
    name: 'Kenji Tanaka 教授',
    role: '副主编',
    affiliation: '东京大学信息科学系, 日本',
    expertise: '可解释AI、AI伦理'
  },
  {
    id: 5,
    name: '李娜 教授',
    role: '副主编',
    affiliation: '北京大学计算机科学系, 中国',
    expertise: '自然语言处理、知识图谱'
  },
  {
    id: 6,
    name: '王明 教授',
    role: '编委',
    affiliation: '浙江大学人工智能研究所, 中国',
    expertise: '医疗AI、生物信息学'
  },
  {
    id: 7,
    name: 'Chen Li 教授',
    role: '编委',
    affiliation: '麻省理工学院计算机科学系, 美国',
    expertise: '联邦学习、隐私保护'
  },
  {
    id: 8,
    name: 'Alex Kim 教授',
    role: '编委',
    affiliation: '首尔国立大学工程学院, 韩国',
    expertise: '智能制造、工业AI'
  },
  {
    id: 9,
    name: '刘洋 教授',
    role: '编委',
    affiliation: '中国科学院自动化研究所, 中国',
    expertise: '机器人学、自主系统'
  },
  {
    id: 10,
    name: '周敏 教授',
    role: '编委',
    affiliation: '复旦大学计算机科学学院, 中国',
    expertise: '计算机视觉、图像处理'
  },
  {
    id: 11,
    name: '吴刚 教授',
    role: '编委',
    affiliation: '上海交通大学人工智能研究院, 中国',
    expertise: '自然语言处理、大语言模型'
  },
  {
    id: 12,
    name: '郑丽 教授',
    role: '编委',
    affiliation: '南京大学人工智能学院, 中国',
    expertise: '机器学习、数据挖掘'
  }
])

// 期刊新闻数据
const journalNews = ref([])

// 政策数据（保持原有的policyDetails不变）
const policyDetails = ref({
  ethical: {
    title: '伦理政策',
    content: `
      <h4>研究伦理原则</h4>
      <p>本刊严格遵守国际出版伦理委员会(COPE)的指导原则，确保所有研究遵循最高的伦理标准。</p>
      
      <h4>作者责任</h4>
      <ul>
        <li><strong>原创性</strong>：投稿必须为原创作品，未在其他地方发表</li>
        <li><strong>署名权</strong>：作者署名应基于对研究的实质性贡献</li>
        <li><strong>利益冲突</strong>：作者需披露可能影响研究的利益关系</li>
        <li><strong>数据真实性</strong>：研究数据必须真实、准确、完整</li>
      </ul>
      
      <h4>研究诚信</h4>
      <p>严禁以下行为：抄袭、伪造数据、重复发表、不当署名等。如发现学术不端行为，将按COPE指南处理。</p>
      
      <h4>人类和动物研究</h4>
      <p>涉及人类受试者或动物的研究必须获得伦理委员会批准，并在论文中提供伦理批准编号。</p>
    `
  },
  review: {
    title: '同行评审政策',
    content: `
      <h4>评审流程</h4>
      <p>本刊采用严格的双盲同行评审制度，确保评审的公正性和客观性。</p>
      
      <h4>评审阶段</h4>
      <ol>
        <li><strong>初审</strong>：主编初步评估论文是否符合期刊范围和质量要求</li>
        <li><strong>送审</strong>：通过初审的稿件送交2-4位领域专家评审</li>
        <li><strong>评审周期</strong>：评审通常在4-6周内完成</li>
        <li><strong>评审意见</strong>：审稿人提供详细的评审意见和改进建议</li>
        <li><strong>决定</strong>：主编基于评审意见做出录用决定</li>
      </ol>
      
      <h4>评审标准</h4>
      <ul>
        <li><strong>创新性</strong>：研究是否提出了新的观点、方法或发现</li>
        <li><strong>科学性</strong>：研究方法是否合理，数据是否充分</li>
        <li><strong>重要性</strong>：研究结果对领域发展的贡献程度</li>
        <li><strong>可重复性</strong>：研究方法和结果是否可重复验证</li>
        <li><strong>写作质量</strong>：论文结构是否清晰，表达是否准确</li>
      </ul>
    `
  },
  openaccess: {
    title: '开放获取政策',
    content: `
      <h4>开放获取模式</h4>
      <p>本刊采用金色开放获取模式，所有文章在发表后立即免费向读者开放。</p>
      
      <h4>许可协议</h4>
      <p>所有文章采用<strong>知识共享署名许可(CC BY 4.0)</strong>，允许：</p>
      <ul>
        <li>自由分享：在任何媒介以任何形式复制、分发文章</li>
        <li>自由改编：对文章进行转换和改编，甚至用于商业目的</li>
        <li>唯一要求：必须给出适当的署名，提供许可证链接</li>
      </ul>
      
      <h4>文章处理费(APC)</h4>
      <p>为支持开放获取，本刊收取文章处理费：</p>
      <ul>
        <li>标准论文：1500美元</li>
        <li>综述论文：2000美元</li>
        <li>减免政策：对来自低收入国家的作者提供费用减免</li>
      </ul>
      
      <h4>存档政策</h4>
      <p>所有文章永久存档在多个数字仓储中，包括：</p>
      <ul>
        <li>本刊网站</li>
        <li>DOAJ（开放获取期刊目录）</li>
        <li>Crossref</li>
        <li>CLOCKSS/LOCKSS系统</li>
      </ul>
    `
  },
  copyright: {
    title: '版权政策',
    content: `
      <h4>版权归属</h4>
      <p>作者保留文章的著作权，授予本刊以下权利：</p>
      <ul>
        <li>非独占的发表和分发权</li>
        <li>将文章纳入期刊的权利</li>
        <li>在各种媒介上传播文章的权利</li>
      </ul>
      
      <h4>作者权利</h4>
      <p>在文章发表后，作者有权：</p>
      <ul>
        <li>在个人网站或机构仓储中发布文章</li>
        <li>在教学、学术交流中使用文章</li>
        <li>在后续作品中引用文章内容</li>
        <li>将文章翻译成其他语言出版</li>
      </ul>
      
      <h4>读者权利</h4>
      <p>根据CC BY 4.0许可，读者有权：</p>
      <ul>
        <li>免费阅读、下载、分享文章</li>
        <li>在注明出处的前提下重用文章内容</li>
        <li>为研究、教学目的使用文章</li>
      </ul>
      
      <h4>许可协议签署</h4>
      <p>录用文章的作者需签署出版许可协议，确认授权条款。</p>
    `
  },
  data: {
    title: '数据共享政策',
    content: `
      <h4>数据共享原则</h4>
      <p>本刊鼓励作者在研究论文中共享支持研究结论的数据。</p>
      
      <h4>数据可用性声明</h4>
      <p>所有文章必须包含数据可用性声明，说明：</p>
      <ul>
        <li>研究数据是否可获取</li>
        <li>数据存储的位置和访问方式</li>
        <li>数据使用的限制条件（如果有）</li>
      </ul>
      
      <h4>数据存储库要求</h4>
      <p>推荐使用以下数据存储库：</p>
      <ul>
        <li><strong>通用数据</strong>：Figshare, Zenodo, Dryad</li>
        <li><strong>特定领域</strong>：Protein Data Bank, Gene Expression Omnibus</li>
        <li><strong>代码</strong>：GitHub, GitLab, Bitbucket</li>
      </ul>
      
      <h4>数据引用规范</h4>
      <p>数据应获得永久标识符（如DOI）并按规范格式引用。</p>
      
      <h4>伦理考量</h4>
      <p>涉及敏感数据时，需遵守相关隐私保护法规。</p>
    `
  },
  author: {
    title: '作者指南',
    content: `
      <h4>论文格式要求</h4>
      <p>投稿请遵循以下格式规范：</p>
      <ul>
        <li><strong>文件格式</strong>：Word或LaTeX文档</li>
        <li><strong>长度限制</strong>：研究论文不超过8000字</li>
        <li><strong>结构要求</strong>：标题、摘要、关键词、引言、方法、结果、讨论、参考文献</li>
        <li><strong>参考文献</strong>：使用APA引用格式</li>
      </ul>
      
      <h4>投稿流程</h4>
      <ol>
        <li>注册/登录投稿系统</li>
        <li>填写稿件信息和作者信息</li>
        <li>上传稿件文件（正文、图表、补充材料）</li>
        <li>提交推荐审稿人（可选）</li>
        <li>确认提交</li>
        <li>通过邮件接收稿件编号和进度通知</li>
      </ol>
      
      <h4>写作规范</h4>
      <ul>
        <li>使用清晰、准确的语言</li>
        <li>避免使用缩写，首次出现时需全称</li>
        <li>图表需有自明的标题和说明</li>
        <li>数学公式需正确排版</li>
        <li>致谢部分说明资助来源</li>
      </ul>
      
      <h4>模板下载</h4>
      <p>提供Word和LaTeX模板，请从作者指南页面下载。</p>
    `
  }
})

// 方法（保持原有的方法不变）
const showNewsDetail = (news) => {
  selectedNews.value = news
}

const closeNewsDetail = () => {
  selectedNews.value = null
}

const showPolicyDetail = (policyKey) => {
  selectedPolicy.value = {
    title: policyDetails.value[policyKey].title,
    content: policyDetails.value[policyKey].content
  }
}

const closePolicyDetail = () => {
  selectedPolicy.value = null
}

const getNewsTypeStyle = (type) => {
  const styles = {
    '新闻': { background: '#e3f2fd', color: '#1565c0' },
    '公告': { background: '#fff3e0', color: '#ef6c00' },
    '特刊': { background: '#e8f5e9', color: '#2e7d32' },
    '会议': { background: '#f3e5f5', color: '#7b1fa2' }
  }
  return styles[type] || { background: '#f5f5f5', color: '#616161' }
}

const formatNewsDate = (dateString) => {
  const date = new Date(dateString)
  const now = new Date()
  const diffTime = Math.abs(now - date)
  const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24))
  
  if (diffDays === 0) return '今天'
  if (diffDays === 1) return '昨天'
  if (diffDays < 7) return `${diffDays}天前`
  
  return date.toLocaleDateString('zh-CN', {
    month: 'short',
    day: 'numeric'
  })
}

const formatFullDate = (dateString) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

// 从数据库加载新闻数据
const loadJournalNews = async () => {
  newsLoading.value = true
  try {
    console.log('开始加载期刊新闻数据...')
    
    // 从后端API加载新闻
    const response = await fetch('/editorial-office/news/list?page=1&pageSize=6&status=Published')
    
    if (response.ok) {
      const result = await response.json()
      console.log('期刊新闻API响应数据:', result)
      
      if (result.code === 200 && result.data && result.data.data) {
        // 转换数据格式
        journalNews.value = result.data.data
          .filter(item => item.publishStatus === 'Published')
          .slice(0, 6) // 只显示最新6条
          .map(item => ({
            id: item.id,
            title: item.title,
            type: getNewsTypeText(item.newsType || item.news_type || 'REGULAR_NEWS'),
            date: item.publishDate || item.publish_date,
            summary: item.content ? item.content.replace(/<[^>]*>/g, '').substring(0, 100) + '...' : '暂无摘要',
            content: item.content || '<p>暂无详细内容</p>',
            attachment: item.attachmentPath ? {
              name: item.attachmentPath.split('/').pop(),
              url: `/download?path=${encodeURIComponent(item.attachmentPath)}`
            } : null
          }))
      } else {
        // 如果API没有返回数据，使用模拟数据
        journalNews.value = getMockNewsData()
      }
    } else {
      console.error('期刊新闻API请求失败:', response.status)
      // 使用模拟数据
      journalNews.value = getMockNewsData()
    }
  } catch (error) {
    console.error('加载期刊新闻数据失败:', error)
    // 使用模拟数据
    journalNews.value = getMockNewsData()
  } finally {
    newsLoading.value = false
  }
}

const getNewsTypeText = (type) => {
  const types = {
    'REGULAR_NEWS': '新闻',
    'CALL_FOR_PAPERS': '征稿',
    'SPECIAL_ISSUE': '特刊',
    'CONFERENCE': '会议',
    'ANNOUNCEMENT': '公告'
  }
  return types[type] || '新闻'
}

// 模拟新闻数据函数
const getMockNewsData = () => {
  return [
    {
      id: 1,
      title: '本刊2024年影响因子提升至4.5，创历史新高',
      type: '新闻',
      date: '2024-11-20T10:30:00',
      summary: '科睿唯安最新发布的期刊引证报告显示，本刊影响因子从3.8提升至4.5，在人工智能领域期刊中排名显著提升。',
      content: '<p>我们非常高兴地宣布，根据科睿唯安最新发布的2024年期刊引证报告，《国际人工智能研究》的影响因子已提升至4.5，较去年的3.8有了显著增长。</p><p>这一成绩的取得离不开作者、审稿人和编委的大力支持。我们将继续致力于出版高质量的人工智能研究成果，推动学术进步和技术创新。</p>',
      attachment: null
    },
    {
      id: 2,
      title: '2025年特刊征稿：人工智能促进可持续发展',
      type: '特刊',
      date: '2024-11-15T14:20:00',
      summary: '本刊将出版"AI for Sustainable Development"特刊，现面向全球征集相关研究论文，投稿截止日期：2025年6月30日。',
      content: '<p>本刊计划于2025年出版特刊"AI for Sustainable Development"，聚焦人工智能技术在实现联合国可持续发展目标中的应用。</p><p>征稿主题包括但不限于：气候变化预测、智能能源管理、可持续城市、精准农业、环境保护等。欢迎全球学者投稿高质量论文。</p>',
      attachment: {
        name: 'cfp_sustainability.pdf',
        url: '/download?path=/upload/attachments/cfp_sustainability.pdf'
      }
    },
    {
      id: 3,
      title: '新增两位国际知名学者加入编委会',
      type: '公告',
      date: '2024-11-10T09:15:00',
      summary: '欢迎来自麻省理工学院和剑桥大学的新编委成员，他们将在各自专业领域为期刊发展贡献力量。',
      content: '<p>我们非常荣幸地宣布，麻省理工学院计算机科学系的Chen Li教授和剑桥大学机器学习研究所的Maria Rodriguez博士已加入本刊编委会。</p><p>两位教授在联邦学习和强化学习领域享有国际声誉，他们的加入将进一步提升期刊的学术质量和国际影响力。</p>',
      attachment: null
    },
    {
      id: 4,
      title: '投稿系统升级通知，新增AI辅助功能',
      type: '公告',
      date: '2024-11-05T16:45:00',
      summary: '本刊投稿系统已完成升级，新增自动查重、在线校对等功能，提供更流畅的投稿体验。',
      content: '<p>为了提高投稿效率和用户体验，我们对投稿系统进行了全面升级。新系统的主要改进包括：</p><ul><li>集成iThenticate查重系统</li><li>增强的在线校对工具</li><li>更直观的用户界面</li><li>移动端适配</li></ul><p>欢迎作者体验新系统并提出宝贵意见。</p>',
      attachment: null
    },
    {
      id: 5,
      title: '国际人工智能大会2024圆满落幕',
      type: '会议',
      date: '2024-10-28T09:30:00',
      summary: '本刊主办的国际人工智能大会2024在北京成功举办，吸引了来自全球的500多位学者参会。',
      content: '<p>由本刊主办的国际人工智能大会2024于10月25-27日在北京成功举办。大会邀请了20多位国际知名学者做主题报告，设置了30多个专题分会，共接收论文投稿800余篇。</p>',
      attachment: null
    },
    {
      id: 6,
      title: '本刊与Springer Nature达成战略合作',
      type: '新闻',
      date: '2024-10-20T11:20:00',
      summary: '本刊与Springer Nature出版集团达成战略合作协议，共同推动人工智能研究成果的全球传播。',
      content: '<p>我们非常荣幸地宣布，《国际人工智能研究》期刊与Springer Nature出版集团达成战略合作协议。通过此次合作，本刊的文章将获得更广泛的国际传播和更高的可见度。</p>',
      attachment: null
    }
  ]
}

const refreshNews = () => {
  loadJournalNews()
}

// 生命周期钩子
onMounted(() => {
  loadJournalNews()
})
</script>

<style scoped>
.journal-page {
  padding: 40px 0;
}

.container {
  max-width: 1400px;
  width: 98%;
  margin: 0 auto;
  padding: 0 15px;
}

.journal-hero {
  text-align: center;
  margin-bottom: 50px;
  padding: 40px 0;
  border-bottom: 2px solid #e0e0e0;
}

.journal-hero h1 {
  font-size: 42px;
  color: #2c3e50;
  margin-bottom: 20px;
  font-weight: 700;
}

.subtitle {
  font-size: 20px;
  color: #7f8c8d;
  max-width: 800px;
  margin: 0 auto;
  line-height: 1.6;
}

/* 通用卡片样式 */
.section-card {
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 12px;
  padding: 40px;
  margin-bottom: 40px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.section-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 30px rgba(0,0,0,0.1);
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 2px solid #f0f0f0;
}

.section-header h2 {
  font-size: 28px;
  color: #2c3e50;
  display: flex;
  align-items: center;
  margin: 0;
}

.section-content {
  margin-top: 30px;
}

/* 宗旨与投稿范围样式 */
.content-block {
  margin-bottom: 40px;
}

.content-block h4 {
  font-size: 22px;
  color: #3498db;
  margin-bottom: 20px;
  padding-left: 15px;
  border-left: 4px solid #3498db;
}

.research-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 25px;
  margin-top: 25px;
}

.research-category {
  background: #f8f9fa;
  padding: 25px;
  border-radius: 10px;
  border: 1px solid #e0e0e0;
}

.research-category h5 {
  font-size: 18px;
  color: #2c3e50;
  margin-bottom: 15px;
  display: flex;
  align-items: center;
}

.research-category ul {
  margin-left: 20px;
}

.research-category li {
  color: #555;
  margin-bottom: 8px;
  font-size: 14px;
  line-height: 1.5;
}

.submission-types {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 25px;
}

.submission-type {
  display: flex;
  gap: 20px;
  padding: 25px;
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 10px;
  transition: all 0.3s ease;
}

.submission-type:hover {
  border-color: #3498db;
  box-shadow: 0 5px 15px rgba(0,0,0,0.05);
}

.type-icon {
  flex-shrink: 0;
  width: 60px;
  height: 60px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 24px;
}

.type-content h5 {
  font-size: 18px;
  color: #2c3e50;
  margin-bottom: 10px;
}

.type-content p {
  color: #666;
  line-height: 1.6;
  font-size: 14px;
}

/* 期刊信息样式 */
.overview-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 25px;
  margin-bottom: 40px;
}

.overview-item {
  display: flex;
  align-items: flex-start;
  gap: 15px;
  padding: 20px;
  background: #f8f9fa;
  border-radius: 10px;
}

.overview-icon {
  width: 50px;
  height: 50px;
  background: #3498db;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 20px;
  flex-shrink: 0;
}

.overview-content h4 {
  font-size: 18px;
  color: #2c3e50;
  margin-bottom: 8px;
}

.overview-content p {
  color: #555;
  margin-bottom: 4px;
  font-size: 14px;
}

.text-muted {
  color: #7f8c8d;
  font-size: 13px;
}

.metrics-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 25px;
  margin: 30px 0;
}

.metric-card {
  padding: 30px;
  border-radius: 10px;
  text-align: center;
  color: white;
}

.metric-card.primary { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
.metric-card.success { background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%); }
.metric-card.info { background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%); }
.metric-card.warning { background: linear-gradient(135deg, #fa709a 0%, #fee140 100%); }
.metric-card.secondary { background: linear-gradient(135deg, #a8edea 0%, #fed6e3 100%); color: #333; }
.metric-card.danger { background: linear-gradient(135deg, #ff9a9e 0%, #fad0c4 100%); color: #333; }

.metric-value {
  font-size: 36px;
  font-weight: 700;
  margin-bottom: 8px;
}

.metric-label {
  font-size: 16px;
  margin-bottom: 8px;
  opacity: 0.9;
}

.metric-trend {
  font-size: 13px;
  opacity: 0.8;
}

.publishing-timeline {
  margin-top: 40px;
}

.timeline {
  position: relative;
  padding-left: 30px;
}

.timeline::before {
  content: '';
  position: absolute;
  left: 20px;
  top: 0;
  bottom: 0;
  width: 2px;
  background: #e0e0e0;
}

.timeline-item {
  position: relative;
  margin-bottom: 30px;
  display: flex;
  align-items: flex-start;
}

.timeline-marker {
  position: absolute;
  left: -30px;
  top: 0;
  width: 40px;
  height: 40px;
  background: #3498db;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: 600;
  z-index: 1;
}

.marker-number {
  font-size: 18px;
}

.timeline-content {
  margin-left: 20px;
  flex: 1;
}

.timeline-content h5 {
  font-size: 18px;
  color: #2c3e50;
  margin-bottom: 10px;
}

.timeline-content p {
  color: #666;
  line-height: 1.6;
  font-size: 14px;
}

/* 编委成员样式 */
.editorial-board {
  margin-top: 20px;
}

.board-section {
  margin-bottom: 40px;
}

.board-title {
  font-size: 22px;
  color: #2c3e50;
  margin-bottom: 25px;
  display: flex;
  align-items: center;
  padding-bottom: 10px;
  border-bottom: 2px solid #f0f0f0;
}

.board-count {
  margin-left: 15px;
  background: #e8f4fc;
  color: #3498db;
  padding: 4px 12px;
  border-radius: 15px;
  font-size: 14px;
  font-weight: 500;
}

.board-members {
  display: grid;
  gap: 25px;
}

.board-members.grid {
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
}

.board-member-card {
  padding: 25px;
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 10px;
  transition: all 0.3s ease;
}

.board-member-card:hover {
  border-color: #3498db;
  box-shadow: 0 5px 15px rgba(0,0,0,0.05);
  transform: translateY(-3px);
}

.board-member-card.compact {
  padding: 20px;
}

.member-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.member-header h5 {
  font-size: 18px;
  color: #2c3e50;
  margin: 0;
}

.member-role {
  background: #e8f4fc;
  color: #3498db;
  padding: 4px 12px;
  border-radius: 15px;
  font-size: 13px;
  font-weight: 500;
}

.member-info {
  margin-bottom: 15px;
}

.member-affiliation,
.member-expertise {
  color: #666;
  margin-bottom: 8px;
  font-size: 14px;
  display: flex;
  align-items: flex-start;
  line-height: 1.5;
}

.member-achievements {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.achievement-tag {
  background: #f0f0f0;
  color: #666;
  padding: 4px 10px;
  border-radius: 12px;
  font-size: 12px;
}

/* 新闻动态样式 */
.section-actions {
  display: flex;
  gap: 10px;
}

.news-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
  gap: 25px;
}

.news-card {
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 10px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.news-card:hover {
  border-color: #3498db;
  box-shadow: 0 8px 25px rgba(0,0,0,0.1);
  transform: translateY(-5px);
}

.news-card-header {
  padding: 15px 20px;
  background: #f8f9fa;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.news-type {
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 600;
}

.news-date {
  color: #7f8c8d;
  font-size: 12px;
}

.news-card-body {
  padding: 20px;
  flex: 1;
}

.news-title {
  font-size: 18px;
  color: #2c3e50;
  margin-bottom: 12px;
  line-height: 1.4;
}

.news-summary {
  color: #666;
  line-height: 1.6;
  margin-bottom: 15px;
  font-size: 14px;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.news-attachment {
  display: flex;
  align-items: center;
  color: #7f8c8d;
  font-size: 13px;
}

.news-card-footer {
  padding: 15px 20px;
  border-top: 1px solid #f0f0f0;
}

.read-more {
  color: #3498db;
  font-weight: 500;
  font-size: 14px;
}

.loading-state {
  text-align: center;
  padding: 60px 0;
}

.spinner {
  width: 50px;
  height: 50px;
  border: 4px solid #f3f3f3;
  border-top: 4px solid #3498db;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin: 0 auto 20px;
}

.empty-state {
  text-align: center;
  padding: 60px 0;
}

.empty-icon {
  width: 80px;
  height: 80px;
  background: #f8f9fa;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 20px;
  color: #7f8c8d;
  font-size: 32px;
}

.empty-state h5 {
  color: #2c3e50;
  margin-bottom: 10px;
}

/* 政策与指南样式 */
.policies-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
  gap: 25px;
}

.policy-card {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 25px;
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.policy-card:hover {
  border-color: #3498db;
  box-shadow: 0 5px 15px rgba(0,0,0,0.05);
  transform: translateY(-3px);
}

.policy-icon {
  width: 60px;
  height: 60px;
  background: #f0f7ff;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #3498db;
  font-size: 24px;
  flex-shrink: 0;
}

.policy-content {
  flex: 1;
}

.policy-content h4 {
  font-size: 18px;
  color: #2c3e50;
  margin-bottom: 10px;
}

.policy-content p {
  color: #666;
  margin-bottom: 12px;
  line-height: 1.5;
  font-size: 14px;
}

.policy-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.policy-tag {
  background: #f0f0f0;
  color: #666;
  padding: 4px 10px;
  border-radius: 12px;
  font-size: 12px;
}

.policy-action {
  color: #3498db;
  font-size: 18px;
}

/* 联系部分样式 */
.contact-section {
  background: linear-gradient(135deg, #2c3e50 0%, #4a6491 100%);
  color: white;
  border: none;
}

.contact-section .section-header h2 {
  color: white;
}

.contact-section .btn-primary {
  background: white;
  color: #2c3e50;
  border-color: white;
}

.contact-section .btn-primary:hover {
  background: rgba(255,255,255,0.9);
  border-color: rgba(255,255,255,0.9);
}

.contact-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 30px;
}

.contact-card {
  display: flex;
  align-items: flex-start;
  gap: 20px;
  padding: 30px;
  background: rgba(255,255,255,0.1);
  border-radius: 10px;
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255,255,255,0.2);
}

.contact-icon {
  width: 60px;
  height: 60px;
  background: rgba(255,255,255,0.2);
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 24px;
  flex-shrink: 0;
}

.contact-content h4 {
  font-size: 20px;
  color: white;
  margin-bottom: 15px;
}

.contact-content p {
  color: rgba(255,255,255,0.9);
  margin-bottom: 8px;
  line-height: 1.5;
}

.contact-content strong {
  display: inline-block;
  min-width: 80px;
  color: white;
}

/* 按钮样式 */
.btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 10px 20px;
  font-size: 14px;
  font-weight: 500;
  text-decoration: none;
  border-radius: 6px;
  transition: all 0.3s;
  cursor: pointer;
  border: 2px solid transparent;
}

.btn-outline {
  background: transparent;
  color: #3498db;
  border-color: #3498db;
}

.btn-outline:hover {
  background: #3498db;
  color: white;
}

.btn-primary {
  background: #3498db;
  color: white;
  border-color: #3498db;
}

.btn-primary:hover {
  background: #2980b9;
  border-color: #2980b9;
}

.me-2 {
  margin-right: 8px;
}

.mb-4 {
  margin-bottom: 20px;
}

.mt-3 {
  margin-top: 15px;
}

.text-center {
  text-align: center;
}

.text-muted {
  color: #7f8c8d;
}

/* 模态框样式（保持原有的模态框样式不变） */
.modal-overlay {
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
}

.modal-content {
  background: white;
  border-radius: 12px;
  max-width: 800px;
  width: 100%;
  max-height: 85vh;
  overflow-y: auto;
  animation: slideUp 0.3s ease;
}

.policy-modal {
  max-width: 700px;
}

.modal-header {
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
}

.modal-header h3 {
  margin: 0;
  color: #333;
  font-size: 22px;
  line-height: 1.4;
}

.modal-close {
  background: none;
  border: none;
  font-size: 24px;
  color: #999;
  cursor: pointer;
  padding: 5px;
  transition: color 0.2s;
}

.modal-close:hover {
  color: #333;
}

.modal-body {
  padding: 30px;
}

.news-meta {
  display: flex;
  gap: 15px;
  margin-bottom: 25px;
  flex-wrap: wrap;
}

.news-type-badge {
  padding: 6px 12px;
  border-radius: 15px;
  font-size: 14px;
  font-weight: 600;
}

.news-date {
  color: #666;
  font-size: 14px;
  display: flex;
  align-items: center;
  gap: 5px;
}

.news-content {
  line-height: 1.8;
  color: #333;
  font-size: 16px;
  margin-bottom: 30px;
}

.news-content h4 {
  color: #2c3e50;
  margin: 25px 0 15px 0;
}

.news-content ul, .news-content ol {
  margin-left: 20px;
  margin-bottom: 15px;
}

.news-content li {
  margin-bottom: 8px;
}

.news-attachment {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 8px;
  border: 1px solid #eee;
}

.news-attachment h5 {
  margin: 0 0 15px 0;
  color: #333;
  display: flex;
  align-items: center;
}

.attachment-link {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px 15px;
  background: white;
  border-radius: 6px;
  border: 1px solid #ddd;
  color: #333;
  text-decoration: none;
  transition: all 0.2s;
}

.attachment-link:hover {
  background: #f5f5f5;
  border-color: #3498db;
}

.policy-content {
  line-height: 1.8;
  color: #333;
}

.policy-content h4 {
  color: #2c3e50;
  margin: 25px 0 15px 0;
  font-size: 18px;
}

.policy-content p {
  margin-bottom: 15px;
}

.policy-content ul, .policy-content ol {
  margin-left: 25px;
  margin-bottom: 20px;
}

.policy-content li {
  margin-bottom: 8px;
}

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

/* 响应式设计 */
@media (max-width: 1200px) {
  .research-grid {
    grid-template-columns: 1fr;
  }
  
  .news-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .policies-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .journal-page {
    padding: 20px 0;
  }
  
  .journal-hero h1 {
    font-size: 32px;
  }
  
  .subtitle {
    font-size: 16px;
  }
  
  .section-card {
    padding: 25px;
  }
  
  .section-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 15px;
  }
  
  .section-header h2 {
    font-size: 24px;
  }
  
  .research-grid {
    grid-template-columns: 1fr;
  }
  
  .submission-types {
    grid-template-columns: 1fr;
  }
  
  .overview-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .metrics-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .board-members.grid {
    grid-template-columns: 1fr;
  }
  
  .news-grid {
    grid-template-columns: 1fr;
  }
  
  .policies-grid {
    grid-template-columns: 1fr;
  }
  
  .contact-grid {
    grid-template-columns: 1fr;
  }
  
  .policy-card,
  .submission-type {
    flex-direction: column;
    gap: 15px;
  }
}

@media (max-width: 480px) {
  .container {
    padding: 0 10px;
  }
  
  .journal-hero {
    padding: 30px 0;
  }
  
  .section-card {
    padding: 20px;
  }
  
  .overview-grid {
    grid-template-columns: 1fr;
  }
  
  .metrics-grid {
    grid-template-columns: 1fr;
  }
}
</style>
