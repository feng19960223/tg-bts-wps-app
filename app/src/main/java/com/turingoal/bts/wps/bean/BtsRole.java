package com.turingoal.bts.wps.bean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 当前用户的操作权限
 */
public class BtsRole {
    public static final String SCHEDULING_ORDER = "schedulingOrder"; // 查看总单的权限
    public static final String SCHEDULING_TASK = "schedulingTask"; // 查看任务的权限
    public static final String BREAKDOWN = "breakdown"; // 查看所有故障的权限
    public static final String WORK_RECORD = "workRecord"; // 查看所有作业记录的权限
    public static final String MATERIAL_AUDIT = "materialAudit"; // 审核的选项

    private static Map<String, List<String>> permissionMap = new HashMap<>();
    private static Map<String, String> roleMap = new HashMap<>();

    static {
        roleMap.put("admin", "管理员");
        roleMap.put("maintainer", "检修员");
        roleMap.put("headForeman", "工长");
        roleMap.put("scheduler", "调度");
        permissionMap.put("admin", Arrays.asList(SCHEDULING_ORDER, SCHEDULING_TASK, BREAKDOWN, MATERIAL_AUDIT, WORK_RECORD)); // admin
        permissionMap.put("maintainer", null); // 检修员
        permissionMap.put("headForeman", Arrays.asList(SCHEDULING_ORDER, SCHEDULING_TASK, BREAKDOWN, MATERIAL_AUDIT, WORK_RECORD)); // 工长
        permissionMap.put("scheduler", Arrays.asList(SCHEDULING_ORDER, SCHEDULING_TASK, BREAKDOWN, MATERIAL_AUDIT, WORK_RECORD)); // 调度
    }

    public static Map<String, List<String>> getPermissionMapMap() {
        return permissionMap;
    }

    public static Map<String, String> getRoleMap() {
        return roleMap;
    }
}
