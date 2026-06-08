package com.ms.system.mapper;

import com.ms.common.core.domain.entity.SysAutoCodeResult;

import java.util.List;

public interface SysAutoCodeResultMapper {
    List<SysAutoCodeResult> selectSysAutoCodeResultList(SysAutoCodeResult sysAutoCodeResult);

    void add(SysAutoCodeResult sysAutoCodeResult);

    void updateAutoCodeResult(SysAutoCodeResult sysAutoCodeResult);

    void deleteById(Long codeId);


}
