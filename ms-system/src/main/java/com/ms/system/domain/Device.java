package com.ms.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ms.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ms.common.annotation.Excel;

/**
 * 设备台账对象 base_device
 *
 * @author ms
 * @date 2026-05-12
 */
public class Device extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备ID */
    private Long id;

    /** 设备编码(固定资产号) */
    @Excel(name = "设备编码(固定资产号)")
    private String deviceCode;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 设备类型ID(关联base_device_type) */
    @Excel(name = "设备类型ID(关联base_device_type)")
    private Long typeId;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String spec;

    /** 品牌 */
    @Excel(name = "品牌")
    private String brand;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 购置日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "购置日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date buyDate;

    /** $column.columnComment */
    private Integer isDeleted;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setDeviceCode(String deviceCode)
    {
        this.deviceCode = deviceCode;
    }

    public String getDeviceCode()
    {
        return deviceCode;
    }
    public void setDeviceName(String deviceName)
    {
        this.deviceName = deviceName;
    }

    public String getDeviceName()
    {
        return deviceName;
    }
    public void setTypeId(Long typeId)
    {
        this.typeId = typeId;
    }

    public Long getTypeId()
    {
        return typeId;
    }
    public void setSpec(String spec)
    {
        this.spec = spec;
    }

    public String getSpec()
    {
        return spec;
    }
    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    public String getBrand()
    {
        return brand;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setBuyDate(Date buyDate)
    {
        this.buyDate = buyDate;
    }

    public Date getBuyDate()
    {
        return buyDate;
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
            .append("deviceCode", getDeviceCode())
            .append("deviceName", getDeviceName())
            .append("typeId", getTypeId())
            .append("spec", getSpec())
            .append("brand", getBrand())
            .append("status", getStatus())
            .append("buyDate", getBuyDate())
            .append("isDeleted", getIsDeleted())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
