package com.ms.system.service;

import com.ms.common.core.domain.entity.SysAutoCodeResult;

import java.util.List;

/**
 * 自动编码记录service
 */
public interface IAutoCodeResultService {

    /**
     * 根据条件查询当前的记录表中是否有满足指定条件的记录
     * gen_date条件要以like 'xxx%' 方式查询
     */
    List<SysAutoCodeResult> list(SysAutoCodeResult sysAutoCodeResult);

    void saveAutoCodeResult(SysAutoCodeResult sysAutoCodeResult);

    void updateAutoCodeResult(SysAutoCodeResult sysAutoCodeResult);
}
