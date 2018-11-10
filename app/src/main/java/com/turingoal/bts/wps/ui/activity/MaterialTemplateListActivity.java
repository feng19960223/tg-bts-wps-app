package com.turingoal.bts.wps.ui.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.bean.MaterialTemplate;
import com.turingoal.bts.wps.bean.MaterialTempletItem;
import com.turingoal.bts.wps.service.MaterialService;
import com.turingoal.bts.wps.ui.adapter.MaterialTemplateAdapter;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.util.ui.TgDialogUtil;
import com.turingoal.common.android.util.ui.TgToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 模板列表
 */
@Route(path = TgArouterPaths.MATERIAL_TEMPLATE_LIST)
public class MaterialTemplateListActivity extends TgBaseActivity {
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MaterialTemplateAdapter adapter = new MaterialTemplateAdapter();
    private List<MaterialTemplate> materialTemplates = new ArrayList<>(); // 物料数据

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_material_template_list;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tg_common_view_title_bar_tvTitle, R.id.tg_common_view_title_bar_ivLeftBut, getString(R.string.title_material_template_list));
        initAdapter();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) { // 有查询内容，显示符合查询的数据
                    adapter.setNewData(getDataByQuery(newText));
                } else { // 没有查询内容，显示全部
                    adapter.setNewData(materialTemplates);
                }
                return false;
            }
        });
        getData();
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.openLoadAnimation(); // 动画
        adapter.setEmptyView(getNotDataView((ViewGroup) recyclerView.getParent())); // 设置空白view
        recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                useMaterialTemplate(((MaterialTemplate) adapter.getData().get(position)).getId());
            }
        });
    }

    /**
     * 使用当前模板
     */
    private void useMaterialTemplate(final String id) {
        TgDialogUtil.showDialog(this, "使用模板会清空已选择的物料，是否使用该模板？", new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                TgApplication.getRetrofit().create(MaterialService.class)
                        .findMaterialTempletItems(id)
                        .enqueue(new TgRetrofitCallback<List<MaterialTempletItem>>(MaterialTemplateListActivity.this, true, true) {
                            @Override
                            public void successHandler(List<MaterialTempletItem> materialTempletItems) {
                                EventBus.getDefault().post(materialTempletItems);
                                TgToastUtil.showShort(R.string.string_material_template_list_success_hint);
                                defaultFinish();
                            }
                        });
            }
        });
    }

    /**
     * 搜索查询获得的数据
     */
    private List<MaterialTemplate> getDataByQuery(final String query) {
        List<MaterialTemplate> newMaterialTemplates = new ArrayList<>();
        for (MaterialTemplate materialTemplate : materialTemplates) {
            if (materialTemplate.getTitle().contains(query)) {
                newMaterialTemplates.add(materialTemplate);
            }
        }
        return newMaterialTemplates;
    }

    /**
     * 获得的模板数据
     */
    private void getData() {
        TgApplication.getRetrofit().create(MaterialService.class)
                .findMaterialTemplates()
                .enqueue(new TgRetrofitCallback<List<MaterialTemplate>>(this, true, true) {
                    @Override
                    public void successHandler(List<MaterialTemplate> list) {
                        materialTemplates = list;
                        adapter.setNewData(list); // 加载数据
                    }
                });
    }
}
