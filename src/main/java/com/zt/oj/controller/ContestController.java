package com.zt.oj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.oj.common.BaseResponse;
import com.zt.oj.common.ResultUtils;
import com.zt.oj.exception.BusinessException;
import com.zt.oj.exception.ErrorCode;
import com.zt.oj.model.dto.contest.ContestQueryRequest;
import com.zt.oj.model.entity.Contest;
import com.zt.oj.service.ContestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/contest")
@Slf4j
public class ContestController {

    @Resource
    private ContestService contestService;

    @GetMapping("/list")
    public BaseResponse<Page<Contest>> getContestList(ContestQueryRequest contestQueryRequest) {
        Page<Contest> contests = contestService.getContests(contestQueryRequest);
        return ResultUtils.success(contests);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Boolean> deleteContest(@PathVariable Long id){
        if (id == null || id < 0) throw new BusinessException(ErrorCode.PARAMS_ERROR,"id问题");
        boolean result = contestService.removeById(id);
        return ResultUtils.success(result);
    }
}
