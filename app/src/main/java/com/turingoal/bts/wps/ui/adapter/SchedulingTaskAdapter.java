package com.turingoal.bts.wps.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.turingoal.bts.common.android.biz.domain.SchedulingTask;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.common.android.constants.TgConstantTaskStatus;
import com.turingoal.common.android.constants.TgConstantYesNo;

/**
 * 任务管理adapter
 */

public class SchedulingTaskAdapter extends BaseQuickAdapter<SchedulingTask, BaseViewHolder> {

    public SchedulingTaskAdapter() {
        super(R.layout.item_dispatch_task);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final SchedulingTask schedulingTask) {
        holder.setText(R.id.tv_item_1, "股道：" + schedulingTask.getTrackCodeNum() + " - " + schedulingTask.getTrackRowNum()) //股道
                .setText(R.id.tv_item_2, "车组：" + schedulingTask.getTrainSetCodeNum()) //车组
                .setText(R.id.tv_item_3, "任务：" + schedulingTask.getMaintenanceTask()) // 类型，一级修，二级修
                .setText(R.id.tv_item_4, "项目：" + schedulingTask.getMaintenanceTaskItem()); //无电作业 有电作业 滤网作业
        if (schedulingTask.getDispatchStatus() == TgConstantYesNo.NO) { // 未派工
            holder.setBackgroundRes(R.id.ll_item, R.mipmap.app_ic_item_bg_gray); // 灰色
        } else {
            if (TgConstantTaskStatus.NO_STARTED == schedulingTask.getStatus()) { // 未开始蓝色
                holder.setBackgroundRes(R.id.ll_item, R.mipmap.app_ic_item_bg_blue);
            } else if (TgConstantTaskStatus.FINISHED == schedulingTask.getStatus()) { // 结束了绿色
                holder.setBackgroundRes(R.id.ll_item, R.mipmap.app_ic_item_bg_green);
            } else { // 否则黄色
                holder.setBackgroundRes(R.id.ll_item, R.mipmap.app_ic_item_bg_yellow);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (schedulingTask.getDispatchStatus() == TgConstantYesNo.NO) { // 未派工
                    TgSystemHelper.handleIntentWithObj(TgArouterPaths.SCHEDULING_TASK_DETAIL, "schedulingTask", schedulingTask); // 完成
                } else {
                    if (TgConstantTaskStatus.NO_STARTED == schedulingTask.getStatus()) {
                        TgSystemHelper.handleIntentWithObj(TgArouterPaths.SCHEDULING_TASK_START, "schedulingTask", schedulingTask); // 未开始
                    } else if (TgConstantTaskStatus.FINISHED == schedulingTask.getStatus()) { // 结束了黄色
                        TgSystemHelper.handleIntentWithObj(TgArouterPaths.SCHEDULING_TASK_DETAIL, "schedulingTask", schedulingTask); // 完成
                    } else {
                        TgSystemHelper.handleIntentWithObj(TgArouterPaths.SCHEDULING_TASK_FINISH, "schedulingTask", schedulingTask); // 正在
                    }
                }
            }
        });
    }
}
