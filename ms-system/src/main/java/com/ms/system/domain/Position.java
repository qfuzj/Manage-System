package com.ms.system.domain;

import com.ms.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ms.common.annotation.Excel;

/**
 * 岗位对象 base_position
 *
 * @author ms
 * @date 2026-05-12
 */
public class Position extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 岗位ID */
    private Long id;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    private String positionName;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setPositionName(String positionName)
    {
        this.positionName = positionName;
    }

    public String getPositionName()
    {
        return positionName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("positionName", getPositionName())
            .append("remark", getRemark())
            .toString();
    }
}
