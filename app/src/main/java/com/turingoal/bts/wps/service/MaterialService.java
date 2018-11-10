package com.turingoal.bts.wps.service;

import com.turingoal.bts.wps.bean.Material;
import com.turingoal.bts.wps.bean.MaterialTemplate;
import com.turingoal.bts.wps.bean.MaterialTempletItem;
import com.turingoal.bts.wps.bean.MaterialUsage;
import com.turingoal.bts.wps.bean.MaterialUsageItem;
import com.turingoal.bts.wps.constants.ConstantUrls;
import com.turingoal.common.android.bean.TgResponseBean;
import com.turingoal.common.android.biz.domain.TgAduitRecord;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 物料
 */
public interface MaterialService {
    /**
     * 物料审核记录
     */
    @POST(ConstantUrls.URL_MATERIAL_AUDIT_RESULT_LIST)
    Call<TgResponseBean<List<MaterialUsage>>> findMaterialAuditResult(@Query("queryDate") String dataStr);

    /**
     * 物料列表，选择商品用,分页
     */
    @POST(ConstantUrls.URL_MATERIAL_LIST)
    Call<TgResponseBean<List<Material>>> findMaterials(@Query("page") Integer page, @Query("limit") Integer limit, @Query("simpleQueryParam") String simpleQueryParam);

    /**
     * 提交物料，提交列表用
     */
    @Multipart
    @POST(ConstantUrls.URL_MATERIAL_ADD)
    Call<TgResponseBean<Object>> add(@PartMap Map<String, RequestBody> map);

    /**
     * 更新物料，提交列表用
     */
    @Multipart
    @POST(ConstantUrls.URL_MATERIAL_UPDATE)
    Call<TgResponseBean<Object>> update(@PartMap Map<String, RequestBody> map);

    /**
     * 模板列表
     */
    @POST(ConstantUrls.URL_MATERIAL_TEMPLATE_LIST)
    Call<TgResponseBean<List<MaterialTemplate>>> findMaterialTemplates();

    /**
     * 得到模板详情，模板里面包含的item
     */
    @POST(ConstantUrls.URL_MATERIAL_TEMPLATE_DETAIL)
    Call<TgResponseBean<List<MaterialTempletItem>>> findMaterialTempletItems(@Query("id") String id);

    /**
     * 待审核列表，审核用
     */
    @POST(ConstantUrls.URL_MATERIAL_AUDIT_WAIT_LIST)
    Call<TgResponseBean<List<MaterialUsage>>> findMaterialAuditWaitList();

    /**
     * 待审核列表，审核用
     */
    @POST(ConstantUrls.URL_MATERIAL_AUDIT_COMPLETE_LIST)
    Call<TgResponseBean<List<MaterialUsage>>> findMaterialAuditCompleteList(@Query("queryDate") String dataStr);

    /**
     * 得到领用列表，领用里面包含的item
     */
    @POST(ConstantUrls.URL_MATERIAL_AUDIT_DETAIL)
    Call<TgResponseBean<List<MaterialUsageItem>>> findMaterialUsageItems(@Query("id") String id);

    /**
     * 物料审核，提交审核用
     */
    @POST(ConstantUrls.URL_MATERIAL_AUDIT)
    Call<TgResponseBean<Object>> auditByForeman(@QueryMap Map<String, Object> map);

    /**
     * 物料审核记录
     */
    @POST(ConstantUrls.URL_MATERIAL_AUDIT_LIST)
    Call<TgResponseBean<List<TgAduitRecord>>> findAuditRecordList(@Query("id") String materialUsageId);
}
