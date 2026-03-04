import request from '@/utils/request'

// 城市 API
export function getProvinces() {
  return request({ url: '/travel/city/provinces', method: 'get' })
}

export function getCityChildren(parentId) {
  return request({ url: `/travel/city/children/${parentId}`, method: 'get' })
}

export function getCity(cityId) {
  return request({ url: `/travel/city/${cityId}`, method: 'get' })
}

export function getCheckedCities(userId) {
  return request({ url: `/travel/city/checked/${userId}`, method: 'get' })
}

// 景点 API
export function getSpotsByCity(cityId) {
  return request({ url: `/travel/spot/list/${cityId}`, method: 'get' })
}

export function getSpot(spotId) {
  return request({ url: `/travel/spot/${spotId}`, method: 'get' })
}

export function getCheckedSpots(userId) {
  return request({ url: `/travel/spot/checked/${userId}`, method: 'get' })
}

// 打卡 API
export function getPublicCheckins(spotId) {
  return request({ url: `/travel/checkin/public/${spotId}`, method: 'get' })
}

export function getUserCheckins(userId) {
  return request({ url: `/travel/checkin/user/${userId}`, method: 'get' })
}

export function getUserSpotCheckins(userId, spotId) {
  return request({ url: `/travel/checkin/user/${userId}/spot/${spotId}`, method: 'get' })
}

export function addCheckin(data) {
  return request({ url: '/travel/checkin', method: 'post', data })
}

export function likeCheckin(checkinId) {
  return request({ url: `/travel/checkin/like/${checkinId}`, method: 'post' })
}

export function unlikeCheckin(checkinId) {
  return request({ url: `/travel/checkin/like/${checkinId}`, method: 'delete' })
}

export function addComment(data) {
  return request({ url: '/travel/checkin/comment', method: 'post', data })
}

// 足迹 API
export function getMyFootprint() {
  return request({ url: '/travel/footprint/my', method: 'get' })
}

export function getUserFootprint(userId) {
  return request({ url: `/travel/footprint/user/${userId}`, method: 'get' })
}

export function checkCity(cityId) {
  return request({ url: `/travel/footprint/check/city/${cityId}`, method: 'get' })
}

export function checkSpot(spotId) {
  return request({ url: `/travel/footprint/check/spot/${spotId}`, method: 'get' })
}

// 用户 API
export function getMyInfo() {
  return request({ url: '/travel/user/my', method: 'get' })
}

export function getUser(userId) {
  return request({ url: `/travel/user/${userId}`, method: 'get' })
}

export function updateNickname(nickname) {
  return request({ url: '/travel/user/nickname', method: 'put', params: { nickname } })
}

export function updateAvatar(avatar) {
  return request({ url: '/travel/user/avatar', method: 'put', params: { avatar } })
}

export function loginByPhone(phone, code) {
  return request({ url: '/auth/login', method: 'post', data: { phone, code } })
}
