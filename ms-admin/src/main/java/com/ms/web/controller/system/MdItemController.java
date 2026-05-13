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
import com.ms.system.domain.MdItem;
import com.ms.system.service.IMdItemService;
import com.ms.common.utils.poi.ExcelUtil;
import com.ms.common.core.page.TableDataInfo;

/**
 * 物料产品Controller
 *
 * @author ms
 * @date 2026-05-13
 */
@RestController
@RequestMapping("/mes:md/mditem")
public class MdItemController extends BaseController
{
    @Autowired
    private IMdItemService mdItemService;

    /**
     * 查询物料产品列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:list')")
    @GetMapping("/list")
    public TableDataInfo list(MdItem mdItem)
    {
        startPage();
        List<MdItem> list = mdItemService.selectMdItemList(mdItem);
        return getDataTable(list);
    }

    /**
     * 导出物料产品列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:export')")
    @Log(title = "物料产品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MdItem mdItem)
    {
        List<MdItem> list = mdItemService.selectMdItemList(mdItem);
        ExcelUtil<MdItem> util = new ExcelUtil<MdItem>(MdItem.class);
        util.exportExcel(response, list, "物料产品数据");
    }

    /**
     * 获取物料产品详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:query')")
    @GetMapping(value = "/{itemId}")
    public AjaxResult getInfo(@PathVariable("itemId") Long itemId)
    {
        return success(mdItemService.selectMdItemByItemId(itemId));
    }

    /**
     * 新增物料产品
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:add')")
    @Log(title = "物料产品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdItem mdItem)
    {
        return toAjax(mdItemService.insertMdItem(mdItem));
    }

    /**
     * 修改物料产品
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:edit')")
    @Log(title = "物料产品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdItem mdItem)
    {
        return toAjax(mdItemService.updateMdItem(mdItem));
    }

    /**
     * 删除物料产品
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:remove')")
    @Log(title = "物料产品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{itemIds}")
    public AjaxResult remove(@PathVariable Long[] itemIds)
    {
        return toAjax(mdItemService.deleteMdItemByItemIds(itemIds));
    }
}
