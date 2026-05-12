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
import com.ms.system.domain.Workstation;
import com.ms.system.service.IWorkstationService;
import com.ms.common.utils.poi.ExcelUtil;
import com.ms.common.core.page.TableDataInfo;

/**
 * 工作站Controller
 *
 * @author ms
 * @date 2026-05-12
 */
@RestController
@RequestMapping("/mes:md/workstation")
public class WorkstationController extends BaseController
{
    @Autowired
    private IWorkstationService workstationService;

    /**
     * 查询工作站列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workstation:list')")
    @GetMapping("/list")
    public TableDataInfo list(Workstation workstation)
    {
        startPage();
        List<Workstation> list = workstationService.selectWorkstationList(workstation);
        return getDataTable(list);
    }

    /**
     * 导出工作站列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workstation:export')")
    @Log(title = "工作站", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Workstation workstation)
    {
        List<Workstation> list = workstationService.selectWorkstationList(workstation);
        ExcelUtil<Workstation> util = new ExcelUtil<Workstation>(Workstation.class);
        util.exportExcel(response, list, "工作站数据");
    }

    /**
     * 获取工作站详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workstation:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(workstationService.selectWorkstationById(id));
    }

    /**
     * 新增工作站
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workstation:add')")
    @Log(title = "工作站", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Workstation workstation)
    {
        return toAjax(workstationService.insertWorkstation(workstation));
    }

    /**
     * 修改工作站
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workstation:edit')")
    @Log(title = "工作站", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Workstation workstation)
    {
        return toAjax(workstationService.updateWorkstation(workstation));
    }

    /**
     * 删除工作站
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workstation:remove')")
    @Log(title = "工作站", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(workstationService.deleteWorkstationByIds(ids));
    }
}
