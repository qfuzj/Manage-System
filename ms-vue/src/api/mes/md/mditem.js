import request from '@/utils/request'

// 查询产品SOP列表
export function listMditem(query) {
  return request({
    url: '/mes:md/mditem/list',
    method: 'get',
    params: query
  })
}

// 查询产品SOP详细
export function getMditem(sopId) {
  return request({
    url: '/mes:md/mditem/' + sopId,
    method: 'get'
  })
}

// 新增产品SOP
export function addMditem(data) {
  return request({
    url: '/mes:md/mditem',
    method: 'post',
    data: data
  })
}

// 修改产品SOP
export function updateMditem(data) {
  return request({
    url: '/mes:md/mditem',
    method: 'put',
    data: data
  })
}

// 删除产品SOP
export function delMditem(sopId) {
  return request({
    url: '/mes:md/mditem/' + sopId,
    method: 'delete'
  })
}
