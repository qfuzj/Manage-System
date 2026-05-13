package com.ms.system.domain;

import com.ms.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ms.common.annotation.Excel;

/**
 * 工序对象 base_process
 *
 * @author ms
 * @date 2026-05-13
 */
public class Process extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工序ID */
    private Long id;

    /** 工序编码 */
    @Excel(name = "工序编码")
    private String processCode;

    /** 工序名称 */
    @Excel(name = "工序名称")
    private String processName;

    /** 工序描述 */
    @Excel(name = "工序描述")
    private String processDesc;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private Integer isActive;

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
    public void setProcessCode(String processCode)
    {
        this.processCode = processCode;
    }

    public String getProcessCode()
    {
        return processCode;
    }
    public void setProcessName(String processName)
    {
        this.processName = processName;
    }

    public String getProcessName()
    {
        return processName;
    }
    public void setProcessDesc(String processDesc)
    {
        this.processDesc = processDesc;
    }

    public String getProcessDesc()
    {
        return processDesc;
    }
    public void setIsActive(Integer isActive)
    {
        this.isActive = isActive;
    }

    public Integer getIsActive()
    {
        return isActive;
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
            .append("processCode", getProcessCode())
            .append("processName", getProcessName())
            .append("processDesc", getProcessDesc())
            .append("isActive", getIsActive())
            .append("createTime", getCreateTime())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
