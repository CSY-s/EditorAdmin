<template>
  <main class="submit-page">
    <div class="container">
      <!-- 提交向导 -->
      <div class="submission-wizard">
        <div class="wizard-steps">
          <div 
            v-for="(step, index) in steps" 
            :key="step.id"
            :class="['wizard-step', { 
              'active': currentStep === index,
              'completed': step.completed 
            }]"
            @click="goToStep(index)"
          >
            <div class="step-number">{{ index + 1 }}</div>
            <div class="step-label">{{ step.label }}</div>
          </div>
        </div>
      </div>

      <!-- 步骤1: 文章类型 -->
      <section v-if="currentStep === 0" class="step-content">
        <h2>选择文章类型</h2>
        <p class="step-description">选择您要提交的文章类型。</p>
        
        <div class="article-types-grid">
          <div 
            v-for="type in articleTypes" 
            :key="type.id"
            :class="['article-type-card', { 'selected': formData.articleType === type.id }]"
            @click="selectArticleType(type.id)"
          >
            <div class="type-icon">{{ type.icon }}</div>
            <h3 class="type-title">{{ type.title }}</h3>
            <p class="type-description">{{ type.description }}</p>
            <div class="type-details">
              <div class="detail-item">
                <strong>字数限制：</strong> {{ type.wordLimit }}
              </div>
              <div class="detail-item">
                <strong>参考文献：</strong> {{ type.references }}
              </div>
            </div>
          </div>
        </div>
        
        <div class="step-actions">
          <button @click="nextStep" class="btn btn-primary" :disabled="!formData.articleType">
            下一步：文章信息 →
          </button>
        </div>
      </section>

      <!-- 步骤2: 文章信息 -->
      <section v-if="currentStep === 1" class="step-content">
        <h2>文章信息</h2>
        <p class="step-description">提供文章的详细信息。</p>
        
        <form @submit.prevent="nextStep" class="article-form">
          <div class="form-grid">
            <div class="form-group">
              <label for="title">文章标题 *</label>
              <input 
                type="text" 
                id="title" 
                v-model="formData.title" 
                placeholder="输入文章完整标题"
                required
              />
              <div class="char-count">{{ formData.title.length }}/200 字符</div>
            </div>
            
            <div class="form-group">
              <label for="abstract">摘要 *</label>
              <textarea 
                id="abstract" 
                v-model="formData.abstract" 
                rows="5"
                placeholder="提供简明摘要（150-250字）"
                required
              ></textarea>
              <div class="char-count">{{ formData.abstract.length }}/2500 字符</div>
            </div>
            
            <div class="form-group full-width">
              <label for="keywords">关键词 *</label>
              <div class="keywords-input">
                <input 
                  type="text" 
                  v-model="keywordInput"
                  @keydown.enter.prevent="addKeyword"
                  placeholder="输入关键词后按Enter键"
                />
                <button type="button" @click="addKeyword" class="btn btn-outline btn-small">
                  添加
                </button>
              </div>
              <div class="keywords-list">
                <span v-for="(keyword, index) in formData.keywords" :key="index" class="keyword-tag">
                  {{ keyword }}
                  <button type="button" @click="removeKeyword(index)" class="remove-keyword">
                    ×
                  </button>
                </span>
              </div>
              <div class="form-hint">添加3-8个相关关键词，用逗号或Enter分隔。</div>
            </div>
            
            <div class="form-group">
              <label for="researchArea">研究领域 *</label>
              <select 
                id="researchArea" 
                v-model="formData.researchArea" 
                required
              >
                <option value="">选择研究领域</option>
                <option value="ml">机器学习</option>
                <option value="nlp">自然语言处理</option>
                <option value="cv">计算机视觉</option>
                <option value="robotics">机器人学</option>
                <option value="ai-theory">人工智能理论</option>
                <option value="ai-applications">人工智能应用</option>
              </select>
            </div>
            
            <div class="form-group">
              <label for="submissionType">提交类型</label>
              <select id="submissionType" v-model="formData.submissionType">
                <option value="regular">常规投稿</option>
                <option value="special">特刊投稿</option>
                <option value="invited">特邀论文</option>
              </select>
            </div>
            
            <div v-if="formData.submissionType === 'special'" class="form-group full-width">
              <label for="specialIssue">特刊 *</label>
              <select id="specialIssue" v-model="formData.specialIssue" required>
                <option value="">选择特刊</option>
                <option value="sustainable-ai">面向可持续发展的AI</option>
                <option value="explainable-ai">医疗领域的可解释AI</option>
                <option value="multimodal-ai">多模态AI系统</option>
              </select>
            </div>
          </div>
          
          <div class="step-actions">
            <button type="button" @click="prevStep" class="btn btn-outline">
              ← 上一步
            </button>
            <button type="submit" class="btn btn-primary">
              下一步：作者信息 →
            </button>
          </div>
        </form>
      </section>

      <!-- 步骤3: 作者与单位 -->
      <section v-if="currentStep === 2" class="step-content">
        <h2>作者与单位</h2>
        <p class="step-description">添加所有作者及其单位信息。第一位作者默认为通讯作者。</p>
        
        <div class="authors-section">
          <div class="authors-list">
            <div 
              v-for="(author, index) in formData.authors" 
              :key="index"
              :class="['author-card', { 'corresponding': author.isCorresponding }]"
            >
              <div class="author-header">
                <h3>作者 {{ index + 1 }}</h3>
                <div class="author-actions">
                  <button 
                    v-if="index > 0"
                    @click="moveAuthorUp(index)" 
                    class="action-btn"
                    title="上移"
                  >
                    ↑
                  </button>
                  <button 
                    v-if="index < formData.authors.length - 1"
                    @click="moveAuthorDown(index)" 
                    class="action-btn"
                    title="下移"
                  >
                    ↓
                  </button>
                  <button 
                    v-if="formData.authors.length > 1"
                    @click="removeAuthor(index)" 
                    class="action-btn remove"
                    title="移除作者"
                  >
                    ×
                  </button>
                </div>
              </div>
              
              <div class="author-form">
                <div class="form-row">
                  <div class="form-group">
                    <label :for="`firstName${index}`">名 *</label>
                    <input 
                      :id="`firstName${index}`"
                      type="text" 
                      v-model="author.firstName"
                      required
                    />
                  </div>
                  
                  <div class="form-group">
                    <label :for="`lastName${index}`">姓 *</label>
                    <input 
                      :id="`lastName${index}`"
                      type="text" 
                      v-model="author.lastName"
                      required
                    />
                  </div>
                </div>
                
                <div class="form-row">
                  <div class="form-group">
                    <label :for="`email${index}`">邮箱 *</label>
                    <input 
                      :id="`email${index}`"
                      type="email" 
                      v-model="author.email"
                      required
                    />
                  </div>
                  
                  <div class="form-group">
                    <label :for="`orcid${index}`">ORCID iD</label>
                    <input 
                      :id="`orcid${index}`"
                      type="text" 
                      v-model="author.orcid"
                      placeholder="0000-0000-0000-0000"
                    />
                  </div>
                </div>
                
                <div class="form-group">
                  <label :for="`affiliation${index}`">主要单位 *</label>
                  <input 
                    :id="`affiliation${index}`"
                    type="text" 
                    v-model="author.affiliation"
                    required
                  />
                </div>
                
                <div class="form-row">
                  <div class="form-group">
                    <label :for="`country${index}`">国家 *</label>
                    <select :id="`country${index}`" v-model="author.country" required>
                      <option value="">选择国家</option>
                      <option value="us">美国</option>
                      <option value="cn">中国</option>
                      <option value="uk">英国</option>
                      <option value="de">德国</option>
                      <option value="jp">日本</option>
                    </select>
                  </div>
                  
                  <div class="form-group">
                    <label :for="`contributions${index}`">贡献</label>
                    <input 
                      :id="`contributions${index}`"
                      type="text" 
                      v-model="author.contributions"
                      placeholder="例如：概念化、数据分析"
                    />
                  </div>
                </div>
                
                <div class="author-options">
                  <label class="checkbox">
                    <input 
                      type="radio" 
                      name="correspondingAuthor"
                      :value="index"
                      v-model="correspondingAuthorIndex"
                    />
                    <span>通讯作者</span>
                  </label>
                </div>
              </div>
            </div>
          </div>
          
          <button @click="addAuthor" class="btn btn-outline add-author-btn">
            + 添加更多作者
          </button>
        </div>
        
        <div class="step-actions">
          <button @click="prevStep" class="btn btn-outline">
            ← 上一步
          </button>
          <button @click="nextStep" class="btn btn-primary">
            下一步：上传文件 →
          </button>
        </div>
      </section>

      <!-- 步骤4: 上传文件 -->
      <section v-if="currentStep === 3" class="step-content">
        <h2>上传文件</h2>
        <p class="step-description">上传提交所需的文件。</p>
        
        <div class="upload-section">
          <div class="file-requirements">
            <h3>文件要求</h3>
            <ul>
              <li>所有文件必须为PDF格式</li>
              <li>最大文件大小：每个文件10MB</li>
              <li>稿件应匿名以进行评审</li>
              <li>稿件中需包含所有图表</li>
            </ul>
          </div>
          
          <div class="upload-zone">
            <div 
              v-for="fileType in fileTypes" 
              :key="fileType.id"
              class="file-upload-card"
            >
              <div class="file-header">
                <h3>{{ fileType.title }}</h3>
                <span class="file-required" v-if="fileType.required">必填</span>
                <span class="file-optional" v-else>可选</span>
              </div>
              
              <p class="file-description">{{ fileType.description }}</p>
              
              <div class="file-input">
                <input 
                  type="file" 
                  :id="fileType.id"
                  :ref="fileType.ref"
                  @change="handleFileUpload(fileType.id, $event)"
                  accept=".pdf,.doc,.docx"
                  :required="fileType.required"
                />
                <label :for="fileType.id" class="file-label">
                  <span class="file-icon">📎</span>
                  <span class="file-label-text">选择文件</span>
                </label>
              </div>
              
              <div v-if="formData.files[fileType.id]" class="file-preview">
                <div class="file-info">
                  <span class="file-name">{{ formData.files[fileType.id].name }}</span>
                  <span class="file-size">{{ formatFileSize(formData.files[fileType.id].size) }}</span>
                </div>
                <button 
                  @click="removeFile(fileType.id)" 
                  class="remove-file-btn"
                  type="button"
                >
                  移除
                </button>
              </div>
            </div>
          </div>
          
          <div class="additional-files">
            <h3>附加文件（可选）</h3>
            <div class="additional-files-list">
              <div v-for="(file, index) in formData.additionalFiles" :key="index" class="additional-file">
                <input 
                  type="file" 
                  @change="handleAdditionalFile(index, $event)"
                />
                <div class="additional-file-info">
                  <input 
                    type="text" 
                    v-model="file.description"
                    placeholder="文件描述（例如：补充材料）"
                  />
                  <button @click="removeAdditionalFile(index)" type="button" class="remove-btn">
                    移除
                  </button>
                </div>
              </div>
              <button @click="addAdditionalFile" type="button" class="btn btn-outline btn-small">
                + 添加更多文件
              </button>
            </div>
          </div>
        </div>
        
        <div class="step-actions">
          <button @click="prevStep" class="btn btn-outline">
            ← 上一步
          </button>
          <button @click="nextStep" class="btn btn-primary">
            下一步：审阅并提交 →
          </button>
        </div>
      </section>

      <!-- 步骤5: 审阅并提交 -->
      <section v-if="currentStep === 4" class="step-content">
        <h2>审阅并提交</h2>
        <p class="step-description">请在提交前审阅所有信息。</p>
        
        <div class="review-section">
          <!-- 文章信息 -->
          <div class="review-card">
            <h3>文章信息</h3>
            <div class="review-content">
              <div class="review-item">
                <strong>文章类型：</strong> {{ getArticleTypeLabel(formData.articleType) }}
              </div>
              <div class="review-item">
                <strong>标题：</strong> {{ formData.title }}
              </div>
              <div class="review-item">
                <strong>摘要：</strong> {{ formData.abstract.substring(0, 150) }}...
              </div>
              <div class="review-item">
                <strong>关键词：</strong> {{ formData.keywords.join(', ') }}
              </div>
              <div class="review-item">
                <strong>研究领域：</strong> {{ getResearchAreaLabel(formData.researchArea) }}
              </div>
            </div>
            <button @click="editStep(1)" class="btn btn-outline btn-small">
              编辑
            </button>
          </div>
          
          <!-- 作者 -->
          <div class="review-card">
            <h3>作者（{{ formData.authors.length }}）</h3>
            <div class="review-content">
              <div v-for="(author, index) in formData.authors" :key="index" class="review-item">
                <strong>作者 {{ index + 1 }}：</strong> {{ author.firstName }} {{ author.lastName }}
                <span v-if="author.isCorresponding" class="corresponding-badge">通讯作者</span>
              </div>
            </div>
            <button @click="editStep(2)" class="btn btn-outline btn-small">
              编辑
            </button>
          </div>
          
          <!-- 文件 -->
          <div class="review-card">
            <h3>文件</h3>
            <div class="review-content">
              <div v-for="fileType in fileTypes" :key="fileType.id" class="review-item">
                <strong>{{ fileType.title }}：</strong> 
                <span v-if="formData.files[fileType.id]">
                  {{ formData.files[fileType.id].name }}（{{ formatFileSize(formData.files[fileType.id].size) }}）
                </span>
                <span v-else class="missing">未上传</span>
              </div>
              <div v-if="formData.additionalFiles.length > 0" class="review-item">
                <strong>附加文件：</strong> {{ formData.additionalFiles.length }} 个文件
              </div>
            </div>
            <button @click="editStep(3)" class="btn btn-outline btn-small">
              编辑
            </button>
          </div>
          
          <!-- 提交条款 -->
          <div class="terms-card">
            <h3>提交条款</h3>
            <div class="terms-content">
              <label class="checkbox">
                <input type="checkbox" v-model="formData.terms.originality" required />
                <span>我确认此稿件是我的原创作品，且未在其他地方发表。</span>
              </label>
              
              <label class="checkbox">
                <input type="checkbox" v-model="formData.terms.copyright" required />
                <span>我同意在稿件接受后将版权转让给出版商。</span>
              </label>
              
              <label class="checkbox">
                <input type="checkbox" v-model="formData.terms.ethics" required />
                <span>我确认本研究符合道德准则。</span>
              </label>
              
              <label class="checkbox">
                <input type="checkbox" v-model="formData.terms.review" required />
                <span>我同意参与同行评审过程。</span>
              </label>
              
              <label class="checkbox">
                <input type="checkbox" v-model="formData.terms.openAccess" />
                <span>我理解这是开放获取期刊，如果被接受，我同意支付文章处理费。</span>
              </label>
            </div>
          </div>
          
          <!-- 提交偏好 -->
          <div class="preferences-card">
            <h3>提交偏好</h3>
            <div class="preferences-content">
              <div class="form-group">
                <label for="recommendedReviewers">推荐审稿人（可选）</label>
                <textarea 
                  id="recommendedReviewers" 
                  v-model="formData.recommendedReviewers"
                  placeholder="输入推荐审稿人的姓名和邮箱地址，每行一个"
                  rows="3"
                ></textarea>
              </div>
              
              <div class="form-group">
                <label for="excludedReviewers">排除审稿人（可选）</label>
                <textarea 
                  id="excludedReviewers" 
                  v-model="formData.excludedReviewers"
                  placeholder="输入您希望排除的审稿人姓名"
                  rows="2"
                ></textarea>
              </div>
              
              <div class="form-group">
                <label for="coverLetter">投稿信（可选）</label>
                <textarea 
                  id="coverLetter" 
                  v-model="formData.coverLetter"
                  placeholder="为编辑添加任何额外信息"
                  rows="4"
                ></textarea>
              </div>
            </div>
          </div>
        </div>
        
        <div class="final-actions">
          <div class="save-draft">
            <button @click="saveDraft" class="btn btn-outline" :disabled="submitting">
              保存草稿
            </button>
            <span class="draft-info">上次保存：{{ lastSaved }}</span>
          </div>
          
          <div class="submit-actions">
            <button @click="prevStep" class="btn btn-outline" :disabled="submitting">
              ← 上一步
            </button>
            <button 
              @click="submitArticle" 
              class="btn btn-primary"
              :disabled="!canSubmit || submitting"
            >
              {{ submitting ? '提交中...' : '提交文章' }}
            </button>
          </div>
        </div>
      </section>

      <!-- 成功模态框 -->
      <div v-if="showSuccessModal" class="modal-overlay">
        <div class="modal-content">
          <div class="modal-icon">✅</div>
          <h2>提交成功！</h2>
          <p class="submission-id">稿件ID：<strong>{{ submissionId }}</strong></p>
          <p>您的文章已成功提交。您将很快收到确认邮件。</p>
          
          <div class="next-steps">
            <h3>后续步骤：</h3>
            <ul>
              <li>在"我的投稿"中跟踪投稿状态</li>
              <li>编辑决定做出后您将收到通知</li>
              <li>平均首次决定时间：4-6周</li>
            </ul>
          </div>
          
          <div class="modal-actions">
            <router-link to="/" class="btn btn-outline">
              返回首页
            </router-link>
            <router-link to="/author/dashboard" class="btn btn-primary">
              前往仪表板
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script setup>
import { ref, computed, onMounted, onBeforeUnmount, watch } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 向导步骤
const steps = ref([
  { id: 'type', label: '文章类型', completed: false },
  { id: 'info', label: '文章信息', completed: false },
  { id: 'authors', label: '作者信息', completed: false },
  { id: 'files', label: '上传文件', completed: false },
  { id: 'review', label: '审阅提交', completed: false }
])

