package com.turingoal.bts.wps.service;

import com.turingoal.bts.common.android.biz.domain.TrainSetModel;
import com.turingoal.bts.wps.constants.ConstantUrls;
import com.turingoal.common.android.bean.TgResponseBean;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 作业指导书用，快速作业
 */
public interface TrainSetModelService {
    /**
     * 根据编码查询车型
     */
    @POST(ConstantUrls.URL_RAILWAY_MODEL_GET_BY_NUM)
    Call<TgResponseBean<TrainSetModel>> getByNum(@Query("modelName") String modelName);
}
