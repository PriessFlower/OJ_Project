package com.zt.oj.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zt.oj.Judge.entity.TestCase;
import com.zt.oj.exception.BusinessException;
import com.zt.oj.exception.ErrorCode;
import com.zt.oj.model.dto.question.QuestionAddRequest;
import com.zt.oj.model.dto.question.QuestionQueryRequest;
import com.zt.oj.model.dto.question.QuestionUpdateRequest;
import com.zt.oj.model.dto.submit.SubmitQueryRequest;
import com.zt.oj.model.entity.Question;
import com.zt.oj.model.entity.QuestionSubmit;
import com.zt.oj.model.entity.User;
import com.zt.oj.model.vo.question.QuestionVO;
import com.zt.oj.service.QuestionService;
import com.zt.oj.mapper.QuestionMapper;
import com.zt.oj.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
* @author 周涛
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2025-04-05 09:29:51
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

    @Resource
    private UserService userService;

    @Resource
    private QuestionMapper questionMapper;

    @Override
    public QuestionVO ToQuestionVO(Question question) {
        if(ObjectUtil.isEmpty(question)) throw new BusinessException(ErrorCode.PARAMS_ERROR,"空指针");
        return BeanUtil.copyProperties(question, QuestionVO.class);
    }

    @Override
    public Page<Question> getProblems(QuestionQueryRequest questionQueryRequest) throws JsonProcessingException {
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        int current = questionQueryRequest.getCurrent();
        int pageSize = questionQueryRequest.getPageSize();
        //构建分页对象
        Page<Question> questionPage = new Page<Question>(current,pageSize);

        //加入查询条件
        Integer level = questionQueryRequest.getLevel();
        String keyword = questionQueryRequest.getKeyword();
        Long userId = questionQueryRequest.getUserId();
        String tags = questionQueryRequest.getTags();

        if (ObjectUtil.isNotNull(level)) questionQueryWrapper.eq("level",level);
        if (ObjectUtil.isNotNull(userId)) questionQueryWrapper.eq("userId",userId);
        if (StrUtil.isNotBlank(keyword)) questionQueryWrapper.like("title",keyword);

        //由于数据库中tags时存储的JSON数组，单独处理
        if (StrUtil.isNotBlank(tags)) {
            List<String> tagList = Arrays.asList(tags.split(","));
            for(String tag: tagList){
                questionQueryWrapper.apply("JSON_CONTAINS(tags,{0})","\"" + tag + "\"");
            }
        }

        Page<Question> questionPageResult = questionMapper.selectPage(questionPage, questionQueryWrapper);
        return questionPageResult;
    }

    @Override
    public Boolean addQuestion(QuestionAddRequest questionAddRequest, HttpServletRequest servletRequest) {
        User loginUser = userService.getLoginUser(servletRequest);
        Long id = loginUser.getId();

        String content = questionAddRequest.getContent();
        String title = questionAddRequest.getTitle();
        Integer level = questionAddRequest.getLevel();
        String tags = questionAddRequest.getTags();
        String judgeCase = questionAddRequest.getJudgeCase();

        if (StrUtil.hasBlank(content,title,tags,judgeCase) || ObjectUtil.isEmpty(level)) throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数不足");
        Question question = new Question();
        BeanUtil.copyProperties(questionAddRequest, question);
        question.setUserId(id);

        Boolean result = this.save(question);
        if(!result) throw new BusinessException(ErrorCode.OPERATION_ERROR);
        return result;
    }

    @Override
    public Boolean updateQuestion(Long id, QuestionUpdateRequest questionUpdateRequest) {
        Question question = new Question();
        BeanUtil.copyProperties(questionUpdateRequest,question);
        question.setId(id);
        int i = questionMapper.updateById(question);
        if (i <= 0) throw new BusinessException(ErrorCode.OPERATION_ERROR,"更新失败");
        return true;
    }

    @Override
    public Boolean deleteQuestion(Long id) {
        int i = questionMapper.deleteById(id);
        if (i <= 0) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        return true;
    }

    @Override
    public String ToJudgeCaseJson(String judgeCase) {
        List<List> list = JSONUtil.toList(judgeCase, List.class);
        List<TestCase> testCaseList = new ArrayList<>();
        for (List item : list) {
            String input = item.get(0).toString();
            String output = item.get(1).toString();
            TestCase testCase = new TestCase(output,input);
            testCaseList.add(testCase);
        }

        String jsonStr = JSONUtil.toJsonStr(testCaseList);
        return jsonStr;
    }
}




