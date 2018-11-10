package com.turingoal.bts.wps.manager;

import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.bean.WorkConfigDb;
import com.turingoal.bts.wps.bean.WorkConfigDb_;

import java.util.List;

/**
 * [作业配置]Manager
 */
public class WorkConfigManager {

    /**
     * 根据车型，分组,任务项 编号获取[作业配置]数据
     */
    public List<WorkConfigDb> getWorkConfigList(final String trainSetModel, final String workNum, final String maintenanceTaskItem) {
        List<WorkConfigDb> workConfigs = TgApplication.getBoxStore().boxFor(WorkConfigDb.class).query()
                .equal(WorkConfigDb_.trainSetModel, trainSetModel)
                .equal(WorkConfigDb_.workNum, workNum)
                .equal(WorkConfigDb_.maintenanceTaskItem, maintenanceTaskItem).build().find();
        return workConfigs;
    }
}
