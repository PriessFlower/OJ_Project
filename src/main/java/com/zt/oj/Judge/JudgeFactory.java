package com.zt.oj.Judge;

public class JudgeFactory {
    public JudgeStrategy getJudgeStrategy(String language) {
        if ("java".equals(language)) {
            return new JavaJudgeStrategy();
        }
        return null;
    }
}
