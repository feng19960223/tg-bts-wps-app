package com.turingoal.bts.wps.app;

import android.app.Activity;
import android.content.Context;

import com.turingoal.bts.wps.bean.BtsRole;
import com.turingoal.common.android.app.TgArouterCommonPaths;
import com.turingoal.common.android.base.TgBaseSystemHelper;
import com.turingoal.common.android.util.lang.TgStringUtil;

import java.util.List;
import java.util.Map;

/**
 * 公用方法
 */
public final class TgSystemHelper extends TgBaseSystemHelper {
    private TgSystemHelper() {
        throw new Error("工具类不能实例化！");
    }

    /**
     * 获取服务器基础路径
     */
    public static String getServerBaseUrl() {
        String serverIp = TgApplication.getTgUserPreferences().getServerIp();   // ip
        if (TgStringUtil.isBlank(serverIp)) {
            serverIp = TgAppConfig.SERVER_IP;
        }
        Integer serverPort = TgApplication.getTgUserPreferences().getServerPort();  // port
        if (serverPort > 0) {
            serverPort = TgAppConfig.SERVER_PORT;
        }
        return TgAppConfig.SERVER_PROTOCOL + "://" + serverIp + ":" + serverPort + "/" + TgAppConfig.SERVER_NAME + "/";
    }

    /**
     * 注销
     */
    public static void logoff(final Context context) {
        String ip = TgApplication.getTgUserPreferences().getServerIp(); // IP
        int port = TgApplication.getTgUserPreferences().getServerPort(); // 端口
        String username = TgApplication.getTgUserPreferences().getUsername(); // 获取用户名
        TgApplication.getTgUserPreferences().clear(); // 清空用户个人信息
        TgApplication.getTgUserPreferences().setServerIp(ip);
        TgApplication.getTgUserPreferences().setServerPort(port);
        TgApplication.getTgUserPreferences().setUsername(username); // 用户名不清空
        TgApplication.clearActivitys(); // 清空activiti堆栈
        TgSystemHelper.handleIntentAndFinish(TgArouterCommonPaths.COMMON_LOGIN, (Activity) context); // 跳转到登录页面
    }

    /**
     * 检查Permission
     */
    public static boolean checkPermission(final String permission) {
        List<String> list = BtsRole.getPermissionMapMap().get(TgApplication.getTgUserPreferences().getRoleCodeNum());
        return list != null && list.contains(permission);
    }

    /**
     * 获取登录信息
     */
    public static void setUserInfo(final Map<String, Object> map) {
        String token = (String) map.get("token");
        TgApplication.getTgUserPreferences().setToken(token); // 存储token
        String userId = (String) map.get("userId");
        TgApplication.getTgUserPreferences().setUserId(userId); // 存储userId
        String userName = (String) map.get("username");
        TgApplication.getTgUserPreferences().setUsername(userName); // 存储username
        String realname = (String) map.get("realname");
        TgApplication.getTgUserPreferences().setRealname(realname); // 存储realname
        String cellphoneNumber = (String) map.get("cellphoneNum");
        TgApplication.getTgUserPreferences().setCellphoneNumber(cellphoneNumber); // 存储cellphoneNumber
        String email = (String) map.get("email");
        TgApplication.getTgUserPreferences().setEmail(email); // 存储email
        String workGroupName = (String) map.get("workGroupName");
        TgApplication.getTgUserPreferences().setWorkGroupName(workGroupName); // 检修班组Name
        String workGroupCodeNum = (String) map.get("workGroupCodeNum");
        TgApplication.getTgUserPreferences().setWorkGroupCodeNum(workGroupCodeNum); // 检修班组codeNum
        String roleCodeNum = (String) map.get("roleCodeNum");
        TgApplication.getTgUserPreferences().setRoleCodeNum(roleCodeNum); // 角色权限
    }
}