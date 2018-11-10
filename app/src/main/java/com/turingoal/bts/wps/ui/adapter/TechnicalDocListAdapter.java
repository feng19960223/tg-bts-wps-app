package com.turingoal.bts.wps.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.turingoal.bts.common.android.biz.domain.TechnicalDoc;
import com.turingoal.bts.wps.R;
import com.turingoal.common.android.util.lang.TgDateUtil;

/**
 * 技术资料适配器
 */
public class TechnicalDocListAdapter extends BaseQuickAdapter<TechnicalDoc, BaseViewHolder> {
    public TechnicalDocListAdapter() {
        super(R.layout.app_item_word);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final TechnicalDoc item) {
        holder.setText(R.id.material_title, item.getTitle()) // 标题
                .setText(R.id.material_desc, item.getDescription()) // 简介
                .setText(R.id.material_date, TgDateUtil.date2String(item.getUploadTime(), TgDateUtil.FORMAT_YYYY_MM_DD)); // 上传时间
    }
}
