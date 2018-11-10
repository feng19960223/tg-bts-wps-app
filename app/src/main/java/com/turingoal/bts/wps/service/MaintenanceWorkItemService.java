package com.turingoal.bts.wps.service;

import com.turingoal.bts.wps.bean.MaintenanceWorkItemDb;
import com.turingoal.bts.wps.constants.ConstantUrls;
import com.turingoal.common.android.bean.TgResponseBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 作业项目
 */
public interface MaintenanceWorkItemService {
    /**
     * 作业项目
     */
    @GET(ConstantUrls.URL_MAINTENANCE_WORK_ITEM_LIST)
    Call<TgResponseBean<List<MaintenanceWorkItemDb>>> list();
}
