package com.turingoal.bts.wps.ui.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.bean.ConstantMaterialTypes;
import com.turingoal.bts.wps.bean.MaterialUsageItem;
import com.turingoal.common.android.ui.layout.TgBigDecimalNumAddSubLinearLayout;
import com.turingoal.common.android.util.ui.TgDialogUtil;

import java.math.BigDecimal;

/**
 * MaterialUsageItem带加减的adapter,提交物料的第一个页面使用
 */
public class MaterialUsageItemNumAdapter extends BaseItemDraggableAdapter<MaterialUsageItem, BaseViewHolder> {
    public MaterialUsageItemNumAdapter() {
        super(R.layout.app_item_material_usage_item_num, null);
    }

    @Override
    protected void convert(final BaseViewHolder holper, final MaterialUsageItem materialUsageItem) {
        holper.setText(R.id.tvTitle, materialUsageItem.getTitle())
                .setText(R.id.tvCodeNum, materialUsageItem.getMaterialCodeNum())
                .setText(R.id.tvMaterialType, ConstantMaterialTypes.getStr(materialUsageItem.getMaterialType()))
                .setTextColor(R.id.tvMaterialType, getMaterialTypeColor(materialUsageItem.getMaterialType()));
        final TgBigDecimalNumAddSubLinearLayout nasNum = holper.getView(R.id.nasNum);
        Log.i(TAG, "convert: " + nasNum.getId() + materialUsageItem.getTitle() + "----" + materialUsageItem.getQuantity());
        nasNum.setNumber(materialUsageItem.getQuantity());
        nasNum.getTvNum().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 输入数量
                new MaterialDialog.Builder(mContext).title("数量")
                        .iconRes(R.mipmap.ic_launcher)
                        .content("输入物料" + materialUsageItem.getTitle() + "的数量")
                        .inputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)
                        .input("数量", "" + materialUsageItem.getQuantity(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                if (!TextUtils.isEmpty(input)) {
                                    nasNum.setNumber(new BigDecimal(input.toString()));
                                }
                            }
                        }).show();
            }
        });
        nasNum.setNumCallback(new TgBigDecimalNumAddSubLinearLayout.BigDecimalNumCallback() {
            @Override
            public void NumChange(BigDecimal bigDecimal, String s, BigDecimal bigDecimal1, String s1) { // // 数量发生改变
                materialUsageItem.setQuantity(bigDecimal);
            }
        });
        holper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 点击跳转到可以修改数量的物料领用页面
                TgSystemHelper.handleIntentWithObj(TgArouterPaths.MATERIAL_USAGE_ITEM_ADD, "materialUsageItem", materialUsageItem);
            }
        });
        holper.getView(R.id.ivDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TgDialogUtil.showDialog(mContext, "是否删除" + materialUsageItem.getTitle(), new MaterialDialog.SingleButtonCallback() {

                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        remove(holper.getAdapterPosition());
                    }
                });
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
