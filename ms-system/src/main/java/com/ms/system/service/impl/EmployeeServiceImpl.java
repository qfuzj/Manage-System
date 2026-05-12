package com.ms.system.service.impl;

import java.util.List;
import com.ms.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ms.system.mapper.EmployeeMapper;
import com.ms.system.domain.Employee;
import com.ms.system.service.IEmployeeService;

/**
 * 员工Service业务层处理
 * 
 * @author ms
 * @date 2026-05-12
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService 
{
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 查询员工
     * 
     * @param id 员工主键
     * @return 员工
     */
    @Override
    public Employee selectEmployeeById(Long id)
    {
        return employeeMapper.selectEmployeeById(id);
    }

    /**
     * 查询员工列表
     * 
     * @param employee 员工
     * @return 员工
     */
    @Override
    public List<Employee> selectEmployeeList(Employee employee)
    {
        return employeeMapper.selectEmployeeList(employee);
    }

    /**
     * 新增员工
     * 
     * @param employee 员工
     * @return 结果
     */
    @Override
    public int insertEmployee(Employee employee)
    {
        employee.setCreateTime(DateUtils.getNowDate());
        return employeeMapper.insertEmployee(employee);
    }

    /**
     * 修改员工
     * 
     * @param employee 员工
     * @return 结果
     */
    @Override
    public int updateEmployee(Employee employee)
    {
        return employeeMapper.updateEmployee(employee);
    }

    /**
     * 批量删除员工
     * 
     * @param ids 需要删除的员工主键
     * @return 结果
     */
    @Override
    public int deleteEmployeeByIds(Long[] ids)
    {
        return employeeMapper.deleteEmployeeByIds(ids);
    }

    /**
     * 删除员工信息
     * 
     * @param id 员工主键
     * @return 结果
     */
    @Override
    public int deleteEmployeeById(Long id)
    {
        return employeeMapper.deleteEmployeeById(id);
    }
}
