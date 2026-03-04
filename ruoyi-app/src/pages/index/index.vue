<template>
  <view class="page">
    <!-- 地图容器 -->
    <view class="map-container">
      <map 
        id="chinaMap"
        class="map-view"
        :latitude="mapCenter.lat"
        :longitude="mapCenter.lng"
        :zoom="4"
        :markers="markers"
        @markertap="onMarkerTap"
        @regionchange="onRegionChange"
        show-location
      />
      
      <!-- 顶部标题栏 -->
      <view class="map-header">
        <view class="header-title">🗺️ 旅途</view>
        <view class="header-subtitle">点击城市，探索更多景点</view>
      </view>
      
      <!-- 底部统计栏 -->
      <view class="map-footer" v-if="userStore.isLoggedIn">
        <view class="stat-item">
          <text class="stat-num">{{ userStore.totalCities }}</text>
          <text class="stat-label">已打卡城市</text>
        </view>
        <view class="stat-item">
          <text class="stat-num">{{ userStore.totalSpots }}</text>
          <text class="stat-label">已打卡景点</text>
        </view>
        <view class="stat-item">
          <text class="stat-num">{{ userStore.footprint.totalCheckins }}</text>
          <text class="stat-label">打卡次数</text>
        </view>
      </view>
    </view>
    
    <!-- 城市选择弹窗 -->
    <uni-popup ref="cityPopup" type="bottom">
      <view class="city-popup">
        <view class="popup-header">
          <text class="city-name">{{ selectedCity.cityName }}</text>
          <text class="close-btn" @click="closeCityPopup">✕</text>
        </view>
        <view class="popup-content">
          <view class="spot-list" v-if="spotList.length > 0">
            <view 
              v-for="spot in spotList" 
              :key="spot.spotId"
              class="spot-item"
              :class="{ checked: spot.isChecked }"
              @click="goToSpot(spot)"
            >
              <image class="spot-cover" :src="spot.coverImage || defaultCover" mode="aspectFill" />
              <view class="spot-info">
                <text class="spot-name">{{ spot.spotName }}</text>
                <text class="spot-count">📍 {{ spot.checkinCount || 0 }}人打卡</text>
              </view>
              <text v-if="spot.isChecked" class="checked-tag">✓ 已点亮</text>
            </view>
          </view>
          <view v-else class="empty-spots">
            <text>该城市暂未添加景点</text>
          </view>
          <button class="explore-btn" @click="goToCity">进入城市 →</button>
        </view>
      </view>
    </uni-popup>
  </view>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useUserStore } from '@/store/user'
import { cityApi, spotApi, footprintApi } from '@/api'

const userStore = useUserStore()

// 地图中心点
const mapCenter = ref({ lat: 36, lng: 105 })

// 省份标记点
const markers = ref([])

// 选中的城市
const selectedCity = ref({})
const spotList = ref([])
const defaultCover = 'https://picsum.photos/200/150'

// 中国省份中心点数据
const provinceCenters = {
  '北京': [116.46, 39.92],
  '天津': [117.2, 39.13],
  '河北': [114.48, 38.03],
  '山西': [112.53, 37.87],
  '内蒙古': [111.67, 40.82],
  '辽宁': [123.38, 41.8],
  '吉林': [125.35, 43.88],
  '黑龙江': [126.63, 45.75],
  '上海': [121.48, 31.22],
  '江苏': [118.78, 32.04],
  '浙江': [120.19, 30.26],
  '安徽': [117.27, 31.86],
  '福建': [119.3, 26.08],
  '江西': [115.89, 28.68],
  '山东': [117.0, 36.65],
  '河南': [113.62, 34.76],
  '湖北': [114.31, 30.52],
  '湖南': [112.94, 28.12],
  '广东': [113.23, 23.16],
  '广西': [108.33, 22.84],
  '海南': [110.35, 20.02],
  '重庆': [106.54, 29.59],
  '四川': [104.06, 30.67],
  '贵州': [106.71, 26.57],
  '云南': [102.73, 25.04],
  '西藏': [91.11, 29.65],
  '陕西': [108.95, 34.27],
  '甘肃': [103.73, 36.03],
  '青海': [101.78, 36.62],
  '宁夏': [106.27, 38.47],
  '新疆': [87.68, 43.77],
  '台湾': [121.97, 25.07],
  '香港': [114.17, 22.28],
  '澳门': [113.54, 22.19]
}

// 已打卡城市ID列表
const checkedCityIds = ref([])

onMounted(async () => {
  await loadProvinces()
  if (userStore.isLoggedIn) {
    await userStore.getMyFootprint()
  }
})

