package com.turingoal.bts.wps.app;

import android.content.Context;

import com.turingoal.common.android.base.TgBaseUserPreferences;
import com.turingoal.common.android.support.system.TgConstantSystemValues;

/**
 * 用户数据_参数保存服务
 */
public class TgUserPreferences extends TgBaseUserPreferences {
    public TgUserPreferences(final Context context, final String spName) {
        super(context, spName);
    }

    /*** 设置ip */
    public void setServerIp(final String ip) {
        sharedPreferences.edit().putString("server_ip", ip).commit();
    }

    /*** 获取ip */
    public String getServerIp() {
        return sharedPreferences.getString("server_ip", TgAppConfig.SERVER_IP);
    }

    /*** 设置port */
    public void setServerPort(final Integer port) {
        sharedPreferences.edit().putInt("server_port", port).apply();
    }

    /*** 获取port */
    public int getServerPort() {
        return sharedPreferences.getInt("server_port", TgAppConfig.SERVER_PORT);
    }

    /*** 设置token */
    public void setToken(final String token) {
        sharedPreferences.edit().putString(TgConstantSystemValues.ACCESS_TOKEN, token).commit();
    }

    /*** 获取token */
    public String getToken() {
        return sharedPreferences.getString(TgConstantSystemValues.ACCESS_TOKEN, "");
    }

    /*** 设置userId*/
    public void setUserId(final String userId) {
        sharedPreferences.edit().putString(TgConstantSystemValues.CURRENT_USER_ID, userId).commit();
    }

    /*** 获取userId */
    public String getUserId() {
        return sharedPreferences.getString(TgConstantSystemValues.CURRENT_USER_ID, "");
    }

    /*** 设置username*/
    public void setCodeNum(final String codeNum) {
        sharedPreferences.edit().putString(TgConstantSystemValues.CURRENT_USER_CODE_NUM, codeNum).commit();
    }

    /*** 获取username */
    public String getCodeNum() {
        return sharedPreferences.getString(TgConstantSystemValues.CURRENT_USER_CODE_NUM, "");
    }

    /*** 设置username*/
    public void setUsername(final String username) {
        sharedPreferences.edit().putString(TgConstantSystemValues.CURRENT_USERNAME, username).commit();
    }

    /*** 获取username */
    public String getUsername() {
        return sharedPreferences.getString(TgConstantSystemValues.CURRENT_USERNAME, "");
    }


    /*** 设置realname*/
    public void setRealname(final String realname) {
        sharedPreferences.edit().putString(TgConstantSystemValues.CURRENT_USER_REALRNAME, realname).commit();
    }

    /*** 获取username */
    public String getRealname() {
        return sharedPreferences.getString(TgConstantSystemValues.CURRENT_USER_REALRNAME, "");
    }

    /*** 设置cellphoneNumber*/
    public void setCellphoneNumber(final String cellphoneNumber) {
        sharedPreferences.edit().putString("cellphoneNum", cellphoneNumber).commit();
    }

    /*** 获取cellphoneNumber */
    public String getCellphoneNumber() {
        return sharedPreferences.getString("cellphoneNum", "");
    }

    /*** 设置email*/
    public void setEmail(final String email) {
        sharedPreferences.edit().putString("email", email).commit();
    }

    /*** 获取email */
    public String getEmail() {
        return sharedPreferences.getString("email", "");
    }

    /**
     * 得到检修班组CodeNum
     */
    public String getWorkGroupCodeNum() {
        return sharedPreferences.getString("workGroupNameCodeNum", "");
    }

    /**
     * 设置检修班组CodeNum
     */
    public void setWorkGroupCodeNum(final String workGroupNameCodeNum) {
        sharedPreferences.edit().putString("workGroupNameCodeNum", workGroupNameCodeNum).commit();
    }

    /*** 检修班组*/
    public String getWorkGroupName() {
        return sharedPreferences.getString("workGroupName", "");
    }

    /**
     * 设置检修班组
     */
    public void setWorkGroupName(final String workGroupName) {
        sharedPreferences.edit().putString("workGroupName", workGroupName).commit();
    }

    /**
     * 得到角色权限
     */
    public String getRoleCodeNum() {
        return sharedPreferences.getString("roleCodeNum", "");
    }

    /**
     * 设置角色权限
     */
    public void setRoleCodeNum(final String roleCodeNum) {
        sharedPreferences.edit().putString("roleCodeNum", roleCodeNum).commit();
    }
}
