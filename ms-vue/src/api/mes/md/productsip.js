import request from '@/utils/request'

// 查询产品SIP列表
export function listProductsip(query) {
    return request({
        url: '/mes:md/productsip/list',
        method: 'get',
        params: query
    })
}

// 查询产品SIP详细
export function getProductsip(sipId) {
    return request({
        url: '/mes:md/productsip/' + sipId,
        method: 'get'
    })
}

// 新增产品SIP
export function addProductsip(data) {
    return request({
        url: '/mes:md/productsip',
        method: 'post',
        data: data
    })
}

// 修改产品SIP
export function updateProductsip(data) {
    return request({
        url: '/mes:md/productsip',
        method: 'put',
        data: data
    })
}

// 删除产品SIP
export function delProductsip(sipId) {
    return request({
        url: '/mes:md/productsip/' + sipId,
        method: 'delete'
    })
}