const currentStep = ref(0)
const keywordInput = ref('')
const correspondingAuthorIndex = ref(0)
const submitting = ref(false)
const showSuccessModal = ref(false)
const submissionId = ref('MS-' + new Date().getFullYear() + '-' + Math.floor(Math.random() * 1000))
const lastSaved = ref('从未保存')
const saveInterval = ref(null)

// 文章类型
const articleTypes = ref([
  {
    id: 'research',
    title: '研究论文',
    icon: '🔬',
    description: '呈现重要发现的原创研究。',
    wordLimit: '6000-8000字',
    references: '30-50篇参考文献'
  },
  {
    id: 'review',
    title: '综述论文',
    icon: '📚',
    description: '对某一领域现有研究的全面综述。',
    wordLimit: '8000-10000字',
    references: '100+篇参考文献'
  },
  {
    id: 'survey',
    title: '综述性论文',
    icon: '📊',
    description: '方法的系统性分析和比较。',
    wordLimit: '5000-7000字',
    references: '50-80篇参考文献'
  },
  {
    id: 'application',
    title: '应用论文',
    icon: '💡',
    description: '将AI方法应用于实际问题。',
    wordLimit: '4000-6000字',
    references: '20-40篇参考文献'
  }
])

// 文件类型
const fileTypes = ref([
  {
    id: 'manuscript',
    title: '稿件',
    ref: 'manuscriptFile',
    description: '主要文章文本，匿名以供评审。',
    required: true
  },
  {
    id: 'coverLetter',
    title: '投稿信',
    ref: 'coverLetterFile',
    description: '向编辑解释投稿的信件。',
    required: false
  },
  {
    id: 'figures',
    title: '图表',
    ref: 'figuresFile',
    description: '高分辨率图表（可选单独提交）。',
    required: false
  },
  {
    id: 'supplementary',
    title: '补充材料',
    ref: 'supplementaryFile',
    description: '附加数据、代码或材料。',
    required: false
  }
])

