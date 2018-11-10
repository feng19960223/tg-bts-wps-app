package com.turingoal.bts.wps.manager;

import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.bean.MaintenanceWorkItemDb;
import com.turingoal.bts.wps.bean.MaintenanceWorkItemDb_;

import java.util.List;

/**
 * [作业项目]Manager
 */
public class MaintenanceWorkItemManager {
    /**
     * 检修任务CodeNum 查询对应的作业项目
     */
    public List<MaintenanceWorkItemDb> getMaintenanceWorkItemList(final String maintenanceTaskCodeNum) {
        List<MaintenanceWorkItemDb> maintenanceWorkItems = TgApplication.getBoxStore().boxFor(MaintenanceWorkItemDb.class).query()
                .equal(MaintenanceWorkItemDb_.maintenanceTaskCodeNum, maintenanceTaskCodeNum).build().find();
        return maintenanceWorkItems;
    }
}
