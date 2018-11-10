package com.turingoal.bts.wps.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.bean.Material;
import com.turingoal.bts.wps.service.MaterialService;
import com.turingoal.bts.wps.ui.adapter.MaterialAddAdapter;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.util.ui.TgToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

/**
 * 物料列表，item带购物车按钮
 */
@Route(path = TgArouterPaths.MATERIAL_LIST)
public class MaterialListActivity extends TgBaseActivity {
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MaterialAddAdapter adapter = new MaterialAddAdapter();
    private int limitSize = 20; // 一次加载多少条数据
    private int pageSize = 1; // 第几页
    private String simpleQueryParam = "";

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_material_list;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tg_common_view_title_bar_tvTitle, R.id.tg_common_view_title_bar_ivLeftBut, getString(R.string.title_material_list));
        initAdapter();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                pageSize = 1;
                simpleQueryParam = s;
                getData(true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) { // 从有数据变成没有数据
                    simpleQueryParam = "";
                    getData(true);
                }
                return false;
            }
        });
        getData(true);
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.isFirstOnly(true); // 只有第一次需要动画
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM); // 动画
        adapter.setEmptyView(getNotDataView((ViewGroup) recyclerView.getParent())); // 设置空白view
        recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                EventBus.getDefault().post(adapter.getData().get(position));
                TgToastUtil.showShort(R.string.string_material_list_success_hint);
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() { // 加载更多
            @Override
            public void onLoadMoreRequested() {
                getData(false);
            }
        }, recyclerView);
    }

    /**
     * 获得的物料数据
     */
    private void getData(final boolean isSearch) {
        TgApplication.getRetrofit().create(MaterialService.class)
                .findMaterials(pageSize, limitSize, simpleQueryParam)
                .enqueue(new TgRetrofitCallback<List<Material>>(this, true, true) {
                    @Override
                    public void successHandler(List<Material> list) {
                        pageSize++;
                        if (isSearch) {
                            adapter.setNewData(list);
                        } else {
                            adapter.addData(list); // 添加数据
                        }
                        if (list.size() < limitSize) { // 第一页如果不够一页就不显示没有更多数据布局
                            adapter.loadMoreEnd(false); // false显示没有更多数据;true不显示任何提示信息(刷新用true)
                        } else {
                            adapter.loadMoreComplete(); // 成功获取更多数据
                        }
                    }

                    @Override
                    public void failHandler(String msg) {
                        super.failHandler(msg);
                        adapter.loadMoreFail(); // 获取更多数据失败，点击重试
                    }
                });
    }
}
