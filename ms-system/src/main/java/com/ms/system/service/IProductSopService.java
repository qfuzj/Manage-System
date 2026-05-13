package com.ms.system.service;

import java.util.List;
import com.ms.system.domain.ProductSop;

/**
 * 产品SOPService接口
 * 
 * @author ms
 * @date 2026-05-13
 */
public interface IProductSopService 
{
    /**
     * 查询产品SOP
     * 
     * @param sopId 产品SOP主键
     * @return 产品SOP
     */
    public ProductSop selectProductSopBySopId(Long sopId);

    /**
     * 查询产品SOP列表
     * 
     * @param productSop 产品SOP
     * @return 产品SOP集合
     */
    public List<ProductSop> selectProductSopList(ProductSop productSop);

    /**
     * 新增产品SOP
     * 
     * @param productSop 产品SOP
     * @return 结果
     */
    public int insertProductSop(ProductSop productSop);

    /**
     * 修改产品SOP
     * 
     * @param productSop 产品SOP
     * @return 结果
     */
    public int updateProductSop(ProductSop productSop);

    /**
     * 批量删除产品SOP
     * 
     * @param sopIds 需要删除的产品SOP主键集合
     * @return 结果
     */
    public int deleteProductSopBySopIds(Long[] sopIds);

    /**
     * 删除产品SOP信息
     * 
     * @param sopId 产品SOP主键
     * @return 结果
     */
    public int deleteProductSopBySopId(Long sopId);
}
