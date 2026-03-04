<template>
  <div class="map-container">
    <!-- 地图容器 -->
    <div id="chinaMap" class="map-view"></div>
    
    <!-- 顶部标题栏 -->
    <div class="map-header">
      <div class="header-title"> 🗺️ 旅途 - 中国地图</div>
      <div class="header-subtitle">点击城市，探索更多景点</div>
    </div>

    <!-- 底部统计栏 -->
    <div class="map-footer" v-if="userStats">
      <div class="stat-item">
        <span class="stat-num">{{ userStats.totalCities || 0 }}</span>
        <span class="stat-label">已打卡城市</span>
      </div>
      <div class="stat-item">
        <span class="stat-num">{{ userStats.totalSpots || 0 }}</span>
        <span class="stat-label">已打卡景点</span>
      </div>
      <div class="stat-item">
        <span class="stat-num">{{ userStats.totalCheckins || 0 }}</span>
        <span class="stat-label">打卡次数</span>
      </div>
    </div>

    <!-- 城市选择弹窗 -->
    <el-dialog
      v-model="cityDialogVisible"
      :title="selectedCity ? selectedCity.cityName : '选择城市'"
      width="90%"
      top="20vh"
    >
      <div v-if="selectedCity" class="city-info">
        <div class="city-name">{{ selectedCity.cityName }}</div>
        <div class="city-desc" v-if="selectedCity.level === 1">
          共 {{ selectedCity.childrenCount || 0 }} 个市
        </div>
        <div class="city-spots" v-if="spotList.length > 0">
          <div class="spots-title">热门景点：</div>
          <div class="spots-list">
            <div 
              v-for="spot in spotList.slice(0, 6)" 
              :key="spot.spotId"
              class="spot-tag"
              :class="{ 'spot-checked': spot.isChecked }"
              @click="goToSpot(spot)"
            >
              {{ spot.spotName }}
              <span v-if="spot.isChecked" class="checked-badge">✓</span>
            </div>
          </div>
        </div>
        <el-button type="primary" class="explore-btn" @click="goToCity(selectedCity)">
          进入城市 →
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { listProvinces, listCityChildren, getCity } from '@/api/travel/city'
import { listSpotByCity } from '@/api/travel/spot'
import { getMyFootprint } from '@/api/travel/footprint'
import { getUserInfo } from '@/api/travel/user'

const router = useRouter()

// 数据
const provinceList = ref([])
const checkedCityIds = ref([])
const userStats = ref(null)
const cityDialogVisible = ref(false)
const selectedCity = ref(null)
const spotList = ref([])
const mapInstance = ref(null)

