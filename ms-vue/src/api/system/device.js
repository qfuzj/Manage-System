import request from '@/utils/request'

// 查询设备台账列表
export function listDevice(query) {
  return request({
    url: '/system/device/list',
    method: 'get',
    params: query
  })
}

// 查询设备台账详细
export function getDevice(id) {
  return request({
    url: '/system/device/' + id,
    method: 'get'
  })
}

// 新增设备台账
export function addDevice(data) {
  return request({
    url: '/system/device',
    method: 'post',
    data: data
  })
}

// 修改设备台账
export function updateDevice(data) {
  return request({
    url: '/system/device',
    method: 'put',
    data: data
  })
}

// 删除设备台账
export function delDevice(id) {
  return request({
    url: '/system/device/' + id,
    method: 'delete'
  })
}
