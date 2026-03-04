<template>
  <view class="spot-page">
    <!-- 头部图片 -->
    <view class="spot-header" :style="{ backgroundImage: `url(${spotInfo.coverImage || defaultCover})` }">
      <view class="header-back" @click="goBack">
        <text>← 返回</text>
      </view>
      <view class="header-content">
        <text class="spot-title">{{ spotInfo.spotName }}</text>
        <text class="spot-address" v-if="spotInfo.address">📍 {{ spotInfo.address }}</text>
      </view>
    </view>
    
    <!-- 景点信息 -->
    <view class="spot-info-card">
      <view class="info-row">
        <view class="info-item">
          <text class="info-label">开放时间</text>
          <text class="info-value">{{ spotInfo.openTime || '暂无' }}</text>
        </view>
        <view class="info-item">
          <text class="info-label">门票</text>
          <text class="info-value price">{{ spotInfo.ticketPrice || '免费' }}</text>
        </view>
      </view>
      <view class="info-row">
        <view class="info-item full">
          <text class="info-label">打卡人数</text>
          <text class="info-value highlight">{{ spotInfo.checkinCount || 0 }}</text>
        </view>
      </view>
      <view class="intro-section" v-if="spotInfo.introduction">
        <text class="intro-title">简介</text>
        <text class="intro-content">{{ spotInfo.introduction }}</text>
      </view>
    </view>
    
    <!-- 打卡按钮 -->
    <view class="checkin-section">
      <view v-if="!isChecked" class="checkin-btn" @click="openCheckin">
        <text>📸 我要打卡</text>
      </view>
      <view v-else class="checked-btn">
        <text>✅ 已打卡</text>
      </view>
    </view>
    
    <!-- 打卡墙 -->
    <view class="checkin-wall">
      <view class="wall-tabs">
        <view 
          class="tab-item" 
          :class="{ active: activeTab === 'my' }"
          @click="activeTab = 'my'"
        >
          我的打卡
        </view>
        <view 
          class="tab-item" 
          :class="{ active: activeTab === 'public' }"
          @click="activeTab = 'public'"
        >
          公开打卡
        </view>
      </view>
      
      <!-- 我的打卡 -->
      <scroll-view scroll-y v-if="activeTab === 'my'" class="tab-content">
        <view v-if="myCheckins.length === 0" class="empty-tips">
          暂无打卡记录，快去打卡吧！
        </view>
        <view v-else class="checkin-list">
          <view v-for="checkin in myCheckins" :key="checkin.checkinId" class="checkin-card">
            <view class="checkin-images" v-if="checkin.imageList && checkin.imageList.length">
              <image 
                v-for="(img, idx) in checkin.imageList" 
                :key="idx" 
                :src="img" 
                mode="aspectFill"
              />
            </view>
            <view class="checkin-content">{{ checkin.content || '记录了一次完美的旅行！' }}</view>
            <view class="checkin-time">{{ formatTime(checkin.createTime) }}</view>
          </view>
        </view>
      </scroll-view>
      
      <!-- 公开打卡 -->
      <scroll-view scroll-y v-if="activeTab === 'public'" class="tab-content">
        <view v-if="publicCheckins.length === 0" class="empty-tips">
          暂无公开打卡
        </view>
        <view v-else class="checkin-list">
          <view v-for="checkin in publicCheckins" :key="checkin.checkinId" class="checkin-card">
            <view class="checkin-user">
              <image :src="checkin.avatar || defaultAvatar" class="user-avatar" />
              <text class="user-name">{{ checkin.nickname || '匿名用户' }}</text>
            </view>
            <view class="checkin-images" v-if="checkin.imageList && checkin.imageList.length">
              <image 
                v-for="(img, idx) in checkin.imageList" 
                :key="idx" 
                :src="img"
                mode="aspectFill"
              />
            </view>
            <view class="checkin-content">{{ checkin.content || '记录了一次完美的旅行！' }}</view>
            <view class="checkin-footer">
              <text class="checkin-time">{{ formatTime(checkin.createTime) }}</text>
              <view class="checkin-actions">
                <text @click="handleLike(checkin)">
                  {{ checkin.isLiked ? '❤️' : '🤍' }} {{ checkin.likeCount || 0 }}
                </text>
                <text>💬 {{ checkin.commentCount || 0 }}</text>
              </view>
            </view>
          </view>
        </view>
      </scroll-view>
    </view>
    
    <!-- 打卡弹窗 -->
    <uni-popup ref="checkinPopup" type="bottom">
      <view class="checkin-popup">
        <view class="popup-header">
          <text class="popup-title">发布打卡</text>
          <text class="close-btn" @click="closeCheckin">✕</text>
        </view>
        <view class="popup-content">
          <textarea 
            v-model="checkinForm.content" 
            placeholder="记录你的旅行感受..." 
            class="content-input"
          />
          <view class="upload-section">
            <view class="upload-btn" @click="chooseImage">
              <text>+</text>
            </view>
            <image 
              v-for="(img, idx) in checkinForm.images" 
              :key="idx" 
              :src="img" 
              class="upload-img"
              @click="removeImage(idx)"
            />
          </view>
          <view class="public-switch">
            <text>是否公开</text>
            <switch v-model="checkinForm.isPublic" color="#409EFF" />
          </view>
          <button class="submit-btn" @click="submitCheckin">发布</button>
        </view>
      </view>
    </uni-popup>
  </view>
