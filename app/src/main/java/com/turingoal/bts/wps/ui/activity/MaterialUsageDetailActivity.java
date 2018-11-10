package com.turingoal.bts.wps.ui.activity;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.bean.MaterialUsage;
import com.turingoal.bts.wps.bean.MaterialUsageItem;
import com.turingoal.bts.wps.service.MaterialService;
import com.turingoal.bts.wps.ui.adapter.MaterialUsageItemAdapter;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.support.workflow.TgConstantWorkflowAduitStatus;
import com.turingoal.common.android.ui.layout.TgTitleContextLinearLayout;
import com.turingoal.common.android.util.lang.TgDateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 审核详情
 */
@Route(path = TgArouterPaths.MATERIAL_USAGE_DETAIL)
public class MaterialUsageDetailActivity extends TgBaseActivity {
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tclCreateTime)
    TgTitleContextLinearLayout tclCreateTime; // 创建时间
    @BindView(R.id.tclCreateName)
    TgTitleContextLinearLayout tclCreateName; // 创建人员
    @BindView(R.id.tclState)
    TgTitleContextLinearLayout tclState; // 审核状态
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView; // recyclerView
    private MaterialUsageItemAdapter adapter = new MaterialUsageItemAdapter(); // adapter
    @Autowired
    MaterialUsage materialUsage;

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_material_usage_detail;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tv_title, R.id.iv_back, "领用详情");
        ivRight.setImageResource(R.mipmap.app_ic_right_title);
        ivRight.setVisibility(View.VISIBLE);
        tclCreateTime.setContextStr(TgDateUtil.date2String(materialUsage.getCreateTime(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_ZH));
        tclCreateName.setContextStr(materialUsage.getUsageUserRealname());
        tclState.setContextStr(TgConstantWorkflowAduitStatus.getStr(materialUsage.getAduitStatus()));
        tclState.setContextTextColor(getColor(materialUsage.getAduitStatus()));
        initAdapter();
        getData();
    }

    private int getColor(Integer integer) {
        if (TgConstantWorkflowAduitStatus.YES == integer) {
            return Color.GREEN;
        } else if (TgConstantWorkflowAduitStatus.NO == integer) {
            return Color.RED;
        } else if (TgConstantWorkflowAduitStatus.PROCESS == integer) {
            return Color.parseColor("#EE8502");
        } else {
            return Color.parseColor("#2385D3");
        }
    }

    /**
     * 初始化adapter
     */
    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.openLoadAnimation(); // 动画
        adapter.setEmptyView(getNotDataView((ViewGroup) recyclerView.getParent())); // 设置空白view
        recyclerView.setAdapter(adapter);
    }

    /**
     * 得到数据
     */
    private void getData() {
        TgApplication.getRetrofit().create(MaterialService.class)
                .findMaterialUsageItems(materialUsage.getId())
                .enqueue(new TgRetrofitCallback<List<MaterialUsageItem>>(this, true, true) {
                    @Override
                    public void successHandler(List<MaterialUsageItem> materialUsageItems) {
                        adapter.setNewData(materialUsageItems);
                    }
                });
    }

    @OnClick(R.id.iv_right)
    public void MaterialAuditProcess() {
        TgSystemHelper.handleIntentWithObj(TgArouterPaths.MATERIAL_AUDIT_PROCESS, "materialUsage", materialUsage);
    }
}
