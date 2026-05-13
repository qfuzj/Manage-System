package com.ms.system.service.impl;

import java.util.List;
import com.ms.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ms.system.mapper.WorkshopMapper;
import com.ms.system.domain.Workshop;
import com.ms.system.service.IWorkshopService;

/**
 * 车间Service业务层处理
 *
 * @author ms
 * @date 2026-05-12
 */
@Service
public class WorkshopServiceImpl implements IWorkshopService
{
    @Autowired
    private WorkshopMapper workshopMapper;

    /**
     * 查询车间
     *
     * @param id 车间主键
     * @return 车间
     */
    @Override
    public Workshop selectWorkshopById(Long id)
    {
        return workshopMapper.selectWorkshopById(id);
    }

    /**
     * 查询车间列表
     *
     * @param workshop 车间
     * @return 车间
     */
    @Override
    public List<Workshop> selectWorkshopList(Workshop workshop)
    {
        return workshopMapper.selectWorkshopList(workshop);
    }

    /**
     * 新增车间
     *
     * @param workshop 车间
     * @return 结果
     */
    @Override
    public int insertWorkshop(Workshop workshop)
    {
        workshop.setCreateTime(DateUtils.getNowDate());
        return workshopMapper.insertWorkshop(workshop);
    }

    /**
     * 修改车间
     *
     * @param workshop 车间
     * @return 结果
     */
    @Override
    public int updateWorkshop(Workshop workshop)
    {
        workshop.setUpdateTime(DateUtils.getNowDate());
        return workshopMapper.updateWorkshop(workshop);
    }

    /**
     * 批量删除车间
     *
     * @param ids 需要删除的车间主键
     * @return 结果
     */
    @Override
    public int deleteWorkshopByIds(Long[] ids)
    {
        return workshopMapper.deleteWorkshopByIds(ids);
    }

    /**
     * 删除车间信息
     *
     * @param id 车间主键
     * @return 结果
     */
    @Override
    public int deleteWorkshopById(Long id)
    {
        return workshopMapper.deleteWorkshopById(id);
    }

    /**
     * 查询车间名称列表
     *
     * @return 车间名称列表
     */
    @Override
    public List<Workshop> selectWorkshopNameList() {
        return workshopMapper.selectWorkshopNameList();
    }

}
