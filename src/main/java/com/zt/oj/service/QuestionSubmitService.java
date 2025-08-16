package com.zt.oj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.oj.model.dto.submit.SubmitQueryRequest;
import com.zt.oj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 周涛
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2025-02-18 15:28:21
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    Page<QuestionSubmit> getSubmissions(SubmitQueryRequest submitQueryRequest);
}
