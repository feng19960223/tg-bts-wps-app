package com.turingoal.bts.wps.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.turingoal.bts.common.android.biz.domain.DispatchTaskItem;
import com.turingoal.bts.common.android.constants.ConstantStatus4Work;
import com.turingoal.bts.wps.R;
import com.turingoal.common.android.util.lang.TgDateUtil;

/**
 * 物料，快速作业adapter
 */
public class MaterialFastWorkItemAdapter extends BaseQuickAdapter<DispatchTaskItem, BaseViewHolder> {
    public MaterialFastWorkItemAdapter() {
        super(R.layout.app_item_material_fast_work);
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
        if (ConstantStatus4Work.NO_STARTED.equals(dispatchTaskItem.getStatus())) { // 未开始蓝色
            holder.setBackgroundRes(R.id.work_bg, R.mipmap.app_ic_item_bg_blue);
        } else { // 否则黄色
            holder.setBackgroundRes(R.id.work_bg, R.mipmap.app_ic_item_bg_yellow);
        }
        //状态
        if (ConstantStatus4Work.NO_STARTED.equals(dispatchTaskItem.getStatus())) { // 未开始
            holder.setText(R.id.record_start_time, "未开始"); // 开始时间
        } else { // 进行中
            holder.setText(R.id.record_start_time, "开始：" + TgDateUtil.date2String(dispatchTaskItem.getStartTime(), TgDateUtil.FORMAT_HH_MM_SS)); // 开始时间
        }
    }
}
