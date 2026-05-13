import request from '@/utils/request'

// 查询物料产品分类列表
export function listItemtype(query) {
  return request({
    url: '/mes:md/itemtype/list',
    method: 'get',
    params: query
  })
}

// 查询物料产品分类详细
export function getItemtype(itemTypeId) {
  return request({
    url: '/mes:md/itemtype/' + itemTypeId,
    method: 'get'
  })
}

// 新增物料产品分类
export function addItemtype(data) {
  return request({
    url: '/mes:md/itemtype',
    method: 'post',
    data: data
  })
}

// 修改物料产品分类
export function updateItemtype(data) {
  return request({
    url: '/mes:md/itemtype',
    method: 'put',
    data: data
  })
}

// 删除物料产品分类
export function delItemtype(itemTypeId) {
  return request({
    url: '/mes:md/itemtype/' + itemTypeId,
    method: 'delete'
  })
}
