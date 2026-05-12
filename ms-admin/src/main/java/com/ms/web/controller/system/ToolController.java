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
import com.ms.system.domain.Tool;
import com.ms.system.service.IToolService;
import com.ms.common.utils.poi.ExcelUtil;
import com.ms.common.core.page.TableDataInfo;

/**
 * 工装夹具清单Controller
 *
 * @author ms
 * @date 2026-05-12
 */
@RestController
@RequestMapping("/system/tool")
public class ToolController extends BaseController
{
    @Autowired
    private IToolService toolService;

    /**
     * 查询工装夹具清单列表
     */
    @PreAuthorize("@ss.hasPermi('system:tool:list')")
    @GetMapping("/list")
    public TableDataInfo list(Tool tool)
    {
        startPage();
        List<Tool> list = toolService.selectToolList(tool);
        return getDataTable(list);
    }

    /**
     * 导出工装夹具清单列表
     */
    @PreAuthorize("@ss.hasPermi('system:tool:export')")
    @Log(title = "工装夹具清单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Tool tool)
    {
        List<Tool> list = toolService.selectToolList(tool);
        ExcelUtil<Tool> util = new ExcelUtil<Tool>(Tool.class);
        util.exportExcel(response, list, "工装夹具清单数据");
    }

    /**
     * 获取工装夹具清单详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:tool:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(toolService.selectToolById(id));
    }

    /**
     * 新增工装夹具清单
     */
    @PreAuthorize("@ss.hasPermi('system:tool:add')")
    @Log(title = "工装夹具清单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Tool tool)
    {
        return toAjax(toolService.insertTool(tool));
    }

    /**
     * 修改工装夹具清单
     */
    @PreAuthorize("@ss.hasPermi('system:tool:edit')")
    @Log(title = "工装夹具清单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Tool tool)
    {
        return toAjax(toolService.updateTool(tool));
    }

    /**
     * 删除工装夹具清单
     */
    @PreAuthorize("@ss.hasPermi('system:tool:remove')")
    @Log(title = "工装夹具清单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(toolService.deleteToolByIds(ids));
    }
}
