package com.turingoal.bts.wps.ui.activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.bean.ConstantMaterialTypes;
import com.turingoal.bts.wps.bean.MaterialUsageItem;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.ui.layout.TgBigDecimalNumAddSubLinearLayout;
import com.turingoal.common.android.ui.layout.TgTitleContextLinearLayout;
import com.turingoal.common.android.util.ui.TgToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 可以修改数量的添加物料物料
 */
@Route(path = TgArouterPaths.MATERIAL_USAGE_ITEM_ADD)
public class MaterialUsageItemAddActivity extends TgBaseActivity {
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
    @BindView(R.id.nasNum)
    TgBigDecimalNumAddSubLinearLayout nasNum;
    @Autowired
    MaterialUsageItem materialUsageItem;

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_material_usage_item_add;
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
        nasNum.setNumCallback(new TgBigDecimalNumAddSubLinearLayout.BigDecimalNumCallback() {
            @Override
            public void NumChange(BigDecimal bigDecimal, String s, BigDecimal bigDecimal1, String s1) {
                materialUsageItem.setQuantity(bigDecimal);
            }
        });
        nasNum.getTvNum().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(MaterialUsageItemAddActivity.this).title("数量")
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
    }

    @OnClick(R.id.btnMaterial)
    public void materialAdd() {
        EventBus.getDefault().post(materialUsageItem);
        TgToastUtil.showLong(R.string.string_material_add_success_hint);
        defaultFinish();
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
