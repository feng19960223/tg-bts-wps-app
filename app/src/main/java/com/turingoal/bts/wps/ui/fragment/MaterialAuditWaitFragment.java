package com.turingoal.bts.wps.ui.fragment;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.bean.MaterialUsage;
import com.turingoal.bts.wps.service.MaterialService;
import com.turingoal.bts.wps.ui.adapter.MaterialAuditWaitAdapter;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.common.android.base.TgBaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * 待处理的审核
 */
public class MaterialAuditWaitFragment extends TgBaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView; // RecyclerView
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout; // SwipeRefreshLayout
    private MaterialAuditWaitAdapter mAdapter = new MaterialAuditWaitAdapter(); // adapter

    @Override
    protected int getLayoutId() {
        return R.layout.app_fragment_material_audit_wait;
    }

    @Override
    protected void initialized() {
        EventBus.getDefault().register(this);
        initAdapter();
        getData();
    }

    /**
     * 初始化adapter
     */
    public void initAdapter() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 102, 108), Color.rgb(85, 193, 255), Color.rgb(204, 202, 111));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter.openLoadAnimation(); // 动画
        mAdapter.setEmptyView(getNotDataView((ViewGroup) mRecyclerView.getParent())); // 设置空白view
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 得到数据
     */
    private void getData() {
        TgApplication.getRetrofit().create(MaterialService.class)
                .findMaterialAuditWaitList()
                .enqueue(new TgRetrofitCallback<List<MaterialUsage>>(getContext(), true, true) {
                    @Override
                    public void successHandler(List<MaterialUsage> list) {
                        mAdapter.setNewData(list); // 加载数据
                        mSwipeRefreshLayout.setRefreshing(false); // 停止刷新
                    }

                    @Override
                    public void failHandler(String msg) {
                        mSwipeRefreshLayout.setRefreshing(false); // 停止刷新
                    }
                });
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String msg) { // 审核成功，刷新列表
        if ("MaterialAuditSuccess".equals(msg)) {
            getData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
