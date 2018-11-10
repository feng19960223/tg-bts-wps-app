package com.turingoal.bts.wps.ui.activity;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.turingoal.bts.common.android.biz.domain.DispatchTaskItem;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.service.FastWorkService;
import com.turingoal.bts.wps.ui.adapter.FastWorkListAdapter;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.common.android.base.TgBaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * 快速作业
 */
@Route(path = TgArouterPaths.FASTWORK)
public class FastWorkActivity extends TgBaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView; // RecyclerView
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout; // SwipeRefreshLayout
    private FastWorkListAdapter mAdapter = new FastWorkListAdapter(); // adapter

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_fast_work;
    }

    @Override
    protected void initialized() {
        EventBus.getDefault().register(this);
        initToolbar(R.id.tv_title, R.id.iv_back, "快速作业"); // 顶部工具条
        initAdapter();
        getData(); // 获取数据
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
        mRecyclerView.setAdapter(mAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String msg) { // 刷新
        if ("UpdateSuccess".equals(msg)) {
            getData();
        }
    }

    /**
     * 加载数据
     */
    private void getData() {
        TgApplication.getRetrofit().create(FastWorkService.class)
                .findSelfNotFinished(TgApplication.getTgUserPreferences().getUserId())
                .enqueue(new TgRetrofitCallback<List<DispatchTaskItem>>(this, true, true) {
                    @Override
                    public void successHandler(List<DispatchTaskItem> list) {
                        mAdapter.setNewData(list); // 加载数据
                        mSwipeRefreshLayout.setRefreshing(false); // 停止刷新
                    }

                    @Override
                    public void failHandler(String msg) {
                        mSwipeRefreshLayout.setRefreshing(false); // 停止刷新
                    }
                });
    }

    /**
     * 刷新数据
     */
    @Override
    public void onRefresh() {
        getData(); // 刷新
    }
}
