package com.turingoal.bts.wps.ui.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.turingoal.bts.common.android.biz.domain.SchedulingOrder;
import com.turingoal.bts.common.android.constants.ConstantWorkShiftTypes;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.service.TaskService;
import com.turingoal.bts.wps.ui.adapter.SchedulingTaskAdapter;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.util.lang.TgDateUtil;
import com.turingoal.common.android.util.ui.TgDateTimePickUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 任务管理list
 */
@Route(path = TgArouterPaths.SCHEDULING_TASK)
public class SchedulingTaskActivity extends TgBaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.iv_right)
    ImageView ivRight; // 新建任务
    @BindView(R.id.query_tv_date)
    TextView tvDate; // 日期
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView; // RecyclerView
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout; // SwipeRefreshLayout
    @BindView(R.id.tv_main_head_day)
    TextView tvMainHeadDay; // 白班
    @BindView(R.id.tv_main_head_night)
    TextView tvMainHeadNight; // 夜班
    private SchedulingTaskAdapter mAdapter = new SchedulingTaskAdapter(); // adapter
    private Date currentDate; // 时间
    private boolean isDayOrNight = true; // 白班或夜班，默认白班
    private String keyBreakdownRecordDesc = ""; // 重点故障
    private String keyTasksDesc = ""; // 重点任务
    private String keyAttentionDesc = ""; // 作业注意事项

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_scheduling_task;
    }

    @Override
    protected void initialized() {
        EventBus.getDefault().register(this);
        initToolbar(R.id.tv_title, R.id.iv_back, "任务列表"); // 顶部工具条
        ivRight.setVisibility(View.VISIBLE);
        initAdapter();
        getData(Calendar.getInstance().getTime());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String msg) { // 选择商品
        if ("UpdateSuccess".equals(msg)) {
            getData(currentDate);
        }
    }

    /**
     * onClick
     */
    @OnClick({R.id.iv_right, R.id.query_tv_date, R.id.query_time_left, R.id.query_time_right, R.id.tv_main_head_night, R.id.tv_main_head_day, R.id.iv_wenhao})
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.iv_right: // 新建任务
                TgSystemHelper.handleIntent(TgArouterPaths.SCHEDULING_TASK_ADD);
                break;
            case R.id.query_time_left:
                getData(TgDateUtil.getBeforeDay(currentDate)); // 前一天
                break;
            case R.id.query_tv_date:
                TgDateTimePickUtil timeSelector = new TgDateTimePickUtil(this, currentDate, TgDateUtil.DEFAULT_START_DATE, new Date(), null);
                timeSelector.dateTimePickDialog(new TgDateTimePickUtil.OnSubmitSelectDateListener() {
                    public void onSumbitSelect(final Date date) {
                        if (currentDate.getTime() / 1000 / 60 / 60 / 24 == date.getTime() / 1000 / 60 / 60 / 24) { //用户选择了当天
                            return;
                        }
                        getData(date); // 选择日期
                    }
                });
                break;
            case R.id.query_time_right:
                getData(TgDateUtil.getNextDay(currentDate)); // 后一天
                break;
            case R.id.tv_main_head_day: // 白班
                if (isDayOrNight) {
                    return;
                }
                isDayOrNight = true;
                tvMainHeadDay.setBackgroundResource(R.drawable.bg_main_head_switch_bule);
                tvMainHeadNight.setBackgroundResource(R.drawable.bg_main_head_switch_transparent);
                getData(currentDate);
                break;
            case R.id.tv_main_head_night: // 夜班
                if (!isDayOrNight) {
                    return;
                }
                isDayOrNight = false;
                tvMainHeadDay.setBackgroundResource(R.drawable.bg_main_head_switch_transparent);
                tvMainHeadNight.setBackgroundResource(R.drawable.bg_main_head_switch_bule);
                getData(currentDate);
                break;
            case R.id.iv_wenhao:
                showMyDialog();
                break;
            default:
                break;
        }
    }

    /**
     * adapter
     */
    private void initAdapter() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 102, 108), Color.rgb(85, 193, 255), Color.rgb(204, 202, 111));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.openLoadAnimation(); // 动画
        mAdapter.setEmptyView(getNotDataView((ViewGroup) mRecyclerView.getParent())); // 设置空白view
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    /**
     * 下拉刷新控件的刷新事件
     */
    public void onRefresh() {
        getData(currentDate);
    }

    /**
     * 得到任务管理数据
     */
    private void getData(final Date date) {
        setTitle("任务列表"); // 标题
        currentDate = date;
        tvDate.setText(TgDateUtil.date2String(currentDate, TgDateUtil.FORMAT_YYYY_MM_DD_ZH)); // 设置日期
        keyBreakdownRecordDesc = ""; // 重点故障
        keyTasksDesc = ""; // 重点任务
        keyAttentionDesc = ""; // 作业注意事项
        mAdapter.setNewData(null);
        TgApplication.getRetrofit().create(TaskService.class)
                .getByDateAndWorkShiftTypeAndWorkGroup(TgDateUtil.date2String(currentDate),
                        isDayOrNight ? ConstantWorkShiftTypes.DAY_SHIFT : ConstantWorkShiftTypes.NIGHY_SHIFT,
                        TgApplication.getTgUserPreferences().getWorkGroupName())
                .enqueue(new TgRetrofitCallback<SchedulingOrder>(this, true, true) {
                    @Override
                    public void successHandler(SchedulingOrder schedulingOrder) {
                        if (schedulingOrder != null) {
                            if (!TextUtils.isEmpty("" + schedulingOrder.getCodeNum())) {
                                setTitle("单号：" + schedulingOrder.getCodeNum());
                            } else {
                                setTitle("任务列表"); // 标题
                            }
                            keyBreakdownRecordDesc = schedulingOrder.getKeyBreakdownDesc(); // 重点故障
                            keyTasksDesc = schedulingOrder.getKeyTasksDesc(); // 重点任务
                            keyAttentionDesc = schedulingOrder.getKeyAttentionDesc(); // 作业注意事项
                            mAdapter.setNewData(schedulingOrder.getTasksList()); // 加载数据
                        }
                        mSwipeRefreshLayout.setRefreshing(false); // 停止刷新
                    }

                    @Override
                    public void failHandler(String message) {
                        mSwipeRefreshLayout.setRefreshing(false); // 停止刷新
                    }
                });
    }

    /**
     * 重点任务，重点故障弹窗
     */
    private void showMyDialog() {
        if (TextUtils.isEmpty(keyBreakdownRecordDesc) && TextUtils.isEmpty(keyTasksDesc) && TextUtils.isEmpty(keyAttentionDesc)) {
            return;
        }
        final Dialog dialog = new Dialog(this);
        View contentView = getLayoutInflater().inflate(R.layout.dialog_desc, null);
        TextView tvKeyBreakdownRecordDesc = contentView.findViewById(R.id.tv_keyBreakdownRecordDesc);
        TextView tvKeyTasksDesc = contentView.findViewById(R.id.tv_keyTasksDesc);
        TextView tvKeyAttentionDesc = contentView.findViewById(R.id.tv_keyAttentionDesc);
        contentView.findViewById(R.id.tv_close).setOnClickListener(new View.OnClickListener() { // 关闭
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        if (TextUtils.isEmpty(keyBreakdownRecordDesc)) {
            tvKeyBreakdownRecordDesc.setVisibility(View.GONE);
        } else {
            tvKeyBreakdownRecordDesc.setText(keyBreakdownRecordDesc);
        }
        if (TextUtils.isEmpty(keyTasksDesc)) {
            tvKeyTasksDesc.setVisibility(View.GONE);
        } else {
            tvKeyTasksDesc.setText(keyTasksDesc);
        }
        if (TextUtils.isEmpty(keyAttentionDesc)) {
            tvKeyAttentionDesc.setVisibility(View.GONE);
        } else {
            tvKeyAttentionDesc.setText(keyAttentionDesc);
        }
        dialog.setContentView(contentView);
        dialog.show();
    }
}
