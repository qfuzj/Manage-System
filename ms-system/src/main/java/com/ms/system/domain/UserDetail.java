package com.ms.system.domain;

import com.ms.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ms.common.annotation.Excel;

/**
 * ${subTable.functionName}对象 user_detail
 *
 * @author ms
 * @date 2026-05-11
 */
public class UserDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 详情编号 */
    private Long udId;

    /** 用户编号 */
    @Excel(name = "用户编号")
    private Long userId;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    public void setUdId(Long udId)
    {
        this.udId = udId;
    }

    public Long getUdId()
    {
        return udId;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("udId", getUdId())
            .append("userId", getUserId())
            .append("email", getEmail())
            .append("phone", getPhone())
            .toString();
    }
}
