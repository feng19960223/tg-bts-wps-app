package com.turingoal.bts.wps.ui.adapter;

import android.graphics.Color;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.bean.ConstantMaterialTypes;
import com.turingoal.bts.wps.bean.MaterialUsageItem;

import java.math.BigDecimal;

/**
 * 领用物料item的adapter
 */
public class MaterialUsageItemAdapter extends BaseQuickAdapter<MaterialUsageItem, BaseViewHolder> {
    public MaterialUsageItemAdapter() {
        super(R.layout.app_item_material_usage_item);
    }

    @Override
    protected void convert(BaseViewHolder holper, final MaterialUsageItem materialUsageItem) {
        holper.setText(R.id.tvTitle, materialUsageItem.getTitle())
                .setText(R.id.tvCodeNum, materialUsageItem.getMaterialCodeNum())
                .setText(R.id.tvMaterialType, ConstantMaterialTypes.getStr(materialUsageItem.getMaterialType()))
                .setTextColor(R.id.tvMaterialType, getMaterialTypeColor(materialUsageItem.getMaterialType()))
                .setText(R.id.tvNum, "x" + materialUsageItem.getQuantity().setScale(2, BigDecimal.ROUND_HALF_UP) + materialUsageItem.getUnit());
        holper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TgSystemHelper.handleIntentWithObj(TgArouterPaths.MATERIAL_USAGE_ITEM_DETAIL, "materialUsageItem", materialUsageItem);
            }
        });
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
