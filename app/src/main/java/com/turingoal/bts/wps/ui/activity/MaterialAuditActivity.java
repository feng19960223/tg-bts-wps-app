package com.turingoal.bts.wps.ui.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.utils.TextUtils;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.bean.MaterialUsage;
import com.turingoal.bts.wps.bean.MaterialUsageItem;
import com.turingoal.bts.wps.service.MaterialService;
import com.turingoal.bts.wps.ui.adapter.MaterialUsageItemAdapter;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.ui.layout.TgTitleContextLinearLayout;
import com.turingoal.common.android.util.lang.TgDateUtil;
import com.turingoal.common.android.util.ui.TgDialogUtil;
import com.turingoal.common.android.util.ui.TgToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 审核
 */
@Route(path = TgArouterPaths.MATERIAL_AUDIT)
public class MaterialAuditActivity extends TgBaseActivity implements CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tclCreateTime)
    TgTitleContextLinearLayout tclCreateTime; // 创建时间
    @BindView(R.id.tclCreateName)
    TgTitleContextLinearLayout tclCreateName; // 创建人员
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView; // recyclerView
    @BindView(R.id.rbState)
    RadioButton rbState; // 如果选中是同意，没选中是不同意
    @BindView(R.id.etIdea)
    EditText etIdea; // 意见
    @BindView(R.id.tvIdeaCount)
    TextView tvIdeaCount; // 意见字数
    private MaterialUsageItemAdapter adapter = new MaterialUsageItemAdapter(); // adapter
    private boolean isEdit; // 是否编辑过
    @Autowired
    MaterialUsage materialUsage;

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_material_audit;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tv_title, R.id.iv_back, getString(R.string.title_material_audit), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEditDialog();
            }
        });
        ivRight.setImageResource(R.mipmap.app_ic_right_title);
        ivRight.setVisibility(View.VISIBLE);
        tclCreateTime.setContextStr(TgDateUtil.date2String(materialUsage.getCreateTime(), TgDateUtil.FORMAT_YYYY_MM_DD_ZH));
        tclCreateName.setContextStr(materialUsage.getUsageUserRealname());
        rbState.setOnCheckedChangeListener(this);
        etIdea.addTextChangedListener(ideaWatcher);
        initAdapter();
        initData();
    }

    /**
     * 初始化adapter
     */
    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.openLoadAnimation(); // 动画
        adapter.setEmptyView(getNotDataView((ViewGroup) recyclerView.getParent())); // 设置空白view
        recyclerView.setAdapter(adapter);
    }

    /**
     * 得到数据
     */
    private void initData() {
        TgApplication.getRetrofit().create(MaterialService.class)
                .findMaterialUsageItems(materialUsage.getId())
                .enqueue(new TgRetrofitCallback<List<MaterialUsageItem>>(this, true, true) {
                    @Override
                    public void successHandler(List<MaterialUsageItem> materialUsageItems) {
                        adapter.setNewData(materialUsageItems);
                    }
                });

    }

    @OnClick({R.id.btnAudit, R.id.iv_right})
    public void onClick(final View view) { // 确定审核
        switch (view.getId()) {
            case R.id.btnAudit:
                if (TextUtils.isEmpty(etIdea.getText().toString().trim())) {
                    TgToastUtil.showShort(R.string.stirng_material_audit_no_idea_hint);
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("id", materialUsage.getId());
                map.put("dealType", rbState.isChecked() ? 1 : 2);
                map.put("dealRemarks", etIdea.getText().toString().trim());
                TgApplication.getRetrofit().create(MaterialService.class)
                        .auditByForeman(map)
                        .enqueue(new TgRetrofitCallback<Object>(this, true, true) {
                            @Override
                            public void successHandler(Object obj) {
                                TgToastUtil.showLong(R.string.string_material_audit_success_hint);
                                EventBus.getDefault().post("MaterialAuditSuccess");
                                defaultFinish();
                            }
                        });
                break;
            case R.id.iv_right:
                TgSystemHelper.handleIntentWithObj(TgArouterPaths.MATERIAL_AUDIT_PROCESS, "materialUsage", materialUsage);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        isEditDialog();
    }

    /**
     * 如果用户编辑过数据，但是没有保存，提示是否没有保存就退出
     */
    private void isEditDialog() {
        if (isEdit) {
            TgDialogUtil.showDialog(this, getString(R.string.stirng_material_audit_no_save_hint), new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    defaultFinish();
                }
            });
        } else {
            defaultFinish();
        }
    }

    /**
     * 意见内容长度监听
     */
    private TextWatcher ideaWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
        }

        @Override
        public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
        }

        @Override
        public void afterTextChanged(final Editable editable) {
            isEdit = true;
            tvIdeaCount.setText("" + editable.toString().trim().length() + "/140"); // 文字计数 140个
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        isEdit = true;
    }
}
