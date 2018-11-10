package com.turingoal.bts.wps.ui.adapter;

import android.graphics.Color;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.bean.ConstantMaterialTypes;
import com.turingoal.bts.wps.bean.MaterialTempletItem;

import java.math.BigDecimal;

/**
 * 模板item的adapter
 */
public class MaterialTemplateItemAdapter extends BaseQuickAdapter<MaterialTempletItem, BaseViewHolder> {
    public MaterialTemplateItemAdapter() {
        super(R.layout.app_item_material_template_item);
    }

    @Override
    protected void convert(BaseViewHolder holper, final MaterialTempletItem materialTempletItem) {
        holper.setText(R.id.tvTitle, materialTempletItem.getTitle())
                .setText(R.id.tvCodeNum, materialTempletItem.getCodeNum())
                .setText(R.id.tvMaterialType, ConstantMaterialTypes.getStr(materialTempletItem.getMaterialType()))
                .setTextColor(R.id.tvMaterialType, getMaterialTypeColor(materialTempletItem.getMaterialType()))
                .setText(R.id.tvNum, "x" + materialTempletItem.getQuantity().setScale(2, BigDecimal.ROUND_HALF_UP) + materialTempletItem.getUnit());
        holper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TgSystemHelper.handleIntentWithObj(TgArouterPaths.MATERIAL_TEMPLATE_ITEM_DETAIL, "materialTempletItem", materialTempletItem);
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
