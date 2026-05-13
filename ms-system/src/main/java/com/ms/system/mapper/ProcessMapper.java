package com.ms.system.mapper;

import java.util.List;
import com.ms.system.domain.Process;

/**
 * 工序Mapper接口
 * 
 * @author ms
 * @date 2026-05-13
 */
public interface ProcessMapper 
{
    /**
     * 查询工序
     * 
     * @param id 工序主键
     * @return 工序
     */
    public Process selectProcessById(Long id);

    /**
     * 查询工序列表
     * 
     * @param process 工序
     * @return 工序集合
     */
    public List<Process> selectProcessList(Process process);

    /**
     * 新增工序
     * 
     * @param process 工序
     * @return 结果
     */
    public int insertProcess(Process process);

    /**
     * 修改工序
     * 
     * @param process 工序
     * @return 结果
     */
    public int updateProcess(Process process);

    /**
     * 删除工序
     * 
     * @param id 工序主键
     * @return 结果
     */
    public int deleteProcessById(Long id);

    /**
     * 批量删除工序
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProcessByIds(Long[] ids);
}