// 中国省份中心点数据
const provinceCenters = {
  '北京': { center: [116.46, 39.92], level: 1 },
  '天津': { center: [117.2, 39.13], level: 1 },
  '河北': { center: [114.48, 38.03], level: 1 },
  '山西': { center: [112.53, 37.87], level: 1 },
  '内蒙古': { center: [111.67, 40.82], level: 1 },
  '辽宁': { center: [123.38, 41.8], level: 1 },
  '吉林': { center: [125.35, 43.88], level: 1 },
  '黑龙江': { center: [126.63, 45.75], level: 1 },
  '上海': { center: [121.48, 31.22], level: 1 },
  '江苏': { center: [118.78, 32.04], level: 1 },
  '浙江': { center: [120.19, 30.26], level: 1 },
  '安徽': { center: [117.27, 31.86], level: 1 },
  '福建': { center: [119.3, 26.08], level: 1 },
  '江西': { center: [115.89, 28.68], level: 1 },
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

// 初始化地图
onMounted(async () => {
  await loadUserStats()
  await loadProvinces()
  initMap()
})

// 加载用户统计
async function loadUserStats() {
  try {
    const res = await getMyFootprint()
    if (res.code === 200) {
      userStats.value = res.msg
    }
  } catch (e) {
    console.error('获取用户统计失败', e)
  }
}

// 加载省份列表
async function loadProvinces() {
  try {
    const res = await listProvinces()
    if (res.code === 200) {
      provinceList.value = res.msg || res.data || []
      // 提取已打卡城市ID
      checkedCityIds.value = provinceList.value
        .filter(p => p.isChecked)
        .map(p => p.cityId)
    }
  } catch (e) {
    console.error('获取省份失败', e)
  }
}

// 初始化高德地图
function initMap() {
  // 动态加载高德地图
  if (!window.AMap) {
    const script = document.createElement('script')
    script.src = 'https://webapi.amap.com/maps?v=2.0&key=YOUR_AMAP_KEY'
    script.onload = () => {
      createMap()
    }
    document.head.appendChild(script)
  } else {
    createMap()
  }
}

function createMap() {
  mapInstance.value = new window.AMap.Map('chinaMap', {
    zoom: 4,
    center: [105, 36],
    mapStyle: 'amap://styles/normal',
    viewMode: '2D'
  })

  // 绑定点击事件
  mapInstance.value.on('click', (e) => {
    handleMapClick(e)
  })

  // 添加省份标记
  addProvinceMarkers()
}

// 添加省份标记
function addProvinceMarkers() {
  provinceList.value.forEach(province => {
    const center = provinceCenters[province.cityName]
    if (!center) return

    const isChecked = checkedCityIds.value.includes(province.cityId)

    const marker = new window.AMap.Marker({
      position: new window.AMap.LngLat(center[0], center[1]),
      title: province.cityName,
      content: `<div class="province-marker ${isChecked ? 'checked' : ''}">${province.cityName}</div>`
    })

    marker.on('click', () => {
      handleProvinceClick(province)
    })

    mapInstance.value.add(marker)
  })
}

// 处理地图点击
function handleMapClick(e) {
  // 可以在这里实现点击空白处返回等逻辑
}

// 处理省份点击
async function handleProvinceClick(province) {
  selectedCity.value = province
  cityDialogVisible.value = true

  // 加载该省份的景点
  try {
    const res = await listSpotByCity(province.cityId)
    if (res.code === 200) {
      spotList.value = res.msg || res.data || []
    }
  } catch (e) {
    console.error('获取景点失败', e)
    spotList.value = []
  }

  // 地图移动到该省份
  const center = provinceCenters[province.cityName]
  if (center) {
    mapInstance.value.setZoomAndCenter(6, new window.AMap.LngLat(center[0], center[1]))
  }
}

// 进入城市页面
function goToCity(city) {
  router.push(`/travel/city/${city.cityId}`)
}

// 进入景点详情
function goToSpot(spot) {
  router.push(`/travel/spot/${spot.spotId}`)
}

onUnmounted(() => {
  if (mapInstance.value) {
    mapInstance.value.destroy()
  }
})
</script>

<style scoped>
.map-container {
  width: 100%;
  height: 100vh;
  position: relative;
  overflow: hidden;
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
  padding: 20px;
  background: linear-gradient(180deg, rgba(0,0,0,0.6) 0%, transparent 100%);
  color: white;
  z-index: 10;
}

.header-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 4px;
}

.header-subtitle {
  font-size: 14px;
  opacity: 0.8;
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
}

.stat-item {
  text-align: center;
}

.stat-num {
  display: block;
  font-size: 24px;
  font-weight: bold;
  color: #409eff;
}

.stat-label {
  font-size: 12px;
  color: #999;
}

.city-info {
  text-align: center;
  padding: 10px;
}

.city-name {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 8px;
}

.city-desc {
  color: #666;
  margin-bottom: 16px;
}

.spots-title {
  text-align: left;
  font-weight: bold;
  margin-bottom: 8px;
}

.spots-list {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 16px;
}

.spot-tag {
  padding: 6px 12px;
  background: #f0f0f0;
  border-radius: 16px;
  font-size: 12px;
  cursor: pointer;
}

.spot-tag.spot-checked {
  background: #fff7e6;
  color: #fa8c16;
  border: 1px solid #fa8c16;
}

.checked-badge {
  margin-left: 4px;
}

.explore-btn {
  width: 100%;
  margin-top: 16px;
}
</style>

<style>
/* 全局样式 - 省份标记 */
.province-marker {
  padding: 6px 12px;
  background: white;
  border-radius: 4px;
  font-size: 12px;
  box-shadow: 0 2px 6px rgba(0,0,0,0.2);
  cursor: pointer;
  white-space: nowrap;
}

.province-marker.checked {
  background: #fa8c16;
  color: white;
  font-weight: bold;
}
</style>
