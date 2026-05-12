package com.ms.system.service;

import java.util.List;
import com.ms.system.domain.Employee;

/**
 * 员工Service接口
 * 
 * @author ms
 * @date 2026-05-12
 */
public interface IEmployeeService 
{
    /**
     * 查询员工
     * 
     * @param id 员工主键
     * @return 员工
     */
    public Employee selectEmployeeById(Long id);

    /**
     * 查询员工列表
     * 
     * @param employee 员工
     * @return 员工集合
     */
    public List<Employee> selectEmployeeList(Employee employee);

    /**
     * 新增员工
     * 
     * @param employee 员工
     * @return 结果
     */
    public int insertEmployee(Employee employee);

    /**
     * 修改员工
     * 
     * @param employee 员工
     * @return 结果
     */
    public int updateEmployee(Employee employee);

    /**
     * 批量删除员工
     * 
     * @param ids 需要删除的员工主键集合
     * @return 结果
     */
    public int deleteEmployeeByIds(Long[] ids);

    /**
     * 删除员工信息
     * 
     * @param id 员工主键
     * @return 结果
     */
    public int deleteEmployeeById(Long id);
}
