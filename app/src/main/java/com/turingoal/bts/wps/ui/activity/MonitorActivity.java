package com.turingoal.bts.wps.ui.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.common.android.base.TgBaseActivity;

import butterknife.OnClick;

/**
 * 作业监控
 */
@Route(path = TgArouterPaths.MONITOR)
public class MonitorActivity extends TgBaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_rfid;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tv_title, R.id.iv_back, "作业监控"); // 顶部工具条
    }

    /**
     * onClick
     */
    @OnClick({R.id.iv_back})
    public void onClick(final View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }
}
