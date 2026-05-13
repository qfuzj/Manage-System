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
import com.ms.system.domain.MdItemType;
import com.ms.system.service.IMdItemTypeService;
import com.ms.common.utils.poi.ExcelUtil;
import com.ms.common.core.page.TableDataInfo;

/**
 * 物料产品分类Controller
 *
 * @author ms
 * @date 2026-05-13
 */
@RestController
@RequestMapping("/mes:md/itemtype")
public class MdItemTypeController extends BaseController
{
    @Autowired
    private IMdItemTypeService mdItemTypeService;

    /**
     * 查询物料产品分类列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:itemtype:list')")
    @GetMapping("/list")
    public TableDataInfo list(MdItemType mdItemType)
    {
        startPage();
        List<MdItemType> list = mdItemTypeService.selectMdItemTypeList(mdItemType);
        return getDataTable(list);
    }

    /**
     * 导出物料产品分类列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:itemtype:export')")
    @Log(title = "物料产品分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MdItemType mdItemType)
    {
        List<MdItemType> list = mdItemTypeService.selectMdItemTypeList(mdItemType);
        ExcelUtil<MdItemType> util = new ExcelUtil<MdItemType>(MdItemType.class);
        util.exportExcel(response, list, "物料产品分类数据");
    }

    /**
     * 获取物料产品分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:md:itemtype:query')")
    @GetMapping(value = "/{itemTypeId}")
    public AjaxResult getInfo(@PathVariable("itemTypeId") Long itemTypeId)
    {
        return success(mdItemTypeService.selectMdItemTypeByItemTypeId(itemTypeId));
    }

    /**
     * 新增物料产品分类
     */
    @PreAuthorize("@ss.hasPermi('mes:md:itemtype:add')")
    @Log(title = "物料产品分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdItemType mdItemType)
    {
        return toAjax(mdItemTypeService.insertMdItemType(mdItemType));
    }

    /**
     * 修改物料产品分类
     */
    @PreAuthorize("@ss.hasPermi('mes:md:itemtype:edit')")
    @Log(title = "物料产品分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdItemType mdItemType)
    {
        return toAjax(mdItemTypeService.updateMdItemType(mdItemType));
    }

    /**
     * 删除物料产品分类
     */
    @PreAuthorize("@ss.hasPermi('mes:md:itemtype:remove')")
    @Log(title = "物料产品分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{itemTypeIds}")
    public AjaxResult remove(@PathVariable Long[] itemTypeIds)
    {
        return toAjax(mdItemTypeService.deleteMdItemTypeByItemTypeIds(itemTypeIds));
    }
}
