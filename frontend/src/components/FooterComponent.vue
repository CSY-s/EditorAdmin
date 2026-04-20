<template>
  <footer class="footer">
    <div class="container">
      <div class="footer-content">
        <div class="footer-section">
          <h3>国际人工智能研究期刊</h3>
          <p>
            ISSN: 1234-5678<br>
            影响因子: 4.5<br>
            出版商: IAIR出版集团
          </p>
        </div>
        
        <div class="footer-section">
          <h4>快速链接</h4>
          <ul>
            <li><router-link to="/">首页</router-link></li>
            <li><router-link to="/about">关于期刊</router-link></li>
            <li><router-link to="/submit">提交论文</router-link></li>
            <li><router-link to="/publications">最新期刊</router-link></li>
            <li><router-link to="/guide">作者指南</router-link></li>
          </ul>
        </div>
        
        <div class="footer-section">
          <h4>联系我们</h4>
          <p>
            邮箱: editor@iair-journal.org<br>
            电话: +1 234 567 8900<br>
            地址: 人工智能研究大道123号，科技城
          </p>
        </div>
        
        <div class="footer-section">
          <h4>关注我们</h4>
          <div class="social-links">
            <a href="#" class="social-link">微博</a>
            <a href="#" class="social-link">微信公众号</a>
            <a href="#" class="social-link">ResearchGate</a>
          </div>
        </div>
      </div>
      
      <div class="footer-bottom">
        <p>&copy; 2024 国际人工智能研究期刊。保留所有权利。</p>
        <div class="footer-links">
          <a href="#">隐私政策</a>
          <a href="#">服务条款</a>
          <a href="#">开放获取政策</a>
        </div>
      </div>
    </div>
  </footer>
</template>

<style scoped>
.footer {
  background-color: #2c3e50;
  color: white;
  padding: 50px 0 20px;
  margin-top: auto;
}

.footer-content {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 40px;
  margin-bottom: 40px;
}

.footer-section h3,
.footer-section h4 {
  margin-bottom: 20px;
  color: white;
}

.footer-section h4 {
  font-size: 18px;
}

.footer-section ul {
  list-style: none;
}

.footer-section ul li {
  margin-bottom: 10px;
}

.footer-section a {
  color: #bdc3c7;
  transition: color 0.3s ease;
}

.footer-section a:hover {
  color: white;
}

.social-links {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.social-link {
  background-color: rgba(255,255,255,0.1);
  padding: 8px 15px;
  border-radius: 4px;
  text-align: center;
}

.footer-bottom {
  border-top: 1px solid rgba(255,255,255,0.1);
  padding-top: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 20px;
}

.footer-links {
  display: flex;
  gap: 20px;
}

.footer-links a {
  color: #bdc3c7;
  font-size: 14px;
}

.footer-links a:hover {
  color: white;
}

@media (max-width: 768px) {
  .footer-bottom {
    flex-direction: column;
    text-align: center;
  }
}
</style>