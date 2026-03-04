<template>
  <div class="city-page">
    <!-- 头部 -->
    <div class="city-header" :style="{ backgroundImage: `url(${cityInfo.coverImage || defaultCover})` }">
      <div class="header-back" @click="goBack">
        <span>← 返回</span>
      </div>
      <div class="header-content">
        <h1 class="city-title">{{ cityInfo.cityName }}</h1>
        <div class="city-stats">
          <span>{{ spotList.length }} 个景点</span>
          <span v-if="cityInfo.checkinCount"> · {{ cityInfo.checkinCount }} 人次打卡</span>
        </div>
      </div>
    </div>

    <!-- 景点列表 -->
    <div class="spot-list">
      <div 
        v-for="spot in spotList" 
        :key="spot.spotId"
        class="spot-card"
        :class="{ 'spot-checked': spot.isChecked }"
        @click="goToSpot(spot)"
      >
        <div class="spot-cover">
          <img :src="spot.coverImage || defaultSpot" alt="spot.spotName" />
          <div v-if="spot.isChecked" class="checked-badge">
            <span class="badge-icon">⭐</span>
            <span class="badge-text">已点亮</span>
          </div>
        </div>
        <div class="spot-info">
          <h3 class="spot-name">{{ spot.spotName }}</h3>
          <p class="spot-intro">{{ spot.introduction || '暂无介绍' }}</p>
          <div class="spot-meta">
            <span class="checkin-count">📍 {{ spot.checkinCount || 0 }} 人打卡</span>
            <span class="ticket-price" v-if="spot.ticketPrice">{{ spot.ticketPrice }}</span>
            <span class="ticket-price free" v-else>免费</span>
          </div>
        </div>
      </div>

      <!-- 空状态 -->
      <div v-if="spotList.length === 0" class="empty-state">
        <div class="empty-icon">🏙️</div>
        <div class="empty-text">该城市暂未添加景点</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { getCity } from '@/api/travel/city'
import { listSpotByCity } from '@/api/travel/spot'
import { checkSpot } from '@/api/travel/footprint'

const router = useRouter()
const route = useRoute()

const cityId = ref(route.params.id)
const cityInfo = ref({})
const spotList = ref([])
const defaultCover = 'https://picx.zhimg.com/80/v2-5c8f3f8c5a4e5c8f3f8c5a4e5c8f3f8c5a4e_1440w.jpg'
const defaultSpot = 'https://picx.zhimg.com/80/v2-placeholder.jpg'

onMounted(async () => {
  await loadCityInfo()
  await loadSpots()
})

// 加载城市信息
async function loadCityInfo() {
  try {
    const res = await getCity(cityId.value)
    if (res.code === 200) {
      cityInfo.value = res.msg || res.data || {}
    }
  } catch (e) {
    console.error('获取城市信息失败', e)
  }
}

// 加载景点列表
async function loadSpots() {
  try {
    const res = await listSpotByCity(cityId.value)
    if (res.code === 200) {
      const spots = res.msg || res.data || []
      // 检查每个景点是否已打卡
      for (const spot of spots) {
        try {
          const checkRes = await checkSpot(spot.spotId)
          spot.isChecked = checkRes.msg === true || checkRes.data === true
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
  router.back()
}

function goToSpot(spot) {
  router.push(`/travel/spot/${spot.spotId}`)
}
</script>

<style scoped>
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
}

.header-back {
  padding: 16px;
  color: white;
  font-size: 16px;
  text-shadow: 0 1px 3px rgba(0,0,0,0.5);
  cursor: pointer;
}

.header-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  padding: 16px;
  background: linear-gradient(transparent, rgba(0,0,0,0.6));
  color: white;
}

.city-title {
  font-size: 28px;
  margin: 0 0 8px 0;
}

.city-stats {
  font-size: 14px;
  opacity: 0.9;
}

.spot-list {
  padding: 16px;
}

.spot-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  margin-bottom: 16px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
}

.spot-card.spot-checked {
  border: 2px solid #fa8c16;
}

.spot-cover {
  position: relative;
  height: 160px;
}

.spot-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.checked-badge {
  position: absolute;
  top: 12px;
  right: 12px;
  background: linear-gradient(135deg, #fa8c16, #ffc069);
  padding: 4px 12px;
  border-radius: 16px;
  color: white;
  font-size: 12px;
  display: flex;
  align-items: center;
  gap: 4px;
}

.spot-info {
  padding: 12px;
}

.spot-name {
  font-size: 18px;
  margin: 0 0 8px 0;
}

.spot-intro {
  font-size: 14px;
  color: #666;
  margin: 0 0 8px 0;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.spot-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 12px;
  color: #999;
}

.checkin-count {
  color: #409eff;
}

.ticket-price {
  background: #f0f0f0;
  padding: 2px 8px;
  border-radius: 4px;
}

.ticket-price.free {
  color: #52c41a;
  background: #f6ffed;
}

.empty-state {
  text-align: center;
  padding: 60px 20px;
}

.empty-icon {
  font-size: 48px;
  margin-bottom: 16px;
}

.empty-text {
  color: #999;
}
</style>
