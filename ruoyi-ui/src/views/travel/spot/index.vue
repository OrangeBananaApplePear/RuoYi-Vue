<template>
  <div class="spot-page">
    <!-- 头部图片 -->
    <div class="spot-header" :style="{ backgroundImage: `url(${spotInfo.coverImage || defaultCover })` }">
      <div class="header-back" @click="goBack">
        <span>← 返回</span>
      </div>
      <div class="header-content">
        <h1 class="spot-title">{{ spotInfo.spotName }}</h1>
        <div class="spot-location" v-if="spotInfo.address">
          <span>📍 {{ spotInfo.address }}</span>
        </div>
      </div>
    </div>

    <!-- 景点基本信息 -->
    <div class="spot-info-card">
      <div class="info-row">
        <div class="info-item">
          <span class="info-label">开放时间</span>
          <span class="info-value">{{ spotInfo.openTime || '暂无' }}</span>
        </div>
        <div class="info-item">
          <span class="info-label">门票</span>
          <span class="info-value price">{{ spotInfo.ticketPrice || '免费' }}</span>
        </div>
      </div>
      <div class="info-row">
        <div class="info-item">
          <span class="info-label">地址</span>
          <span class="info-value">{{ spotInfo.address || '暂无' }}</span>
        </div>
        <div class="info-item">
          <span class="info-label">打卡人数</span>
          <span class="info-value highlight">{{ spotInfo.checkinCount || 0 }}</span>
        </div>
      </div>
      <div class="intro-section" v-if="spotInfo.introduction">
        <div class="intro-title">简介</div>
        <div class="intro-content">{{ spotInfo.introduction }}</div>
      </div>
      <div class="intro-section" v-if="spotInfo.description">
        <div class="intro-title">详细介绍</div>
        <div class="intro-content">{{ spotInfo.description }}</div>
      </div>
    </div>

    <!-- 打卡按钮 -->
    <div class="checkin-section">
      <div v-if="!isChecked" class="checkin-btn" @click="handleCheckin">
        <span class="btn-icon">📸</span>
        <span class="btn-text">我要打卡</span>
      </div>
      <div v-else class="checked-btn">
        <span class="btn-icon">✅</span>
        <span class="btn-text">已打卡</span>
      </div>
    </div>

    <!-- 打卡墙 Tab -->
    <div class="checkin-wall">
      <div class="wall-tabs">
        <div 
          class="tab-item" 
          :class="{ active: activeTab === 'my' }"
          @click="activeTab = 'my'"
        >
          我的打卡
        </div>
        <div 
          class="tab-item" 
          :class="{ active: activeTab === 'public' }"
          @click="activeTab = 'public'"
        >
          公开打卡
        </div>
      </div>

      <!-- 我的打卡 -->
      <div v-if="activeTab === 'my'" class="tab-content">
        <div v-if="myCheckins.length === 0" class="empty-tips">
          暂无打卡记录，快去打卡吧！
        </div>
        <div v-else class="checkin-list">
          <div v-for="checkin in myCheckins" :key="checkin.checkinId" class="checkin-card">
            <div class="checkin-images" v-if="checkin.imageList && checkin.imageList.length">
              <img 
                v-for="(img, idx) in checkin.imageList" 
                :key="idx" 
                :src="img" 
                @click="previewImage(checkin.imageList, idx)"
              />
            </div>
            <div class="checkin-content">{{ checkin.content || '记录了一次完美的旅行！' }}</div>
            <div class="checkin-time">{{ formatTime(checkin.createTime) }}</div>
          </div>
        </div>
      </div>

      <!-- 公开打卡 -->
      <div v-if="activeTab === 'public'" class="tab-content">
        <div v-if="publicCheckins.length === 0" class="empty-tips">
          暂无公开打卡
        </div>
        <div v-else class="checkin-list">
          <div v-for="checkin in publicCheckins" :key="checkin.checkinId" class="checkin-card">
            <div class="checkin-user">
              <img :src="checkin.avatar || defaultAvatar" class="user-avatar" />
              <span class="user-name">{{ checkin.nickname || '匿名用户' }}</span>
            </div>
            <div class="checkin-images" v-if="checkin.imageList && checkin.imageList.length">
              <img 
                v-for="(img, idx) in checkin.imageList" 
                :key="idx" 
                :src="img"
                @click="previewImage(checkin.imageList, idx)"
              />
            </div>
            <div class="checkin-content">{{ checkin.content || '记录了一次完美的旅行！' }}</div>
            <div class="checkin-footer">
              <span class="checkin-time">{{ formatTime(checkin.createTime) }}</span>
              <div class="checkin-actions">
                <span @click="handleLike(checkin)">
                  {{ checkin.isLiked ? '❤️' : '🤍' }} {{ checkin.likeCount || 0 }}
                </span>
                <span>💬 {{ checkin.commentCount || 0 }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 打卡弹窗 -->
    <el-dialog v-model="checkinDialogVisible" title="发布打卡" width="90%">
      <el-form>
        <el-form-item label="打卡内容">
          <el-input
            v-model="checkinForm.content"
            type="textarea"
            :rows="3"
            placeholder="记录你的旅行感受..."
          />
        </el-form-item>
        <el-form-item label="上传图片">
          <el-upload
            v-model:file-list="checkinForm.images"
            action="/resource/upload"
            list-type="picture-card"
            :on-success="handleUploadSuccess"
            :before-upload="beforeUpload"
          >
            <el-icon><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="是否公开">
          <el-switch v-model="checkinForm.isPublic" active-text="公开" inactive-text="私密" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="checkinDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitCheckin">发布</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getSpot } from '@/api/travel/spot'