// 表单数据
const formData = ref({
  articleType: '',
  title: '',
  abstract: '',
  keywords: [],
  researchArea: '',
  submissionType: 'regular',
  specialIssue: '',
  authors: [
    {
      firstName: '',
      lastName: '',
      email: '',
      orcid: '',
      affiliation: '',
      country: '',
      contributions: '',
      isCorresponding: true
    }
  ],
  files: {},
  additionalFiles: [],
  recommendedReviewers: '',
  excludedReviewers: '',
  coverLetter: '',
  terms: {
    originality: false,
    copyright: false,
    ethics: false,
    review: false,
    openAccess: false
  }
})

// 计算属性
const canSubmit = computed(() => {
  const hasRequiredFiles = fileTypes.value
    .filter(type => type.required)
    .every(type => formData.value.files[type.id])
  
  const allTermsAccepted = Object.values(formData.value.terms).every(term => term)
  
  const hasRequiredInfo = formData.value.articleType && 
                         formData.value.title && 
                         formData.value.abstract && 
                         formData.value.keywords.length >= 3 &&
                         formData.value.researchArea
  
  const hasValidAuthors = formData.value.authors.every(author => 
    author.firstName && author.lastName && author.email && author.affiliation && author.country
  )
  
  return hasRequiredFiles && allTermsAccepted && hasRequiredInfo && hasValidAuthors
})

