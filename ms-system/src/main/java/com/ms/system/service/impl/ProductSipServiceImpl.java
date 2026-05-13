package com.ms.system.service.impl;

import java.util.List;
import com.ms.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ms.system.mapper.ProductSipMapper;
import com.ms.system.domain.ProductSip;
import com.ms.system.service.IProductSipService;

/**
 * 产品SIPService业务层处理
 * 
 * @author ms
 * @date 2026-05-13
 */
@Service
public class ProductSipServiceImpl implements IProductSipService 
{
    @Autowired
    private ProductSipMapper productSipMapper;

    /**
     * 查询产品SIP
     * 
     * @param sipId 产品SIP主键
     * @return 产品SIP
     */
    @Override
    public ProductSip selectProductSipBySipId(Long sipId)
    {
        return productSipMapper.selectProductSipBySipId(sipId);
    }

    /**
     * 查询产品SIP列表
     * 
     * @param productSip 产品SIP
     * @return 产品SIP
     */
    @Override
    public List<ProductSip> selectProductSipList(ProductSip productSip)
    {
        return productSipMapper.selectProductSipList(productSip);
    }

    /**
     * 新增产品SIP
     * 
     * @param productSip 产品SIP
     * @return 结果
     */
    @Override
    public int insertProductSip(ProductSip productSip)
    {
        productSip.setCreateTime(DateUtils.getNowDate());
        return productSipMapper.insertProductSip(productSip);
    }

    /**
     * 修改产品SIP
     * 
     * @param productSip 产品SIP
     * @return 结果
     */
    @Override
    public int updateProductSip(ProductSip productSip)
    {
        productSip.setUpdateTime(DateUtils.getNowDate());
        return productSipMapper.updateProductSip(productSip);
    }

    /**
     * 批量删除产品SIP
     * 
     * @param sipIds 需要删除的产品SIP主键
     * @return 结果
     */
    @Override
    public int deleteProductSipBySipIds(Long[] sipIds)
    {
        return productSipMapper.deleteProductSipBySipIds(sipIds);
    }

    /**
     * 删除产品SIP信息
     * 
     * @param sipId 产品SIP主键
     * @return 结果
     */
    @Override
    public int deleteProductSipBySipId(Long sipId)
    {
        return productSipMapper.deleteProductSipBySipId(sipId);
    }
}
