<template>
  <view class="city-page">
    <!-- 头部 -->
    <view class="city-header" :style="{ backgroundImage: `url(${defaultCover})` }">
      <view class="header-back" @click="goBack">
        <text>← 返回</text>
      </view>
      <view class="header-content">
        <text class="city-title">{{ cityName }}</text>
        <text class="city-stats">{{ spotList.length }} 个景点</text>
      </view>
    </view>
    
    <!-- 景点列表 -->
    <scroll-view scroll-y class="spot-list">
      <view 
        v-for="spot in spotList" 
        :key="spot.spotId"
        class="spot-card"
        :class="{ 'spot-checked': spot.isChecked }"
        @click="goToSpot(spot)"
      >
        <image class="spot-cover" :src="spot.coverImage || defaultCover" mode="aspectFill" />
        <view class="spot-info">
          <text class="spot-name">{{ spot.spotName }}</text>
          <text class="spot-intro">{{ spot.introduction || '暂无介绍' }}</text>
          <view class="spot-meta">
            <text class="checkin-count">📍 {{ spot.checkinCount || 0 }} 人打卡</text>
            <text class="ticket-price" :class="{ free: !spot.ticketPrice || spot.ticketPrice === '免费' }">
              {{ spot.ticketPrice || '免费' }}
            </text>
          </view>
        </view>
        <view v-if="spot.isChecked" class="checked-badge">
          <text>⭐ 已点亮</text>
        </view>
      </view>
      
      <!-- 空状态 -->
      <view v-if="spotList.length === 0" class="empty-state">
        <text class="empty-icon">🏙️</text>
        <text class="empty-text">该城市暂未添加景点</text>
      </view>
    </scroll-view>
  </view>
</template>

<script setup>
import { ref, onLoad } from '@dcloudio/uni-app'
import { spotApi, footprintApi } from '@/api'

const cityId = ref('')
const cityName = ref('')
const spotList = ref([])
const defaultCover = 'https://picsum.photos/400/300'

onLoad((options) => {
  cityId.value = options.id || ''
  cityName.value = options.name || '城市'
  uni.setNavigationBarTitle({ title: cityName.value })
  loadSpots()
})

// 加载景点列表
async function loadSpots() {
  try {
    const res = await spotApi.getSpotsByCity(cityId.value)
    if (res.code === 200) {
      const spots = res.data || []
      // 检查是否已打卡
      for (const spot of spots) {
        try {
          const checkRes = await footprintApi.checkSpot(spot.spotId)
          spot.isChecked = checkRes.data === true
        } catch (e) {
          spot.isChecked = false
        }
      }
      spotList.value = spots
    }
  } catch (e) {
    console.error('获取景点列表失败', e)
  }
}

function goBack() {
  uni.navigateBack()
}

function goToSpot(spot) {
  uni.navigateTo({
    url: `/pages/spot/spot?id=${spot.spotId}&name=${spot.spotName}`
  })
}
</script>

<style lang="scss" scoped>
.city-page {
  min-height: 100vh;
  background: #f5f5f5;
}

.city-header {
  height: 200px;
  background-size: cover;
  background-position: center;
  position: relative;
  display: flex;
  flex-direction: column;
  
  .header-back {
    padding: 40px 16px 16px;
    color: white;
    font-size: 16px;
    text-shadow: 0 1px 3px rgba(0,0,0,0.5);
  }
  
  .header-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: flex-end;
    padding: 16px;
    background: linear-gradient(transparent, rgba(0,0,0,0.6));
    color: white;
    
    .city-title {
      font-size: 28px;
      font-weight: bold;
    }
    
    .city-stats {
      font-size: 14px;
      opacity: 0.9;
      margin-top: 4px;
    }
  }
}

.spot-list {
  padding: 16px;
  height: calc(100vh - 200px);
}

.spot-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  margin-bottom: 16px;
  position: relative;
  
  &.spot-checked {
    border: 2px solid #FA8C16;
  }
  
  .spot-cover {
    width: 100%;
    height: 160px;
  }
  
  .spot-info {
    padding: 12px;
    
    .spot-name {
      font-size: 18px;
      font-weight: bold;
      display: block;
      margin-bottom: 8px;
    }
    
    .spot-intro {
      font-size: 14px;
      color: #666;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      overflow: hidden;
      margin-bottom: 8px;
    }
    
    .spot-meta {
      display: flex;
      justify-content: space-between;
      align-items: center;
      font-size: 12px;
      color: #999;
      
      .checkin-count {
        color: #409EFF;
      }
      
      .ticket-price {
        background: #f0f0f0;
        padding: 2px 8px;
        border-radius: 4px;
        
        &.free {
          color: #52C41A;
          background: #F6FFED;
        }
      }
    }
  }
  
  .checked-badge {
    position: absolute;
    top: 12px;
    right: 12px;
    background: linear-gradient(135deg, #FA8C16, #FFC069);
    padding: 4px 12px;
    border-radius: 16px;
    color: white;
    font-size: 12px;
  }
}

.empty-state {
  text-align: center;
  padding: 60px 20px;
  
  .empty-icon {
    font-size: 48px;
    display: block;
    margin-bottom: 16px;
  }
  
  .empty-text {
    color: #999;
  }
}
</style>
