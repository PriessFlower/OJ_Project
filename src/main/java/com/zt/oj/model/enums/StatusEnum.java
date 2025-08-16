package com.zt.oj.model.enums;

import cn.hutool.core.util.ObjUtil;
import lombok.Getter;

@Getter
public enum StatusEnum {

    ACCEPTED("通过",2),
    PENDING("等待中",0),
    JUDGING("判题中",1),
    WRONG_ANSWER("答案错误",3),
    TIME_LIMIT_EXCEEDED("时间超限",4),
    MEMORY_LIMIT_EXCEEDED("内存超限",5),
    RUNTIME_ERROR("运行时错误",6),
    COMPILE_ERROR("编译错误",7),
    SYSTEM_ERROR("系统错误",8);


    private final String text;

    private final Integer value;

    StatusEnum(String text,Integer value){
        this.text = text;
        this.value = value;
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value 枚举值的value
     * @return 枚举值
     */
    public static StatusEnum getEnumByValue(Integer value) {
        if (ObjUtil.isEmpty(value)) {
            return null;
        }
        for (StatusEnum anEnum : StatusEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

}
