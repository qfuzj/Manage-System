package com.ms.system.mapper;

import java.util.List;
import com.ms.system.domain.Unit;

/**
 * 计量单位Mapper接口
 * 
 * @author ms
 * @date 2026-05-11
 */
public interface UnitMapper 
{
    /**
     * 查询计量单位
     * 
     * @param id 计量单位主键
     * @return 计量单位
     */
    public Unit selectUnitById(Long id);

    /**
     * 查询计量单位列表
     * 
     * @param unit 计量单位
     * @return 计量单位集合
     */
    public List<Unit> selectUnitList(Unit unit);

    /**
     * 新增计量单位
     * 
     * @param unit 计量单位
     * @return 结果
     */
    public int insertUnit(Unit unit);

    /**
     * 修改计量单位
     * 
     * @param unit 计量单位
     * @return 结果
     */
    public int updateUnit(Unit unit);

    /**
     * 删除计量单位
     * 
     * @param id 计量单位主键
     * @return 结果
     */
    public int deleteUnitById(Long id);

    /**
     * 批量删除计量单位
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUnitByIds(Long[] ids);

    /**
     * 查询主单位列表
     *
     * @return 主单位列表
     */
    public List<Unit> selectMainUnitList();
}
