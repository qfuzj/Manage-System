package com.ms.system.domain;

import java.math.BigDecimal;

import com.ms.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ms.common.annotation.Excel;

/**
 * 车间对象 base_workshop
 *
 * @author ms
 * @date 2026-05-12
 */
public class Workshop extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 车间ID */
    private Long id;

    /** 车间编码 */
    @Excel(name = "车间编码")
    private String workshopCode;

    /** 车间名称 */
    @Excel(name = "车间名称")
    private String workshopName;

    /** 车间面积(㎡) */
    @Excel(name = "车间面积(㎡)")
    private BigDecimal area;

    /** 负责人(关联员工ID) */
    @Excel(name = "负责人(关联员工ID)")
    private Long managerId;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 逻辑删除 */
    private Integer isDeleted;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setWorkshopCode(String workshopCode)
    {
        this.workshopCode = workshopCode;
    }

    public String getWorkshopCode()
    {
        return workshopCode;
    }
    public void setWorkshopName(String workshopName)
    {
        this.workshopName = workshopName;
    }

    public String getWorkshopName()
    {
        return workshopName;
    }
    public void setArea(BigDecimal area)
    {
        this.area = area;
    }

    public BigDecimal getArea()
    {
        return area;
    }
    public void setManagerId(Long managerId)
    {
        this.managerId = managerId;
    }

    public Long getManagerId()
    {
        return managerId;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("workshopCode", getWorkshopCode())
            .append("workshopName", getWorkshopName())
            .append("area", getArea())
            .append("managerId", getManagerId())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