// 加载省份数据
async function loadProvinces() {
  try {
    const res = await cityApi.getProvinces()
    if (res.code === 200) {
      const provinces = res.data || []
      // 构建标记点
      markers.value = provinces.map(p => {
        const center = provinceCenters[p.cityName]
        const isChecked = checkedCityIds.value.includes(p.cityId)
        return {
          id: p.cityId,
          latitude: center ? center[1] : p.latitude,
          longitude: center ? center[0] : p.longitude,
          title: p.cityName,
          width: isChecked ? 50 : 40,
          height: isChecked ? 50 : 40,
          iconPath: isChecked ? '/static/marker-checked.png' : '/static/marker.png',
          callout: {
            content: p.cityName,
            color: isChecked ? '#FA8C16' : '#333333',
            fontSize: 12,
            borderRadius: 4,
            padding: 4,
            display: 'BYCLICK',
            bgColor: isChecked ? '#FFF7E6' : '#FFFFFF'
          }
        }
      })
    }
  } catch (e) {
    console.error('获取省份失败', e)
  }
}

// 标记点点击
async function onMarkerTap(e) {
  const markerId = e.detail.markerId
  const province = markers.value.find(m => m.id === markerId)
  if (province) {
    selectedCity.value = { cityId: markerId, cityName: province.title }
    // 加载景点
    await loadSpots(markerId)
    // 打开弹窗
    this.$refs.cityPopup.open()
  }
}

// 加载城市景点
async function loadSpots(cityId) {
  try {
    const res = await spotApi.getSpotsByCity(cityId)
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
    console.error('获取景点失败', e)
    spotList.value = []
  }
}

function closeCityPopup() {
  this.$refs.cityPopup.close()
}

// 跳转到城市页面
function goToCity() {
  uni.navigateTo({
    url: `/pages/city/city?id=${selectedCity.value.cityId}&name=${selectedCity.value.cityName}`
  })
}

// 跳转到景点详情
function goToSpot(spot) {
  uni.navigateTo({
    url: `/pages/spot/spot?id=${spot.spotId}&name=${spot.spotName}`
  })
}

// 地图区域变化
function onRegionChange(e) {
  // 可以在这里处理地图拖动
}
</script>

<style lang="scss" scoped>
.page {
  height: 100vh;
  overflow: hidden;
}

.map-container {
  position: relative;
  height: 100%;
}

.map-view {
  width: 100%;
  height: 100%;
}

.map-header {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  padding: 40px 20px 20px;
  background: linear-gradient(180deg, rgba(0,0,0,0.6) 0%, transparent 100%);
  color: white;
  z-index: 10;
  
  .header-title {
    font-size: 24px;
    font-weight: bold;
  }
  
  .header-subtitle {
    font-size: 14px;
    opacity: 0.8;
    margin-top: 4px;
  }
}

.map-footer {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  justify-content: space-around;
  padding: 16px;
  background: white;
  box-shadow: 0 -2px 10px rgba(0,0,0,0.1);
  z-index: 10;
  
  .stat-item {
    text-align: center;
    
    .stat-num {
      display: block;
      font-size: 20px;
      font-weight: bold;
      color: #409EFF;
    }
    
    .stat-label {
      font-size: 12px;
      color: #999;
    }
  }
}

.city-popup {
  background: white;
  border-radius: 20px 20px 0 0;
  max-height: 70vh;
  
  .popup-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 20px;
    border-bottom: 1px solid #f0f0f0;
    
    .city-name {
      font-size: 20px;
      font-weight: bold;
    }
    
    .close-btn {
      font-size: 20px;
      color: #999;
    }
  }
  
  .popup-content {
    padding: 20px;
    max-height: 60vh;
    overflow-y: auto;
  }
  
  .spot-list {
    .spot-item {
      display: flex;
      align-items: center;
      padding: 12px;
      margin-bottom: 12px;
      background: #f9f9f9;
      border-radius: 12px;
      
      &.checked {
        background: #FFF7E6;
        border: 1px solid #FA8C16;
      }
      
      .spot-cover {
        width: 80px;
        height: 60px;
        border-radius: 8px;
        margin-right: 12px;
      }
      
      .spot-info {
        flex: 1;
        
        .spot-name {
          font-size: 16px;
          font-weight: bold;
          display: block;
        }
        
        .spot-count {
          font-size: 12px;
          color: #999;
        }
      }
      
      .checked-tag {
        color: #FA8C16;
        font-size: 12px;
      }
    }
  }
  
  .empty-spots {
    text-align: center;
    padding: 40px;
    color: #999;
  }
  
  .explore-btn {
    width: 100%;
    margin-top: 20px;
    background: #409EFF;
    color: white;
    border-radius: 25px;
  }
}
</style>
