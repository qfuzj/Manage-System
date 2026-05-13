import request from '@/utils/request'

// 查询产品BOM关系列表
export function listMdproductbom(query) {
    return request({
        url: '/mes:md/mdproductbom/list',
        method: 'get',
        params: query
    })
}

// 查询产品BOM关系详细
export function getMdproductbom(bomId) {
    return request({
        url: '/mes:md/mdproductbom/' + bomId,
        method: 'get'
    })
}

// 新增产品BOM关系
export function addMdproductbom(data) {
    return request({
        url: '/mes:md/mdproductbom',
        method: 'post',
        data: data
    })
}

// 修改产品BOM关系
export function updateMdproductbom(data) {
    return request({
        url: '/mes:md/mdproductbom',
        method: 'put',
        data: data
    })
}

// 删除产品BOM关系
export function delMdproductbom(bomId) {
    return request({
        url: '/mes:md/mdproductbom/' + bomId,
        method: 'delete'
    })
}
