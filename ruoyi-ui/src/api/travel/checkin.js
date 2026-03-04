import request from '@/utils/request'

// 获取打卡列表
export function listCheckin(query) {
  return request({
    url: '/travel/checkin/list',
    method: 'get',
    params: query
  })
}

// 查询打卡详细
export function getCheckin(checkinId) {
  return request({
    url: '/travel/checkin/' + checkinId,
    method: 'get'
  })
}

// 获取某景点公开打卡
export function listPublicCheckins(spotId) {
  return request({
    url: '/travel/checkin/public/' + spotId,
    method: 'get'
  })
}

// 获取用户打卡
export function listUserCheckins(userId) {
  return request({
    url: '/travel/checkin/user/' + userId,
    method: 'get'
  })
}

// 打卡
export function addCheckin(data) {
  return request({
    url: '/travel/checkin',
    method: 'post',
    data: data
  })
}

// 修改打卡
export function updateCheckin(data) {
  return request({
    url: '/travel/checkin',
    method: 'put',
    data: data
  })
}

// 删除打卡
export function delCheckin(checkinId) {
  return request({
    url: '/travel/checkin/' + checkinId,
    method: 'delete'
  })
}

// 点赞
export function likeCheckin(checkinId) {
  return request({
    url: '/travel/checkin/like/' + checkinId,
    method: 'post'
  })
}

// 取消点赞
export function unlikeCheckin(checkinId) {
  return request({
    url: '/travel/checkin/like/' + checkinId,
    method: 'delete'
  })
}

// 评论
export function addComment(data) {
  return request({
    url: '/travel/checkin/comment',
    method: 'post',
    data: data
  })
}
