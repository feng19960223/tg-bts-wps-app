package com.turingoal.bts.wps.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * 下载文件
 */
public interface DownloadFileService {
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);
}
