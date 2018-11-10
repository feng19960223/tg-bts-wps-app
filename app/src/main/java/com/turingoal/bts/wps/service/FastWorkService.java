package com.turingoal.bts.wps.service;

import com.turingoal.bts.common.android.biz.domain.DispatchTaskItem;
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
 * 快速作业
 */
public interface FastWorkService {
    /**
     * 查询当前用户为完成的作业，包括未开始的和进行中的
     */
    @POST(ConstantUrls.URL_DISPATCH_TASK_ITEM_FINISH_LIST)
    Call<TgResponseBean<List<DispatchTaskItem>>> findSelfNotFinished(@Query("userId") String userId);

    /**
     * 完成作业
     */
    @POST(ConstantUrls.URL_WORK_FINISH)
    Call<TgResponseBean<Object>> finish(@Query("id") String id);

    /**
     * 提交作业项
     */
    @Multipart
    @POST(ConstantUrls.URL_WORK_ITEM_SUBMIT)
    Call<TgResponseBean<Object>> submitItem(@Part MultipartBody.Part part,
                                            @PartMap Map<String, RequestBody> map);

    /**
     * 开始作业
     */
    @POST(ConstantUrls.URL_WORK_START)
    Call<TgResponseBean<Object>> start(@Query("id") String id);
}
