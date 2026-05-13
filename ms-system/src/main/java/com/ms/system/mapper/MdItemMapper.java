package com.ms.system.mapper;

import java.util.List;
import com.ms.system.domain.MdItem;
import com.ms.system.domain.MdProductBom;
import com.ms.system.domain.ProductSop;
import com.ms.system.domain.ProductSip;

/**
 * 物料产品Mapper接口
 * 
 * @author ms
 * @date 2026-05-13
 */
public interface MdItemMapper 
{
    /**
     * 查询物料产品
     * 
     * @param itemId 物料产品主键
     * @return 物料产品
     */
    public MdItem selectMdItemByItemId(Long itemId);

    /**
     * 查询物料产品列表
     * 
     * @param mdItem 物料产品
     * @return 物料产品集合
     */
    public List<MdItem> selectMdItemList(MdItem mdItem);

    /**
     * 新增物料产品
     * 
     * @param mdItem 物料产品
     * @return 结果
     */
    public int insertMdItem(MdItem mdItem);

    /**
     * 修改物料产品
     * 
     * @param mdItem 物料产品
     * @return 结果
     */
    public int updateMdItem(MdItem mdItem);

    /**
     * 删除物料产品
     * 
     * @param itemId 物料产品主键
     * @return 结果
     */
    public int deleteMdItemByItemId(Long itemId);

    /**
     * 批量删除物料产品
     * 
     * @param itemIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMdItemByItemIds(Long[] itemIds);

    /**
     * 批量删除产品SOP
     * 
     * @param itemIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProductSopByItemIds(Long[] itemIds);
    
    /**
     * 批量新增产品SOP
     * 
     * @param productSopList 产品SOP列表
     * @return 结果
     */
    public int batchProductSop(List<ProductSop> productSopList);

    /**
     * 新增：通过物料产品ID查询产品SOP列表
     *
     * @param itemId 物料产品ID
     * @return 产品SOP列表
     */
    public List<ProductSop> selectProductSopByItemId(Long itemId);
    

    /**
     * 通过物料产品主键删除产品SOP信息
     * 
     * @param itemId 物料产品ID
     * @return 结果
     */
    public int deleteProductSopByItemId(Long itemId);

    /**
     * 新增：批量删除产品BOM
     *
     * @param itemIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMdItemBomByItemIds(Long[] itemIds);

    /**
     * 新增：批量新增产品BOM
     *
     * @param mdItemBomList 产品BOM列表
     * @return 结果
     */
    public int batchMdItemBom(List<MdProductBom> mdItemBomList);

    /**
     * 新增：通过物料产品主键删除产品BOM信息
     *
     * @param itemId 物料产品ID
     * @return 结果
     */
    public int deleteMdItemBomByItemId(Long itemId);

    /**
     * 新增：通过物料产品ID查询产品BOM列表
     *
     * @param itemId 物料产品ID
     * @return 产品BOM列表
     */
    public List<MdProductBom> selectMdItemBomByItemId(Long itemId);

    /**
     * 新增：批量删除产品SIP
     *
     * @param itemIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMdItemSipByItemIds(Long[] itemIds);

    /**
     * 新增：批量新增产品SIP
     *
     * @param mdItemSipList 产品SIP列表
     * @return 结果
     */
    public int batchMdItemSip(List<ProductSip> mdItemSipList);

    /**
     * 新增：通过物料产品主键删除产品SIP信息
     *
     * @param itemId 物料产品ID
     * @return 结果
     */
    public int deleteMdItemSipByItemId(Long itemId);

    /**
     * 新增：通过物料产品ID查询产品SIP列表
     *
     * @param itemId 物料产品ID
     * @return 产品SIP列表
     */
    public List<ProductSip> selectMdItemSipByItemId(Long itemId);
}
