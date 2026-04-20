<template>
  <div class="login-page">
    <div class="login-container">
      <div class="login-header">
        <h1>登录IAIR期刊系统</h1>
        <p>访问您的账户以提交和管理论文</p>
      </div>

      <div class="login-content">
        <!-- 角色选择 -->
        <div class="role-selection">
          <div class="role-options">
            <button 
              v-for="role in roles" 
              :key="role.id"
              :class="['role-btn', { 'active': selectedRole === role.id }]"
              @click="selectRole(role.id)"
            >
              <span class="role-icon">{{ role.icon }}</span>
              <span class="role-name">{{ role.name }}</span>
            </button>
          </div>
        </div>

        <!-- 登录表单 -->
        <form @submit.prevent="handleLogin" class="login-form">
          <div class="form-group">
            <label for="username">用户名</label>
            <input
              type="text"
              id="username"
              v-model="loginData.username"
              placeholder="请输入用户名"
              required
            />
          </div>

          <div class="form-group">
            <label for="password">密码</label>
            <input
              type="password"
              id="password"
              v-model="loginData.password"
              placeholder="请输入密码"
              required
            />
          </div>

          <div class="form-options">
            <label class="checkbox">
              <input type="checkbox" v-model="rememberMe" />
              <span>记住我</span>
            </label>
            <router-link to="/forgot-password" class="forgot-link">忘记密码？</router-link>
          </div>

          <button type="submit" class="btn btn-primary btn-block" :disabled="loading">
            {{ loading ? '登录中...' : '登录' }}
          </button>

          <div v-if="error" class="error-message">
            {{ error }}
          </div>
        </form>

        <!-- 其他登录方式 -->
        <div class="alternative-login">
          <div class="divider">
            <span>或通过以下方式登录</span>
          </div>
          <div class="alt-options">
            <button class="alt-btn orcid-btn">
              <span class="alt-icon">🔗</span>
              <span>ORCID ID</span>
            </button>
            <button class="alt-btn institution-btn">
              <span class="alt-icon">🏛️</span>
              <span>机构登录</span>
            </button>
          </div>
        </div>

        <!-- 注册链接 -->
        <div class="registration-section">
          <p>没有账户？</p>
          <div class="registration-links">
            <router-link to="/register/author" class="btn btn-outline">注册为作者</router-link>
            <router-link to="/register/reviewer" class="btn btn-outline">注册为审稿人</router-link>
          </div>
        </div>

        <!-- 帮助链接 -->
        <div class="help-links">
          <a href="#" class="help-link">什么是ORCID？</a>
          <a href="#" class="help-link">什么是Elsevier账户？</a>
          <a href="#" class="help-link">发送登录信息</a>
          <a href="#" class="help-link">登录帮助</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const roles = [
  { id: 'author', name: '作者登录', icon: '✍️' },
  { id: 'reviewer', name: '审稿人登录', icon: '👁️' },
  { id: 'editor', name: '编辑登录', icon: '📝' },
  { id: 'admin', name: '管理员登录', icon: '⚙️' }
]

const selectedRole = ref('author')
const loginData = reactive({
  username: '',
  password: ''
})
const rememberMe = ref(false)
const loading = ref(false)
const error = ref('')

const selectRole = (roleId) => {
  selectedRole.value = roleId
}

const handleLogin = () => {
  loading.value = true
  error.value = ''

  // 模拟登录请求
  setTimeout(() => {
    // 这里应该是实际的API调用
    if (loginData.username && loginData.password) {
      localStorage.setItem('isAuthenticated', 'true')
      localStorage.setItem('userName', loginData.username)
      localStorage.setItem('userRole', selectedRole.value)
      
      // 根据角色跳转到不同页面
      switch(selectedRole.value) {
        case 'author':
          router.push('/submit')
          break
        case 'reviewer':
          router.push('/')
          break
        case 'editor':
          window.location.href = '/admin'
          break
        case 'admin':
          window.location.href = '/admin'
          break
        default:
          router.push('/')
      }
    } else {
      error.value = '用户名或密码无效'
    }
    loading.value = false
  }, 1000)
}
</script>

