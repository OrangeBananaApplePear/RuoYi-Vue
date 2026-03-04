import request from '@/utils/request'

// 获取当前用户信息
export function getUserInfo() {
  return request({
    url: '/travel/user/my',
    method: 'get'
  })
}

// 获取指定用户信息
export function getUser(userId) {
  return request({
    url: '/travel/user/' + userId,
    method: 'get'
  })
}

// 更新用户昵称
export function updateNickname(nickname) {
  return request({
    url: '/travel/user/nickname',
    method: 'put',
    params: { nickname }
  })
}

// 更新用户头像
export function updateAvatar(avatar) {
  return request({
    url: '/travel/user/avatar',
    method: 'put',
    params: { avatar }
  })
}
