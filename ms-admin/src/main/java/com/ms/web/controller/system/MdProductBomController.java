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
import com.ms.system.domain.MdProductBom;
import com.ms.system.service.IMdProductBomService;
import com.ms.common.utils.poi.ExcelUtil;
import com.ms.common.core.page.TableDataInfo;

/**
 * 产品BOM关系Controller
 *
 * @author ms
 * @date 2026-05-13
 */
@RestController
@RequestMapping("/mes:md/mdproductbom")
public class MdProductBomController extends BaseController
{
    @Autowired
    private IMdProductBomService mdProductBomService;

    /**
     * 查询产品BOM关系列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:list')")
    @GetMapping("/list")
    public TableDataInfo list(MdProductBom mdProductBom)
    {
        startPage();
        List<MdProductBom> list = mdProductBomService.selectMdProductBomList(mdProductBom);
        return getDataTable(list);
    }

    /**
     * 导出产品BOM关系列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:export')")
    @Log(title = "产品BOM关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MdProductBom mdProductBom)
    {
        List<MdProductBom> list = mdProductBomService.selectMdProductBomList(mdProductBom);
        ExcelUtil<MdProductBom> util = new ExcelUtil<>(MdProductBom.class);
        util.exportExcel(response, list, "产品BOM关系数据");
    }

    /**
     * 获取产品BOM关系详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:query')")
    @GetMapping(value = "/{bomId}")
    public AjaxResult getInfo(@PathVariable("bomId") Long bomId)
    {
        return success(mdProductBomService.selectMdProductBomByBomId(bomId));
    }

    /**
     * 新增产品BOM关系
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:add')")
    @Log(title = "产品BOM关系", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdProductBom mdProductBom)
    {
        return toAjax(mdProductBomService.insertMdProductBom(mdProductBom));
    }

    /**
     * 修改产品BOM关系
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:edit')")
    @Log(title = "产品BOM关系", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdProductBom mdProductBom)
    {
        return toAjax(mdProductBomService.updateMdProductBom(mdProductBom));
    }

    /**
     * 删除产品BOM关系
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:remove')")
    @Log(title = "产品BOM关系", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bomIds}")
    public AjaxResult remove(@PathVariable Long[] bomIds)
    {
        return toAjax(mdProductBomService.deleteMdProductBomByBomIds(bomIds));
    }
}
