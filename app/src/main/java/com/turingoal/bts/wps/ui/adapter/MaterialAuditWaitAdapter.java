package com.turingoal.bts.wps.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.bean.MaterialUsage;
import com.turingoal.common.android.util.lang.TgDateUtil;

/**
 * 待审核列表的adapter
 */
public class MaterialAuditWaitAdapter extends BaseQuickAdapter<MaterialUsage, BaseViewHolder> {
    public MaterialAuditWaitAdapter() {
        super(R.layout.app_item_material_audit_wait);
    }

    @Override
    protected void convert(BaseViewHolder holper, final MaterialUsage materialUsage) {
        holper.setText(R.id.tvCreateTime, "时间：" + TgDateUtil.date2String(materialUsage.getCreateTime(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_ZH))
                .setText(R.id.tvCreateName, "领用人：" + materialUsage.getUsageUserRealname());
        holper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TgSystemHelper.handleIntentWithObj(TgArouterPaths.MATERIAL_AUDIT, "materialUsage", materialUsage);
            }
        });
    }
}
