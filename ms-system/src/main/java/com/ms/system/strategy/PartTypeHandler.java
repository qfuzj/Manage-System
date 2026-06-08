package com.ms.system.strategy;

import com.ms.common.core.domain.entity.SysAutoCodePart;
import com.ms.common.enums.PartTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自动编码分类处理器
 */
@Component
public class PartTypeHandler {

    @Autowired
    List<PartTypeTemplate> partTypeTemplates;

    /**
     * 根据组成部分的类型，选择对应的处理器进行处理
     */
    public String choiceExecute(SysAutoCodePart sysAutoCodePart){
        String partType = sysAutoCodePart.getPartType();
        return partTypeTemplates.get(PartTypeEnum.getByCode(partType).getBeanIndex()).partHandle(sysAutoCodePart);
    }

}
