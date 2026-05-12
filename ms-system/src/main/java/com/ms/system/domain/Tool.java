package com.ms.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ms.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ms.common.annotation.Excel;

/**
 * 工装夹具清单对象 base_tool
 *
 * @author ms
 * @date 2026-05-12
 */
public class Tool extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工装ID */
    private Long id;

    /** 工装唯一编码 */
    @Excel(name = "工装唯一编码")
    private String toolCode;

    /** 工装名称 */
    @Excel(name = "工装名称")
    private String toolName;

    /** 所属类型ID */
    @Excel(name = "所属类型ID")
    private Long toolTypeId;

    /** 品牌 */
    @Excel(name = "品牌")
    private String brand;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String spec;

    /** 总量 */
    @Excel(name = "总量")
    private Long qty;

    /** 可用数量 */
    @Excel(name = "可用数量")
    private Long qtyAvail;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 下次保养日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "下次保养日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date nextMaintenDate;

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
    public void setToolCode(String toolCode)
    {
        this.toolCode = toolCode;
    }

    public String getToolCode()
    {
        return toolCode;
    }
    public void setToolName(String toolName)
    {
        this.toolName = toolName;
    }

    public String getToolName()
    {
        return toolName;
    }
    public void setToolTypeId(Long toolTypeId)
    {
        this.toolTypeId = toolTypeId;
    }

    public Long getToolTypeId()
    {
        return toolTypeId;
    }
    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    public String getBrand()
    {
        return brand;
    }
    public void setSpec(String spec)
    {
        this.spec = spec;
    }

    public String getSpec()
    {
        return spec;
    }
    public void setQty(Long qty)
    {
        this.qty = qty;
    }

    public Long getQty()
    {
        return qty;
    }
    public void setQtyAvail(Long qtyAvail)
    {
        this.qtyAvail = qtyAvail;
    }

    public Long getQtyAvail()
    {
        return qtyAvail;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setNextMaintenDate(Date nextMaintenDate)
    {
        this.nextMaintenDate = nextMaintenDate;
    }

    public Date getNextMaintenDate()
    {
        return nextMaintenDate;
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
            .append("toolCode", getToolCode())
            .append("toolName", getToolName())
            .append("toolTypeId", getToolTypeId())
            .append("brand", getBrand())
            .append("spec", getSpec())
            .append("qty", getQty())
            .append("qtyAvail", getQtyAvail())
            .append("status", getStatus())
            .append("nextMaintenDate", getNextMaintenDate())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
