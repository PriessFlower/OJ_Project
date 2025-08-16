package com.zt.oj.Judge.entity;

import com.zt.oj.annotion.AuthCheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeResult {
    //1.运行时间，内存大小
    //2.用例结果列表
    //3.运行结果（成功or失败）
    //4.失败信息

    private String TimeExpense;

    private String MemoryExpense;


    private String ErrorMessage;

    private Boolean Result;
}
