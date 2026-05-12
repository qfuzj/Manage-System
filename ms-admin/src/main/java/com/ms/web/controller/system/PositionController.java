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
import com.ms.system.domain.Position;
import com.ms.system.service.IPositionService;
import com.ms.common.utils.poi.ExcelUtil;
import com.ms.common.core.page.TableDataInfo;

/**
 * 岗位Controller
 *
 * @author ms
 * @date 2026-05-12
 */
@RestController
@RequestMapping("/system/position")
public class PositionController extends BaseController
{
    @Autowired
    private IPositionService positionService;

    /**
     * 查询岗位列表
     */
    @PreAuthorize("@ss.hasPermi('system:position:list')")
    @GetMapping("/list")
    public TableDataInfo list(Position position)
    {
        startPage();
        List<Position> list = positionService.selectPositionList(position);
        return getDataTable(list);
    }

    /**
     * 导出岗位列表
     */
    @PreAuthorize("@ss.hasPermi('system:position:export')")
    @Log(title = "岗位", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Position position)
    {
        List<Position> list = positionService.selectPositionList(position);
        ExcelUtil<Position> util = new ExcelUtil<Position>(Position.class);
        util.exportExcel(response, list, "岗位数据");
    }

    /**
     * 获取岗位详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:position:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(positionService.selectPositionById(id));
    }

    /**
     * 新增岗位
     */
    @PreAuthorize("@ss.hasPermi('system:position:add')")
    @Log(title = "岗位", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Position position)
    {
        return toAjax(positionService.insertPosition(position));
    }

    /**
     * 修改岗位
     */
    @PreAuthorize("@ss.hasPermi('system:position:edit')")
    @Log(title = "岗位", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Position position)
    {
        return toAjax(positionService.updatePosition(position));
    }

    /**
     * 删除岗位
     */
    @PreAuthorize("@ss.hasPermi('system:position:remove')")
    @Log(title = "岗位", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(positionService.deletePositionByIds(ids));
    }
}
