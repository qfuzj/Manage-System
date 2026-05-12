package com.ms.system.domain;

import java.util.List;

import com.ms.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ms.common.annotation.Excel;

/**
 * 工作站对象 base_workstation
 *
 * @author ms
 * @date 2026-05-12
 */
public class Workstation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工位ID */
    private Long id;

    /** 工位编码 */
    @Excel(name = "工位编码")
    private String workstationCode;

    /** 工位名称 */
    @Excel(name = "工位名称")
    private String workstationName;

    /** 所属车间ID */
    @Excel(name = "所属车间ID")
    private Long workshopId;

    /** 所属工序ID */
    @Excel(name = "所属工序ID")
    private Long processId;

    /** 物理位置 */
    @Excel(name = "物理位置")
    private String location;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 逻辑删除 */
    private Integer isDeleted;

    /** 关联-工位岗位配置信息 */
    private List<RelStationPosition> relStationPositionList;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setWorkstationCode(String workstationCode)
    {
        this.workstationCode = workstationCode;
    }

    public String getWorkstationCode()
    {
        return workstationCode;
    }
    public void setWorkstationName(String workstationName)
    {
        this.workstationName = workstationName;
    }

    public String getWorkstationName()
    {
        return workstationName;
    }
    public void setWorkshopId(Long workshopId)
    {
        this.workshopId = workshopId;
    }

    public Long getWorkshopId()
    {
        return workshopId;
    }
    public void setProcessId(Long processId)
    {
        this.processId = processId;
    }

    public Long getProcessId()
    {
        return processId;
    }
    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getLocation()
    {
        return location;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }
    public void setIsDeleted(Integer isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted()
    {
        return isDeleted;
    }

    public List<RelStationPosition> getRelStationPositionList()
    {
        return relStationPositionList;
    }

    public void setRelStationPositionList(List<RelStationPosition> relStationPositionList)
    {
        this.relStationPositionList = relStationPositionList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("workstationCode", getWorkstationCode())
            .append("workstationName", getWorkstationName())
            .append("workshopId", getWorkshopId())
            .append("processId", getProcessId())
            .append("location", getLocation())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("isDeleted", getIsDeleted())
            .append("relStationPositionList", getRelStationPositionList())
            .toString();
    }
}