<!-- 样式保持不变 -->
<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  padding: 20px;
}

.login-container {
  background: white;
  border-radius: 12px;
  box-shadow: 0 20px 60px rgba(0,0,0,0.1);
  width: 100%;
  max-width: 500px;
  overflow: hidden;
}

.login-header {
  padding: 40px 40px 20px;
  text-align: center;
  background: linear-gradient(135deg, #2c3e50 0%, #4a6491 100%);
  color: white;
}

.login-header h1 {
  font-size: 28px;
  margin-bottom: 10px;
}

.login-header p {
  opacity: 0.9;
  font-size: 16px;
}

.login-content {
  padding: 40px;
}

.role-selection {
  margin-bottom: 30px;
}

.role-options {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
}

.role-btn {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 15px;
  border: 2px solid #e0e0e0;
  border-radius: 8px;
  background: white;
  cursor: pointer;
  transition: all 0.3s ease;
}

.role-btn:hover {
  border-color: #2c3e50;
  background-color: #f8f9fa;
}

.role-btn.active {
  border-color: #2c3e50;
  background-color: #2c3e50;
  color: white;
}

.role-icon {
  font-size: 24px;
  margin-bottom: 8px;
}

.role-name {
  font-size: 14px;
  font-weight: 500;
}

.login-form {
  margin-bottom: 30px;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  color: #2c3e50;
}

.form-group input {
  width: 100%;
  padding: 12px 16px;
  border: 2px solid #e0e0e0;
  border-radius: 6px;
  font-size: 16px;
  transition: border-color 0.3s ease;
}

.form-group input:focus {
  outline: none;
  border-color: #2c3e50;
}

.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.checkbox {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}

.checkbox input[type="checkbox"] {
  width: auto;
}

.forgot-link {
  color: #3498db;
  text-decoration: none;
}

.forgot-link:hover {
  text-decoration: underline;
}

.btn-block {
  width: 100%;
  padding: 14px;
  font-size: 16px;
}

.error-message {
  margin-top: 15px;
  padding: 12px;
  background-color: #fee;
  color: #c33;
  border-radius: 6px;
  border: 1px solid #fcc;
  text-align: center;
}

.alternative-login {
  margin-bottom: 30px;
}

.divider {
  display: flex;
  align-items: center;
  text-align: center;
  margin: 20px 0;
  color: #95a5a6;
}

.divider::before,
.divider::after {
  content: '';
  flex: 1;
  border-bottom: 1px solid #e0e0e0;
}

.divider span {
  padding: 0 15px;
}

.alt-options {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
}

.alt-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  padding: 12px;
  border: 2px solid #e0e0e0;
  border-radius: 6px;
  background: white;
  cursor: pointer;
  transition: all 0.3s ease;
}

.alt-btn:hover {
  border-color: #2c3e50;
  background-color: #f8f9fa;
}

.alt-icon {
  font-size: 18px;
}

.registration-section {
  text-align: center;
  margin-bottom: 30px;
  padding: 20px;
  background-color: #f8f9fa;
  border-radius: 8px;
}

.registration-section p {
  margin-bottom: 15px;
  color: #2c3e50;
  font-weight: 500;
}

.registration-links {
  display: flex;
  gap: 10px;
  justify-content: center;
}

.help-links {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 20px;
  padding-top: 20px;
  border-top: 1px solid #e0e0e0;
}

.help-link {
  color: #3498db;
  text-decoration: none;
  font-size: 14px;
}

.help-link:hover {
  text-decoration: underline;
}

@media (max-width: 576px) {
  .login-container {
    margin: 10px;
  }
  
  .login-content {
    padding: 20px;
  }
  
  .role-options,
  .alt-options {
    grid-template-columns: 1fr;
  }
  
  .registration-links {
    flex-direction: column;
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
