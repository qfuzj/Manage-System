package com.ms.system.mapper;

import java.util.List;
import com.ms.system.domain.Tool;

/**
 * 工装夹具清单Mapper接口
 * 
 * @author ms
 * @date 2026-05-12
 */
public interface ToolMapper 
{
    /**
     * 查询工装夹具清单
     * 
     * @param id 工装夹具清单主键
     * @return 工装夹具清单
     */
    public Tool selectToolById(Long id);

    /**
     * 查询工装夹具清单列表
     * 
     * @param tool 工装夹具清单
     * @return 工装夹具清单集合
     */
    public List<Tool> selectToolList(Tool tool);

    /**
     * 新增工装夹具清单
     * 
     * @param tool 工装夹具清单
     * @return 结果
     */
    public int insertTool(Tool tool);

    /**
     * 修改工装夹具清单
     * 
     * @param tool 工装夹具清单
     * @return 结果
     */
    public int updateTool(Tool tool);

    /**
     * 删除工装夹具清单
     * 
     * @param id 工装夹具清单主键
     * @return 结果
     */
    public int deleteToolById(Long id);

    /**
     * 批量删除工装夹具清单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteToolByIds(Long[] ids);
}
