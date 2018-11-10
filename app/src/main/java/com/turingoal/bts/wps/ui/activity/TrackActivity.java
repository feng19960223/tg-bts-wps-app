package com.turingoal.bts.wps.ui.activity;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.turingoal.bts.common.android.constants.ConstantTrackTypes;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.bean.BtsRole;
import com.turingoal.bts.wps.bean.Track;
import com.turingoal.bts.wps.service.TrackService;
import com.turingoal.bts.wps.ui.adapter.TrackStatusListAdapter;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.common.android.base.TgBaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * [股道]信息
 */
@Route(path = TgArouterPaths.TRACK)
public class TrackActivity extends TgBaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.iv_right)
    ImageView ivRight; // 右侧按钮
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView; // RecyclerView
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout; // SwipeRefreshLayout
    private TrackStatusListAdapter mAdapter = new TrackStatusListAdapter();

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_track_status;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tv_title, R.id.iv_back, "股道状态"); // 顶部工具条
        if (TgSystemHelper.checkPermission(BtsRole.WORK_RECORD)) {
            ivRight.setImageResource(R.mipmap.app_ic_right_title);
            ivRight.setVisibility(View.VISIBLE); // 右侧按钮显示
        }
        initAdapter();
        getData(); // 获取数据
    }

    /**
     * 获取适配器
     */
    private void initAdapter() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 102, 108), Color.rgb(85, 193, 255), Color.rgb(204, 202, 111));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.openLoadAnimation(); // 动画
        mAdapter.setEmptyView(getNotDataView((ViewGroup) mRecyclerView.getParent())); // 设置空白view
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 获取数据
     */
    private void getData() {
        TgApplication.getRetrofit().create(TrackService.class)
                .findStatus(ConstantTrackTypes.MAINTENANCE)
                .enqueue(new TgRetrofitCallback<List<Track>>(this, true, true) {
                    @Override
                    public void successHandler(List<Track> list) {
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
     * 刷新数据
     */
    @Override
    public void onRefresh() {
        getData(); // 刷新
    }

    /**
     * onClick
     */
    @OnClick(R.id.iv_right)
    public void record() {
        TgSystemHelper.handleIntent(TgArouterPaths.TRACK_RECORD); // 跳转到股道查询页面
    }
}
