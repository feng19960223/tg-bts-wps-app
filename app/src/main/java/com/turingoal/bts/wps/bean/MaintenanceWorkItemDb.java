package com.turingoal.bts.wps.bean;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import lombok.Data;

/**
 * 作业项目，数据库专用
 */
@Data
@Entity
public class MaintenanceWorkItemDb {
    @Id
    public long entityId; // objectBox 的id需要为Long 类型
    public String id;
    public String codeNum;
    public String title; // 显示
    public String maintenanceTaskCodeNum; // 检修任务编码,和任务一对多关系
    public Integer trainSetType;
    public String maintenanceLevelCodeNum;
    public String description;
    public Integer sortOrder;
    public Integer enabled;
    public Integer teamsType;

    public MaintenanceWorkItemDb() {

    }
}
