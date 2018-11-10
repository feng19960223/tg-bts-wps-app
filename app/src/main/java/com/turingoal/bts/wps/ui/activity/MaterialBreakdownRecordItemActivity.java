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
import com.turingoal.bts.common.android.biz.domain.BreakdownRecordItem;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.service.BreakdownRecordItemService;
import com.turingoal.bts.wps.ui.adapter.MaterialBreakdownRecordItemAdapter;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.util.lang.TgDateUtil;
import com.turingoal.common.android.util.ui.TgDateTimePickUtil;
import com.turingoal.common.android.util.ui.TgToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 物料故障，获取车号
 */
@Route(path = TgArouterPaths.MATERIAL_BREAKDOWN_RECORD_ITEM)
public class MaterialBreakdownRecordItemActivity extends TgBaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.query_tv_date)
    TextView tvDate; // 日期
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView; // RecyclerView
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout; // SwipeRefreshLayout
    private MaterialBreakdownRecordItemAdapter mAdapter = new MaterialBreakdownRecordItemAdapter(); // adapter
    private Date currentDate; // 时间

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_material_breakdown;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tv_title, R.id.iv_back, "故障作业");
        initAdapter();
        getData(Calendar.getInstance().getTime());
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
                TgDateTimePickUtil timeSelector = new TgDateTimePickUtil(this, currentDate, TgDateUtil.DEFAULT_START_DATE, new Date(), "");
                timeSelector.dateTimePickDialog(new TgDateTimePickUtil.OnSubmitSelectDateListener() {
                    public void onSumbitSelect(final Date date) {
                        if (currentDate.getTime() / 1000 / 60 / 60 / 24 != date.getTime() / 1000 / 60 / 60 / 24) { //用户没有选择当天
                            getData(date); // 选择日期
                        }
                    }
                });
                break;
            case R.id.query_time_right:
                if (!TgDateUtil.isToday(currentDate)) { // 如果现在选择的不是今天
                    getData(TgDateUtil.getNextDay(currentDate)); // 后一天
                }
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
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EventBus.getDefault().post(adapter.getData().get(position));
                TgToastUtil.showLong("选择作业成功！");
                defaultFinish();
            }
        });
    }

    /**
     * 下拉刷新控件的刷新事件
     */
    @Override
    public void onRefresh() {
        getData(currentDate);
    }

    /**
     * 得到故障处理数据
     */
    private void getData(final Date date) {
        currentDate = date;
        tvDate.setText(TgDateUtil.date2String(currentDate, TgDateUtil.FORMAT_YYYY_MM_DD_ZH)); // 设置日期
        TgApplication.getRetrofit().create(BreakdownRecordItemService.class)
                .findByDate(TgDateUtil.date2String(currentDate, TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS), TgApplication.getTgUserPreferences().getUserId())
                .enqueue(new TgRetrofitCallback<List<BreakdownRecordItem>>(this, true, true) {
                    @Override
                    public void successHandler(final List<BreakdownRecordItem> list) {
                        mAdapter.setNewData(list); // 加载数据
                        mSwipeRefreshLayout.setRefreshing(false); // 停止刷新
                    }

                    @Override
                    public void failHandler(String msg) {
                        mSwipeRefreshLayout.setRefreshing(false); // 停止刷新
                    }
                });
    }
}
