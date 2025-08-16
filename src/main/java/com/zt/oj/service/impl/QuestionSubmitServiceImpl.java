package com.zt.oj.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.oj.constant.CommonConstant;
import com.zt.oj.model.dto.submit.SubmitQueryRequest;
import com.zt.oj.model.entity.QuestionSubmit;
import com.zt.oj.service.QuestionSubmitService;
import com.zt.oj.mapper.QuestionSubmitMapper;
import com.zt.oj.utils.SqlUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 周涛
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2025-02-18 15:28:21
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService{

    @Resource
    private QuestionSubmitMapper questionSubmitMapper;

    @Override
    public Page<QuestionSubmit> getSubmissions(SubmitQueryRequest submitQueryRequest) {
        String language = submitQueryRequest.getLanguage();
        Long userId = submitQueryRequest.getUserId();
        Long questionId = submitQueryRequest.getQuestionId();
        Integer status = submitQueryRequest.getStatus();

        QueryWrapper<QuestionSubmit> questionSubmitQueryWrapper = new QueryWrapper<>();

        if(StrUtil.isNotBlank(language)) questionSubmitQueryWrapper.eq("language",language);
        if(userId != null && userId > 0) questionSubmitQueryWrapper.eq("userId",userId);
        if(questionId != null && questionId > 0) questionSubmitQueryWrapper.eq("questionId",questionId);
        if (status != null) questionSubmitQueryWrapper.eq("status",status);

        String sortField = submitQueryRequest.getSortField();
        String sortOrder = submitQueryRequest.getSortOrder();
        if (StrUtil.isAllNotBlank(sortOrder,sortField)) {
            questionSubmitQueryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                    sortField);
        }

        Page<QuestionSubmit> questionSubmitPage = new Page<>(submitQueryRequest.getCurrent(), submitQueryRequest.getPageSize());
        return questionSubmitMapper.selectPage(questionSubmitPage, questionSubmitQueryWrapper);
    }
}




