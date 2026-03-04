<template>
  <view class="profile-page">
    <!-- 用户信息头部 -->
    <view class="profile-header">
      <view class="avatar-section">
        <image :src="userStore.userInfo?.avatar || defaultAvatar" class="user-avatar" />
        <text class="user-name" v-if="userStore.isLoggedIn">{{ userStore.userInfo?.nickname || '旅行者' }}</text>
        <text class="user-name" v-else @click="goToLogin">点击登录</text>
        <text class="user-phone" v-if="userStore.isLoggedIn && userStore.userInfo?.phone">
          {{ maskPhone(userStore.userInfo.phone) }}
        </text>
      </view>
    </view>
    
    <!-- 统计数据 -->
    <view class="stats-card">
      <view class="stat-item" @click="goToFootprint('cities')">
        <text class="stat-num">{{ userStore.footprint.totalCities || 0 }}</text>
        <text class="stat-label">城市</text>
      </view>
      <view class="stat-item" @click="goToFootprint('spots')">
        <text class="stat-num">{{ userStore.footprint.totalSpots || 0 }}</text>
        <text class="stat-label">景点</text>
      </view>
      <view class="stat-item">
        <text class="stat-num">{{ userStore.footprint.totalCheckins || 0 }}</text>
        <text class="stat-label">打卡</text>
      </view>
    </view>
    
    <!-- 功能菜单 -->
    <view class="menu-section">
      <view class="menu-item" @click="goToFootprint('cities')">
        <text class="menu-icon">🏙️</text>
        <text class="menu-text">我的足迹</text>
        <text class="menu-arrow">→</text>
      </view>
      <view class="menu-item" @click="goToMap">
        <text class="menu-icon">🗺️</text>
        <text class="menu-text">探索中国</text>
        <text class="menu-arrow">→</text>
      </view>
    </view>
    
    <!-- 设置菜单 -->
    <view class="menu-section">
      <view class="menu-item" @click="editNickname" v-if="userStore.isLoggedIn">
        <text class="menu-icon">✏️</text>
        <text class="menu-text">修改昵称</text>
        <text class="menu-arrow">→</text>
      </view>
      <view class="menu-item" @click="handleLogout" v-if="userStore.isLoggedIn">
        <text class="menu-icon">🚪</text>
        <text class="menu-text">退出登录</text>
        <text class="menu-arrow">→</text>
      </view>
    </view>
  </view>
</template>

<script setup>
import { computed } from 'vue'
import { useUserStore } from '@/store/user'

const userStore = useUserStore()

const defaultAvatar = 'https://picsum.photos/200/200'

function maskPhone(phone) {
  if (!phone) return ''
  return phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2')
}

function goToLogin() {
  uni.navigateTo({ url: '/pages/login/login' })
}

function goToFootprint(type) {
  if (!userStore.isLoggedIn) {
    uni.showToast({ title: '请先登录', icon: 'none' })
    return
  }
  uni.showToast({ title: '功能开发中', icon: 'none' })
}

function goToMap() {
  uni.switchTab({ url: '/pages/index/index' })
}

function editNickname() {
  uni.showModal({
    title: '修改昵称',
    editable: true,
    success: async (res) => {
      if (res.confirm && res.content) {
        // 调用API修改昵称
        uni.showToast({ title: '功能开发中', icon: 'none' })
      }
    }
  })
}

function handleLogout() {
  uni.showModal({
    title: '提示',
    content: '确定要退出登录吗？',
    success: (res) => {
      if (res.confirm) {
        userStore.logout()
        uni.showToast({ title: '已退出登录', icon: 'success' })
      }
    }
  })
}
</script>

<style lang="scss" scoped>
.profile-page {
  min-height: 100vh;
  background: #f5f5f5;
}

.profile-header {
  background: linear-gradient(135deg, #667EEA 0%, #764BA2 100%);
  padding: 60px 20px 40px;
  text-align: center;
  color: white;
  
  .avatar-section {
    display: flex;
    flex-direction: column;
    align-items: center;
    
    .user-avatar {
      width: 80px;
      height: 80px;
      border-radius: 50%;
      border: 3px solid white;
      margin-bottom: 12px;
    }
    
    .user-name {
      font-size: 20px;
      font-weight: bold;
      margin-bottom: 4px;
    }
    
    .user-phone {
      font-size: 14px;
      opacity: 0.8;
    }
  }
}

.stats-card {
  display: flex;
  background: white;
  margin: -20px 16px 16px;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.1);
  
  .stat-item {
    flex: 1;
    text-align: center;
    
    .stat-num {
      display: block;
      font-size: 24px;
      font-weight: bold;
      color: #409EFF;
      margin-bottom: 4px;
    }
    
    .stat-label {
      font-size: 12px;
      color: #999;
    }
  }
}

.menu-section {
  background: white;
  margin: 0 16px 16px;
  border-radius: 12px;
  overflow: hidden;
  
  .menu-item {
    display: flex;
    align-items: center;
    padding: 16px;
    border-bottom: 1px solid #f0f0f0;
    
    &:last-child {
      border-bottom: none;
    }
    
    .menu-icon {
      font-size: 20px;
      margin-right: 12px;
    }
    
    .menu-text {
      flex: 1;
      font-size: 16px;
    }
    
    .menu-arrow {
      color: #999;
    }
  }
}
</style>
