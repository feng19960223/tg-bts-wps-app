package com.turingoal.bts.wps.bean;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import lombok.Data;

/**
 * 检修任务，数据库专用
 */
@Data
@Entity
public class MaintenanceTaskDb {
    @Id
    public long entityId; // objectBox 的id需要为Long 类型
    public String id;
    public String codeNum;
    public String title; // 显示
    public Integer trainSetType;
    public String maintenanceLevelCodeNum;
    public String description;
    public Integer sortOrder;
    public Integer enabled;

    public MaintenanceTaskDb() {

    }
}
