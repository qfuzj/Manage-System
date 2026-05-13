package com.ms.system.service.impl;

import java.util.List;
import com.ms.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ms.system.mapper.ProcessMapper;
import com.ms.system.domain.Process;
import com.ms.system.service.IProcessService;

/**
 * 工序Service业务层处理
 * 
 * @author ms
 * @date 2026-05-13
 */
@Service
public class ProcessServiceImpl implements IProcessService 
{
    @Autowired
    private ProcessMapper processMapper;

    /**
     * 查询工序
     * 
     * @param id 工序主键
     * @return 工序
     */
    @Override
    public Process selectProcessById(Long id)
    {
        return processMapper.selectProcessById(id);
    }

    /**
     * 查询工序列表
     * 
     * @param process 工序
     * @return 工序
     */
    @Override
    public List<Process> selectProcessList(Process process)
    {
        return processMapper.selectProcessList(process);
    }

    /**
     * 新增工序
     * 
     * @param process 工序
     * @return 结果
     */
    @Override
    public int insertProcess(Process process)
    {
        process.setCreateTime(DateUtils.getNowDate());
        return processMapper.insertProcess(process);
    }

    /**
     * 修改工序
     * 
     * @param process 工序
     * @return 结果
     */
    @Override
    public int updateProcess(Process process)
    {
        return processMapper.updateProcess(process);
    }

    /**
     * 批量删除工序
     * 
     * @param ids 需要删除的工序主键
     * @return 结果
     */
    @Override
    public int deleteProcessByIds(Long[] ids)
    {
        return processMapper.deleteProcessByIds(ids);
    }

    /**
     * 删除工序信息
     * 
     * @param id 工序主键
     * @return 结果
     */
    @Override
    public int deleteProcessById(Long id)
    {
        return processMapper.deleteProcessById(id);
    }
}
