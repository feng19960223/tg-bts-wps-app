package com.turingoal.bts.wps.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.bean.MaterialTemplate;
import com.turingoal.common.android.util.lang.TgDateUtil;

/**
 * 模板的adapter
 */
public class MaterialTemplateAdapter extends BaseQuickAdapter<MaterialTemplate, BaseViewHolder> {
    public MaterialTemplateAdapter() {
        super(R.layout.app_item_material_template);
    }

    @Override
    protected void convert(BaseViewHolder holper, final MaterialTemplate materialTemplate) {
        holper.setText(R.id.tvTitle, materialTemplate.getTitle())
                .setText(R.id.tvCodeNum, materialTemplate.getDescription())
                .setText(R.id.tvCreateTime, TgDateUtil.date2String(materialTemplate.getCreateTime(), TgDateUtil.FORMAT_YYYY_MM_DD_ZH));
        holper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TgSystemHelper.handleIntentWithObj(TgArouterPaths.MATERIAL_TEMPLATE_DETAIL, "materialTemplate", materialTemplate);
            }
        });
        holper.addOnClickListener(R.id.tvUse);
    }
}
