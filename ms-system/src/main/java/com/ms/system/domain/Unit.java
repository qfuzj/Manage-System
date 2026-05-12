package com.ms.system.domain;

import java.math.BigDecimal;

import com.ms.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ms.common.annotation.Excel;

/**
 * 计量单位对象 unit
 *
 * @author ms
 * @date 2026-05-11
 */
public class Unit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 单位编号 */
    private Long id;

    /** 单位编码 */
    @Excel(name = "单位编码")
    private String code;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String name;

    /** 单位描述 */
    @Excel(name = "单位描述")
    private String description;

    /** 是否主单位(0:是,1:否) */
    @Excel(name = "是否主单位(0:是,1:否)")
    private Integer isMainUnit;

    /** 主单位编号 */
    @Excel(name = "主单位编号")
    private Long mainUnitId;

    /** 换算比例 */
    @Excel(name = "换算比例")
    private BigDecimal conversionRatio;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private Integer status;

    /** 主单位名称（用于显示，不映射数据库） */
    private String mainUnitName;

    /** 删除标记 */
    private Long deleteFlag;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setIsMainUnit(Integer isMainUnit)
    {
        this.isMainUnit = isMainUnit;
    }

    public Integer getIsMainUnit()
    {
        return isMainUnit;
    }
    public void setMainUnitId(Long mainUnitId)
    {
        this.mainUnitId = mainUnitId;
    }

    public Long getMainUnitId()
    {
        return mainUnitId;
    }
    public void setConversionRatio(BigDecimal conversionRatio)
    {
        this.conversionRatio = conversionRatio;
    }

    public BigDecimal getConversionRatio()
    {
        return conversionRatio;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }
    public void setDeleteFlag(Long deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }

    public Long getDeleteFlag()
    {
        return deleteFlag;
    }

    public String getMainUnitName()
    {
        return mainUnitName;
    }

    public void setMainUnitName(String mainUnitName)
    {
        this.mainUnitName = mainUnitName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("name", getName())
            .append("description", getDescription())
            .append("isMainUnit", getIsMainUnit())
            .append("mainUnitId", getMainUnitId())
            .append("conversionRatio", getConversionRatio())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("deleteFlag", getDeleteFlag())
            .toString();
    }
}
