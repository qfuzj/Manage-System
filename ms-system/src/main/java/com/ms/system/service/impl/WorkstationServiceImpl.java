package com.ms.system.service.impl;

import java.util.List;
import com.ms.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ms.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ms.system.domain.RelStationPosition;
import com.ms.system.mapper.WorkstationMapper;
import com.ms.system.domain.Workstation;
import com.ms.system.service.IWorkstationService;

/**
 * 工作站Service业务层处理
 * 
 * @author ms
 * @date 2026-05-12
 */
@Service
public class WorkstationServiceImpl implements IWorkstationService 
{
    @Autowired
    private WorkstationMapper workstationMapper;

    /**
     * 查询工作站
     * 
     * @param id 工作站主键
     * @return 工作站
     */
    @Override
    public Workstation selectWorkstationById(Long id)
    {
        return workstationMapper.selectWorkstationById(id);
    }

    /**
     * 查询工作站列表
     * 
     * @param workstation 工作站
     * @return 工作站
     */
    @Override
    public List<Workstation> selectWorkstationList(Workstation workstation)
    {
        return workstationMapper.selectWorkstationList(workstation);
    }

    /**
     * 新增工作站
     * 
     * @param workstation 工作站
     * @return 结果
     */
    @Transactional
    @Override
    public int insertWorkstation(Workstation workstation)
    {
        workstation.setCreateTime(DateUtils.getNowDate());
        int rows = workstationMapper.insertWorkstation(workstation);
        insertRelStationPosition(workstation);
        return rows;
    }

    /**
     * 修改工作站
     * 
     * @param workstation 工作站
     * @return 结果
     */
    @Transactional
    @Override
    public int updateWorkstation(Workstation workstation)
    {
        workstation.setUpdateTime(DateUtils.getNowDate());
        workstationMapper.deleteRelStationPositionByWorkstationId(workstation.getId());
        insertRelStationPosition(workstation);
        return workstationMapper.updateWorkstation(workstation);
    }

    /**
     * 批量删除工作站
     * 
     * @param ids 需要删除的工作站主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWorkstationByIds(Long[] ids)
    {
        workstationMapper.deleteRelStationPositionByWorkstationIds(ids);
        return workstationMapper.deleteWorkstationByIds(ids);
    }

    /**
     * 删除工作站信息
     * 
     * @param id 工作站主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWorkstationById(Long id)
    {
        workstationMapper.deleteRelStationPositionByWorkstationId(id);
        return workstationMapper.deleteWorkstationById(id);
    }

    /**
     * 新增关联-工位岗位配置信息
     * 
     * @param workstation 工作站对象
     */
    public void insertRelStationPosition(Workstation workstation)
    {
        List<RelStationPosition> relStationPositionList = workstation.getRelStationPositionList();
        Long id = workstation.getId();
        if (StringUtils.isNotNull(relStationPositionList))
        {
            List<RelStationPosition> list = new ArrayList<RelStationPosition>();
            for (RelStationPosition relStationPosition : relStationPositionList)
            {
                relStationPosition.setWorkstationId(id);
                list.add(relStationPosition);
            }
            if (list.size() > 0)
            {
                workstationMapper.batchRelStationPosition(list);
            }
        }
    }
}
