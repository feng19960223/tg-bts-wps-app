package com.turingoal.bts.wps.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.turingoal.bts.common.android.biz.domain.BreakdownRecordItem;
import com.turingoal.bts.common.android.constants.ConstantMontorBreakdownSystemTypes;
import com.turingoal.bts.common.android.constants.ConstantStatus4Work;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.common.android.constants.TgConstantYesNo;

/**
 * 任务管理adapter
 */
public class BreakdownRecordItemAllAdapter extends BaseQuickAdapter<BreakdownRecordItem, BaseViewHolder> {

    public BreakdownRecordItemAllAdapter() {
        super(R.layout.app_item_breakdown_all);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final BreakdownRecordItem breakdownRecordItem) {
        holder.setText(R.id.tv_item_1, "车组：" + breakdownRecordItem.getTrainSetCodeNum())
                .setText(R.id.tv_item_2, "车厢：" + breakdownRecordItem.getCarriage())
                .setText(R.id.tv_item_3, "分类：" + ConstantMontorBreakdownSystemTypes.getStr(breakdownRecordItem.getSystemType()))
                .setText(R.id.tv_item_4, "发现人：" + breakdownRecordItem.getCreateUserRealname());
        if (breakdownRecordItem.getDispatchStatus() == TgConstantYesNo.NO) { // 未派工
            holder.setBackgroundRes(R.id.ll_item, R.mipmap.app_ic_item_bg_gray);
        } else {
            if (ConstantStatus4Work.FINISHED.equals(breakdownRecordItem.getHandlingStatus())) { // 结束
                if (ConstantStatus4Work.FINISHED.equals(breakdownRecordItem.getQcStatus())) { // 质检结束
                    holder.setBackgroundRes(R.id.ll_item, R.mipmap.app_ic_item_bg_green);
                } else if (ConstantStatus4Work.NO_STARTED.equals(breakdownRecordItem.getQcStatus())) { // 质检待处理
                    holder.setBackgroundRes(R.id.ll_item, R.mipmap.app_ic_item_bg_blue);
                } else {
                    holder.setBackgroundRes(R.id.ll_item, R.mipmap.app_ic_item_bg_yellow);
                }
            } else if (ConstantStatus4Work.NO_STARTED.equals(breakdownRecordItem.getHandlingStatus())) { // 待处理
                holder.setBackgroundRes(R.id.ll_item, R.mipmap.app_ic_item_bg_blue);
            } else { // 处理中
                holder.setBackgroundRes(R.id.ll_item, R.mipmap.app_ic_item_bg_yellow);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                TgSystemHelper.handleIntentWithObj(TgArouterPaths.BREAKDOWN_DETAIL, "breakdownRecordItem", breakdownRecordItem);
            }
        });
    }
}
