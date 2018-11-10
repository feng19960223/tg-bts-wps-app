package com.turingoal.bts.wps.service;

import com.turingoal.bts.wps.constants.ConstantUrls;
import com.turingoal.common.android.bean.TgKeyValueBean;
import com.turingoal.common.android.bean.TgResponseBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 用户
 */
public interface UserService {
    /**
     * 检查版本
     */
    @POST(ConstantUrls.URL_CHECK_VERSION)
    Call<TgResponseBean<TgKeyValueBean>> checkVersion(@Query("currentVersion") Integer currentVersion, @Query("packageName") String pageName);

    /**
     * 登录
     */
    @POST(ConstantUrls.URL_USER_LOGIN)
    Call<TgResponseBean<Map<String, Object>>> login(@Query("username") String username, @Query("password") String password);

    /**
     * 检查token
     */
    @POST(ConstantUrls.URL_CHECK_TOKEN)
    Call<TgResponseBean<Map<String, Object>>> checkToken();

    /**
     * 修改密码
     */
    @POST(ConstantUrls.URL_USER_CHANGE_PASS)
    Call<TgResponseBean<Object>> changePass(@Query("userId") String userId, @Query("oldUserPass") String oldUserPass, @Query("userPass") String userPass);
}
