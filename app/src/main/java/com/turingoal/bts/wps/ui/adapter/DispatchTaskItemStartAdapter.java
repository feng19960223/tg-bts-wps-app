package com.turingoal.bts.wps.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.turingoal.bts.common.android.biz.domain.DispatchTaskItem;
import com.turingoal.bts.wps.R;


/**
 * 任务管理详情adapter
 */
public class DispatchTaskItemStartAdapter extends BaseQuickAdapter<DispatchTaskItem, BaseViewHolder> {
    public DispatchTaskItemStartAdapter() {
        super(R.layout.item_dispatch_task_item_start);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final DispatchTaskItem dispatchTaskItem) {
        holder.setText(R.id.tvGroup, "" + dispatchTaskItem.getWorkNum())
                .setText(R.id.tvName, "" + dispatchTaskItem.getWorkUserRealname());
    }

}
