package com.turingoal.bts.wps.app;

import android.os.Environment;

import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.bean.BtsRole;
import com.turingoal.common.android.app.TgArouterCommonPaths;
import com.turingoal.common.android.base.TgBaseAppConfig;
import com.turingoal.common.android.bean.TgGridItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统配置
 */
public class TgAppConfig extends TgBaseAppConfig {
    // 关于
    public static final String CONTRACT_WEBSITE = "www.bjtds.com.cn"; // 联系网址
    public static final String CONTRACT_TEL = "010-51852819"; //  联系电话
    public static final String CONTRACT_ADDRESS = "北京中关村科技园总部基地"; // 联系地址
    public static final String CONTRACT_NAME = "北京铁道工程机电技术研究所股份有限公司"; // 联系名称
    // 基本配置
    public static final String PROJECT_NAME = "tg-bts-wps—follow"; // 项目名字
    public static final String APP_BASE_PATH = "/bts/wps/"; // 页面路由库，要求二级路径，防止出错
    public static final String LOG_TAG = PROJECT_NAME + "-log"; // log tag
    public static final String SP_NAME = PROJECT_NAME + "-sp"; // SharedPreferences名称
    // server 配置
    public static final String SERVER_IP = "192.168.0.254"; // server ip // 47.94.19.152
    public static final Integer SERVER_PORT = 8086; // server 端口
    public static final String SERVER_NAME = "tg-bts-wps"; // server 名称
    // IM
    public static final String IM_APP_KEY = "3081023dfd98c579b6009527"; // AppKey
    public static final String IM_SERVER_IP = SERVER_IP; // server ip
    public static final Integer IM_SERVER_PORT = 7901; // server port
    // 其它配置
    public static final String BASE_STORE_PATH = Environment.getExternalStorageDirectory() + "/bts/wps/"; // 基本保存路径
    public static final String IMG_STORE_PATH = BASE_STORE_PATH + "imgs/"; // 基本保存路径,图片
    public static final String PDF_STORE_PATH = BASE_STORE_PATH + "/pdf/"; // 基本保存路径,PDF
    public static final boolean MODE_FOREMAN_AUTO = true; // 工长自动化模式
    // 首页九宫格
    public static final List<TgGridItem> MAIN_GRID_DATE; // 首页九宫格

    static {
        MAIN_GRID_DATE = new ArrayList<>();
        MAIN_GRID_DATE.add(new TgGridItem(R.mipmap.app_ic_task, "任务管理", TgArouterPaths.SCHEDULING_TASK));
        MAIN_GRID_DATE.add(new TgGridItem(R.mipmap.app_ic_breakdown_report, "故障提报", TgArouterPaths.BREAKDOWN_REPORT));
        MAIN_GRID_DATE.add(new TgGridItem(R.mipmap.app_ic_fault, "故障处理", TgArouterPaths.BREAKDOWN));
        MAIN_GRID_DATE.add(new TgGridItem(R.mipmap.app_ic_material, "物料领用", TgArouterPaths.MATERIAL_AUDIT_RESULT));
        if (TgSystemHelper.checkPermission(BtsRole.MATERIAL_AUDIT)) { // 如果有物料审核权限，显示物料审核按钮
            MAIN_GRID_DATE.add(new TgGridItem(R.mipmap.app_ic_material_audit, "物料审核", TgArouterPaths.MATERIAL_AUDIT_LIST));
        }
        MAIN_GRID_DATE.add(new TgGridItem(R.mipmap.app_ic_query, "股道查询", TgArouterPaths.TRACK));
        MAIN_GRID_DATE.add(new TgGridItem(R.mipmap.app_ic_technical_doc, "技术资料", TgArouterPaths.WORD));
        MAIN_GRID_DATE.add(new TgGridItem(R.mipmap.app_ic_record, "作业记录", TgArouterPaths.WORKRECORD));
//        MAIN_GRID_DATE.add(new TgGridItem(R.mipmap.app_ic_monitor, "作业监控", TgArouterPaths.MONITOR));
        MAIN_GRID_DATE.add(new TgGridItem(R.mipmap.common_ic_userinfo, "个人信息", TgArouterCommonPaths.COMMON_SELF_INFO));
        MAIN_GRID_DATE.add(new TgGridItem(R.mipmap.common_ic_info, "关于系统", TgArouterCommonPaths.COMMON_ABOUT));
        MAIN_GRID_DATE.add(new TgGridItem(R.mipmap.common_ic_help, "系统帮助", TgArouterCommonPaths.COMMON_HELP));
        MAIN_GRID_DATE.add(new TgGridItem(R.mipmap.app_ic_logout, "注销退出", TgArouterCommonPaths.COMMON_LOGOFF));
    }
}
