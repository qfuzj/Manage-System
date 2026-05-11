package com.ms.system.service;

import java.util.List;
import com.ms.system.domain.F_User;

/**
 * 前台用户Service接口
 * 
 * @author ms
 * @date 2026-05-11
 */
public interface IF_UserService 
{
    /**
     * 查询前台用户
     * 
     * @param userId 前台用户主键
     * @return 前台用户
     */
    public F_User selectF_UserByUserId(Long userId);

    /**
     * 查询前台用户列表
     * 
     * @param f_User 前台用户
     * @return 前台用户集合
     */
    public List<F_User> selectF_UserList(F_User f_User);

    /**
     * 新增前台用户
     * 
     * @param f_User 前台用户
     * @return 结果
     */
    public int insertF_User(F_User f_User);

    /**
     * 修改前台用户
     * 
     * @param f_User 前台用户
     * @return 结果
     */
    public int updateF_User(F_User f_User);

    /**
     * 批量删除前台用户
     * 
     * @param userIds 需要删除的前台用户主键集合
     * @return 结果
     */
    public int deleteF_UserByUserIds(Long[] userIds);

    /**
     * 删除前台用户信息
     * 
     * @param userId 前台用户主键
     * @return 结果
     */
    public int deleteF_UserByUserId(Long userId);
}
