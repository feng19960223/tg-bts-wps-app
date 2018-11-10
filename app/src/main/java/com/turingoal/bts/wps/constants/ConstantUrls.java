package com.turingoal.bts.wps.constants;


import com.turingoal.bts.wps.app.TgAppConfig;

/**
 * url路径
 */
public interface ConstantUrls {
    // 基本
    String URL_USER_LOGIN = TgAppConfig.URL_BASE + "login" + TgAppConfig.URL_SUFFIX; // 登录路径
    String URL_USER_CHANGE_PASS = TgAppConfig.URL_BASE + "changePass" + TgAppConfig.URL_SUFFIX; // 修改密码
    String URL_CHECK_TOKEN = TgAppConfig.URL_BASE + "checkToken" + TgAppConfig.URL_SUFFIX; // 校验token
    String URL_CHECK_VERSION = TgAppConfig.URL_BASE + "checkVersion" + TgAppConfig.URL_SUFFIX; // 检查版本
    // 技术资料
    String URL_TECHNICAL_DOC_LIST = TgAppConfig.URL_BASE + "technicalDoc/findPage" + TgAppConfig.URL_SUFFIX; //  技术资料
    // 股道
    String URL_TRACK_STATUS = TgAppConfig.URL_BASE + "track/findStatus" + TgAppConfig.URL_SUFFIX; // 股道状态
    String URL_TRACK_RECORD = TgAppConfig.URL_BASE + "trackRecord/findByDate" + TgAppConfig.URL_SUFFIX; // 股道记录
    // 调度任务
    String URL_SCHEDULING_ORDER = TgAppConfig.URL_BASE + "schedulingOrder/getByDateAndWorkShiftTypeAndWorkGroupName" + TgAppConfig.URL_SUFFIX; // 任务列表，时间，白班或夜班，检修班组
    String URL_SCHEDULING_TASK_ADD = TgAppConfig.URL_BASE + "schedulingTask/add" + TgAppConfig.URL_SUFFIX; // 新建作业
    String URL_SCHEDULING_TASK_START = TgAppConfig.URL_BASE + "schedulingTask/start" + TgAppConfig.URL_SUFFIX; // 开始任务
    String URL_SCHEDULING_TASK_FINISH = TgAppConfig.URL_BASE + "schedulingTask/finish" + TgAppConfig.URL_SUFFIX; // 完成任务
    // 快速作业List
    String URL_DISPATCH_TASK_ITEM_FINISH_LIST = TgAppConfig.URL_BASE + "dispatchTaskItem/findSelfNotFinished" + TgAppConfig.URL_SUFFIX; // 查询自己未完成的 [派工任务详细项]，包括未开始和进行中的
    String URL_RAILWAY_MODEL_GET_BY_NUM = TgAppConfig.URL_BASE + "trainSetModel/getByCodeNum" + TgAppConfig.URL_SUFFIX; // 根据编码查询车型
    // 作业
    String URL_WORK_FINISHED_LSIT = TgAppConfig.URL_BASE + "dispatchTaskItem/findFinishedByDate" + TgAppConfig.URL_SUFFIX; // 完成的作业
    String URL_WORK_START = TgAppConfig.URL_BASE + "dispatchTaskItem/start" + TgAppConfig.URL_SUFFIX; // 开始作业
    String URL_WORK_FINISH = TgAppConfig.URL_BASE + "dispatchTaskItem/finish" + TgAppConfig.URL_SUFFIX; // 完成作业
    String URL_WORK_ITEM_SUBMIT = TgAppConfig.URL_BASE + "dispatchTaskItem/submitItem" + TgAppConfig.URL_SUFFIX; // 提交作业项
    String URL_WORK_GET_ITEMS = TgAppConfig.URL_BASE + "dispatchTaskItem/findItems" + TgAppConfig.URL_SUFFIX; // 根据作业记录查询作业项
    String URL_WORK_GET_BY_TASK = TgAppConfig.URL_BASE + "dispatchTaskItem/findBySchedulingTaskCodeNum" + TgAppConfig.URL_SUFFIX; // 根据任务查询作业
    // 故障
    String URL_BREAKDOWN_RECORD_ITEM_LIST = TgAppConfig.URL_BASE + "breakdownRecordItem/findByDate" + TgAppConfig.URL_SUFFIX; // 故障列表
    String URL_BREAKDOWN_RECORD_ITEM_COMMIT = TgAppConfig.URL_BASE + "breakdownRecordItem/add" + TgAppConfig.URL_SUFFIX; // 故障提报
    String URL_BREAKDOWN_RECORD_ITEM_START = TgAppConfig.URL_BASE + "breakdownRecordItem/start" + TgAppConfig.URL_SUFFIX; // 开始处理故障
    String URL_BREAKDOWN_RECORD_ITEM_FINISH = TgAppConfig.URL_BASE + "breakdownRecordItem/finish" + TgAppConfig.URL_SUFFIX; // 结束处理故障
    String URL_BREAKDOWN_RECORD_ITEM_QC_START = TgAppConfig.URL_BASE + "breakdownRecordItem/startQc" + TgAppConfig.URL_SUFFIX; // 开始质检故障
    String URL_BREAKDOWN_RECORD_ITEM_QC_FINISH = TgAppConfig.URL_BASE + "breakdownRecordItem/finishQc" + TgAppConfig.URL_SUFFIX; // 结束质检故障
    String URL_BREAKDOWN_RECORD_ITEM = TgAppConfig.URL_BASE + "breakdownRecordItem/get" + TgAppConfig.URL_SUFFIX; // 得到保护图片的故障
    // 基础配置
    String URL_WORK_CONFIG_LIST = TgAppConfig.URL_BASE + "workConfig/find" + TgAppConfig.URL_SUFFIX; // 作业配置
    String URL_TRACK_LIST = TgAppConfig.URL_BASE + "track/find" + TgAppConfig.URL_SUFFIX; // 股道配置
    String URL_TRAIN_SET_LIST = TgAppConfig.URL_BASE + "trainSet/find" + TgAppConfig.URL_SUFFIX; // 车号配置
    String URL_MAINTENANCE_TASK_LIST = TgAppConfig.URL_BASE + "maintenanceTask/find" + TgAppConfig.URL_SUFFIX; // 项目配置
    String URL_MAINTENANCE_WORK_ITEM_LIST = TgAppConfig.URL_BASE + "maintenanceWorkItem/find" + TgAppConfig.URL_SUFFIX; // 任务配置
    // 物料
    String URL_MATERIAL_AUDIT_RESULT_LIST = TgAppConfig.URL_BASE + "materialUsage/findPage" + TgAppConfig.URL_SUFFIX; // 工人提报的领用项，是否审核成功的列表,包括通过和驳回的
    String URL_MATERIAL_LIST = TgAppConfig.URL_BASE + "material/findPage" + TgAppConfig.URL_SUFFIX; // 物料列表
    String URL_MATERIAL_ADD = TgAppConfig.URL_BASE + "materialUsage/add" + TgAppConfig.URL_SUFFIX; // 添加物料
    String URL_MATERIAL_UPDATE = TgAppConfig.URL_BASE + "materialUsage/update" + TgAppConfig.URL_SUFFIX; // 更新物料
    String URL_MATERIAL_TEMPLATE_LIST = TgAppConfig.URL_BASE + "materialTemplet/findPage" + TgAppConfig.URL_SUFFIX; // 模板列表
    String URL_MATERIAL_TEMPLATE_DETAIL = TgAppConfig.URL_BASE + "materialTemplet/findItems" + TgAppConfig.URL_SUFFIX; // 模板详情
    String URL_MATERIAL_AUDIT_LIST = TgAppConfig.URL_BASE + "aduitRecord/findForUsage" + TgAppConfig.URL_SUFFIX; // 物料审核过程列表
    String URL_MATERIAL_AUDIT_WAIT_LIST = TgAppConfig.URL_BASE + "materialUsageAduit/findWait" + TgAppConfig.URL_SUFFIX; // 待审核列表
    String URL_MATERIAL_AUDIT_COMPLETE_LIST = TgAppConfig.URL_BASE + "materialUsageAduit/findFinished" + TgAppConfig.URL_SUFFIX; // 待审核列表
    String URL_MATERIAL_AUDIT_DETAIL = TgAppConfig.URL_BASE + "materialUsage/findItems" + TgAppConfig.URL_SUFFIX; // 物料审核详情
    String URL_MATERIAL_AUDIT = TgAppConfig.URL_BASE + "materialUsageAduit/aduitByForeman" + TgAppConfig.URL_SUFFIX; // 物料审核
}
