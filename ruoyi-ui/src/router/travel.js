// 旅途 App 移动端路由配置
// 用于移动端 (H5 / uni-app) 的路由

const travelRoutes = [
  // 首页 - 中国地图
  {
    path: '/travel/map',
    name: 'TravelMap',
    component: () => import('@/views/travel/map/index'),
    meta: { title: '旅途 - 中国地图', keepAlive: true }
  },
  // 城市页面
  {
    path: '/travel/city/:id',
    name: 'TravelCity',
    component: () => import('@/views/travel/city/index'),
    meta: { title: '城市详情' }
  },
  // 景点详情
  {
    path: '/travel/spot/:id',
    name: 'TravelSpot',
    component: () => import('@/views/travel/spot/index'),
    meta: { title: '景点详情' }
  },
  // 个人中心
  {
    path: '/travel/profile',
    name: 'TravelProfile',
    component: () => import('@/views/travel/profile/index'),
    meta: { title: '个人中心' }
  },
  // 我的足迹
  {
    path: '/travel/footprint',
    name: 'TravelFootprint',
    component: () => import('@/views/travel/profile/footprint'),
    meta: { title: '我的足迹' }
  },
  // 我的打卡
  {
    path: '/travel/checkins',
    name: 'TravelCheckins',
    component: () => import('@/views/travel/profile/checkins'),
    meta: { title: '我的打卡' }
  }
]

export default travelRoutes
