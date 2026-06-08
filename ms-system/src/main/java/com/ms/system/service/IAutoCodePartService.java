package com.ms.system.service;

import com.ms.common.core.domain.entity.SysAutoCodePart;

import java.util.List;

public interface IAutoCodePartService {

    List<SysAutoCodePart> listPart(SysAutoCodePart sysAutoCodePart);

    SysAutoCodePart findById(Long partId);

    boolean checkPartUnique(SysAutoCodePart sysAutoCodePart);

    int insertPart(SysAutoCodePart sysAutoCodePart);

    int updatePart(SysAutoCodePart sysAutoCodePart);

    int deleteByIds(Long[] partIds);

}
