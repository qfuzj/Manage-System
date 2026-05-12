import request from '@/utils/request'

// 查询工装夹具清单列表
export function listTool(query) {
  return request({
    url: '/system/tool/list',
    method: 'get',
    params: query
  })
}

// 查询工装夹具清单详细
export function getTool(id) {
  return request({
    url: '/system/tool/' + id,
    method: 'get'
  })
}

// 新增工装夹具清单
export function addTool(data) {
  return request({
    url: '/system/tool',
    method: 'post',
    data: data
  })
}

// 修改工装夹具清单
export function updateTool(data) {
  return request({
    url: '/system/tool',
    method: 'put',
    data: data
  })
}

// 删除工装夹具清单
export function delTool(id) {
  return request({
    url: '/system/tool/' + id,
    method: 'delete'
  })
}
