package com.turingoal.bts.wps.ui.activity;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.turingoal.bts.common.android.biz.domain.DispatchTaskItem;
import com.turingoal.bts.common.android.constants.ConstantStatus4Work;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgAppConfig;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.service.FastWorkService;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.util.lang.TgDateUtil;
import com.turingoal.common.android.util.ui.TgDialogUtil;
import com.turingoal.common.android.util.ui.TgToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 快速作业，开始作业
 */
@Route(path = TgArouterPaths.FASTWORK_LEVEL1_START)
public class FastWorkLevel1StartActivity extends TgBaseActivity {
    @BindView(R.id.tv_date)
    TextView tvDate; // 日期
    @BindView(R.id.tv_train_set_code_num)
    TextView tvTrainSetCodeNum; // 车号
    @BindView(R.id.tv_track)
    TextView tvTrack; // 股道
    @BindView(R.id.tv_maintenance_task)
    TextView tvMaintenanceTask; // 任务
    @BindView(R.id.tv_maintenance_task_item)
    TextView tvMaintenanceTaskItem; // 项目
    @BindView(R.id.tv_work_num)
    TextView tvWorkNum; // 编号
    @BindView(R.id.btn_submit)
    Button btnSubmit; // 提交按钮
    @BindView(R.id.tv_status)
    TextView tvStatus; // 状态
    @BindView(R.id.tv_message)
    TextView tvMessage; // 消息
    @Autowired
    DispatchTaskItem dispatchTaskItem; // ARouter自动解析参数对象

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_fast_work_start;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tv_title, R.id.iv_back, "开始作业"); // 顶部工具条
        tvDate.setText(TgDateUtil.date2String(dispatchTaskItem.getTaskDate(), TgDateUtil.FORMAT_YYYY_MM_DD_ZH)); // 日期
        tvTrainSetCodeNum.setText("" + dispatchTaskItem.getTrainSetCodeNum()); // 车号
        tvTrack.setText(dispatchTaskItem.getTrackCodeNum() + "-" + dispatchTaskItem.getTrackRowNum()); // 股道
        tvMaintenanceTask.setText("" + dispatchTaskItem.getMaintenanceTask()); // 任务
        tvMaintenanceTaskItem.setText("" + dispatchTaskItem.getMaintenanceTaskItem()); // 项目
        tvWorkNum.setText("" + dispatchTaskItem.getWorkNum()); // 作业编号
        tvStatus.setText("【任务未开始】");
        tvMessage.setVisibility(View.VISIBLE);
        tvMessage.setText("请等待检修工长开始任务！");
        if (ConstantStatus4Work.NO_STARTED.equals(dispatchTaskItem.getStatus()) || TgAppConfig.MODE_FOREMAN_AUTO) { // 任务开始
            btnSubmit.setEnabled(true); // 启用提交按钮
            btnSubmit.setBackground(getDrawable(R.drawable.common_btn_red_style));
            tvMessage.setVisibility(View.GONE); // 隐藏消息
        }
    }

    /**
     * onClick
     */
    @OnClick({R.id.btn_submit})
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                TgDialogUtil.showDialog(this, "确定要开始当前作业吗？", new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(final @NonNull MaterialDialog materialDialog, final @NonNull DialogAction dialogAction) {
                        submitRequest(); // 提交请求
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * 提交请求
     */
    private void submitRequest() {
        TgApplication.getRetrofit().create(FastWorkService.class)
                .start(dispatchTaskItem.getId())
                .enqueue(new TgRetrofitCallback<Object>(this, true, true) {
                    @Override
                    public void successHandler(Object obj) {
                        EventBus.getDefault().post("UpdateSuccess");
                        TgToastUtil.showLong("开始作业！"); // 弹出信息
                        dispatchTaskItem.setStartTime(new Date()); // 开始计时
                        dispatchTaskItem.setStatus(ConstantStatus4Work.WORKING); // 修改状态为开始
                        TgSystemHelper.handleIntentWithObjAndFinish(TgArouterPaths.FASTWORK_LEVEL1_PROCESS, "dispatchTaskItem", dispatchTaskItem, FastWorkLevel1StartActivity.this); //跳转到等待开始无电作业页面，关闭当前页面
                    }
                });
    }
}