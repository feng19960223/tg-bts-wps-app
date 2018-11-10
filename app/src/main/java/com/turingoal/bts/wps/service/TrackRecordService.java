package com.turingoal.bts.wps.service;

import com.turingoal.bts.common.android.biz.domain.TrackRecord;
import com.turingoal.bts.wps.constants.ConstantUrls;
import com.turingoal.common.android.bean.TgResponseBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 股道记录
 */
public interface TrackRecordService {
    /**
     * 股道记录
     */
    @GET(ConstantUrls.URL_TRACK_RECORD)
    Call<TgResponseBean<List<TrackRecord>>> findByDate(@Query("date") String dateStr, @Query("workType") Integer workType);
}
