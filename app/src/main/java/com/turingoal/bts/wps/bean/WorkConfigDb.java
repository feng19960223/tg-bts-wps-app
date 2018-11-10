package com.turingoal.bts.wps.bean;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import lombok.Data;

/**
 * 作业配置，数据库专用
 */
@Data
@Entity
public class WorkConfigDb {
    @Id
    public long entityId; // objectBox 的id需要为Long 类型
    public String id; // 服务器id
    public String maintenanceTask; // 检修任务
    public Integer maintenanceTaskType; // 检修任务类型
    public String trainSetModel; // 车型
    public String workTeam; // 作业组
    public String workNum; // 编号
    public String workItem; // 作业项目
    public String workDesc; // 作业内容
    public String carriage; // 车厢
    public String maintenanceTaskItem; // 任务项
    public String standardImgPath; // 标准图片
    public String standardImgUrl; // 标准图片
    public String standardImgPathLocal; // 标准图片,本地下载的
    public String imgWork; // 作业图片
    public String imgWorkLocal; // 作业图片，本地拍照的
    public Integer weight; // 权重
    public Integer step; // 步骤

    public WorkConfigDb() {

    }
}