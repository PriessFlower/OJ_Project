package com.zt.oj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 比赛表
 * @TableName contest
 */
@TableName(value ="contest")
@Data
public class Contest {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 比赛名称
     */
    private String title;

    /**
     * 比赛描述
     */
    private String description;

    /**
     * 0 公开赛 1 私有赛
     */
    private Integer type;

    /**
     * 如果时私有赛则需要输入密码
     */
    private String password;

    /**
     * 比赛状态 0 未开始，1进行中 2 已结束
     */
    private Integer status;

    /**
     * 比赛开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 报名开始时间
     */
    private Date signUpStart;

    /**
     * 报名结束时间
     */
    private Date signUpEnd;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private Integer isDelete;

    /**
     * 问题列表
     */
    @TableField(exist = false)
    private List<Question> questionList;
}