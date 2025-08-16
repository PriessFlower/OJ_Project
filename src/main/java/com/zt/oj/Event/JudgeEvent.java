package com.zt.oj.Event;

import cn.hutool.json.JSONUtil;
import com.zt.oj.Judge.entity.TestCase;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class JudgeEvent extends ApplicationEvent {

    private final String code;

    private final String judgeCase;

    private final String language;

    public JudgeEvent(Object source, String language,String code, String judgeCase) {
        super(source);
        this.code = code;
        this.judgeCase = judgeCase;
        this.language = language;
    }

    public String getCode() {
        return code;
    }

    public List<TestCase> getTestCase() {
        List<TestCase> testCaseList = JSONUtil.toList(judgeCase, TestCase.class);
        return testCaseList;
    }

    public String getLanguage() {
        return language;
    }

}
