package com.ms.system.mapper;

import java.util.List;
import com.ms.system.domain.Workstation;
import com.ms.system.domain.RelStationPosition;

/**
 * 工作站Mapper接口
 * 
 * @author ms
 * @date 2026-05-12
 */
public interface WorkstationMapper 
{
    /**
     * 查询工作站
     * 
     * @param id 工作站主键
     * @return 工作站
     */
    public Workstation selectWorkstationById(Long id);

    /**
     * 查询工作站列表
     * 
     * @param workstation 工作站
     * @return 工作站集合
     */
    public List<Workstation> selectWorkstationList(Workstation workstation);

    /**
     * 新增工作站
     * 
     * @param workstation 工作站
     * @return 结果
     */
    public int insertWorkstation(Workstation workstation);

    /**
     * 修改工作站
     * 
     * @param workstation 工作站
     * @return 结果
     */
    public int updateWorkstation(Workstation workstation);

    /**
     * 删除工作站
     * 
     * @param id 工作站主键
     * @return 结果
     */
    public int deleteWorkstationById(Long id);

    /**
     * 批量删除工作站
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWorkstationByIds(Long[] ids);

    /**
     * 批量删除关联-工位岗位配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRelStationPositionByWorkstationIds(Long[] ids);
    
    /**
     * 批量新增关联-工位岗位配置
     * 
     * @param relStationPositionList 关联-工位岗位配置列表
     * @return 结果
     */
    public int batchRelStationPosition(List<RelStationPosition> relStationPositionList);
    

    /**
     * 通过工作站主键删除关联-工位岗位配置信息
     * 
     * @param id 工作站ID
     * @return 结果
     */
    public int deleteRelStationPositionByWorkstationId(Long id);
}
