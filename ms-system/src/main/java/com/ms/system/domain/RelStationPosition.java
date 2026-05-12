package com.ms.system.domain;

import com.ms.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ms.common.annotation.Excel;

/**
 * 关联-工位岗位配置对象 rel_station_position
 *
 * @author ms
 * @date 2026-05-12
 */
public class RelStationPosition extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 关联ID */
    private Long id;

    /** 工位ID */
    @Excel(name = "工位ID")
    private Long workstationId;

    /** 岗位ID */
    @Excel(name = "岗位ID")
    private Long positionId;

    /** 标准定员人数 */
    @Excel(name = "标准定员人数")
    private Long standardCount;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setWorkstationId(Long workstationId)
    {
        this.workstationId = workstationId;
    }

    public Long getWorkstationId()
    {
        return workstationId;
    }
    public void setPositionId(Long positionId)
    {
        this.positionId = positionId;
    }

    public Long getPositionId()
    {
        return positionId;
    }
    public void setStandardCount(Long standardCount)
    {
        this.standardCount = standardCount;
    }

    public Long getStandardCount()
    {
        return standardCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("workstationId", getWorkstationId())
            .append("positionId", getPositionId())
            .append("standardCount", getStandardCount())
            .toString();
    }
}
