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
import com.ms.system.domain.F_User;
import com.ms.system.service.IF_UserService;
import com.ms.common.utils.poi.ExcelUtil;
import com.ms.common.core.page.TableDataInfo;

/**
 * 前台列表Controller
 *
 * @author ms
 * @date 2026-05-11
 */
@RestController
@RequestMapping("/system/f_user")
public class F_UserController extends BaseController
{
    @Autowired
    private IF_UserService f_UserService;

    /**
     * 查询前台列表列表
     */
    @PreAuthorize("@ss.hasPermi('system:f_user:list')")
    @GetMapping("/list")
    public TableDataInfo list(F_User f_User)
    {
        startPage();
        List<F_User> list = f_UserService.selectF_UserList(f_User);
        return getDataTable(list);
    }

    /**
     * 导出前台列表列表
     */
    @PreAuthorize("@ss.hasPermi('system:f_user:export')")
    @Log(title = "前台列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, F_User f_User)
    {
        List<F_User> list = f_UserService.selectF_UserList(f_User);
        ExcelUtil<F_User> util = new ExcelUtil<F_User>(F_User.class);
        util.exportExcel(response, list, "前台列表数据");
    }

    /**
     * 获取前台列表详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:f_user:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return success(f_UserService.selectF_UserByUserId(userId));
    }

    /**
     * 新增前台列表
     */
    @PreAuthorize("@ss.hasPermi('system:f_user:add')")
    @Log(title = "前台列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody F_User f_User)
    {
        return toAjax(f_UserService.insertF_User(f_User));
    }

    /**
     * 修改前台列表
     */
    @PreAuthorize("@ss.hasPermi('system:f_user:edit')")
    @Log(title = "前台列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody F_User f_User)
    {
        return toAjax(f_UserService.updateF_User(f_User));
    }

    /**
     * 删除前台列表
     */
    @PreAuthorize("@ss.hasPermi('system:f_user:remove')")
    @Log(title = "前台列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(f_UserService.deleteF_UserByUserIds(userIds));
    }
}
