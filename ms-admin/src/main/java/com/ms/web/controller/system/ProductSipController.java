package com.ms.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ms.common.annotation.Log;
import com.ms.common.core.controller.BaseController;
import com.ms.common.core.domain.AjaxResult;
import com.ms.common.enums.BusinessType;
import com.ms.system.domain.ProductSip;
import com.ms.system.service.IProductSipService;
import com.ms.common.utils.poi.ExcelUtil;
import com.ms.common.core.page.TableDataInfo;

/**
 * 产品SIPController
 *
 * @author ms
 * @date 2026-05-13
 */
@RestController
@RequestMapping("/mes:md/productsip")
public class ProductSipController extends BaseController
{
    @Autowired
    private IProductSipService productSipService;

    /**
     * 查询产品SIP列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProductSip productSip)
    {
        startPage();
        List<ProductSip> list = productSipService.selectProductSipList(productSip);
        return getDataTable(list);
    }

    /**
     * 导出产品SIP列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:export')")
    @Log(title = "产品SIP", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProductSip productSip)
    {
        List<ProductSip> list = productSipService.selectProductSipList(productSip);
        ExcelUtil<ProductSip> util = new ExcelUtil<ProductSip>(ProductSip.class);
        util.exportExcel(response, list, "产品SIP数据");
    }

    /**
     * 获取产品SIP详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:query')")
    @GetMapping(value = "/{sipId}")
    public AjaxResult getInfo(@PathVariable("sipId") Long sipId)
    {
        return success(productSipService.selectProductSipBySipId(sipId));
    }

    /**
     * 新增产品SIP
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:add')")
    @Log(title = "产品SIP", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProductSip productSip)
    {
        return toAjax(productSipService.insertProductSip(productSip));
    }

    /**
     * 修改产品SIP
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:edit')")
    @Log(title = "产品SIP", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProductSip productSip)
    {
        return toAjax(productSipService.updateProductSip(productSip));
    }

    /**
     * 删除产品SIP
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:remove')")
    @Log(title = "产品SIP", businessType = BusinessType.DELETE)
	@DeleteMapping("/{sipIds}")
    public AjaxResult remove(@PathVariable Long[] sipIds)
    {
        return toAjax(productSipService.deleteProductSipBySipIds(sipIds));
    }
}
