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
import com.ms.system.domain.Workshop;
import com.ms.system.service.IWorkshopService;
import com.ms.common.utils.poi.ExcelUtil;
import com.ms.common.core.page.TableDataInfo;

/**
 * 车间Controller
 *
 * @author ms
 * @date 2026-05-12
 */
@RestController
@RequestMapping("/mes:md/workshop")
public class WorkshopController extends BaseController
{
    @Autowired
    private IWorkshopService workshopService;

    /**
     * 查询车间列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workshop:list')")
    @GetMapping("/list")
    public TableDataInfo list(Workshop workshop)
    {
        startPage();
        List<Workshop> list = workshopService.selectWorkshopList(workshop);
        return getDataTable(list);
    }

    /**
     * 导出车间列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workshop:export')")
    @Log(title = "车间", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Workshop workshop)
    {
        List<Workshop> list = workshopService.selectWorkshopList(workshop);
        ExcelUtil<Workshop> util = new ExcelUtil<Workshop>(Workshop.class);
        util.exportExcel(response, list, "车间数据");
    }

    /**
     * 获取车间详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workshop:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(workshopService.selectWorkshopById(id));
    }

    /**
     * 新增车间
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workshop:add')")
    @Log(title = "车间", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Workshop workshop)
    {
        return toAjax(workshopService.insertWorkshop(workshop));
    }

    /**
     * 修改车间
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workshop:edit')")
    @Log(title = "车间", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Workshop workshop)
    {
        return toAjax(workshopService.updateWorkshop(workshop));
    }

    /**
     * 删除车间
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workshop:remove')")
    @Log(title = "车间", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(workshopService.deleteWorkshopByIds(ids));
    }
}
