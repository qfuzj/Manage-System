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
import com.ms.system.domain.Unit;
import com.ms.system.service.IUnitService;
import com.ms.common.utils.poi.ExcelUtil;
import com.ms.common.core.page.TableDataInfo;

/**
 * 计量单位Controller
 *
 * @author ms
 * @date 2026-05-11
 */
@RestController
@RequestMapping("/system/unit")
public class UnitController extends BaseController
{
    @Autowired
    private IUnitService unitService;

    /**
     * 查询计量单位列表
     */
    @PreAuthorize("@ss.hasPermi('system:unit:list')")
    @GetMapping("/list")
    public TableDataInfo list(Unit unit)
    {
        startPage();
        List<Unit> list = unitService.selectUnitList(unit);
        return getDataTable(list);
    }

    /**
     * 导出计量单位列表
     */
    @PreAuthorize("@ss.hasPermi('system:unit:export')")
    @Log(title = "计量单位", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Unit unit)
    {
        List<Unit> list = unitService.selectUnitList(unit);
        ExcelUtil<Unit> util = new ExcelUtil<Unit>(Unit.class);
        util.exportExcel(response, list, "计量单位数据");
    }

    /**
     * 获取计量单位详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:unit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(unitService.selectUnitById(id));
    }

    /**
     * 新增计量单位
     */
    @PreAuthorize("@ss.hasPermi('system:unit:add')")
    @Log(title = "计量单位", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Unit unit)
    {
        return toAjax(unitService.insertUnit(unit));
    }

    /**
     * 修改计量单位
     */
    @PreAuthorize("@ss.hasPermi('system:unit:edit')")
    @Log(title = "计量单位", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Unit unit)
    {
        return toAjax(unitService.updateUnit(unit));
    }

    /**
     * 删除计量单位
     */
    @PreAuthorize("@ss.hasPermi('system:unit:remove')")
    @Log(title = "计量单位", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(unitService.deleteUnitByIds(ids));
    }

    /**
     * 获取主单位列表
     */
    @PreAuthorize("@ss.hasPermi('system:unit:list')")
    @GetMapping("/mainUnitList")
    public AjaxResult getMainUnitList() {
        return success(unitService.selectMainUnitList());
    }
}
