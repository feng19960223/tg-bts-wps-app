package com.turingoal.bts.wps.ui.activity.common;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgAppConfig;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.manager.DbInitManager;
import com.turingoal.common.android.app.TgArouterCommonPaths;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.bean.TgGridItem;
import com.turingoal.common.android.ui.adapter.TgMainGridAdapter;
import com.turingoal.common.android.util.ui.TgDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页
 */
@Route(path = TgArouterCommonPaths.MAIN_INDEX)
public class TgMainActivity extends TgBaseActivity {
    @BindView(R.id.nine_recycler)
    RecyclerView nineRecycler; // 九宫格
    @BindView(R.id.tv_position)
    TextView tvPosition; // 职位

    @Override
    protected int getLayoutId() {
        return R.layout.common_activity_main_grid;
    }

    @Override
    protected void initialized() {
        tvPosition.setText(TgApplication.getTgUserPreferences().getRealname()); // 当前用户
        TgMainGridAdapter mAdapter = new TgMainGridAdapter(TgAppConfig.MAIN_GRID_DATE); // adapter
        mAdapter.openLoadAnimation(); // 动画
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                TgGridItem item = (TgGridItem) adapter.getItem(position);
                if (TgArouterCommonPaths.COMMON_LOGOFF.equals(item.getUrlPath())) { // 注销退出
                    TgDialogUtil.showDialog(TgMainActivity.this, getString(R.string.string_logoff_hint), new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            TgSystemHelper.logoff(TgMainActivity.this);
                        }
                    });
                } else {
                    TgSystemHelper.handleIntent(item.getUrlPath()); // 跳转到对应的路径
                }
            }
        });
        nineRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        nineRecycler.setAdapter(mAdapter);
        // 更新数据库
        DbInitManager.initData();
    }

    /**
     * 快速作业
     */
    @OnClick(R.id.ll_fast)
    public void goFast() {
        TgSystemHelper.handleIntent(TgArouterPaths.FASTWORK);
    }

    /**
     * 点击返回按钮
     */
    @Override
    public void onBackPressed() {
        TgSystemHelper.dbClickExit(); //  再按一次退出系统
    }
}
