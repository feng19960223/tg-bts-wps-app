package com.turingoal.bts.wps.ui.adapter;

import android.graphics.Color;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.bean.ConstantMaterialTypes;
import com.turingoal.bts.wps.bean.Material;

/**
 * 物料带购物车按钮的adapter
 */
public class MaterialAddAdapter extends BaseQuickAdapter<Material, BaseViewHolder> {
    public MaterialAddAdapter() {
        super(R.layout.app_item_material_add);
    }

    @Override
    protected void convert(BaseViewHolder holper, final Material material) {
        holper.setText(R.id.tvTitle, material.getTitle())
                .setText(R.id.tvCodeNum, material.getCodeNum())
                .setText(R.id.tvMaterialType, ConstantMaterialTypes.getStr(material.getMaterialType()))
                .setTextColor(R.id.tvMaterialType, getMaterialTypeColor(material.getMaterialType()));
        holper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TgSystemHelper.handleIntentWithObj(TgArouterPaths.MATERIAL_ADD, "material", material);
            }
        });
        holper.addOnClickListener(R.id.fabAdd);
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