// 方法
const nextStep = () => {
  if (currentStep.value < steps.value.length - 1) {
    steps.value[currentStep.value].completed = true
    currentStep.value++
    autoSaveDraft()
  }
}

const prevStep = () => {
  if (currentStep.value > 0) {
    currentStep.value--
  }
}

const goToStep = (index) => {
  if (index < steps.value.length && index <= currentStep.value) {
    currentStep.value = index
  }
}

const editStep = (stepIndex) => {
  currentStep.value = stepIndex
}

const selectArticleType = (typeId) => {
  formData.value.articleType = typeId
}

const addKeyword = () => {
  if (keywordInput.value.trim()) {
    const keywords = keywordInput.value.split(',')
      .map(k => k.trim())
      .filter(k => k && !formData.value.keywords.includes(k))
    
    formData.value.keywords.push(...keywords)
    keywordInput.value = ''
  }
}

const removeKeyword = (index) => {
  formData.value.keywords.splice(index, 1)
}

const addAuthor = () => {
  formData.value.authors.push({
    firstName: '',
    lastName: '',
    email: '',
    orcid: '',
    affiliation: '',
    country: '',
    contributions: '',
    isCorresponding: false
  })
}

const removeAuthor = (index) => {
  if (formData.value.authors.length > 1) {
    formData.value.authors.splice(index, 1)
  }
}

