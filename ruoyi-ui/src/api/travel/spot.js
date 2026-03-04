import request from '@/utils/request'

export function listSpot(query) {
  return request({
    url: '/travel/spot/list',
    method: 'get',
    params: query
  })
}

export function getSpot(spotId) {
  return request({
    url: '/travel/spot/' + spotId,
    method: 'get'
  })
}

export function addSpot(data) {
  return request({
    url: '/travel/spot',
    method: 'post',
    data: data
  })
}

export function updateSpot(data) {
  return request({
    url: '/travel/spot',
    method: 'put',
    data: data
  })
}

export function delSpot(spotId) {
  return request({
    url: '/travel/spot/' + spotId,
    method: 'delete'
  })
}
