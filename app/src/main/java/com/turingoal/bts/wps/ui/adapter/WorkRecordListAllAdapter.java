package com.turingoal.bts.wps.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.turingoal.bts.common.android.biz.domain.DispatchTaskItem;
import com.turingoal.bts.common.android.constants.ConstantStatus4Work;
import com.turingoal.bts.wps.R;
import com.turingoal.common.android.util.lang.TgDateUtil;

/**
 * 全部作业记录适配器
 */
public class WorkRecordListAllAdapter extends BaseQuickAdapter<DispatchTaskItem, BaseViewHolder> {
    public WorkRecordListAllAdapter() {
        super(R.layout.app_item_work_record_all);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final DispatchTaskItem dispatchTaskItem) {
        holder.setText(R.id.record_all_person_name, "" + dispatchTaskItem.getWorkUserRealname()) // 作业人姓名
                .setText(R.id.record_all_person_num, "" + dispatchTaskItem.getWorkNum()) // 作业位置编号
                .setText(R.id.record_all_gd_num, dispatchTaskItem.getTrackCodeNum() + "-" + dispatchTaskItem.getTrackRowNum()) // 股道列位
                .setText(R.id.record_all_train_num, "" + dispatchTaskItem.getTrainSetCodeNum()) // 车号
                .setText(R.id.record_maintenance_task, "任务：" + dispatchTaskItem.getMaintenanceTask()) // 任务
                .setText(R.id.record_maintenance_task_item, "项目：" + dispatchTaskItem.getMaintenanceTaskItem()) // 项目
                .setText(R.id.record_all_start_time, "开始：" + TgDateUtil.date2String(dispatchTaskItem.getStartTime(), TgDateUtil.FORMAT_HH_MM_SS) + "") // 开始时间
                .setText(R.id.record_all_end_time, "结束：" + TgDateUtil.date2String(dispatchTaskItem.getFinishTime(), TgDateUtil.FORMAT_HH_MM_SS) + ""); // 结束时间
        if (ConstantStatus4Work.FINISHED.equals(dispatchTaskItem.getStatus())) {
            holder.setBackgroundRes(R.id.work_bg, R.mipmap.app_ic_item_bg_green);
        } else {
            holder.setBackgroundRes(R.id.work_bg, R.mipmap.app_ic_item_bg_yellow);
        }
    }
}
