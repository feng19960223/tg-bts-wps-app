package com.turingoal.bts.wps.service;

import com.turingoal.bts.common.android.biz.domain.DispatchTaskItem;
import com.turingoal.bts.common.android.biz.domain.SchedulingOrder;
import com.turingoal.bts.wps.constants.ConstantUrls;
import com.turingoal.common.android.bean.TgResponseBean;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 任务
 */
public interface TaskService {

    /**
     * 新建任务
     */
    @POST(ConstantUrls.URL_SCHEDULING_TASK_ADD)
    Call<TgResponseBean<Object>> add(@QueryMap Map<String, Object> map);

    /**
     * 作业记录,查询全部人的
     */
    @POST(ConstantUrls.URL_WORK_FINISHED_LSIT)
    Call<TgResponseBean<List<DispatchTaskItem>>> findFinishedByDate(@Query("date") String dateStr);

    /**
     * 作业记录,查询个人的
     */
    @POST(ConstantUrls.URL_WORK_FINISHED_LSIT)
    Call<TgResponseBean<List<DispatchTaskItem>>> findFinishedByDate(@Query("date") String dateStr, @Query("userId") String userId);

    /**
     * 任务列表，时间，白班或夜班，检修班组
     */
    @POST(ConstantUrls.URL_SCHEDULING_ORDER)
    Call<TgResponseBean<SchedulingOrder>> getByDateAndWorkShiftTypeAndWorkGroup(@Query("taskDate") String taskDateStr, @Query("workShiftType") Integer workShiftType, @Query("workGroupName") String workGroupName);

    /**
     * 根据任务查询作业详情
     */
    @POST(ConstantUrls.URL_WORK_GET_BY_TASK)
    Call<TgResponseBean<List<DispatchTaskItem>>> findDispatchTaskItemsByTask(@Query("schedulingTaskCodeNum") String schedulingTaskCodeNum);

    /**
     * 一级修 任务开始
     */
    @POST(ConstantUrls.URL_SCHEDULING_TASK_START)
    Call<TgResponseBean<Object>> start(@Query("id") String id);

    /**
     * 一级修  任务结束
     */
    @POST(ConstantUrls.URL_SCHEDULING_TASK_FINISH)
    Call<TgResponseBean<Object>> finish(@Query("id") String id);
}

