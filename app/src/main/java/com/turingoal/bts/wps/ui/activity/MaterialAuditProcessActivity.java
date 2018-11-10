package com.turingoal.bts.wps.ui.activity;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.bean.MaterialUsage;
import com.turingoal.bts.wps.service.MaterialService;
import com.turingoal.bts.wps.ui.adapter.MaterialAuditProcessAdapter;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.biz.domain.TgAduitRecord;

import java.util.List;

import butterknife.BindView;

/**
 * 审核过程记录界面
 */
@Route(path = TgArouterPaths.MATERIAL_AUDIT_PROCESS)
public class MaterialAuditProcessActivity extends TgBaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView; // RecyclerView
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout; // SwipeRefreshLayout
    @Autowired
    MaterialUsage materialUsage;
    private MaterialAuditProcessAdapter mAdapter = new MaterialAuditProcessAdapter();

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_material_audit_process;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tg_common_view_title_bar_tvTitle, R.id.tg_common_view_title_bar_ivLeftBut, "审核记录");
        initAdapter();
        getData(materialUsage.getId());
    }

    /**
     * 初始化adapter
     */
    public void initAdapter() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 102, 108), Color.rgb(85, 193, 255), Color.rgb(204, 202, 111));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.openLoadAnimation(); // 动画
        mAdapter.setEmptyView(getNotDataView((ViewGroup) mRecyclerView.getParent())); // 设置空白view
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 得到数据
     */
    private void getData(String materialUsageId) {
        TgApplication.getRetrofit().create(MaterialService.class)
                .findAuditRecordList(materialUsageId)
                .enqueue(new TgRetrofitCallback<List<TgAduitRecord>>(this, true, true) {
                    @Override
                    public void successHandler(List<TgAduitRecord> list) {
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
        getData(materialUsage.getId());
    }
}
