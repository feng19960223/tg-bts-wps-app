package com.turingoal.bts.wps.service;

import com.turingoal.bts.wps.bean.WorkConfigDb;
import com.turingoal.bts.wps.constants.ConstantUrls;
import com.turingoal.common.android.bean.TgResponseBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 作业配置
 */
public interface WorkConfigService {
    /**
     * 作业配置
     */
    @GET(ConstantUrls.URL_WORK_CONFIG_LIST)
    Call<TgResponseBean<List<WorkConfigDb>>> list();
}
