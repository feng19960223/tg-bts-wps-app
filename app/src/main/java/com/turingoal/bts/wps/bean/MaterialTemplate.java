package com.turingoal.bts.wps.bean;

import java.util.Date;

import lombok.Data;

/**
 * 物料模板
 */
@Data
public class MaterialTemplate {
    private String id; // 物料模板
    private String title; // 名称
    private String description; // 描述
    private Date createTime; // 创建时间
    private String createUserId; // 创建人
    private String createUserUsername; // 创建人
    private String createUserRealname; // 创建人
}
