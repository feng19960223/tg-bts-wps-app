package com.turingoal.bts.wps.bean;

import java.util.Date;

import lombok.Data;

/**
 * 物料领用
 */
@Data
public class MaterialUsage {
    private String id; // 物料领用
    private String codeNum; // 编码
    private String file; // 文件
    private String remarks; // 描述
    private String usageUserId; // 领用人id
    private String usageUserUsername; // 领用人用户名
    private String usageUserRealname; // 领用人真实姓名
    private Date createTime; // 领用时间
    private Integer aduitStatus; // 处理结果：1通过 2不通过 10待审核 99审核中
    private Integer dealType; // 审核结果 通过，驳回
    private String dealRemarks; // 审核意见

    private String bizType;
    private String processInstanceId;
    private String createUserId;
    private String createUsername;
    private String createUserRealname;
    private Date startTime;
    private Date endTime;
    private String processCurrentTask;
    private String processCurrentTaskName;
    private Date processCurrentTaskStartTime;
    private String assigneeDept;
    private String assigneePost;
    private String assignee;
}
