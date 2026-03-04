/**
 * 旅途 App - API 接口封装
 * 基于 uni.request 封装
 */

const baseUrl = 'http://localhost:8080' // 后端接口地址

/**
 * 发起网络请求
 */
function request(options) {
  return new Promise((resolve, reject) => {
    const token = uni.getStorageSync('token')
    
    uni.request({
      url: baseUrl + options.url,
      method: options.method || 'GET',
      data: options.data || {},
      header: {
        'Content-Type': 'application/json',
        'Authorization': token ? `Bearer ${token}` : '',
        ...options.header
      },
      success: (res) => {
        if (res.statusCode === 200) {
          resolve(res.data)
        } else if (res.statusCode === 401) {
          // 未登录，跳转登录页
          uni.removeStorageSync('token')
          uni.removeStorageSync('userInfo')
          uni.reLaunch({ url: '/pages/login/login' })
          reject(new Error('未授权'))
        } else {
          uni.showToast({
            title: res.data.msg || '请求失败',
            icon: 'none'
          })
          reject(res.data)
        }
      },
      fail: (err) => {
        uni.showToast({
          title: '网络请求失败',
          icon: 'none'
        })
        reject(err)
      }
    })
  })
}

// ========== 城市 API ==========
export const cityApi = {
  // 获取所有省份
  getProvinces() {
    return request({ url: '/travel/city/provinces' })
  },
  // 获取子城市
  getChildren(parentId) {
    return request({ url: `/travel/city/children/${parentId}` })
  },
  // 获取城市详情
  getCity(cityId) {
    return request({ url: `/travel/city/${cityId}` })
  },
  // 获取用户已打卡城市
  getCheckedCities(userId) {
    return request({ url: `/travel/city/checked/${userId}` })
  }
}

// ========== 景点 API ==========
export const spotApi = {
  // 获取城市景点列表
  getSpotsByCity(cityId) {
    return request({ url: `/travel/spot/list/${cityId}` })
  },
  // 获取景点详情
  getSpot(spotId) {
    return request({ url: `/travel/spot/${spotId}` })
  },
  // 获取用户已打卡景点
  getCheckedSpots(userId) {
    return request({ url: `/travel/spot/checked/${userId}` })
  }
}

// ========== 打卡 API ==========
export const checkinApi = {
  // 获取景点公开打卡
  getPublicCheckins(spotId) {
    return request({ url: `/travel/checkin/public/${spotId}` })
  },
  // 获取用户打卡
  getUserCheckins(userId) {
    return request({ url: `/travel/checkin/user/${userId}` })
  },
  // 获取用户在某景点的打卡
  getUserSpotCheckins(userId, spotId) {
    return request({ url: `/travel/checkin/user/${userId}/spot/${spotId}` })
  },
  // 发起打卡
  addCheckin(data) {
    return request({ url: '/travel/checkin', method: 'POST', data })
  },
  // 点赞
  likeCheckin(checkinId) {
    return request({ url: `/travel/checkin/like/${checkinId}`, method: 'POST' })
  },
  // 取消点赞
  unlikeCheckin(checkinId) {
    return request({ url: `/travel/checkin/like/${checkinId}`, method: 'DELETE' })
  },
  // 评论
  addComment(data) {
    return request({ url: '/travel/checkin/comment', method: 'POST', data })
  }
}

// ========== 足迹 API ==========
export const footprintApi = {
  // 获取我的足迹
  getMyFootprint() {
    return request({ url: '/travel/footprint/my' })
  },
  // 获取用户足迹
  getUserFootprint(userId) {
    return request({ url: `/travel/footprint/user/${userId}` })
  },
  // 检查城市是否已打卡
  checkCity(cityId) {
    return request({ url: `/travel/footprint/check/city/${cityId}` })
  },
  // 检查景点是否已打卡
  checkSpot(spotId) {
    return request({ url: `/travel/footprint/check/spot/${spotId}` })
  }
}

// ========== 用户 API ==========
export const userApi = {
  // 获取当前用户信息
  getMyInfo() {
    return request({ url: '/travel/user/my' })
  },
  // 获取指定用户信息
  getUser(userId) {
    return request({ url: `/travel/user/${userId}` })
  },
  // 更新昵称
  updateNickname(nickname) {
    return request({ url: '/travel/user/nickname', method: 'PUT', data: { nickname } })
  },
  // 更新头像
  updateAvatar(avatar) {
    return request({ url: '/travel/user/avatar', method: 'PUT', data: { avatar } })
  },
  // 手机号登录
  loginByPhone(phone, code) {
    return request({ url: '/auth/login', method: 'POST', data: { phone, code } })
  }
}

export default {
  cityApi,
  spotApi,
  checkinApi,
  footprintApi,
  userApi
}
