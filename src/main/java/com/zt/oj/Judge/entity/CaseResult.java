package com.zt.oj.Judge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseResult {
    private String input;
    private String actualOutput;
    private boolean isPassed;
    private String errorMsg;
}
