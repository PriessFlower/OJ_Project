package com.zt.oj.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zt.oj.Judge.entity.TestCase;
import com.zt.oj.annotion.AuthCheck;
import com.zt.oj.common.BaseResponse;
import com.zt.oj.common.ResultUtils;
import com.zt.oj.constant.UserConstant;
import com.zt.oj.model.dto.question.QuestionAddRequest;
import com.zt.oj.model.dto.question.QuestionQueryRequest;
import com.zt.oj.model.dto.question.QuestionUpdateRequest;
import com.zt.oj.model.entity.Question;
import com.zt.oj.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("/question")
@RestController
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @GetMapping("/problems")
    public BaseResponse<Page<Question>> getProblems(QuestionQueryRequest questionQueryRequest) throws JsonProcessingException {
        Page<Question> problems = questionService.getProblems(questionQueryRequest);
        return ResultUtils.success(problems);
    }

    // 添加题目
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> addQuestion(@RequestBody QuestionAddRequest questionAddRequest, HttpServletRequest servletRequest) {
        String judgeCase = questionAddRequest.getJudgeCase();
        if (StrUtil.isNotBlank(judgeCase)) {
            String jsonStr = questionService.ToJudgeCaseJson(judgeCase);
            questionAddRequest.setJudgeCase(jsonStr);
        }
        Boolean res = questionService.addQuestion(questionAddRequest,servletRequest);
        return ResultUtils.success(res);
    }

    // 更新题目
    @PutMapping("/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateQuestion(@PathVariable Long id, @RequestBody QuestionUpdateRequest questionUpdateRequest) {
        String judgeCase = questionUpdateRequest.getJudgeCase();
        if (StrUtil.isNotBlank(judgeCase)) {
            String jsonStr = questionService.ToJudgeCaseJson(judgeCase);
            questionUpdateRequest.setJudgeCase(jsonStr);
        }
        Boolean res = questionService.updateQuestion(id, questionUpdateRequest);
        return ResultUtils.success(res);
    }

    // 获取题目详情
    @GetMapping("/{id}")
    public BaseResponse<Question> getQuestionDetail(@PathVariable Long id) {
        Question question = questionService.getById(id);
        //这里又要把JSON变成嵌套二维数组
        String judgeCase = question.getJudgeCase();
        if (StrUtil.isNotBlank(judgeCase)) {
            List<TestCase> testCaseList = JSONUtil.toList(judgeCase, TestCase.class);
            List<List<String>> jsonArray = new ArrayList<>();
            for (TestCase testCase : testCaseList) {
                List<String> list = new ArrayList<>();
                list.add(0,testCase.getInput());
                list.add(1,testCase.getExpectedOutput());
                jsonArray.add(list);
            }
            String jsonStr = JSONUtil.toJsonStr(jsonArray);
            log.info(jsonStr);
            question.setJudgeCase(jsonStr);
        }
        return ResultUtils.success(question);
    }

    @DeleteMapping("/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteQuestion(@PathVariable Long id) {
        Boolean res = questionService.deleteQuestion(id);
        return ResultUtils.success(res);
    }

}
