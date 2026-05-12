package com.ms.system.mapper;

import java.util.List;
import com.ms.system.domain.Workshop;

/**
 * 车间Mapper接口
 * 
 * @author ms
 * @date 2026-05-12
 */
public interface WorkshopMapper 
{
    /**
     * 查询车间
     * 
     * @param id 车间主键
     * @return 车间
     */
    public Workshop selectWorkshopById(Long id);

    /**
     * 查询车间列表
     * 
     * @param workshop 车间
     * @return 车间集合
     */
    public List<Workshop> selectWorkshopList(Workshop workshop);

    /**
     * 新增车间
     * 
     * @param workshop 车间
     * @return 结果
     */
    public int insertWorkshop(Workshop workshop);

    /**
     * 修改车间
     * 
     * @param workshop 车间
     * @return 结果
     */
    public int updateWorkshop(Workshop workshop);

    /**
     * 删除车间
     * 
     * @param id 车间主键
     * @return 结果
     */
    public int deleteWorkshopById(Long id);

    /**
     * 批量删除车间
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWorkshopByIds(Long[] ids);
}
