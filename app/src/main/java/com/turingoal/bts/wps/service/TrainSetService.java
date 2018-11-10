package com.turingoal.bts.wps.service;

import com.turingoal.bts.wps.bean.TrainSetDb;
import com.turingoal.bts.wps.constants.ConstantUrls;
import com.turingoal.common.android.bean.TgResponseBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 车号配置
 */
public interface TrainSetService {
    /**
     * 车号配置
     */
    @GET(ConstantUrls.URL_TRAIN_SET_LIST)
    Call<TgResponseBean<List<TrainSetDb>>> list();
}
