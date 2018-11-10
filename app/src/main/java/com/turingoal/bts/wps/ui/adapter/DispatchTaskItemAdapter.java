package com.turingoal.bts.wps.ui.adapter;

import android.graphics.Color;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.turingoal.bts.common.android.biz.domain.DispatchTaskItem;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.constants.ConstantWpsCommon;


/**
 * 任务管理详情adapter
 */
public class DispatchTaskItemAdapter extends BaseQuickAdapter<DispatchTaskItem, BaseViewHolder> {
    public DispatchTaskItemAdapter() {
        super(R.layout.item_dispatch_detail);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final DispatchTaskItem dispatchTaskItem) {
        holder.setText(R.id.tv_group, "" + dispatchTaskItem.getWorkNum())
                .setText(R.id.tv_name, "" + dispatchTaskItem.getWorkUserRealname())
                .setText(R.id.tv_grade, dispatchTaskItem.getScore() + "")
                .setTextColor(R.id.tv_grade, dispatchTaskItem.getScore() > ConstantWpsCommon.PASS_SCORE ? Color.GREEN : Color.RED);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                TgSystemHelper.handleIntentWithObj(TgArouterPaths.WORKRECORD_DETAIL, "dispatchTaskItem", dispatchTaskItem); //跳转到详情页面
            }
        });
    }

}
