import request from '@/utils/request'

export function listCheckin(query) {
  return request({
    url: '/travel/checkin/list',
    method: 'get',
    params: query
  })
}

export function getCheckin(checkinId) {
  return request({
    url: '/travel/checkin/' + checkinId,
    method: 'get'
  })
}

export function delCheckin(checkinId) {
  return request({
    url: '/travel/checkin/' + checkinId,
    method: 'delete'
  })
}
