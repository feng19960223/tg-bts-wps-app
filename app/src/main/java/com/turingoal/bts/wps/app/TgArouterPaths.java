package com.turingoal.bts.wps.app;

/**
 * 常量-Activity路径
 */
public interface TgArouterPaths {
    // 任务
    String SCHEDULING_TASK = TgAppConfig.APP_BASE_PATH + "scheduing/task/list"; // 任务管理list
    String SCHEDULING_TASK_DETAIL = TgAppConfig.APP_BASE_PATH + "scheduing/task/detail"; // 任务管理详情
    String SCHEDULING_TASK_START = TgAppConfig.APP_BASE_PATH + "scheduing/task/start"; // 开始任务
    String SCHEDULING_TASK_FINISH = TgAppConfig.APP_BASE_PATH + "scheduing/task/finish"; // 结束任务
    String SCHEDULING_TASK_ADD = TgAppConfig.APP_BASE_PATH + "scheduing/task/add"; // 新建任务
    // 故障
    String BREAKDOWN = TgAppConfig.APP_BASE_PATH + "breakdown/index"; // 故障
    String BREAKDOWN_ALL = TgAppConfig.APP_BASE_PATH + "breakdown/all"; // 全部故障
    String BREAKDOWN_HANDLING_START = TgAppConfig.APP_BASE_PATH + "breakdown/handling/start"; // 故障处理开始
    String BREAKDOWN_HANDLING_FINISH = TgAppConfig.APP_BASE_PATH + "breakdown/handling/finish"; // 故障处理结束
    String BREAKDOWN_QC_START = TgAppConfig.APP_BASE_PATH + "breakdown/qc/start"; // 故障质检开始
    String BREAKDOWN_QC_FINISH = TgAppConfig.APP_BASE_PATH + "breakdown/qc/finish"; // 故障质检结束
    String BREAKDOWN_DETAIL = TgAppConfig.APP_BASE_PATH + "breakdown/detail"; // 故障详情
    String BREAKDOWN_REPORT = TgAppConfig.APP_BASE_PATH + "breakdown/report"; // 故障提报
    String PHOTO_SHOW = TgAppConfig.APP_BASE_PATH + "photo/show"; // 查看照片
    // 作业记录
    String WORKRECORD = TgAppConfig.APP_BASE_PATH + "workRecord/list"; // 作业记录
    String WORKRECORD_ALL = TgAppConfig.APP_BASE_PATH + "record/all"; // 全部作业记录
    String WORKRECORD_DETAIL = TgAppConfig.APP_BASE_PATH + "record/detail"; // 作业记录详情
    // 快速作业
    String FASTWORK = TgAppConfig.APP_BASE_PATH + "fastWork/index"; // 快速作业
    String FASTWORK_COOKBOOK = TgAppConfig.APP_BASE_PATH + "fastWork/cookbook"; // 快速作业，作业指导书
    String FASTWORK_LEVEL1_START = TgAppConfig.APP_BASE_PATH + "fastWork/start"; // 快速作业，开始作业
    String FASTWORK_LEVEL1_PROCESS = TgAppConfig.APP_BASE_PATH + "fastWork/process"; // 作业过程
    String FASTWORK_LEVEL1_FINISH = TgAppConfig.APP_BASE_PATH + "fastWork/finish"; // 快速作业，完成作业
    String FASTWORK_BREAKDOWN_REPORT = TgAppConfig.APP_BASE_PATH + "fastWork/breakdown/report"; // 快速作业故障提报

    // 技术资料
    String WORD = TgAppConfig.APP_BASE_PATH + "word/list"; // 技术资料
    String WORD_DETAIL = TgAppConfig.APP_BASE_PATH + "word/detail"; // 技术资料详情
    // 监控
    String MONITOR = TgAppConfig.APP_BASE_PATH + "work/monitor"; // 作业监控
    // 股道
    String TRACK = TgAppConfig.APP_BASE_PATH + "track/status"; // 股道信息
    String TRACK_RECORD = TgAppConfig.APP_BASE_PATH + "track/record"; // 股道查询
    // 物料领用
    String MATERIAL_AUDIT_RESULT = TgAppConfig.APP_BASE_PATH + "material/audit/result"; // 工人提报的领用项，是否审核成功的列表
    String MATERIAL_USAGE_UPDATE = TgAppConfig.APP_BASE_PATH + "material/usage/update"; // 物料审核更新
    String MATERIAL = TgAppConfig.APP_BASE_PATH + "material"; // 物料购物车
    String MATERIAL_BREAKDOWN_RECORD_ITEM = TgAppConfig.APP_BASE_PATH + "material/breakdown/record/item"; // 物料故障列表，选择车号
    String MATERIAL_FAST_WORK_ITEM = TgAppConfig.APP_BASE_PATH + "material/fast/work/item"; // 物料快速作业列表，选择车号
    String MATERIAL_SCHEDULING_TASK_ITEM = TgAppConfig.APP_BASE_PATH + "material/scheduling/task/item"; // 物料任务管理列表，选择车号
    String MATERIAL_USAGE_ITEM_ADD = TgAppConfig.APP_BASE_PATH + "material/usage/item/add"; // 领用item单独增加
    String MATERIAL_LIST = TgAppConfig.APP_BASE_PATH + "material/list"; // 物料列表
    String MATERIAL_ADD = TgAppConfig.APP_BASE_PATH + "material/add"; // 物料添加购物车
    String MATERIAL_TEMPLATE_LIST = TgAppConfig.APP_BASE_PATH + "material/template/list"; // 物料模板列表
    String MATERIAL_TEMPLATE_DETAIL = TgAppConfig.APP_BASE_PATH + "material/template/detail"; // 物料模板详情
    String MATERIAL_TEMPLATE_ITEM_DETAIL = TgAppConfig.APP_BASE_PATH + "material/template/item/detail"; // 物料模板item详情
    // 物料审核
    String MATERIAL_AUDIT_LIST = TgAppConfig.APP_BASE_PATH + "material/audit/list"; // 物料审核列表
    String MATERIAL_AUDIT = TgAppConfig.APP_BASE_PATH + "material/audit"; // 物料审核
    String MATERIAL_AUDIT_PROCESS = TgAppConfig.APP_BASE_PATH + "material/audit/process"; // 物料审核过程记录
    String MATERIAL_USAGE_DETAIL = TgAppConfig.APP_BASE_PATH + "material/usage/detail"; // 物料审核详情
    String MATERIAL_USAGE_ITEM_DETAIL = TgAppConfig.APP_BASE_PATH + "material/audit/item/detail"; // 物料审核item详情
}
