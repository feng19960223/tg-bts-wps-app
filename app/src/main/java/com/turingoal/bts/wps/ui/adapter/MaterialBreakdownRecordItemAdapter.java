package com.turingoal.bts.wps.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.turingoal.bts.common.android.biz.domain.BreakdownRecordItem;
import com.turingoal.bts.common.android.constants.ConstantMontorBreakdownSystemTypes;
import com.turingoal.bts.wps.R;

/**
 * 物料，故障adapter
 */
public class MaterialBreakdownRecordItemAdapter extends BaseQuickAdapter<BreakdownRecordItem, BaseViewHolder> {

    public MaterialBreakdownRecordItemAdapter() {
        super(R.layout.app_item_material_breakdown);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final BreakdownRecordItem breakdownRecordItem) {
        holder.setText(R.id.tv_item_1, "车组：" + breakdownRecordItem.getTrainSetCodeNum())
                .setText(R.id.tv_item_2, "车厢：" + breakdownRecordItem.getCarriage())
                .setText(R.id.tv_item_3, "分类：" + ConstantMontorBreakdownSystemTypes.getStr(breakdownRecordItem.getSystemType()))
                .setText(R.id.tv_item_4, "发现人：" + breakdownRecordItem.getHandlerUserRealname());
    }
}
