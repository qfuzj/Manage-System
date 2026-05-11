package com.ms.system.domain;

import java.util.List;

import com.ms.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ms.common.annotation.Excel;

/**
 * 前台列表对象 user
 *
 * @author ms
 * @date 2026-05-11
 */
public class F_User extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户编号 */
    private Long userId;

    /** 用户名 */
    @Excel(name = "用户名")
    private String username;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** $table.subTable.functionName信息 */
    private List<UserDetail> userDetailList;

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }

    public List<UserDetail> getUserDetailList()
    {
        return userDetailList;
    }

    public void setUserDetailList(List<UserDetail> userDetailList)
    {
        this.userDetailList = userDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("username", getUsername())
            .append("password", getPassword())
            .append("userDetailList", getUserDetailList())
            .toString();
    }
}
