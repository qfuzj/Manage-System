package com.ms.system.domain;

import java.math.BigDecimal;
import java.util.List;

import com.ms.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ms.common.annotation.Excel;

/**
 * 物料产品对象 md_item
 *
 * @author ms
 * @date 2026-05-13
 */
public class MdItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 产品物料ID */
    private Long itemId;

    /** 产品物料编码 */
    @Excel(name = "产品物料编码")
    private String itemCode;

    /** 产品物料名称 */
    @Excel(name = "产品物料名称")
    private String itemName;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specification;

    /** 单位编码 */
    @Excel(name = "单位编码")
    private String unitOfMeasure;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 产品物料标识 */
    @Excel(name = "产品物料标识")
    private String itemOrProduct;

    /** 物料类型ID */
    @Excel(name = "物料类型ID")
    private Long itemTypeId;

    /** 物料类型编码 */
    @Excel(name = "物料类型编码")
    private String itemTypeCode;

    /** 物料类型名称 */
    @Excel(name = "物料类型名称")
    private String itemTypeName;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private String enableFlag;

    /** 是否设置安全库存 */
    @Excel(name = "是否设置安全库存")
    private String safeStockFlag;

    /** 最低库存量 */
    @Excel(name = "最低库存量")
    private BigDecimal minStock;

    /** 最大库存量 */
    @Excel(name = "最大库存量")
    private BigDecimal maxStock;

    /** 高价值物资 */
    @Excel(name = "高价值物资")
    private String highValue;

    /** 批次管理 */
    @Excel(name = "批次管理")
    private String batchFlag;

    /** 预留字段1 */
    private String attr1;

    /** 预留字段2 */
    private String attr2;

    /** 预留字段3 */
    private Long attr3;

    /** 预留字段4 */
    private Long attr4;

    /** 产品SOP信息 */
    private List<ProductSop> productSopList;

    public void setItemId(Long itemId)
    {
        this.itemId = itemId;
    }

    public Long getItemId()
    {
        return itemId;
    }
    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

    public String getItemCode()
    {
        return itemCode;
    }
    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public String getItemName()
    {
        return itemName;
    }
    public void setSpecification(String specification)
    {
        this.specification = specification;
    }

    public String getSpecification()
    {
        return specification;
    }
    public void setUnitOfMeasure(String unitOfMeasure)
    {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getUnitOfMeasure()
    {
        return unitOfMeasure;
    }
    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

    public String getUnitName()
    {
        return unitName;
    }
    public void setItemOrProduct(String itemOrProduct)
    {
        this.itemOrProduct = itemOrProduct;
    }

    public String getItemOrProduct()
    {
        return itemOrProduct;
    }
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
    public void setEnableFlag(String enableFlag)
    {
        this.enableFlag = enableFlag;
    }

    public String getEnableFlag()
    {
        return enableFlag;
    }
    public void setSafeStockFlag(String safeStockFlag)
    {
        this.safeStockFlag = safeStockFlag;
    }

    public String getSafeStockFlag()
    {
        return safeStockFlag;
    }
    public void setMinStock(BigDecimal minStock)
    {
        this.minStock = minStock;
    }

    public BigDecimal getMinStock()
    {
        return minStock;
    }
    public void setMaxStock(BigDecimal maxStock)
    {
        this.maxStock = maxStock;
    }

    public BigDecimal getMaxStock()
    {
        return maxStock;
    }
    public void setHighValue(String highValue)
    {
        this.highValue = highValue;
    }

    public String getHighValue()
    {
        return highValue;
    }
    public void setBatchFlag(String batchFlag)
    {
        this.batchFlag = batchFlag;
    }

    public String getBatchFlag()
    {
        return batchFlag;
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

    public List<ProductSop> getProductSopList()
    {
        return productSopList;
    }

    public void setProductSopList(List<ProductSop> productSopList)
    {
        this.productSopList = productSopList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("itemId", getItemId())
            .append("itemCode", getItemCode())
            .append("itemName", getItemName())
            .append("specification", getSpecification())
            .append("unitOfMeasure", getUnitOfMeasure())
            .append("unitName", getUnitName())
            .append("itemOrProduct", getItemOrProduct())
            .append("itemTypeId", getItemTypeId())
            .append("itemTypeCode", getItemTypeCode())
            .append("itemTypeName", getItemTypeName())
            .append("enableFlag", getEnableFlag())
            .append("safeStockFlag", getSafeStockFlag())
            .append("minStock", getMinStock())
            .append("maxStock", getMaxStock())
            .append("highValue", getHighValue())
            .append("batchFlag", getBatchFlag())
            .append("remark", getRemark())
            .append("attr1", getAttr1())
            .append("attr2", getAttr2())
            .append("attr3", getAttr3())
            .append("attr4", getAttr4())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("productSopList", getProductSopList())
            .toString();
    }
}
