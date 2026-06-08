package com.ms.system.mapper;

import com.ms.common.core.domain.entity.SysAutoCodePart;

import java.util.List;

public interface SysAutoCodePartMapper {
    List<SysAutoCodePart> selectSysAutoCodePartList(SysAutoCodePart sysAutoCodePart);

    SysAutoCodePart findById(Long partId);

    SysAutoCodePart checkPartUnique(SysAutoCodePart sysAutoCodePart);

    int add(SysAutoCodePart sysAutoCodePart);

    int updateSysAutoCodePart(SysAutoCodePart sysAutoCodePart);

    int deleteById(Long partId);
}
