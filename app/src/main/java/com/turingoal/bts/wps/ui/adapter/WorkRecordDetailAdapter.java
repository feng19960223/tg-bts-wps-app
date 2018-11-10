package com.turingoal.bts.wps.ui.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.turingoal.bts.common.android.biz.domain.WorkRecordItem;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.constants.ConstantWpsCommon;
import com.turingoal.common.android.util.lang.TgDateUtil;

/**
 * 记录详情适配器
 */
public class WorkRecordDetailAdapter extends BaseQuickAdapter<WorkRecordItem, BaseViewHolder> {

    public WorkRecordDetailAdapter() {
        super(R.layout.app_item_work_record_detail);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final WorkRecordItem workRecordItem) {
        holder.setText(R.id.record_detail_item_title, "" + workRecordItem.getWorkItem()) // 标题
                .setText(R.id.record_detail_item_carriage, "车厢：" + workRecordItem.getCarriage()) //  车厢
                .setText(R.id.record_detail_item_finish_time, "结束：" + TgDateUtil.date2String(workRecordItem.getFinishTime(), TgDateUtil.FORMAT_HH_MM_SS)) //完成时间
                .setText(R.id.tv_grade, "" + workRecordItem.getCompareResult());
        if (workRecordItem.getCompareResult() > ConstantWpsCommon.PASS_SCORE) { //根据分数，改变字体颜色
            holder.setTextColor(R.id.tv_grade, Color.GREEN);
        } else {
            holder.setTextColor(R.id.tv_grade, Color.RED);
        }
    }
}
