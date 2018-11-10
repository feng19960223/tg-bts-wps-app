package com.turingoal.bts.wps.service;

import com.turingoal.bts.common.android.biz.domain.BreakdownRecordItem;
import com.turingoal.bts.wps.constants.ConstantUrls;
import com.turingoal.common.android.bean.TgResponseBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * 故障
 */
public interface BreakdownRecordItemService {

    /**
     * 故障列表，个人
     */
    @POST(ConstantUrls.URL_BREAKDOWN_RECORD_ITEM_LIST)
    Call<TgResponseBean<List<BreakdownRecordItem>>> findByDate(@Query("taskDate") String taskDate, @Query("userId") String userId);

    /**
     * 故障列表，全部
     */
    @POST(ConstantUrls.URL_BREAKDOWN_RECORD_ITEM_LIST)
    Call<TgResponseBean<List<BreakdownRecordItem>>> findByDate(@Query("taskDate") String taskDate);

    /**
     * 故障开始维修
     */
    @POST(ConstantUrls.URL_BREAKDOWN_RECORD_ITEM_START)
    Call<TgResponseBean<Object>> start(@Query("id") String id);

    /**
     * 故障结束维修
     */
    @POST(ConstantUrls.URL_BREAKDOWN_RECORD_ITEM_FINISH)
    Call<TgResponseBean<Object>> finish(@Query("id") String id, @Query("handlingDesc") String handlingDesc);

    /**
     * 故障开始质检
     */
    @POST(ConstantUrls.URL_BREAKDOWN_RECORD_ITEM_QC_START)
    Call<TgResponseBean<Object>> qcStart(@Query("id") String id);

    /**
     * 故障结束质检
     */
    @POST(ConstantUrls.URL_BREAKDOWN_RECORD_ITEM_QC_FINISH)
    Call<TgResponseBean<Object>> qcFinish(@Query("id") String id, @Query("qcDesc") String qcDesc);

    /**
     * 提报故障
     */
    @Multipart
    @POST(ConstantUrls.URL_BREAKDOWN_RECORD_ITEM_COMMIT)
    Call<TgResponseBean<Object>> commit(@Part List<MultipartBody.Part> parts, @PartMap Map<String, RequestBody> map);

    /**
     * 故障详情
     */
    @POST(ConstantUrls.URL_BREAKDOWN_RECORD_ITEM)
    Call<TgResponseBean<BreakdownRecordItem>> get(@Query("id") String id);
}
