package com.ms.system.mapper;

import java.util.List;
import com.ms.system.domain.ProductSip;

/**
 * 产品SIPMapper接口
 * 
 * @author ms
 * @date 2026-05-13
 */
public interface ProductSipMapper 
{
    /**
     * 查询产品SIP
     * 
     * @param sipId 产品SIP主键
     * @return 产品SIP
     */
    public ProductSip selectProductSipBySipId(Long sipId);

    /**
     * 查询产品SIP列表
     * 
     * @param productSip 产品SIP
     * @return 产品SIP集合
     */
    public List<ProductSip> selectProductSipList(ProductSip productSip);

    /**
     * 新增产品SIP
     * 
     * @param productSip 产品SIP
     * @return 结果
     */
    public int insertProductSip(ProductSip productSip);

    /**
     * 修改产品SIP
     * 
     * @param productSip 产品SIP
     * @return 结果
     */
    public int updateProductSip(ProductSip productSip);

    /**
     * 删除产品SIP
     * 
     * @param sipId 产品SIP主键
     * @return 结果
     */
    public int deleteProductSipBySipId(Long sipId);

    /**
     * 批量删除产品SIP
     * 
     * @param sipIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProductSipBySipIds(Long[] sipIds);
}