import { getPublicCheckins, getUserCheckinsBySpot, addCheckin, likeCheckin } from '@/api/travel/checkin'
import { checkSpot } from '@/api/travel/footprint'

const router = useRouter()
const route = useRoute()

const spotId = ref(route.params.id)
const spotInfo = ref({})
const isChecked = ref(false)
const activeTab = ref('my')
const myCheckins = ref([])
const publicCheckins = ref([])
const checkinDialogVisible = ref(false)
const checkinForm = ref({
  content: '',
  images: [],
  isPublic: true
})

const defaultCover = 'https://picx.zhimg.com/80/v2-placeholder.jpg'
const defaultAvatar = 'https://picx.zhimg.com/80/v2-default-avatar.jpg'

onMounted(async () => {
  await loadSpotInfo()
  await checkIfChecked()
  await loadMyCheckins()
  await loadPublicCheckins()
})

async function loadSpotInfo() {
  try {
    const res = await getSpot(spotId.value)
    if (res.code === 200) {
      spotInfo.value = res.msg || res.data || {}
    }
  } catch (e) {
    console.error('获取景点信息失败', e)
  }
}

async function checkIfChecked() {
  try {
    const res = await checkSpot(spotId.value)
    isChecked.value = res.msg === true || res.data === true
  } catch (e) {
    isChecked.value = false
  }
}

async function loadMyCheckins() {
  try {
    // 这里需要传递userId，实际从当前用户获取
    const res = await getUserCheckinsBySpot(0, spotId.value)
    if (res.code === 200) {
      const list = res.msg || res.data || []
      list.forEach(item => {
        if (item.images) {
          try {
            item.imageList = JSON.parse(item.images)
          } catch (e) {
            item.imageList = []
          }
        }
      })
      myCheckins.value = list
    }
  } catch (e) {
    myCheckins.value = []
  }
}

async function loadPublicCheckins() {
  try {
    const res = await getPublicCheckins(spotId.value)
    if (res.code === 200) {
      const list = res.msg || res.data || []
      list.forEach(item => {
        if (item.images) {
          try {
            item.imageList = JSON.parse(item.images)
          } catch (e) {
            item.imageList = []
          }
        }
      })
      publicCheckins.value = list
    }
  } catch (e) {
    publicCheckins.value = []
  }
}

function goBack() {
  router.back()
}

function handleCheckin() {
  checkinDialogVisible.value = true
}

function handleUploadSuccess(response, file) {
  // 处理图片上传成功
  console.log('上传成功', response)
}

function beforeUpload(file) {
  // 图片上传前检查
  const isImage = file.type.startsWith('image/')
  const isLt5M = file.size / 1024 / 1024 < 5
  if (!isImage) {
    ElMessage.error('只能上传图片文件!')
    return false
  }
  if (!isLt5M) {
    ElMessage.error('图片大小不能超过 5MB!')
    return false
  }
  return true
}

