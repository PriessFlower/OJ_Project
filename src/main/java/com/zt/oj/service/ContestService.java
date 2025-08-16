package com.zt.oj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.oj.model.dto.contest.ContestQueryRequest;
import com.zt.oj.model.entity.Contest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 周涛
* @description 针对表【contest(比赛表)】的数据库操作Service
* @createDate 2025-04-24 16:28:06
*/
public interface ContestService extends IService<Contest> {
    Page<Contest> getContests(ContestQueryRequest contestQueryRequest);
}
