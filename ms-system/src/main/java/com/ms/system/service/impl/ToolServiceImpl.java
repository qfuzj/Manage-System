package com.ms.system.service.impl;

import java.util.List;
import com.ms.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ms.system.mapper.ToolMapper;
import com.ms.system.domain.Tool;
import com.ms.system.service.IToolService;

/**
 * 工装夹具清单Service业务层处理
 * 
 * @author ms
 * @date 2026-05-12
 */
@Service
public class ToolServiceImpl implements IToolService 
{
    @Autowired
    private ToolMapper toolMapper;

    /**
     * 查询工装夹具清单
     * 
     * @param id 工装夹具清单主键
     * @return 工装夹具清单
     */
    @Override
    public Tool selectToolById(Long id)
    {
        return toolMapper.selectToolById(id);
    }

    /**
     * 查询工装夹具清单列表
     * 
     * @param tool 工装夹具清单
     * @return 工装夹具清单
     */
    @Override
    public List<Tool> selectToolList(Tool tool)
    {
        return toolMapper.selectToolList(tool);
    }

    /**
     * 新增工装夹具清单
     * 
     * @param tool 工装夹具清单
     * @return 结果
     */
    @Override
    public int insertTool(Tool tool)
    {
        tool.setCreateTime(DateUtils.getNowDate());
        return toolMapper.insertTool(tool);
    }

    /**
     * 修改工装夹具清单
     * 
     * @param tool 工装夹具清单
     * @return 结果
     */
    @Override
    public int updateTool(Tool tool)
    {
        tool.setUpdateTime(DateUtils.getNowDate());
        return toolMapper.updateTool(tool);
    }

    /**
     * 批量删除工装夹具清单
     * 
     * @param ids 需要删除的工装夹具清单主键
     * @return 结果
     */
    @Override
    public int deleteToolByIds(Long[] ids)
    {
        return toolMapper.deleteToolByIds(ids);
    }

    /**
     * 删除工装夹具清单信息
     * 
     * @param id 工装夹具清单主键
     * @return 结果
     */
    @Override
    public int deleteToolById(Long id)
    {
        return toolMapper.deleteToolById(id);
    }
}