async function submitCheckin() {
  if (checkinForm.value.images.length === 0) {
    ElMessage.warning('请至少上传一张图片')
    return
  }

  try {
    const images = checkinForm.value.images.map(f => f.url || f.response?.url)
    await addCheckin({
      spotId: spotId.value,
      cityId: spotInfo.value.cityId,
      content: checkinForm.value.content,
      images: JSON.stringify(images),
      isPublic: checkinForm.value.isPublic ? '1' : '0'
    })
    ElMessage.success('打卡成功!')
    checkinDialogVisible.value = false
    isChecked.value = true
    await loadMyCheckins()
  } catch (e) {
    ElMessage.error('打卡失败')
  }
}

async function handleLike(checkin) {
  try {
    await likeCheckin(checkin.checkinId)
    checkin.likeCount = (checkin.likeCount || 0) + 1
    checkin.isLiked = true
  } catch (e) {
    ElMessage.error('操作失败')
  }
}

function formatTime(time) {
  if (!time) return ''
  const date = new Date(time)
  return `${date.getFullYear()}-${String(date.getMonth()+1).padStart(2,'0')}-${String(date.getDate()).padStart(2,'0')}`
}

function previewImage(images, index) {
  // 图片预览
  console.log('预览图片', images, index)
}
</script>

<style scoped>
.spot-page {
  min-height: 100vh;
  background: #f5f5f5;
  padding-bottom: 80px;
}

.spot-header {
  height: 250px;
  background-size: cover;
  background-position: center;
  position: relative;
}

.header-back {
  position: absolute;
  top: 16px;
  left: 16px;
  color: white;
  font-size: 16px;
  text-shadow: 0 1px 3px rgba(0,0,0,0.5);
  cursor: pointer;
  z-index: 10;
}

.header-content {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 16px;
  background: linear-gradient(transparent, rgba(0,0,0,0.7));
  color: white;
}

.spot-title {
  font-size: 28px;
  margin: 0 0 8px 0;
}

.spot-location {
  font-size: 14px;
  opacity: 0.9;
}

.spot-info-card {
  background: white;
  margin: 16px;
  border-radius: 12px;
  padding: 16px;
}

.info-row {
  display: flex;
  margin-bottom: 12px;
}

.info-item {
  flex: 1;
}

.info-label {
  display: block;
  font-size: 12px;
  color: #999;
  margin-bottom: 4px;
}

.info-value {
  font-size: 14px;
}

.info-value.price {
  color: #fa8c16;
  font-weight: bold;
}

.info-value.highlight {
  color: #409eff;
  font-weight: bold;
}

.intro-section {
  margin-top: 16px;
  padding-top: 16px;
  border-top: 1px solid #f0f0f0;
}

.intro-title {
  font-weight: bold;
  margin-bottom: 8px;
}

.intro-content {
  font-size: 14px;
  color: #666;
  line-height: 1.6;
}

.checkin-section {
  padding: 0 16px;
  margin-bottom: 16px;
}

.checkin-btn, .checked-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  padding: 16px;
  border-radius: 12px;
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;
}

.checkin-btn {
  background: linear-gradient(135deg, #409eff, #67c23a);
  color: white;
}

.checked-btn {
  background: #fa8c16;
  color: white;
}

.checkin-wall {
  background: white;
  margin: 0 16px;
  border-radius: 12px;
  overflow: hidden;
}

.wall-tabs {
  display: flex;
  border-bottom: 1px solid #f0f0f0;
}

.tab-item {
  flex: 1;
  text-align: center;
  padding: 16px;
  cursor: pointer;
  font-size: 16px;
}

.tab-item.active {
  color: #409eff;
  border-bottom: 2px solid #409eff;
}

.tab-content {
  padding: 16px;
  min-height: 200px;
}

.empty-tips {
  text-align: center;
  color: #999;
  padding: 40px 20px;
}

.checkin-card {
  background: #f9f9f9;
  border-radius: 8px;
  padding: 12px;
  margin-bottom: 12px;
}

.checkin-user {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 8px;
}

.user-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
}

.user-name {
  font-size: 14px;
  font-weight: bold;
}

.checkin-images {
  display: flex;
  gap: 8px;
  overflow-x: auto;
  margin-bottom: 8px;
}

.checkin-images img {
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 8px;
  flex-shrink: 0;
}

.checkin-content {
  font-size: 14px;
  line-height: 1.5;
  margin-bottom: 8px;
}

.checkin-time {
  font-size: 12px;
  color: #999;
}

.checkin-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.checkin-actions {
  display: flex;
  gap: 16px;
}

.checkin-actions span {
  cursor: pointer;
  font-size: 14px;
}
</style>
