import request from '@/utils/request'

// 获取景点列表
export function listSpot(query) {
  return request({
    url: '/travel/spot/list',
    method: 'get',
    params: query
  })
}

// 查询景点详细
export function getSpot(spotId) {
  return request({
    url: '/travel/spot/' + spotId,
    method: 'get'
  })
}

// 获取某城市的景点
export function listSpotByCity(cityId) {
  return request({
    url: '/travel/spot/list/' + cityId,
    method: 'get'
  })
}

// 新增景点
export function addSpot(data) {
  return request({
    url: '/travel/spot',
    method: 'post',
    data: data
  })
}

// 修改景点
export function updateSpot(data) {
  return request({
    url: '/travel/spot',
    method: 'put',
    data: data
  })
}

// 删除景点
export function delSpot(spotId) {
  return request({
    url: '/travel/spot/' + spotId,
    method: 'delete'
  })
}
