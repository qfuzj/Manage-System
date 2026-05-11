package com.ms.system.domain;

import com.ms.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ms.common.annotation.Excel;

/**
 * 客户对象 customer
 *
 * @author ms
 * @date 2026-05-11
 */
public class Customer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 客户编号 */
    private Long id;

    /** 客户编码 */
    @Excel(name = "客户编码")
    private String code;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String name;

    /** 客户地址 */
    @Excel(name = "客户地址")
    private String address;

    /** 客户联系人 */
    @Excel(name = "客户联系人")
    private String contactPerson;

    /** 客户电话 */
    @Excel(name = "客户电话")
    private String phone;

    /** 客户传真 */
    @Excel(name = "客户传真")
    private String fax;

    /** 客户邮箱 */
    @Excel(name = "客户邮箱")
    private String email;

    /** 客户描述 */
    @Excel(name = "客户描述")
    private String description;

    /** 是否启用(0:禁用,1:启用) */
    @Excel(name = "是否启用(0:禁用,1:启用)")
    private Integer status;

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
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setContactPerson(String contactPerson)
    {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson()
    {
        return contactPerson;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setFax(String fax)
    {
        this.fax = fax;
    }

    public String getFax()
    {
        return fax;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("name", getName())
            .append("address", getAddress())
            .append("contactPerson", getContactPerson())
            .append("phone", getPhone())
            .append("fax", getFax())
            .append("email", getEmail())
            .append("description", getDescription())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("deleteFlag", getDeleteFlag())
            .toString();
    }
}
