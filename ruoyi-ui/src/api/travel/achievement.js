import request from '@/utils/request'

export function listAchievement(query) {
  return request({
    url: '/travel/achievement/list',
    method: 'get',
    params: query
  })
}

export function getAchievement(achievementId) {
  return request({
    url: '/travel/achievement/' + achievementId,
    method: 'get'
  })
}

export function addAchievement(data) {
  return request({
    url: '/travel/achievement',
    method: 'post',
    data: data
  })
}

export function updateAchievement(data) {
  return request({
    url: '/travel/achievement',
    method: 'put',
    data: data
  })
}

export function delAchievement(achievementId) {
  return request({
    url: '/travel/achievement/' + achievementId,
    method: 'delete'
  })
}
