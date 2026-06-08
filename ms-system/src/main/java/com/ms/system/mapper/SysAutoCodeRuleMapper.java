package com.ms.system.mapper;

import com.ms.common.core.domain.entity.SysAutoCodeRule;

import java.util.List;

public interface SysAutoCodeRuleMapper {

    List<SysAutoCodeRule> selectSysAutoCodeResultList(SysAutoCodeRule sysAutoCodeRule);

    SysAutoCodeRule findById(Long ruleId);

    int add(SysAutoCodeRule sysAutoCodeRule);

    int updateSysAutoCodeRule(SysAutoCodeRule sysAutoCodeRule);

    int deleteById(Long ruleId);

    SysAutoCodeRule checkRuleCodeUnique(String ruleCode);

    SysAutoCodeRule checkRuleNameUnique(String ruleName);
}
