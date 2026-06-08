package com.ms.system.strategy;

import com.ms.common.core.domain.entity.SysAutoCodePart;

/**
 * 分类处理模板
 */
public interface PartTypeTemplate {
    /**
     * 分类处理
     */
    String partHandle(SysAutoCodePart sysAutoCodePart);
}
