<template>
  <header class="header">
    <div class="container">
      <div class="header-content">
        <div class="logo">
          <router-link to="/">
            <h1>国际人工智能研究期刊</h1>
            <span class="subtitle">AI研究与应用期刊</span>
          </router-link>
        </div>
        
        <nav class="main-nav">
          <ul>
            <li><router-link to="/" exact-active-class="active">首页</router-link></li>
            <li><router-link to="/about" active-class="active">关于期刊</router-link></li>
            <li><router-link to="/papers" active-class="active">论文</router-link></li>
            <li><router-link to="/publications" active-class="active">出版物</router-link></li>
            <li><router-link to="/guide" active-class="active">用户指南</router-link></li>
            <li class="dropdown">
              <a href="#">投稿 <span class="arrow">▼</span></a>
              <ul class="dropdown-menu">
                <li><router-link to="/submit">提交文章</router-link></li>
                <li><router-link to="/guide#authors">作者指南</router-link></li>
                <li><a href="#">征稿启事</a></li>
              </ul>
            </li>
          </ul>
        </nav>

        <div class="auth-section">
          <a href="/admin" class="btn btn-outline btn-small">后台入口</a>
          <template v-if="isAuthenticated">
            <div class="user-info">
              <span class="user-name">{{ userName }}</span>
              <button @click="logout" class="btn btn-outline btn-small">退出登录</button>
            </div>
          </template>
          <template v-else>
            <router-link to="/login" class="btn btn-outline btn-small">登录</router-link>
            <router-link to="/login?register=true" class="btn btn-primary btn-small">注册</router-link>
          </template>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 模拟用户认证状态
const isAuthenticated = ref(!!localStorage.getItem('isAuthenticated'))
const userName = ref(localStorage.getItem('userName') || '用户')

const logout = () => {
  localStorage.removeItem('isAuthenticated')
  localStorage.removeItem('userName')
  isAuthenticated.value = false
  router.push('/')
}
</script>

<style scoped>
.header {
  background-color: white;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
  position: sticky;
  top: 0;
  z-index: 1000;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 0;
}

.logo a {
  color: #2c3e50;
  text-decoration: none;
}

.logo h1 {
  font-size: 20px;
  font-weight: 700;
  margin-bottom: 4px;
}

.subtitle {
  font-size: 12px;
  color: #7f8c8d;
  font-style: italic;
}

.main-nav ul {
  display: flex;
  list-style: none;
  gap: 30px;
}

.main-nav a {
  color: #34495e;
  font-weight: 500;
  padding: 8px 0;
  position: relative;
  transition: color 0.3s ease;
}

.main-nav a:hover,
.main-nav a.active {
  color: #2c3e50;
}

.main-nav a.active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 2px;
  background-color: #2c3e50;
}

.dropdown {
  position: relative;
}

.dropdown-menu {
  position: absolute;
  top: 100%;
  left: 0;
  background: white;
  min-width: 200px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.15);
  border-radius: 4px;
  opacity: 0;
  visibility: hidden;
  transform: translateY(10px);
  transition: all 0.3s ease;
  z-index: 100;
}

.dropdown:hover .dropdown-menu {
  opacity: 1;
  visibility: visible;
  transform: translateY(0);
}

.dropdown-menu li {
  padding: 0;
}

.dropdown-menu a {
  display: block;
  padding: 12px 20px;
  border-bottom: 1px solid #ecf0f1;
}

.dropdown-menu a:hover {
  background-color: #f8f9fa;
}

.auth-section {
  display: flex;
  gap: 10px;
  align-items: center;
}

.btn-small {
  padding: 6px 12px;
  font-size: 14px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 15px;
}

.user-name {
  font-weight: 500;
  color: #2c3e50;
}

@media (max-width: 1024px) {
  .header-content {
    flex-wrap: wrap;
  }
  
  .main-nav {
    order: 3;
    width: 100%;
    margin-top: 15px;
  }
  
  .main-nav ul {
    justify-content: center;
    gap: 20px;
  }
}
</style>
