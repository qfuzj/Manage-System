import request from '@/utils/request'

// 查询物料产品列表
export function listMditem(query) {
  return request({
    url: '/mes:md/mditem/list',
    method: 'get',
    params: query
  })
}

// 查询物料产品详细
export function getMditem(itemId) {
  return request({
    url: '/mes:md/mditem/' + itemId,
    method: 'get'
  })
}

// 新增物料产品
export function addMditem(data) {
  return request({
    url: '/mes:md/mditem',
    method: 'post',
    data: data
  })
}

// 修改物料产品
export function updateMditem(data) {
  return request({
    url: '/mes:md/mditem',
    method: 'put',
    data: data
  })
}

// 删除物料产品
export function delMditem(itemId) {
  return request({
    url: '/mes:md/mditem/' + itemId,
    method: 'delete'
  })
}
