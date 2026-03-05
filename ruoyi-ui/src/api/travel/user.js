import request from '@/utils/request'

export function listUser(query) {
  return request({
    url: '/travel/user/list',
    method: 'get',
    params: query
  })
}

export function getUser(phone) {
  return request({
    url: '/travel/user/' + phone,
    method: 'get'
  })
}

export function addUser(data) {
  return request({
    url: '/travel/user',
    method: 'post',
    data: data
  })
}

export function updateUser(data) {
  return request({
    url: '/travel/user',
    method: 'put',
    data: data
  })
}

export function delUser(phones) {
  return request({
    url: '/travel/user/' + phones,
    method: 'delete'
  })
}
