package com.ms.system.service;

import java.util.List;
import com.ms.system.domain.MdItem;

/**
 * 物料产品Service接口
 * 
 * @author ms
 * @date 2026-05-13
 */
public interface IMdItemService 
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
     * 批量删除物料产品
     * 
     * @param itemIds 需要删除的物料产品主键集合
     * @return 结果
     */
    public int deleteMdItemByItemIds(Long[] itemIds);

    /**
     * 删除物料产品信息
     * 
     * @param itemId 物料产品主键
     * @return 结果
     */
    public int deleteMdItemByItemId(Long itemId);
}
