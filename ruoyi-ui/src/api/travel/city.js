import request from '@/utils/request'

// 获取城市列表
export function listCity(query) {
  return request({
    url: '/travel/city/list',
    method: 'get',
    params: query
  })
}

// 查询城市详细
export function getCity(cityId) {
  return request({
    url: '/travel/city/' + cityId,
    method: 'get'
  })
}

// 获取所有省份
export function listProvinces() {
  return request({
    url: '/travel/city/provinces',
    method: 'get'
  })
}

// 获取子城市
export function listCityChildren(parentId) {
  return request({
    url: '/travel/city/children/' + parentId,
    method: 'get'
  })
}

// 新增城市
export function addCity(data) {
  return request({
    url: '/travel/city',
    method: 'post',
    data: data
  })
}

// 修改城市
export function updateCity(data) {
  return request({
    url: '/travel/city',
    method: 'put',
    data: data
  })
}

// 删除城市
export function delCity(cityId) {
  return request({
    url: '/travel/city/' + cityId,
    method: 'delete'
  })
}
