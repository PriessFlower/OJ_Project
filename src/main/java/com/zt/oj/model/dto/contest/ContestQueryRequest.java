package com.zt.oj.model.dto.contest;

import com.zt.oj.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class ContestQueryRequest extends PageRequest implements Serializable {
    //todo 比赛查询
    private String keyword;

    private Integer status;
}
