import request from '@/utils/request'

// 查询前台用户列表
export function listF_user(query) {
  return request({
    url: '/system/f_user/list',
    method: 'get',
    params: query
  })
}

// 查询前台用户详细
export function getF_user(userId) {
  return request({
    url: '/system/f_user/' + userId,
    method: 'get'
  })
}

// 新增前台用户
export function addF_user(data) {
  return request({
    url: '/system/f_user',
    method: 'post',
    data: data
  })
}

// 修改前台用户
export function updateF_user(data) {
  return request({
    url: '/system/f_user',
    method: 'put',
    data: data
  })
}

// 删除前台用户
export function delF_user(userId) {
  return request({
    url: '/system/f_user/' + userId,
    method: 'delete'
  })
}
