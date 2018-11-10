package com.turingoal.bts.wps.ui.activity;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.turingoal.bts.common.android.biz.domain.DispatchTaskItem;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.service.TaskService;
import com.turingoal.bts.wps.ui.adapter.WorkRecordListAllAdapter;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.util.lang.TgDateUtil;
import com.turingoal.common.android.util.ui.TgDateTimePickUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 全部作业记录
 */
@Route(path = TgArouterPaths.WORKRECORD_ALL)
public class WorkRecordAllActivity extends TgBaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.query_tv_date)
    TextView tvDate;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView; // RecyclerView
    private Date currentDate; // 日期
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout; // SwipeRefreshLayout
    private WorkRecordListAllAdapter mAdapter = new WorkRecordListAllAdapter(); // adapter

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_work_record_all;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tv_title, R.id.iv_back, "作业记录(全)"); // 顶部工具条
        initAdapter();
        getData(Calendar.getInstance().getTime()); // 获取数据
    }

    /**
     * 获取适配器与点击事件
     */
    private void initAdapter() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 102, 108), Color.rgb(85, 193, 255), Color.rgb(204, 202, 111));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.openLoadAnimation(); // 动画
        mAdapter.setEmptyView(getNotDataView((ViewGroup) mRecyclerView.getParent())); // 设置空白view
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                DispatchTaskItem dispatchTaskItem = (DispatchTaskItem) adapter.getItem(position);
                TgSystemHelper.handleIntentWithObj(TgArouterPaths.WORKRECORD_DETAIL, "dispatchTaskItem", dispatchTaskItem); //跳转到详情页面
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 加载数据
     */
    private void getData(Date date) {
        currentDate = date; // 日期
        tvDate.setText(TgDateUtil.date2String(currentDate, TgDateUtil.FORMAT_YYYY_MM_DD_ZH)); // 设置日期
        mAdapter.setNewData(null);
        TgApplication.getRetrofit().create(TaskService.class)
                .findFinishedByDate(TgDateUtil.date2String(currentDate))
                .enqueue(new TgRetrofitCallback<List<DispatchTaskItem>>(this, true, true) {
                    @Override
                    public void successHandler(List<DispatchTaskItem> list) {
                        mAdapter.setNewData(list); // 加载数据
                        mSwipeRefreshLayout.setRefreshing(false); // 停止刷新
                    }

                    @Override
                    public void failHandler(String message) {
                        mSwipeRefreshLayout.setRefreshing(false); // 停止刷新
                    }
                });
    }

    /**
     * onClick
     */
    @OnClick({R.id.query_tv_date, R.id.query_time_left, R.id.query_time_right})
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.query_time_left:
                getData(TgDateUtil.getBeforeDay(currentDate)); // 前一天
                break;
            case R.id.query_tv_date:
                TgDateTimePickUtil timeSelector = new TgDateTimePickUtil(this, currentDate, TgDateUtil.DEFAULT_START_DATE, new Date(), null);
                timeSelector.dateTimePickDialog(new TgDateTimePickUtil.OnSubmitSelectDateListener() {
                    public void onSumbitSelect(final Date date) {
                        if (currentDate.getTime() / 1000 / 60 / 60 / 24 != date.getTime() / 1000 / 60 / 60 / 24) { //用户没有选择当天
                            getData(date); // 获取数据并刷新
                        }
                    }
                });
                break;
            case R.id.query_time_right:
                if (!TgDateUtil.isToday(currentDate)) {
                    getData(TgDateUtil.getNextDay(currentDate)); // 后一天
                }
                break;
            default:
                break;
        }
    }

    /**
     * 刷新数据
     */
    @Override
    public void onRefresh() {
        getData(currentDate); // 刷新
    }
}
