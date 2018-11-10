package com.turingoal.bts.wps.service;

import com.turingoal.bts.common.android.biz.domain.TechnicalDoc;
import com.turingoal.bts.wps.constants.ConstantUrls;
import com.turingoal.common.android.bean.TgResponseBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 技术资料
 */
public interface TechnicalDocService {
    @POST(ConstantUrls.URL_TECHNICAL_DOC_LIST)
    Call<TgResponseBean<List<TechnicalDoc>>> find(@Query("simpleQueryParam") String simpleQueryParam);
}
