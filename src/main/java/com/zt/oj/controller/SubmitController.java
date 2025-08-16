package com.zt.oj.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.oj.Event.JudgeEvent;
import com.zt.oj.common.BaseResponse;
import com.zt.oj.common.ResultUtils;
import com.zt.oj.exception.BusinessException;
import com.zt.oj.exception.ErrorCode;
import com.zt.oj.model.dto.submit.SubmitAddRequest;
import com.zt.oj.model.dto.submit.SubmitQueryRequest;
import com.zt.oj.model.entity.Question;
import com.zt.oj.model.entity.QuestionSubmit;
import com.zt.oj.model.entity.User;
import com.zt.oj.model.enums.StatusEnum;
import com.zt.oj.service.QuestionService;
import com.zt.oj.service.QuestionSubmitService;
import com.zt.oj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/submit")
@Slf4j
public class SubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private QuestionService questionService;

    @Resource
    private UserService userService;

    @Resource
    private ApplicationEventPublisher publisher;

    @PostMapping
    public BaseResponse<Long> submitProblem(@RequestBody SubmitAddRequest submitAddRequest, HttpServletRequest servletRequest) {
        String code = submitAddRequest.getCode();
        String language = submitAddRequest.getLanguage();
        Long questionId = submitAddRequest.getQuestionId();
        User loginUser = userService.getLoginUser(servletRequest);
        Long userId = loginUser.getId();

        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setUserId(userId);
        questionSubmit.setCode(code);
        questionSubmit.setLanguage(language);
        questionSubmit.setStatus(StatusEnum.PENDING.getValue());
        boolean save = questionSubmitService.save(questionSubmit);
        if (!save) throw new BusinessException(ErrorCode.OPERATION_ERROR);

        //可以发布判题事件(用户代码，以及处理输入，期望输出)
        Question question = questionService.getById(questionId);
        if (question == null) throw new BusinessException(ErrorCode.PARAMS_ERROR,"对应问题不存在，无法判题");
        JudgeEvent judgeEvent = new JudgeEvent(this,language,code, question.getJudgeCase());
        publisher.publishEvent(judgeEvent);
        return ResultUtils.success(questionSubmit.getId());
    }

    @GetMapping("/{id}")
    public BaseResponse<QuestionSubmit> getSubmission(@PathVariable Long id) {
        if (id <= 0) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        QuestionSubmit questionSubmit = questionSubmitService.getById(id);
        if (ObjectUtil.isEmpty(questionSubmit)) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(questionSubmit);
    }

    @GetMapping("/submissions")
    public BaseResponse<Page<QuestionSubmit>> getSubmissionList(SubmitQueryRequest submitQueryRequest) {
        Page<QuestionSubmit> submissions = questionSubmitService.getSubmissions(submitQueryRequest);
        return ResultUtils.success(submissions);
    }
}
