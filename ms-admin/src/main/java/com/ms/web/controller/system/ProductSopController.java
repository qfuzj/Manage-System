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
import com.ms.system.domain.ProductSop;
import com.ms.system.service.IProductSopService;
import com.ms.common.utils.poi.ExcelUtil;
import com.ms.common.core.page.TableDataInfo;

/**
 * 产品SOPController
 *
 * @author ms
 * @date 2026-05-13
 */
@RestController
@RequestMapping("/mes:md/productsop")
public class ProductSopController extends BaseController
{
    @Autowired
    private IProductSopService productSopService;

    /**
     * 查询产品SOP列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProductSop productSop)
    {
        startPage();
        List<ProductSop> list = productSopService.selectProductSopList(productSop);
        return getDataTable(list);
    }

    /**
     * 导出产品SOP列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:export')")
    @Log(title = "产品SOP", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProductSop productSop)
    {
        List<ProductSop> list = productSopService.selectProductSopList(productSop);
        ExcelUtil<ProductSop> util = new ExcelUtil<ProductSop>(ProductSop.class);
        util.exportExcel(response, list, "产品SOP数据");
    }

    /**
     * 获取产品SOP详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:query')")
    @GetMapping(value = "/{sopId}")
    public AjaxResult getInfo(@PathVariable("sopId") Long sopId)
    {
        return success(productSopService.selectProductSopBySopId(sopId));
    }

    /**
     * 新增产品SOP
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:add')")
    @Log(title = "产品SOP", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProductSop productSop)
    {
        return toAjax(productSopService.insertProductSop(productSop));
    }

    /**
     * 修改产品SOP
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:edit')")
    @Log(title = "产品SOP", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProductSop productSop)
    {
        return toAjax(productSopService.updateProductSop(productSop));
    }

    /**
     * 删除产品SOP
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:remove')")
    @Log(title = "产品SOP", businessType = BusinessType.DELETE)
	@DeleteMapping("/{sopIds}")
    public AjaxResult remove(@PathVariable Long[] sopIds)
    {
        return toAjax(productSopService.deleteProductSopBySopIds(sopIds));
    }
}
