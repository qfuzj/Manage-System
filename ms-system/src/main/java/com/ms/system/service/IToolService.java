package com.ms.system.service;

import java.util.List;
import com.ms.system.domain.Tool;

/**
 * 工装夹具清单Service接口
 * 
 * @author ms
 * @date 2026-05-12
 */
public interface IToolService 
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
     * 批量删除工装夹具清单
     * 
     * @param ids 需要删除的工装夹具清单主键集合
     * @return 结果
     */
    public int deleteToolByIds(Long[] ids);

    /**
     * 删除工装夹具清单信息
     * 
     * @param id 工装夹具清单主键
     * @return 结果
     */
    public int deleteToolById(Long id);
}
