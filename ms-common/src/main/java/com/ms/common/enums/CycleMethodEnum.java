package com.ms.common.enums;

/**
 * 编码循环方式枚举
 */
public enum CycleMethodEnum {

    CYCLE_METHOD_YEAR("YEAR","按年"),
    CYCLE_METHOD_MONTH("MONTH","按月"),
    CYCLE_METHOD_DAY("DAY","按日"),
    CYCLE_METHOD_HOUR("HOUR","按小时"),
    CYCLE_METHOD_MINUTE("MINUTE","按分钟"),
    CYCLE_METHOD_OTHER("OTHER","其他");

    private String code;
    private String name;

    CycleMethodEnum(String code, String name){
        this.code=code;
        this.name=name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static CycleMethodEnum getByCode(String code){
        for(CycleMethodEnum value : CycleMethodEnum.values()){
            if(value.getCode().equals(code)){
                return value;
            }
        }
        return CYCLE_METHOD_OTHER;
    }
}