</template>

<script setup>
import { ref, onLoad } from '@dcloudio/uni-app'
import { spotApi, checkinApi, footprintApi } from '@/api'
import { useUserStore } from '@/store/user'

const userStore = useUserStore()

const spotId = ref('')
const spotInfo = ref({})
const isChecked = ref(false)
const activeTab = ref('my')
const myCheckins = ref([])
const publicCheckins = ref([])
const checkinForm = ref({
  content: '',
  images: [],
  isPublic: true
})

const defaultCover = 'https://picsum.photos/400/300'
const defaultAvatar = 'https://picsum.photos/100/100'

onLoad((options) => {
  spotId.value = options.id || ''
  uni.setNavigationBarTitle({ title: options.name || '景点详情' })
  loadSpotInfo()
  checkIfChecked()
  loadPublicCheckins()
})

async function loadSpotInfo() {
  try {
    const res = await spotApi.getSpot(spotId.value)
    if (res.code === 200) {
      spotInfo.value = res.data || {}
    }
  } catch (e) {
    console.error('获取景点信息失败', e)
  }
}

async function checkIfChecked() {
  if (!userStore.isLoggedIn) return
  try {
    const res = await footprintApi.checkSpot(spotId.value)
    isChecked.value = res.data === true
  } catch (e) {
    isChecked.value = false
  }
}

