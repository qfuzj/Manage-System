package com.ms.system.service.impl;

import java.util.List;
import com.ms.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ms.system.mapper.ProductSopMapper;
import com.ms.system.domain.ProductSop;
import com.ms.system.service.IProductSopService;

/**
 * 产品SOPService业务层处理
 * 
 * @author ms
 * @date 2026-05-13
 */
@Service
public class ProductSopServiceImpl implements IProductSopService 
{
    @Autowired
    private ProductSopMapper productSopMapper;

    /**
     * 查询产品SOP
     * 
     * @param sopId 产品SOP主键
     * @return 产品SOP
     */
    @Override
    public ProductSop selectProductSopBySopId(Long sopId)
    {
        return productSopMapper.selectProductSopBySopId(sopId);
    }

    /**
     * 查询产品SOP列表
     * 
     * @param productSop 产品SOP
     * @return 产品SOP
     */
    @Override
    public List<ProductSop> selectProductSopList(ProductSop productSop)
    {
        return productSopMapper.selectProductSopList(productSop);
    }

    /**
     * 新增产品SOP
     * 
     * @param productSop 产品SOP
     * @return 结果
     */
    @Override
    public int insertProductSop(ProductSop productSop)
    {
        productSop.setCreateTime(DateUtils.getNowDate());
        return productSopMapper.insertProductSop(productSop);
    }

    /**
     * 修改产品SOP
     * 
     * @param productSop 产品SOP
     * @return 结果
     */
    @Override
    public int updateProductSop(ProductSop productSop)
    {
        productSop.setUpdateTime(DateUtils.getNowDate());
        return productSopMapper.updateProductSop(productSop);
    }

    /**
     * 批量删除产品SOP
     * 
     * @param sopIds 需要删除的产品SOP主键
     * @return 结果
     */
    @Override
    public int deleteProductSopBySopIds(Long[] sopIds)
    {
        return productSopMapper.deleteProductSopBySopIds(sopIds);
    }

    /**
     * 删除产品SOP信息
     * 
     * @param sopId 产品SOP主键
     * @return 结果
     */
    @Override
    public int deleteProductSopBySopId(Long sopId)
    {
        return productSopMapper.deleteProductSopBySopId(sopId);
    }
}