const moveAuthorUp = (index) => {
  if (index > 0) {
    const temp = formData.value.authors[index]
    formData.value.authors[index] = formData.value.authors[index - 1]
    formData.value.authors[index - 1] = temp
  }
}

const moveAuthorDown = (index) => {
  if (index < formData.value.authors.length - 1) {
    const temp = formData.value.authors[index]
    formData.value.authors[index] = formData.value.authors[index + 1]
    formData.value.authors[index + 1] = temp
  }
}

const handleFileUpload = (fileType, event) => {
  const file = event.target.files[0]
  if (file) {
    // 检查文件大小（10MB限制）
    if (file.size > 10 * 1024 * 1024) {
      alert('文件大小超过10MB限制')
      return
    }
    
    // 检查文件类型
    const validTypes = ['application/pdf', 'application/msword', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document']
    if (!validTypes.includes(file.type)) {
      alert('请仅上传PDF或Word文档')
      return
    }
    
    formData.value.files[fileType] = file
  }
}

const removeFile = (fileType) => {
  delete formData.value.files[fileType]
}

const addAdditionalFile = () => {
  formData.value.additionalFiles.push({
    file: null,
    description: ''
  })
}

const handleAdditionalFile = (index, event) => {
  const file = event.target.files[0]
  if (file) {
    formData.value.additionalFiles[index].file = file
  }
}

const removeAdditionalFile = (index) => {
  formData.value.additionalFiles.splice(index, 1)
}

const formatFileSize = (bytes) => {
  if (bytes === 0) return '0 Bytes'
  const k = 1024
  const sizes = ['Bytes', 'KB', 'MB', 'GB']
  const i = Math.floor(Math.log(bytes) / Math.log(k))
  return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i]
}

