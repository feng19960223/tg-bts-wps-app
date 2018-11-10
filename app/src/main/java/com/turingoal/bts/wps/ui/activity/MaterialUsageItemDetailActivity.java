package com.turingoal.bts.wps.ui.activity;

import android.graphics.Color;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.bean.ConstantMaterialTypes;
import com.turingoal.bts.wps.bean.MaterialUsageItem;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.ui.layout.TgTitleContextLinearLayout;

import butterknife.BindView;

/**
 * 领用物料审核item详情
 */
@Route(path = TgArouterPaths.MATERIAL_USAGE_ITEM_DETAIL)
public class MaterialUsageItemDetailActivity extends TgBaseActivity {
    @BindView(R.id.tclCodeNum)
    TgTitleContextLinearLayout tclCodeNum;
    @BindView(R.id.tclTitle)
    TgTitleContextLinearLayout tclTitle;
    @BindView(R.id.tclMaterialType)
    TgTitleContextLinearLayout tclMaterialType;
    @BindView(R.id.tclMaterialSpecification)
    TgTitleContextLinearLayout tclMaterialSpecification;
    @BindView(R.id.tclMaterialUnit)
    TgTitleContextLinearLayout tclMaterialUnit;
    @Autowired
    MaterialUsageItem materialUsageItem;

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_material_usage_item_detail;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tg_common_view_title_bar_tvTitle, R.id.tg_common_view_title_bar_ivLeftBut, getString(R.string.title_material_add));
        tclCodeNum.setContextStr(materialUsageItem.getMaterialCodeNum());
        tclTitle.setContextStr(materialUsageItem.getTitle());
        tclMaterialType.setContextStr(ConstantMaterialTypes.getStr(materialUsageItem.getMaterialType()));
        tclMaterialType.setContextTextColor(getMaterialTypeColor(materialUsageItem.getMaterialType()));
        tclMaterialSpecification.setContextStr("" + materialUsageItem.getSpecification());
        tclMaterialUnit.setContextStr("" + materialUsageItem.getUnit());
    }

    private int getMaterialTypeColor(String materialType) {
        if (ConstantMaterialTypes.FITTINGS_MATERIAL.equals(materialType)) {
            return Color.parseColor("#EE8502");
        } else if (ConstantMaterialTypes.LARGE_MATERIAL.equals(materialType)) {
            return Color.parseColor("#2385D3");
        } else if (ConstantMaterialTypes.SMALL_MATERIAL.equals(materialType)) {
            return Color.parseColor("#00CC99");
        } else {
            return Color.BLACK;
        }
    }
}
