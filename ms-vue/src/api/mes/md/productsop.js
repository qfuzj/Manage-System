import request from '@/utils/request'

// 查询产品SOP列表
export function listProductsop(query) {
    return request({
        url: '/mes:md/productsop/list',
        method: 'get',
        params: query
    })
}

// 查询产品SOP详细
export function getProductsop(sopId) {
    return request({
        url: '/mes:md/productsop/' + sopId,
        method: 'get'
    })
}

// 新增产品SOP
export function addProductsop(data) {
    return request({
        url: '/mes:md/productsop',
        method: 'post',
        data: data
    })
}

// 修改产品SOP
export function updateProductsop(data) {
    return request({
        url: '/mes:md/productsop',
        method: 'put',
        data: data
    })
}

// 删除产品SOP
export function delProductsop(sopId) {
    return request({
        url: '/mes:md/productsop/' + sopId,
        method: 'delete'
    })
}
