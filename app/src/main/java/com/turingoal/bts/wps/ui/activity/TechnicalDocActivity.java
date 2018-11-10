package com.turingoal.bts.wps.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.turingoal.bts.common.android.biz.domain.TechnicalDoc;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.service.TechnicalDocService;
import com.turingoal.bts.wps.ui.adapter.TechnicalDocListAdapter;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.util.ui.TgToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 技术资料
 */
@Route(path = TgArouterPaths.WORD)
public class TechnicalDocActivity extends TgBaseActivity {
    @BindView(R.id.data_et_search)
    EditText etSearch;
    @BindView(R.id.data_iv_search)
    ImageView ivSearch;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView; // RecyclerView
    private TechnicalDocListAdapter mAdapter = new TechnicalDocListAdapter(); // adapter

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_technical_doc;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tv_title, R.id.iv_back, "技术资料"); // 顶部工具条
        initAdapter();
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() { // 搜索事件
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(textView.getText().toString())) {
                        TgToastUtil.showLong("搜索内容为空，请输入搜索内容！");
                    } else {
                        getData(textView.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 获取适配器与点击事件
     */
    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.openLoadAnimation(); // 动画
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                TechnicalDoc technicalDoc = (TechnicalDoc) adapter.getItem(position);
                TgSystemHelper.handleIntentWithObj(TgArouterPaths.WORD_DETAIL, "technicalDoc", technicalDoc);
            }
        });
        mAdapter.setEmptyView(getNotDataView((ViewGroup) mRecyclerView.getParent())); // 设置空白view
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 获取数据
     */
    private void getData(final String keyword) {
        TgApplication.getRetrofit().create(TechnicalDocService.class)
                .find(keyword)
                .enqueue(new TgRetrofitCallback<List<TechnicalDoc>>(this, true, true) {
                    @Override
                    public void successHandler(List<TechnicalDoc> list) {
                        mAdapter.setNewData(list); // 加载数据
                    }
                });
    }

    /**
     * onClick
     */
    @OnClick(R.id.data_iv_search)
    public void search() {
        String keyword = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(keyword)) {
            TgToastUtil.showLong("搜索内容为空，请输入搜索内容！");
        } else {
            getData(keyword);
        }
    }
}
