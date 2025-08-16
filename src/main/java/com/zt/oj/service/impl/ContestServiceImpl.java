package com.zt.oj.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.oj.constant.CommonConstant;
import com.zt.oj.exception.BusinessException;
import com.zt.oj.exception.ErrorCode;
import com.zt.oj.model.dto.contest.ContestQueryRequest;
import com.zt.oj.model.entity.Contest;
import com.zt.oj.service.ContestService;
import com.zt.oj.mapper.ContestMapper;
import com.zt.oj.utils.SqlUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 周涛
* @description 针对表【contest(比赛表)】的数据库操作Service实现
* @createDate 2025-04-24 16:28:06
*/
@Service
public class ContestServiceImpl extends ServiceImpl<ContestMapper, Contest>
    implements ContestService{

    @Resource
    private ContestMapper contestMapper;

    @Override
    public Page<Contest> getContests(ContestQueryRequest contestQueryRequest) {
        QueryWrapper<Contest> contestQueryWrapper = new QueryWrapper<>();
        if (contestQueryRequest == null) throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数不能为空");

        String keyword = contestQueryRequest.getKeyword();
        Integer status = contestQueryRequest.getStatus();

        if (StrUtil.isNotBlank(keyword)) contestQueryWrapper.like("title",keyword);
        if (status != null) contestQueryWrapper.eq("status",status);

        String sortOrder = contestQueryRequest.getSortOrder();
        String sortField = contestQueryRequest.getSortField();

        contestQueryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);

        Page<Contest> contestPage = new Page<>();
        return contestMapper.selectPage(contestPage,contestQueryWrapper);
    }
}




