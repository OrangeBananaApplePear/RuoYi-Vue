import request from '@/utils/request'

// 获取用户足迹
export function getMyFootprint() {
  return request({
    url: '/travel/footprint/my',
    method: 'get'
  })
}

// 获取指定用户足迹
export function getUserFootprint(userId) {
  return request({
    url: '/travel/footprint/user/' + userId,
    method: 'get'
  })
}

// 检查城市是否已打卡
export function checkCity(cityId) {
  return request({
    url: '/travel/footprint/check/city/' + cityId,
    method: 'get'
  })
}

// 检查景点是否已打卡
export function checkSpot(spotId) {
  return request({
    url: '/travel/footprint/check/spot/' + spotId,
    method: 'get'
  })
}
