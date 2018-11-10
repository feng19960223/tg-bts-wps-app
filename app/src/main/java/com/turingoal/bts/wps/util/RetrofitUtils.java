package com.turingoal.bts.wps.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/10/17 11:22.
 */
public class RetrofitUtils {
    /**
     * @param map 数据文件转表单
     * @return
     */
    public static Map<String, RequestBody> mapToRequestBodyMap(Map<String, Object> map) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String key : map.keySet()) {
            RequestBody requestBody = strToRequestBody(String.valueOf(map.get(key)));
            requestBodyMap.put(key, requestBody);
        }
        return requestBodyMap;
    }

    /**
     * @param str 数据文件转表单
     * @return
     */
    public static RequestBody strToRequestBody(String str) {
        return RequestBody.create(MediaType.parse("text/plain"), str);
    }

    /**
     * @param file 单图片文件转表单
     * @return
     */
    public static MultipartBody.Part fileToMultipartBodyPart(File file, String name) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData(name, file.getName(), requestBody);
        return part;
    }

    /**
     * @param files 多图片文件转表单
     * @return
     */
    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }

}
