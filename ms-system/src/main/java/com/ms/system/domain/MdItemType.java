package com.ms.system.domain;

import com.ms.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ms.common.annotation.Excel;

/**
 * 物料产品分类对象 md_item_type
 *
 * @author ms
 * @date 2026-05-13
 */
public class MdItemType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 产品物料类型ID */
    private Long itemTypeId;

    /** 产品物料类型编码 */
    @Excel(name = "产品物料类型编码")
    private String itemTypeCode;

    /** 产品物料类型名称 */
    @Excel(name = "产品物料类型名称")
    private String itemTypeName;

    /** 父类型ID */
    @Excel(name = "父类型ID")
    private Long parentTypeId;

    /** 所有层级父节点 */
    @Excel(name = "所有层级父节点")
    private String ancestors;

    /** 产品物料标识 */
    @Excel(name = "产品物料标识")
    private String itemOrProduct;

    /** 排列顺序 */
    @Excel(name = "排列顺序")
    private Long orderNum;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private String enableFlag;

    /** 预留字段1 */
    private String attr1;

    /** 预留字段2 */
    private String attr2;

    /** 预留字段3 */
    private Long attr3;

    /** 预留字段4 */
    private Long attr4;

    public void setItemTypeId(Long itemTypeId)
    {
        this.itemTypeId = itemTypeId;
    }

    public Long getItemTypeId()
    {
        return itemTypeId;
    }
    public void setItemTypeCode(String itemTypeCode)
    {
        this.itemTypeCode = itemTypeCode;
    }

    public String getItemTypeCode()
    {
        return itemTypeCode;
    }
    public void setItemTypeName(String itemTypeName)
    {
        this.itemTypeName = itemTypeName;
    }

    public String getItemTypeName()
    {
        return itemTypeName;
    }
    public void setParentTypeId(Long parentTypeId)
    {
        this.parentTypeId = parentTypeId;
    }

    public Long getParentTypeId()
    {
        return parentTypeId;
    }
    public void setAncestors(String ancestors)
    {
        this.ancestors = ancestors;
    }

    public String getAncestors()
    {
        return ancestors;
    }
    public void setItemOrProduct(String itemOrProduct)
    {
        this.itemOrProduct = itemOrProduct;
    }

    public String getItemOrProduct()
    {
        return itemOrProduct;
    }
    public void setOrderNum(Long orderNum)
    {
        this.orderNum = orderNum;
    }

    public Long getOrderNum()
    {
        return orderNum;
    }
    public void setEnableFlag(String enableFlag)
    {
        this.enableFlag = enableFlag;
    }

    public String getEnableFlag()
    {
        return enableFlag;
    }
    public void setAttr1(String attr1)
    {
        this.attr1 = attr1;
    }

    public String getAttr1()
    {
        return attr1;
    }
    public void setAttr2(String attr2)
    {
        this.attr2 = attr2;
    }

    public String getAttr2()
    {
        return attr2;
    }
    public void setAttr3(Long attr3)
    {
        this.attr3 = attr3;
    }

    public Long getAttr3()
    {
        return attr3;
    }
    public void setAttr4(Long attr4)
    {
        this.attr4 = attr4;
    }

    public Long getAttr4()
    {
        return attr4;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("itemTypeId", getItemTypeId())
            .append("itemTypeCode", getItemTypeCode())
            .append("itemTypeName", getItemTypeName())
            .append("parentTypeId", getParentTypeId())
            .append("ancestors", getAncestors())
            .append("itemOrProduct", getItemOrProduct())
            .append("orderNum", getOrderNum())
            .append("enableFlag", getEnableFlag())
            .append("remark", getRemark())
            .append("attr1", getAttr1())
            .append("attr2", getAttr2())
            .append("attr3", getAttr3())
            .append("attr4", getAttr4())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
