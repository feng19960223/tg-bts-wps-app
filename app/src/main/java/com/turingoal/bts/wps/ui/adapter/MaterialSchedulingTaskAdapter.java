package com.turingoal.bts.wps.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.turingoal.bts.common.android.biz.domain.SchedulingTask;
import com.turingoal.bts.wps.R;
import com.turingoal.common.android.constants.TgConstantTaskStatus;

/**
 * 物料，任务管理adapter
 */

public class MaterialSchedulingTaskAdapter extends BaseQuickAdapter<SchedulingTask, BaseViewHolder> {

    public MaterialSchedulingTaskAdapter() {
        super(R.layout.item_dispatch_task);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final SchedulingTask schedulingTask) {
        holder.setText(R.id.tv_item_1, "股道：" + schedulingTask.getTrackCodeNum() + " - " + schedulingTask.getTrackRowNum()) //股道
                .setText(R.id.tv_item_2, "车组：" + schedulingTask.getTrainSetCodeNum()) //车组
                .setText(R.id.tv_item_3, "任务：" + schedulingTask.getMaintenanceTask()) // 类型，一级修，二级修
                .setText(R.id.tv_item_4, "项目：" + schedulingTask.getMaintenanceTaskItem()); //无电作业 有电作业 滤网作业
        if (TgConstantTaskStatus.NO_STARTED == schedulingTask.getStatus()) { // 未开始蓝色
            holder.setBackgroundRes(R.id.ll_item, R.mipmap.app_ic_item_bg_blue);
        } else if (TgConstantTaskStatus.STARTED == schedulingTask.getStatus()) { // 开始了黄色
            holder.setBackgroundRes(R.id.ll_item, R.mipmap.app_ic_item_bg_yellow);
        } else { // 否则黄色
            holder.setBackgroundRes(R.id.ll_item, R.mipmap.app_ic_item_bg_green);
        }
    }
}
