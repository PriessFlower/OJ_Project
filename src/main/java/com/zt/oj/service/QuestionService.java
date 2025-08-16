package com.zt.oj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zt.oj.model.dto.question.QuestionAddRequest;
import com.zt.oj.model.dto.question.QuestionQueryRequest;
import com.zt.oj.model.dto.question.QuestionUpdateRequest;
import com.zt.oj.model.dto.submit.SubmitQueryRequest;
import com.zt.oj.model.entity.Question;
import com.zt.oj.model.entity.QuestionSubmit;
import com.zt.oj.model.vo.question.QuestionVO;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 周涛
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2025-04-05 09:29:51
*/
public interface QuestionService extends IService<Question> {
    QuestionVO ToQuestionVO(Question question);

    Page<Question> getProblems(QuestionQueryRequest questionQueryRequest) throws JsonProcessingException;

    Boolean addQuestion(QuestionAddRequest questionAddRequest, HttpServletRequest servletRequest);

    Boolean updateQuestion(Long id,QuestionUpdateRequest questionUpdateRequest);

    Boolean deleteQuestion(Long id);

    String ToJudgeCaseJson(String judgeCase);

}
