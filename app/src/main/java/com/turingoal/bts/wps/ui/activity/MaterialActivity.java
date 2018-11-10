package com.turingoal.bts.wps.ui.activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.turingoal.bts.common.android.biz.domain.BreakdownRecordItem;
import com.turingoal.bts.common.android.biz.domain.DispatchTaskItem;
import com.turingoal.bts.common.android.biz.domain.SchedulingTask;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.bean.Material;
import com.turingoal.bts.wps.bean.MaterialTempletItem;
import com.turingoal.bts.wps.bean.MaterialUsageItem;
import com.turingoal.bts.wps.service.MaterialService;
import com.turingoal.bts.wps.ui.adapter.MaterialUsageItemNumAdapter;
import com.turingoal.bts.wps.util.RetrofitUtils;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.bts.wps.util.WpsBeanConverterUtil;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.ui.layout.TgTitleContextLinearLayout;
import com.turingoal.common.android.util.io.TgJsonUtil;
import com.turingoal.common.android.util.ui.TgDialogUtil;
import com.turingoal.common.android.util.ui.TgToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 物料购物车
 */
@Route(path = TgArouterPaths.MATERIAL)
public class MaterialActivity extends TgBaseActivity {
    @BindView(R.id.tg_common_view_title_bar_tvRightText)
    TextView tvEnd;
    @BindView(R.id.tclTrackSetCodeNum)
    TgTitleContextLinearLayout tclTrackSetCodeNum; // 车号
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.llBtnView)
    LinearLayout llBtnView; // 3个按钮的视图控制器
    private MaterialUsageItemNumAdapter adapter = new MaterialUsageItemNumAdapter();
    private ItemTouchHelper itemTouchHelper;
    private String schedulingTaskId = "";
    private String dispatchTaskItemId = "";
    private String breakdownRecordItemId = "";

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_material;
    }

    @Override
    protected void initialized() {
        EventBus.getDefault().register(this);
        initToolbar(R.id.tg_common_view_title_bar_tvTitle, R.id.tg_common_view_title_bar_ivLeftBut, getString(R.string.title_material));
        tvEnd.setText(R.string.string_material_clear);
        tvEnd.setVisibility(View.VISIBLE);
        tclTrackSetCodeNum.setContextTextColor(Color.BLACK);
        initAdapter();
    }

    private void initAdapter() {
        itemTouchHelper = new ItemTouchHelper(new ItemDragAndSwipeCallback(adapter) {
            @Override
            public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current, RecyclerView.ViewHolder target) {
                return true;
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.openLoadAnimation(); // 动画
        adapter.setEmptyView(getNotDataView((ViewGroup) recyclerView.getParent())); // 设置空白view
        recyclerView.setAdapter(adapter);
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                itemTouchHelper.startDrag(recyclerView.getChildViewHolder(view));
                return false;
            }
        });
    }

    @OnClick({R.id.btnSelect, R.id.tclTrackSetCodeNum, R.id.btnImport, R.id.btnSubmit, R.id.tg_common_view_title_bar_tvRightText})
    public void OnClick(final View view) {
        switch (view.getId()) {
            case R.id.tg_common_view_title_bar_tvRightText: // 清空
                TgDialogUtil.showDialog(this, "是否清空已选择的物料列表？", new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        adapter.setNewData(null);
                    }
                });
                break;
            case R.id.tclTrackSetCodeNum: // 选择车号
                List<String> items = new ArrayList<>();
                items.add("从故障作业选择");
                items.add("从任务管理选择");
                items.add("从快速作业选择");
                new MaterialDialog.Builder(this).items(items).itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        if (position == 0) {
                            TgSystemHelper.handleIntent(TgArouterPaths.MATERIAL_BREAKDOWN_RECORD_ITEM);
                        } else if (position == 1) {
                            TgSystemHelper.handleIntent(TgArouterPaths.MATERIAL_SCHEDULING_TASK_ITEM);
                        } else {
                            TgSystemHelper.handleIntent(TgArouterPaths.MATERIAL_FAST_WORK_ITEM);
                        }
                    }
                }).show();
                break;
            case R.id.btnSelect: // 选择商品
                TgSystemHelper.handleIntent(TgArouterPaths.MATERIAL_LIST);
                break;
            case R.id.btnImport: // 导入模板
                TgSystemHelper.handleIntent(TgArouterPaths.MATERIAL_TEMPLATE_LIST);
                break;
            case R.id.btnSubmit: // 提交
                if (adapter.getData().size() == 0) {
                    TgToastUtil.showLong("请选择物料");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("schedulingTaskId", schedulingTaskId);
                map.put("dispatchTaskItemId", dispatchTaskItemId);
                map.put("breakdownRecordItemId", breakdownRecordItemId);
                map.put("materialsStr", TgJsonUtil.object2Json(adapter.getData()));
                TgApplication.getRetrofit().create(MaterialService.class)
                        .add(RetrofitUtils.mapToRequestBodyMap(map))
                        .enqueue(new TgRetrofitCallback<Object>(this, true, true) {
                            @Override
                            public void successHandler(Object obj) {
                                TgToastUtil.showLong(R.string.string_material_success_hint);
                                EventBus.getDefault().post("UpDataSuccess");
                                defaultFinish();
                            }
                        });
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SchedulingTask schedulingTask) { // 从任务管理列表
        tclTrackSetCodeNum.setContextStr(schedulingTask.getTrainSetCodeNum());
        schedulingTaskId = schedulingTask.getId();
        dispatchTaskItemId = "";
        breakdownRecordItemId = "";
        llBtnView.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DispatchTaskItem dispatchTaskItem) { // 从快速作业列表
        tclTrackSetCodeNum.setContextStr(dispatchTaskItem.getTrainSetCodeNum());
        schedulingTaskId = "";
        dispatchTaskItemId = dispatchTaskItem.getId();
        breakdownRecordItemId = "";
        llBtnView.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BreakdownRecordItem breakdownRecordItem) { // 从故障列表
        tclTrackSetCodeNum.setContextStr(breakdownRecordItem.getTrainSetCodeNum());
        schedulingTaskId = "";
        dispatchTaskItemId = "";
        breakdownRecordItemId = breakdownRecordItem.getBreakdownRecordId();
        llBtnView.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Material material) { // 来自选择商品item点击购物车/选择商品item——>详情——>修改数量
        boolean isExist = false;
        MaterialUsageItem materialUsageItem = WpsBeanConverterUtil.material2MaterialUsageItem(material, null, null, null);
        for (MaterialUsageItem item : adapter.getData()) {
            if (item.equals(materialUsageItem)) { // 购物车已经有这个商品，则修改他的数量
                int index = adapter.getData().indexOf(item);
                adapter.getData().set(index, materialUsageItem);
                adapter.notifyItemChanged(index);
                isExist = true;
            }
        }
        if (!isExist) { // 如果购物车里没有这个商品
            adapter.addData(materialUsageItem); // 将视频添加到购物车
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MaterialUsageItem materialUsageItem) { // 来自物料领用item查看——>修改数量
        boolean isExist = false;
        for (MaterialUsageItem item : adapter.getData()) {
            if (item.equals(materialUsageItem)) { // 购物车已经有这个商品，则修改他的数量
                int index = adapter.getData().indexOf(item);
                adapter.getData().set(index, materialUsageItem);
                adapter.notifyItemChanged(index);
                isExist = true;
            }
        }
        if (!isExist) { // 如果购物车里没有这个商品
            adapter.addData(materialUsageItem); // 将商品添加到购物车
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(List<MaterialTempletItem> materialTempletItems) { // 来自使用模板/模板item——>使用模板
        List<MaterialUsageItem> materialUsageItems = new ArrayList<>();
        for (MaterialTempletItem materialTempletItem : materialTempletItems) {
            MaterialUsageItem materialUsageItem = WpsBeanConverterUtil.materialTempletItem2Material(materialTempletItem);
            materialUsageItems.add(materialUsageItem);
        }
        adapter.setNewData(materialUsageItems);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
