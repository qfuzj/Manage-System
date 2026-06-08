package com.ms.system.service;

import com.ms.common.core.domain.entity.SysAutoCodeRule;

import java.util.List;

public interface IAutoCodeRuleService {

    SysAutoCodeRule getOne(String ruleCode);

    List<SysAutoCodeRule> selectAutoCodeList(SysAutoCodeRule sysAutoCodeRule);

    SysAutoCodeRule findById(Long ruleId);

    boolean checkRuleCodeUnique(SysAutoCodeRule sysAutoCodeRule);

    boolean checkRuleNameUnique(SysAutoCodeRule sysAutoCodeRule);

    int insertInfo(SysAutoCodeRule rule);

    int updateInfo(SysAutoCodeRule rule);

    int deleteById(Long ruleId);

    int deleteByIds(Long[] ruleIds);

}