const getArticleTypeLabel = (typeId) => {
  const type = articleTypes.value.find(t => t.id === typeId)
  return type ? type.title : ''
}

const getResearchAreaLabel = (areaId) => {
  const areas = {
    'ml': '机器学习',
    'nlp': '自然语言处理',
    'cv': '计算机视觉',
    'robotics': '机器人学',
    'ai-theory': '人工智能理论',
    'ai-applications': '人工智能应用'
  }
  return areas[areaId] || areaId
}

const autoSaveDraft = () => {
  const draftData = {
    ...formData.value,
    lastSaved: new Date().toISOString()
  }
  localStorage.setItem('submissionDraft', JSON.stringify(draftData))
  lastSaved.value = new Date().toLocaleTimeString()
}

const saveDraft = () => {
  autoSaveDraft()
  alert('草稿保存成功！')
}

const submitArticle = async () => {
  submitting.value = true
  
  try {
    // 模拟API调用
    await new Promise(resolve => setTimeout(resolve, 2000))
    
    // 从localStorage清除草稿
    localStorage.removeItem('submissionDraft')
    
    // 显示成功模态框
    showSuccessModal.value = true
    
  } catch (error) {
    alert('提交失败。请重试。')
  } finally {
    submitting.value = false
  }
}

const loadDraft = () => {
  const draft = localStorage.getItem('submissionDraft')
  if (draft) {
    const draftData = JSON.parse(draft)
    formData.value = { ...formData.value, ...draftData }
    if (draftData.lastSaved) {
      lastSaved.value = new Date(draftData.lastSaved).toLocaleString()
    }
  }
}

// 监视通讯作者变化
watch(() => correspondingAuthorIndex.value, (newIndex) => {
  formData.value.authors.forEach((author, index) => {
    author.isCorresponding = index === newIndex
  })
})

// 生命周期钩子
onMounted(() => {
  loadDraft()
  
  // 每2分钟自动保存
  saveInterval.value = setInterval(autoSaveDraft, 120000)
})

onBeforeUnmount(() => {
  if (saveInterval.value) {
    clearInterval(saveInterval.value)
  }
})
</script>

<style scoped>
/* 样式保持不变，与英文版相同 */
.submit-page {
  padding: 40px 0 60px;
  background-color: #f8f9fa;
}

.submission-wizard {
  background: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 15px rgba(0,0,0,0.05);
  margin-bottom: 40px;
}

.wizard-steps {
  display: flex;
  justify-content: space-between;
  position: relative;
}

.wizard-steps::before {
  content: '';
  position: absolute;
  top: 20px;
  left: 0;
  right: 0;
  height: 2px;
  background-color: #e0e0e0;
  z-index: 1;
}

.wizard-step {
  position: relative;
  z-index: 2;
  text-align: center;
  cursor: pointer;
  flex: 1;
}

.wizard-step.active .step-number {
  background-color: #2c3e50;
  color: white;
  border-color: #2c3e50;
}

.wizard-step.completed .step-number {
  background-color: #27ae60;
  color: white;
  border-color: #27ae60;
}

.step-number {
  width: 40px;
  height: 40px;
  border: 2px solid #e0e0e0;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 10px;
  background: white;
  font-weight: 600;
  transition: all 0.3s ease;
}

.step-label {
  font-size: 14px;
  color: #7f8c8d;
}

.step-content {
  background: white;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 2px 15px rgba(0,0,0,0.05);
  margin-bottom: 30px;
}

.step-content h2 {
  font-size: 28px;
  color: #2c3e50;
  margin-bottom: 10px;
}

.step-description {
  color: #7f8c8d;
  margin-bottom: 30px;
  font-size: 16px;
}

.article-types-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
  margin-bottom: 40px;
}

.article-type-card {
  border: 2px solid #e0e0e0;
  border-radius: 10px;
  padding: 25px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
}

.article-type-card:hover {
  border-color: #3498db;
  transform: translateY(-5px);
}

.article-type-card.selected {
  border-color: #2c3e50;
  background-color: #f8f9fa;
}

.type-icon {
  font-size: 40px;
  margin-bottom: 15px;
}

