package com.turingoal.bts.wps.service;

import com.turingoal.bts.wps.bean.Track;
import com.turingoal.bts.wps.bean.TrackDb;
import com.turingoal.bts.wps.constants.ConstantUrls;
import com.turingoal.common.android.bean.TgResponseBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 股道配置
 */
public interface TrackService {
    /**
     * 股道状态
     */
    @POST(ConstantUrls.URL_TRACK_STATUS)
    Call<TgResponseBean<List<Track>>> findStatus(@Query("workType") Integer workType);

    /**
     * 股道配置
     */
    @GET(ConstantUrls.URL_TRACK_LIST)
    Call<TgResponseBean<List<TrackDb>>> list();
}
