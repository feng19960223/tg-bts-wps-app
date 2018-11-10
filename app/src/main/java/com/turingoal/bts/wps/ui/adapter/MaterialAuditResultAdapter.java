package com.turingoal.bts.wps.ui.adapter;

import android.graphics.Color;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.bean.MaterialUsage;
import com.turingoal.common.android.support.workflow.TgConstantWorkflowAduitStatus;
import com.turingoal.common.android.support.workflow.TgConstantWorkflowDealTypes;
import com.turingoal.common.android.util.lang.TgDateUtil;

/**
 * 工人被审核的列表，包括通过和驳回的adapter
 */
public class MaterialAuditResultAdapter extends BaseQuickAdapter<MaterialUsage, BaseViewHolder> {
    public MaterialAuditResultAdapter() {
        super(R.layout.app_item_material_audit_result);
    }

    @Override
    protected void convert(BaseViewHolder holper, final MaterialUsage materialUsage) {
        holper.setText(R.id.tvCodeNum, "编号：" + materialUsage.getCodeNum())
                .setText(R.id.tvDealType, TgConstantWorkflowAduitStatus.getStr(materialUsage.getAduitStatus()))
                .setTextColor(R.id.tvDealType, getColor(materialUsage.getAduitStatus()))
                .setText(R.id.tvCreateTime, "时间：" + TgDateUtil.date2String(materialUsage.getCreateTime(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_ZH))
                .setText(R.id.tvBtn, materialUsage.getAduitStatus() == TgConstantWorkflowAduitStatus.NO ? "修改更新" : "查看详情")
                .setBackgroundRes(R.id.tvBtn, materialUsage.getAduitStatus() == TgConstantWorkflowAduitStatus.NO ? R.drawable.bg_btn_orange_style : R.drawable.bg_btn_green_style);
        holper.getView(R.id.tvBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (materialUsage.getAduitStatus() == TgConstantWorkflowAduitStatus.NO) { // 驳回，到修改界面
                    TgSystemHelper.handleIntentWithObj(TgArouterPaths.MATERIAL_USAGE_UPDATE, "materialUsage", materialUsage);
                } else { // 到详情界面
                    TgSystemHelper.handleIntentWithObj(TgArouterPaths.MATERIAL_USAGE_DETAIL, "materialUsage", materialUsage);
                }
            }
        });
    }

    private int getColor(Integer integer) {
        if (TgConstantWorkflowAduitStatus.YES == integer) {
            return Color.GREEN;
        } else if (TgConstantWorkflowAduitStatus.NO == integer) {
            return Color.RED;
        } else if (TgConstantWorkflowAduitStatus.PROCESS == integer) {
            return Color.parseColor("#EE8502");
        } else {
            return Color.parseColor("#2385D3");
        }
    }
}
