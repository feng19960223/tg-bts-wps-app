package com.turingoal.bts.wps.ui.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.turingoal.bts.common.android.biz.domain.DispatchTaskItem;
import com.turingoal.bts.common.android.constants.ConstantMontorMaintenanceLevels;
import com.turingoal.bts.common.android.constants.ConstantStatus4Work;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.common.android.util.lang.TgDateUtil;
import com.turingoal.common.android.util.ui.TgDialogUtil;

/**
 * 快速作业适配器
 */
public class FastWorkListAdapter extends BaseQuickAdapter<DispatchTaskItem, BaseViewHolder> {
    public FastWorkListAdapter() {
        super(R.layout.app_item_fast_work);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final DispatchTaskItem dispatchTaskItem) {
        holder.setText(R.id.work_num, "" + dispatchTaskItem.getWorkNum()) // 作业编号
                .setText(R.id.record_track_num, "" + dispatchTaskItem.getTrackCodeNum() + "-" + dispatchTaskItem.getTrackRowNum()) // 股道和列位
                .setText(R.id.record_train_set_code_num, "车号：" + dispatchTaskItem.getTrainSetCodeNum()) // 车号
                .setText(R.id.record_maintenance_task, "任务：" + dispatchTaskItem.getMaintenanceTask()) // 检修任务
                .setText(R.id.record_maintenance_task_item, "项目：" + dispatchTaskItem.getMaintenanceTaskItem()); // 检修项目
        // 3 4 号位用红色表示, 1 2号默认是蓝色
        if ("A3".equals(dispatchTaskItem.getWorkNum()) || "A4".equals(dispatchTaskItem.getWorkNum()) || "B3".equals(dispatchTaskItem.getWorkNum()) || "B4".equals(dispatchTaskItem.getWorkNum())) {
            holder.setBackgroundRes(R.id.work_num, R.drawable.circle_light_red);
        } else {
            holder.setBackgroundRes(R.id.work_num, R.drawable.circle_light_blue);
        }
        //状态
        if (ConstantStatus4Work.NO_STARTED.equals(dispatchTaskItem.getStatus())) { // 未开始
            holder.setText(R.id.record_start_time, "未开始"); // 开始时间
        } else { // 进行中
            holder.setText(R.id.record_start_time, "开 始：" + TgDateUtil.date2String(dispatchTaskItem.getStartTime(), TgDateUtil.FORMAT_HH_MM_SS)); // 开始时间
        }
        if (ConstantStatus4Work.NO_STARTED.equals(dispatchTaskItem.getStatus())) { // 未开始蓝色
            holder.setBackgroundRes(R.id.work_bg, R.mipmap.app_ic_item_bg_blue);
        } else { // 否则黄色
            holder.setBackgroundRes(R.id.work_bg, R.mipmap.app_ic_item_bg_yellow);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path;
                if (ConstantMontorMaintenanceLevels.LEVEL_1.equals(dispatchTaskItem.getMaintenanceLevelCodeNum())) { // 如果是一级修
                    if (ConstantStatus4Work.NO_STARTED.equals(dispatchTaskItem.getStatus())) {
                        path = TgArouterPaths.FASTWORK_LEVEL1_START; // 开始
                    } else if (ConstantStatus4Work.FINISHED_WAIT.equals(dispatchTaskItem.getStatus())) {
                        path = TgArouterPaths.FASTWORK_LEVEL1_FINISH; // 结束
                    } else {
                        path = TgArouterPaths.FASTWORK_LEVEL1_PROCESS; // 作业中
                    }
                    TgSystemHelper.handleIntentWithObj(path, "dispatchTaskItem", dispatchTaskItem); //跳转到作业页面
                } else { // 非一级修
                    TgDialogUtil.showDialog(mContext, "非一级修快速作业功能暂不开放!", new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
    }
}