.type-title {
  font-size: 18px;
  color: #2c3e50;
  margin-bottom: 10px;
}

.type-description {
  color: #7f8c8d;
  font-size: 14px;
  margin-bottom: 15px;
  line-height: 1.5;
}

.type-details {
  font-size: 13px;
  color: #95a5a6;
}

.detail-item {
  margin-bottom: 5px;
}

.step-actions {
  display: flex;
  justify-content: flex-end;
}

.article-form {
  margin-bottom: 30px;
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.form-group {
  margin-bottom: 20px;
}

.form-group.full-width {
  grid-column: 1 / -1;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  color: #2c3e50;
}

.form-group input,
.form-group textarea,
.form-group select {
  width: 100%;
  padding: 12px 16px;
  border: 2px solid #e0e0e0;
  border-radius: 6px;
  font-size: 16px;
  transition: border-color 0.3s ease;
}

.form-group input:focus,
.form-group textarea:focus,
.form-group select:focus {
  outline: none;
  border-color: #2c3e50;
}

.char-count {
  text-align: right;
  font-size: 12px;
  color: #95a5a6;
  margin-top: 5px;
}

.keywords-input {
  display: flex;
  gap: 10px;
  margin-bottom: 10px;
}

.keywords-input input {
  flex: 1;
}

.keywords-list {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 10px;
}

.keyword-tag {
  display: inline-flex;
  align-items: center;
  gap: 5px;
  background-color: #e8f4fd;
  color: #3498db;
  padding: 6px 12px;
  border-radius: 20px;
  font-size: 14px;
}

.remove-keyword {
  background: none;
  border: none;
  color: #3498db;
  font-size: 18px;
  cursor: pointer;
  padding: 0;
  width: 18px;
  height: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
  line-height: 1;
}

.form-hint {
  font-size: 12px;
  color: #95a5a6;
  margin-top: 5px;
}

.authors-section {
  margin-bottom: 30px;
}

.authors-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-bottom: 20px;
}

.author-card {
  border: 2px solid #e0e0e0;
  border-radius: 8px;
  padding: 20px;
}

.author-card.corresponding {
  border-color: #3498db;
  background-color: #f8fbfe;
}

.author-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.author-header h3 {
  font-size: 18px;
  color: #2c3e50;
  margin: 0;
}

.author-actions {
  display: flex;
  gap: 5px;
}

.action-btn {
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  padding: 6px 10px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s ease;
}

.action-btn:hover {
  background-color: #f8f9fa;
  border-color: #2c3e50;
}

.action-btn.remove {
  color: #e74c3c;
}

.action-btn.remove:hover {
  background-color: #fee;
  border-color: #e74c3c;
}

.author-form {
  margin-bottom: 15px;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 15px;
  margin-bottom: 15px;
}

.author-options {
  margin-top: 15px;
}

.checkbox {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  font-size: 14px;
}

.add-author-btn {
  width: 100%;
  padding: 12px;
  font-size: 16px;
}

.upload-section {
  margin-bottom: 30px;
}

.file-requirements {
  background-color: #f8f9fa;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 30px;
}

.file-requirements h3 {
  font-size: 18px;
  color: #2c3e50;
  margin-bottom: 15px;
}

.file-requirements ul {
  list-style-type: disc;
  margin-left: 20px;
  color: #555;
}

.file-requirements li {
  margin-bottom: 8px;
}

