package com.ms.system.service.impl;

import java.util.List;
import com.ms.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ms.system.domain.MdProductBom;
import com.ms.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ms.system.domain.ProductSip;
import com.ms.system.domain.ProductSop;
import com.ms.system.mapper.MdItemMapper;
import com.ms.system.domain.MdItem;
import com.ms.system.service.IMdItemService;

/**
 * 物料产品Service业务层处理
 * 
 * @author ms
 * @date 2026-05-13
 */
@Service
public class MdItemServiceImpl implements IMdItemService 
{
    @Autowired
    private MdItemMapper mdItemMapper;

    /**
     * 查询物料产品
     * 
     * @param itemId 物料产品主键
     * @return 物料产品
     */
    @Override
    public MdItem selectMdItemByItemId(Long itemId)
    {
        return mdItemMapper.selectMdItemByItemId(itemId);
    }

    /**
     * 查询物料产品列表
     * 
     * @param mdItem 物料产品
     * @return 物料产品
     */
    @Override
    public List<MdItem> selectMdItemList(MdItem mdItem)
    {
        return mdItemMapper.selectMdItemList(mdItem);
    }

    /**
     * 新增物料产品
     * 
     * @param mdItem 物料产品
     * @return 结果
     */
    @Transactional
    @Override
    public int insertMdItem(MdItem mdItem)
    {
        mdItem.setCreateTime(DateUtils.getNowDate());
        int rows = mdItemMapper.insertMdItem(mdItem);
        insertProductSop(mdItem);
        insertMdItemBom(mdItem);
        insertMdItemSip(mdItem);
        return rows;
    }

    /**
     * 修改物料产品
     * 
     * @param mdItem 物料产品
     * @return 结果
     */
    @Transactional
    @Override
    public int updateMdItem(MdItem mdItem)
    {
        mdItem.setUpdateTime(DateUtils.getNowDate());
        mdItemMapper.deleteProductSopByItemId(mdItem.getItemId());
        mdItemMapper.deleteMdItemBomByItemId(mdItem.getItemId());
        mdItemMapper.deleteMdItemSipByItemId(mdItem.getItemId());
        insertProductSop(mdItem);
        insertMdItemBom(mdItem);
        insertMdItemSip(mdItem);
        return mdItemMapper.updateMdItem(mdItem);
    }

    /**
     * 批量删除物料产品
     * 
     * @param itemIds 需要删除的物料产品主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMdItemByItemIds(Long[] itemIds)
    {
        mdItemMapper.deleteProductSopByItemIds(itemIds);
        mdItemMapper.deleteMdItemBomByItemIds(itemIds);
        mdItemMapper.deleteMdItemSipByItemIds(itemIds);
        return mdItemMapper.deleteMdItemByItemIds(itemIds);
    }

    /**
     * 删除物料产品信息
     * 
     * @param itemId 物料产品主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMdItemByItemId(Long itemId)
    {
        mdItemMapper.deleteProductSopByItemId(itemId);
        mdItemMapper.deleteMdItemBomByItemId(itemId);
        mdItemMapper.deleteMdItemSipByItemId(itemId);
        return mdItemMapper.deleteMdItemByItemId(itemId);
    }

    /**
     * 新增产品SOP信息
     * 
     * @param mdItem 物料产品对象
     */
    public void insertProductSop(MdItem mdItem)
    {
        List<ProductSop> productSopList = mdItem.getProductSopList();
        Long itemId = mdItem.getItemId();
        if (StringUtils.isNotNull(productSopList))
        {
            List<ProductSop> list = new ArrayList<ProductSop>();
            for (ProductSop productSop : productSopList)
            {
                productSop.setItemId(itemId);
                list.add(productSop);
            }
            if (list.size() > 0)
            {
                mdItemMapper.batchProductSop(list);
            }
        }
    }

    /**
     * 新增：新增产品BOM信息
     *
     * @param mdItem 物料产品对象
     */
    public void insertMdItemBom(MdItem mdItem)
    {
        List<MdProductBom> mdItemBomList = mdItem.getMdItemBomList();
        Long itemId = mdItem.getItemId();
        if (StringUtils.isNotNull(mdItemBomList))
        {
            List<MdProductBom> list = new ArrayList<MdProductBom>();
            for (MdProductBom mdItemBom : mdItemBomList)
            {
                mdItemBom.setItemId(itemId);
                list.add(mdItemBom);
            }
            if (list.size() > 0)
            {
                mdItemMapper.batchMdItemBom(list);
            }
        }
    }

    /**
     * 新增：新增产品SIP信息
     *
     * @param mdItem 物料产品对象
     */
    public void insertMdItemSip(MdItem mdItem)
    {
        List<ProductSip> mdItemSipList = mdItem.getMdItemSipList();
        Long itemId = mdItem.getItemId();
        if (StringUtils.isNotNull(mdItemSipList))
        {
            List<ProductSip> list = new ArrayList<ProductSip>();
            for (ProductSip mdItemSip : mdItemSipList)
            {
                mdItemSip.setItemId(itemId);
                list.add(mdItemSip);
            }
            if (list.size() > 0)
            {
                mdItemMapper.batchMdItemSip(list);
            }
        }
    }
}
