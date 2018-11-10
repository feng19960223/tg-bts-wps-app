package com.turingoal.bts.wps.ui.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.bean.MaterialTemplate;
import com.turingoal.bts.wps.bean.MaterialTempletItem;
import com.turingoal.bts.wps.service.MaterialService;
import com.turingoal.bts.wps.ui.adapter.MaterialTemplateItemAdapter;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.util.ui.TgDialogUtil;
import com.turingoal.common.android.util.ui.TgToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 模板详情
 */
@Route(path = TgArouterPaths.MATERIAL_TEMPLATE_DETAIL)
public class MaterialTemplateDetailActivity extends TgBaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    MaterialTemplateItemAdapter adapter = new MaterialTemplateItemAdapter();
    @Autowired
    MaterialTemplate materialTemplate; // 模板

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_material_template_detail;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tg_common_view_title_bar_tvTitle, R.id.tg_common_view_title_bar_ivLeftBut, getString(R.string.title_material_template_detail));
        initAdapter();
        getData();
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
                .findMaterialTempletItems(materialTemplate.getId())
                .enqueue(new TgRetrofitCallback<List<MaterialTempletItem>>(this, true, true) {
                    @Override
                    public void successHandler(List<MaterialTempletItem> materialTempletItems) {
                        adapter.setNewData(materialTempletItems);
                    }
                });
    }

    @OnClick(R.id.btnUse)
    public void useTemplate() {
        TgDialogUtil.showDialog(this, "使用模板会清空已选择的物料，是否使用该模板？", new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                EventBus.getDefault().post(adapter.getData()); // 将模板中的物料转到购物车页面
                TgToastUtil.showShort(R.string.string_material_template_detail_success_hint);
                defaultFinish();
            }
        });
    }
}
