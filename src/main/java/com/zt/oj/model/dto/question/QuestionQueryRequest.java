package com.zt.oj.model.dto.question;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zt.oj.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class QuestionQueryRequest extends PageRequest implements Serializable {

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 标签列表（json 数组）
     */
    private String tags;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 难度等级 level
     */
    private Integer level;

}
