package com.ms.system.service.impl;

import java.util.List;
import com.ms.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ms.system.mapper.UnitMapper;
import com.ms.system.domain.Unit;
import com.ms.system.service.IUnitService;

/**
 * 计量单位Service业务层处理
 * 
 * @author ms
 * @date 2026-05-11
 */
@Service
public class UnitServiceImpl implements IUnitService 
{
    @Autowired
    private UnitMapper unitMapper;

    /**
     * 查询计量单位
     * 
     * @param id 计量单位主键
     * @return 计量单位
     */
    @Override
    public Unit selectUnitById(Long id)
    {
        return unitMapper.selectUnitById(id);
    }

    /**
     * 查询计量单位列表
     * 
     * @param unit 计量单位
     * @return 计量单位
     */
    @Override
    public List<Unit> selectUnitList(Unit unit)
    {
        return unitMapper.selectUnitList(unit);
    }

    /**
     * 新增计量单位
     * 
     * @param unit 计量单位
     * @return 结果
     */
    @Override
    public int insertUnit(Unit unit)
    {
        unit.setCreateTime(DateUtils.getNowDate());
        return unitMapper.insertUnit(unit);
    }

    /**
     * 修改计量单位
     * 
     * @param unit 计量单位
     * @return 结果
     */
    @Override
    public int updateUnit(Unit unit)
    {
        unit.setUpdateTime(DateUtils.getNowDate());
        return unitMapper.updateUnit(unit);
    }

    /**
     * 批量删除计量单位
     * 
     * @param ids 需要删除的计量单位主键
     * @return 结果
     */
    @Override
    public int deleteUnitByIds(Long[] ids)
    {
        return unitMapper.deleteUnitByIds(ids);
    }

    /**
     * 删除计量单位信息
     *
     * @param id 计量单位主键
     * @return 结果
     */
    @Override
    public int deleteUnitById(Long id)
    {
        return unitMapper.deleteUnitById(id);
    }

    /**
     * 查询主单位列表
     *
     * @return 主单位列表
     */
    @Override
    public List<Unit> selectMainUnitList()
    {
        return unitMapper.selectMainUnitList();
    }
}
