package com.turingoal.bts.wps.ui.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.turingoal.bts.wps.R;
import com.turingoal.common.android.biz.domain.TgAduitRecord;
import com.turingoal.common.android.support.workflow.TgConstantWorkflowDealTypes;
import com.turingoal.common.android.util.lang.TgDateUtil;
import com.turingoal.common.android.util.lang.TgStringUtil;

/**
 * 物料审核过程记录的adapter
 */
public class MaterialAuditProcessAdapter extends BaseQuickAdapter<TgAduitRecord, BaseViewHolder> {
    public MaterialAuditProcessAdapter() {
        super(R.layout.app_item_material_audit_process);
    }

    @Override
    protected void convert(BaseViewHolder holper, final TgAduitRecord aduitRecord) {
        holper.setText(R.id.tvNum, "" + (mData.indexOf(aduitRecord) + 1)) // 序号
                .setText(R.id.tvTaskName, aduitRecord.getTaskName())
                .setText(R.id.tvAuditDealResult, TgConstantWorkflowDealTypes.getStr(aduitRecord.getAduitDealResult()))
                .setTextColor(R.id.tvAuditDealResult, getColor(aduitRecord.getAduitDealResult()))
                .setText(R.id.tvDealRemarks, TgStringUtil.isBlank(aduitRecord.getDealRemarks()) ? "意见:无" : "意见：" + aduitRecord.getDealRemarks())
                .setText(R.id.tvAduitUserRealname, "审核人：" + aduitRecord.getAduitUserRealname())
                .setText(R.id.tvDealTime, TgDateUtil.date2String(aduitRecord.getDealTime(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS_ZH));
    }

    private int getColor(Integer integer) {
        if (TgConstantWorkflowDealTypes.YES == integer) {
            return Color.GREEN; // 绿色
        } else if (TgConstantWorkflowDealTypes.NO == integer) {
            return Color.RED;
        } else {
            return Color.parseColor("#2385D3"); // 蓝色 2385D3 黄 EE8502
        }
    }
}