async function loadPublicCheckins() {
  try {
    const res = await checkinApi.getPublicCheckins(spotId.value)
    if (res.code === 200) {
      const list = res.data || []
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
  uni.navigateBack()
}

function openCheckin() {
  if (!userStore.isLoggedIn) {
    uni.showToast({ title: '请先登录', icon: 'none' })
    uni.navigateTo({ url: '/pages/login/login' })
    return
  }
  this.$refs.checkinPopup.open()
}

function closeCheckin() {
  this.$refs.checkinPopup.close()
}

function chooseImage() {
  uni.chooseImage({
    count: 9 - checkinForm.value.images.length,
    sizeType: ['compressed'],
    sourceType: ['album', 'camera'],
    success: (res) => {
      // 这里应该上传到服务器，获取URL
      // 暂时使用本地路径演示
      checkinForm.value.images = [...checkinForm.value.images, ...res.tempFilePaths]
    }
  })
}

function removeImage(index) {
  checkinForm.value.images.splice(index, 1)
}

async function submitCheckin() {
  if (checkinForm.value.images.length === 0) {
    uni.showToast({ title: '请至少上传一张图片', icon: 'none' })
    return
  }
  
  try {
    await checkinApi.addCheckin({
      spotId: spotId.value,
      cityId: spotInfo.value.cityId,
      content: checkinForm.value.content,
      images: JSON.stringify(checkinForm.value.images),
      isPublic: checkinForm.value.isPublic ? '1' : '0'
    })
    uni.showToast({ title: '打卡成功!', icon: 'success' })
    closeCheckin()
    isChecked.value = true
    loadPublicCheckins()
  } catch (e) {
    uni.showToast({ title: '打卡失败', icon: 'none' })
  }
}

async function handleLike(checkin) {
  if (!userStore.isLoggedIn) {
    uni.showToast({ title: '请先登录', icon: 'none' })
    return
  }
  try {
    await checkinApi.likeCheckin(checkin.checkinId)
    checkin.likeCount = (checkin.likeCount || 0) + 1
    checkin.isLiked = true
  } catch (e) {
    uni.showToast({ title: '操作失败', icon: 'none' })
  }
}

function formatTime(time) {
  if (!time) return ''
  const date = new Date(time)
  return `${date.getFullYear()}-${String(date.getMonth()+1).padStart(2,'0')}-${String(date.getDate()).padStart(2,'0')}`
}
</script>

<style lang="scss" scoped>
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
  
  .header-back {
    position: absolute;
    top: 40px;
    left: 16px;
    color: white;
    font-size: 16px;
    text-shadow: 0 1px 3px rgba(0,0,0,0.5);
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
    
    .spot-title {
      font-size: 28px;
      display: block;
    }
    
    .spot-address {
      font-size: 14px;
      opacity: 0.9;
      margin-top: 4px;
      display: block;
    }
  }
}

.spot-info-card {
  background: white;
  margin: 16px;
  border-radius: 12px;
  padding: 16px;
  
  .info-row {
    display: flex;
    margin-bottom: 12px;
    
    .info-item {
      flex: 1;
      
      &.full {
        flex: none;
        width: 100%;
      }
      
      .info-label {
        font-size: 12px;
        color: #999;
        display: block;
        margin-bottom: 4px;
      }
      
      .info-value {
        font-size: 14px;
        
        &.price {
          color: #FA8C16;
          font-weight: bold;
        }
        
        &.highlight {
          color: #409EFF;
          font-weight: bold;
        }
      }
    }
  }
  
  .intro-section {
    margin-top: 16px;
    padding-top: 16px;
    border-top: 1px solid #f0f0f0;
    
    .intro-title {
      font-weight: bold;
      display: block;
      margin-bottom: 8px;
    }
    
    .intro-content {
      font-size: 14px;
      color: #666;
      line-height: 1.6;
    }
  }
}

.checkin-section {
  padding: 0 16px;
  margin-bottom: 16px;
  
  .checkin-btn, .checked-btn {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 16px;
    border-radius: 12px;
    font-size: 18px;
    font-weight: bold;
  }
  
  .checkin-btn {
    background: linear-gradient(135deg, #409EFF, #67C23A);
    color: white;
  }
  
  .checked-btn {
    background: #FA8C16;
    color: white;
  }
}

.checkin-wall {
  background: white;
  margin: 0 16px;
  border-radius: 12px;
  overflow: hidden;
  
  .wall-tabs {
    display: flex;
    border-bottom: 1px solid #f0f0f0;
    
    .tab-item {
      flex: 1;
      text-align: center;
      padding: 16px;
      font-size: 16px;
      
      &.active {
        color: #409EFF;
        border-bottom: 2px solid #409EFF;
      }
    }
  }
  
  .tab-content {
    height: 400px;
    padding: 16px;
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
    
    .checkin-user {
      display: flex;
      align-items: center;
      margin-bottom: 8px;
      
      .user-avatar {
        width: 32px;
        height: 32px;
        border-radius: 50%;
        margin-right: 8px;
      }
      
      .user-name {
        font-size: 14px;
        font-weight: bold;
      }
    }
    
    .checkin-images {
      display: flex;
      gap: 8px;
      margin-bottom: 8px;
      overflow-x: auto;
      
      image {
        width: 100px;
        height: 100px;
        border-radius: 8px;
        flex-shrink: 0;
      }
    }
    
    .checkin-content {
      font-size: 14px;
      line-height: 1.5;
      margin-bottom: 8px;
    }
    
    .checkin-footer {
      display: flex;
      justify-content: space-between;
      align-items: center;
      
      .checkin-time {
        font-size: 12px;
        color: #999;
      }
      
      .checkin-actions {
        display: flex;
        gap: 16px;
        
        text {
          font-size: 14px;
        }
      }
    }
  }
}

.checkin-popup {
  background: white;
  border-radius: 20px 20px 0 0;
  
  .popup-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 20px;
    border-bottom: 1px solid #f0f0f0;
    
    .popup-title {
      font-size: 18px;
      font-weight: bold;
    }
    
    .close-btn {
      font-size: 20px;
      color: #999;
    }
  }
  
  .popup-content {
    padding: 20px;
    
    .content-input {
      width: 100%;
      height: 100px;
      border: 1px solid #eee;
      border-radius: 8px;
      padding: 12px;
      box-sizing: border-box;
      font-size: 14px;
      margin-bottom: 16px;
    }
    
    .upload-section {
      display: flex;
      flex-wrap: wrap;
      gap: 8px;
      margin-bottom: 16px;
      
      .upload-btn {
        width: 80px;
        height: 80px;
        border: 1px dashed #ddd;
        border-radius: 8px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 30px;
        color: #999;
      }
      
      .upload-img {
        width: 80px;
        height: 80px;
        border-radius: 8px;
      }
    }
    
    .public-switch {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 20px;
      
      text {
        font-size: 14px;
      }
    }
    
    .submit-btn {
      width: 100%;
      background: #409EFF;
      color: white;
      border-radius: 25px;
    }
  }
}
</style>
