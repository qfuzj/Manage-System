package com.ms.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ms.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ms.system.domain.UserDetail;
import com.ms.system.mapper.F_UserMapper;
import com.ms.system.domain.F_User;
import com.ms.system.service.IF_UserService;

/**
 * 前台用户Service业务层处理
 * 
 * @author ms
 * @date 2026-05-11
 */
@Service
public class F_UserServiceImpl implements IF_UserService 
{
    @Autowired
    private F_UserMapper f_UserMapper;

    /**
     * 查询前台用户
     * 
     * @param userId 前台用户主键
     * @return 前台用户
     */
    @Override
    public F_User selectF_UserByUserId(Long userId)
    {
        return f_UserMapper.selectF_UserByUserId(userId);
    }

    /**
     * 查询前台用户列表
     * 
     * @param f_User 前台用户
     * @return 前台用户
     */
    @Override
    public List<F_User> selectF_UserList(F_User f_User)
    {
        return f_UserMapper.selectF_UserList(f_User);
    }

    /**
     * 新增前台用户
     * 
     * @param f_User 前台用户
     * @return 结果
     */
    @Transactional
    @Override
    public int insertF_User(F_User f_User)
    {
        int rows = f_UserMapper.insertF_User(f_User);
        insertUserDetail(f_User);
        return rows;
    }

    /**
     * 修改前台用户
     * 
     * @param f_User 前台用户
     * @return 结果
     */
    @Transactional
    @Override
    public int updateF_User(F_User f_User)
    {
        f_UserMapper.deleteUserDetailByUserId(f_User.getUserId());
        insertUserDetail(f_User);
        return f_UserMapper.updateF_User(f_User);
    }

    /**
     * 批量删除前台用户
     * 
     * @param userIds 需要删除的前台用户主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteF_UserByUserIds(Long[] userIds)
    {
        f_UserMapper.deleteUserDetailByUserIds(userIds);
        return f_UserMapper.deleteF_UserByUserIds(userIds);
    }

    /**
     * 删除前台用户信息
     * 
     * @param userId 前台用户主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteF_UserByUserId(Long userId)
    {
        f_UserMapper.deleteUserDetailByUserId(userId);
        return f_UserMapper.deleteF_UserByUserId(userId);
    }

    /**
     * 新增${subTable.functionName}信息
     * 
     * @param f_User 前台用户对象
     */
    public void insertUserDetail(F_User f_User)
    {
        List<UserDetail> userDetailList = f_User.getUserDetailList();
        Long userId = f_User.getUserId();
        if (StringUtils.isNotNull(userDetailList))
        {
            List<UserDetail> list = new ArrayList<UserDetail>();
            for (UserDetail userDetail : userDetailList)
            {
                userDetail.setUserId(userId);
                list.add(userDetail);
            }
            if (list.size() > 0)
            {
                f_UserMapper.batchUserDetail(list);
            }
        }
    }
}
