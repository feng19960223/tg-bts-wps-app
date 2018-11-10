package com.turingoal.bts.wps.ui.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.turingoal.bts.common.android.biz.domain.DispatchTaskItem;
import com.turingoal.bts.common.android.constants.ConstantStatus4Work;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.constants.ConstantWpsCommon;
import com.turingoal.common.android.util.lang.TgDateUtil;

/**
 * 作业记录适配器
 */
public class WorkRecordListAdapter extends BaseQuickAdapter<DispatchTaskItem, BaseViewHolder> {

    public WorkRecordListAdapter() {
        super(R.layout.app_item_work_record_self);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final DispatchTaskItem dispatchTaskItem) {
        holder.setText(R.id.work_num, "" + dispatchTaskItem.getWorkNum()) // 作业编号
                .setText(R.id.record_track_num, "" + dispatchTaskItem.getTrackCodeNum() + "-" + dispatchTaskItem.getTrackRowNum()) // 股道和列位
                .setText(R.id.record_train_set_code_num, "" + dispatchTaskItem.getTrainSetCodeNum()) // 车号
                .setText(R.id.record_maintenance_task, "任务：" + dispatchTaskItem.getMaintenanceTask()) // 检修任务
                .setText(R.id.record_maintenance_task_item, "项目：" + dispatchTaskItem.getMaintenanceTaskItem()) // 检修项目
                .setText(R.id.record_start_time, "开始：" + TgDateUtil.date2String(dispatchTaskItem.getStartTime(), TgDateUtil.FORMAT_HH_MM)) // 开始时间
                .setText(R.id.record_end_time, "结束：" + TgDateUtil.date2String(dispatchTaskItem.getFinishTime(), TgDateUtil.FORMAT_HH_MM)) // 结束时间
                .setText(R.id.tv_work_grade, "" + dispatchTaskItem.getScore())
                .setTextColor(R.id.tv_work_grade, dispatchTaskItem.getScore() > ConstantWpsCommon.PASS_SCORE ? Color.GREEN : Color.RED);
        // 3 4 号位用红色表示, 1 2号默认是蓝色
        if ("A3".equals(dispatchTaskItem.getWorkNum()) || "A4".equals(dispatchTaskItem.getWorkNum()) || "B3".equals(dispatchTaskItem.getWorkNum()) || "B4".equals(dispatchTaskItem.getWorkNum())) {
            holder.setBackgroundRes(R.id.work_num, R.drawable.circle_light_red);
        } else {
            holder.setBackgroundRes(R.id.work_num, R.drawable.circle_light_blue);
        }
        if (ConstantStatus4Work.FINISHED.equals(dispatchTaskItem.getStatus())) {
            holder.setBackgroundRes(R.id.work_bg, R.mipmap.app_ic_item_bg_green);
        } else {
            holder.setBackgroundRes(R.id.work_bg, R.mipmap.app_ic_item_bg_yellow);
        }
    }
}
