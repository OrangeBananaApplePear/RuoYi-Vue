import request from '@/utils/request'

export function listCity(query) {
  return request({
    url: '/travel/city/list',
    method: 'get',
    params: query
  })
}

export function treeCity() {
  return request({
    url: '/travel/city/tree',
    method: 'get'
  })
}

export function getCity(cityId) {
  return request({
    url: '/travel/city/' + cityId,
    method: 'get'
  })
}

export function addCity(data) {
  return request({
    url: '/travel/city',
    method: 'post',
    data: data
  })
}

export function updateCity(data) {
  return request({
    url: '/travel/city',
    method: 'put',
    data: data
  })
}

export function delCity(cityId) {
  return request({
    url: '/travel/city/' + cityId,
    method: 'delete'
  })
}