.upload-zone {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.file-upload-card {
  border: 2px dashed #e0e0e0;
  border-radius: 8px;
  padding: 20px;
  transition: all 0.3s ease;
}

.file-upload-card:hover {
  border-color: #3498db;
}

.file-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.file-header h3 {
  font-size: 16px;
  color: #2c3e50;
  margin: 0;
}

.file-required,
.file-optional {
  font-size: 12px;
  padding: 2px 8px;
  border-radius: 12px;
  font-weight: 600;
}

.file-required {
  background-color: #e8f8f0;
  color: #27ae60;
}

.file-optional {
  background-color: #fef5e7;
  color: #f39c12;
}

.file-description {
  color: #7f8c8d;
  font-size: 14px;
  margin-bottom: 15px;
  line-height: 1.5;
}

.file-input {
  position: relative;
  margin-bottom: 15px;
}

.file-input input[type="file"] {
  position: absolute;
  width: 0;
  height: 0;
  opacity: 0;
}

.file-label {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px 20px;
  background-color: #f8f9fa;
  border: 2px solid #e0e0e0;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.file-label:hover {
  background-color: #e8f4fd;
  border-color: #3498db;
}

.file-icon {
  font-size: 18px;
}

.file-label-text {
  font-weight: 500;
  color: #2c3e50;
}

.file-preview {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  background-color: #f8f9fa;
  border-radius: 6px;
  margin-top: 10px;
}

.file-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.file-name {
  font-weight: 500;
  color: #2c3e50;
  font-size: 14px;
}

.file-size {
  font-size: 12px;
  color: #7f8c8d;
}

.remove-file-btn {
  background: none;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  padding: 6px 12px;
  font-size: 14px;
  color: #e74c3c;
  cursor: pointer;
  transition: all 0.3s ease;
}

.remove-file-btn:hover {
  background-color: #fee;
  border-color: #e74c3c;
}

.additional-files {
  margin-top: 30px;
}

.additional-files h3 {
  font-size: 18px;
  color: #2c3e50;
  margin-bottom: 15px;
}

.additional-files-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.additional-file {
  display: grid;
  grid-template-columns: 1fr 2fr auto;
  gap: 15px;
  align-items: center;
}

.additional-file-info {
  display: flex;
  gap: 10px;
}

.additional-file-info input {
  flex: 1;
  padding: 8px 12px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
}

.remove-btn {
  background: none;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  padding: 8px 12px;
  color: #e74c3c;
  cursor: pointer;
  transition: all 0.3s ease;
}

.remove-btn:hover {
  background-color: #fee;
  border-color: #e74c3c;
}

.review-section {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-bottom: 30px;
}

.review-card,
.terms-card,
.preferences-card {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 25px;
  position: relative;
}

.review-card h3,
.terms-card h3,
.preferences-card h3 {
  font-size: 18px;
  color: #2c3e50;
  margin-bottom: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.review-content {
  margin-bottom: 15px;
}

.review-item {
  margin-bottom: 10px;
  line-height: 1.5;
}

.review-item strong {
  color: #2c3e50;
  margin-right: 5px;
}

.corresponding-badge {
  display: inline-block;
  background-color: #e8f4fd;
  color: #3498db;
  padding: 2px 8px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 600;
  margin-left: 10px;
}

.missing {
  color: #e74c3c;
  font-style: italic;
}

.terms-content {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.terms-content .checkbox {
  align-items: flex-start;
}

.terms-content .checkbox span {
  line-height: 1.5;
  font-size: 14px;
}

.preferences-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.preferences-content .form-group {
  margin-bottom: 0;
}

.preferences-content textarea {
  width: 100%;
  min-height: 80px;
  resize: vertical;
}

.final-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 20px;
  padding-top: 20px;
  border-top: 1px solid #e0e0e0;
}

.save-draft {
  display: flex;
  align-items: center;
  gap: 15px;
}

.draft-info {
  font-size: 14px;
  color: #7f8c8d;
}

.submit-actions {
  display: flex;
  gap: 15px;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0,0,0,0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  padding: 20px;
}

.modal-content {
  background: white;
  border-radius: 10px;
  padding: 40px;
  max-width: 500px;
  width: 100%;
  text-align: center;
  animation: modalSlideIn 0.3s ease;
}

@keyframes modalSlideIn {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.modal-icon {
  font-size: 60px;
  margin-bottom: 20px;
}

.modal-content h2 {
  font-size: 28px;
  color: #2c3e50;
  margin-bottom: 10px;
}

.submission-id {
  font-size: 18px;
  color: #7f8c8d;
  margin-bottom: 20px;
}

.modal-content > p {
  color: #555;
  line-height: 1.6;
  margin-bottom: 25px;
}

.next-steps {
  text-align: left;
  margin-bottom: 30px;
}

.next-steps h3 {
  font-size: 16px;
  color: #2c3e50;
  margin-bottom: 10px;
}

.next-steps ul {
  list-style-type: disc;
  margin-left: 20px;
  color: #555;
}

.next-steps li {
  margin-bottom: 8px;
  line-height: 1.5;
}

.modal-actions {
  display: flex;
  gap: 15px;
  justify-content: center;
}

@media (max-width: 768px) {
  .form-grid {
    grid-template-columns: 1fr;
  }
  
  .form-row {
    grid-template-columns: 1fr;
  }
  
  .wizard-steps {
    flex-direction: column;
    gap: 20px;
  }
  
  .wizard-steps::before {
    display: none;
  }
  
  .wizard-step {
    display: flex;
    align-items: center;
    gap: 15px;
    text-align: left;
  }
  
  .step-number {
    margin: 0;
  }
  
  .upload-zone {
    grid-template-columns: 1fr;
  }
  
  .additional-file {
    grid-template-columns: 1fr;
  }
  
  .final-actions {
    flex-direction: column;
    align-items: stretch;
  }
  
  .save-draft {
    flex-direction: column;
    align-items: center;
    text-align: center;
  }
  
  .modal-content {
    padding: 20px;
  }
  
  .modal-actions {
    flex-direction: column;
  }
}

@media (max-width: 480px) {
  .step-content {
    padding: 20px;
  }
  
  .author-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }
  
  .author-actions {
    align-self: flex-end;
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