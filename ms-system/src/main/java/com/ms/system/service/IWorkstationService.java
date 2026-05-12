package com.ms.system.service;

import java.util.List;
import com.ms.system.domain.Workstation;

/**
 * 工作站Service接口
 * 
 * @author ms
 * @date 2026-05-12
 */
public interface IWorkstationService 
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
     * 批量删除工作站
     * 
     * @param ids 需要删除的工作站主键集合
     * @return 结果
     */
    public int deleteWorkstationByIds(Long[] ids);

    /**
     * 删除工作站信息
     * 
     * @param id 工作站主键
     * @return 结果
     */
    public int deleteWorkstationById(Long id);
}
