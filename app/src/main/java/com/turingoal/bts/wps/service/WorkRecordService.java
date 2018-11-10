package com.turingoal.bts.wps.service;

import com.turingoal.bts.common.android.biz.domain.WorkRecordItem;
import com.turingoal.bts.wps.constants.ConstantUrls;
import com.turingoal.common.android.bean.TgResponseBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Query;
import retrofit2.http.GET;

/**
 * 作业记录
 */
public interface WorkRecordService {
    /**
     * 根据作业记录查询作业项
     */
    @GET(ConstantUrls.URL_WORK_GET_ITEMS)
    Call<TgResponseBean<List<WorkRecordItem>>> findItems(@Query("dispatchTaskItemId") String recordId);
}
