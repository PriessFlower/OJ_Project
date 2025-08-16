package com.zt.oj.Judge;

import com.zt.oj.Judge.entity.JudgeResult;
import com.zt.oj.Judge.entity.TestCase;

import java.io.IOException;
import java.util.List;

public interface JudgeStrategy {
    JudgeResult execute(String userCode, List<TestCase> testCaseList) throws IOException, InterruptedException;
}
