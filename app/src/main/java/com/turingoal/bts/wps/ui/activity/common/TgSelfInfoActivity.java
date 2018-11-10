package com.turingoal.bts.wps.ui.activity.common;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.bean.BtsRole;
import com.turingoal.common.android.app.TgArouterCommonPaths;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.util.ui.TgDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人信息
 */
@Route(path = TgArouterCommonPaths.COMMON_SELF_INFO)
public class TgSelfInfoActivity extends TgBaseActivity {
    @BindView(R.id.info_tv_name)
    TextView tvRealname; // 姓名
    @BindView(R.id.info_tv_tel)
    TextView tvTel; // 电话
    @BindView(R.id.info_tv_email)
    TextView tvEMail; // 邮箱
    @BindView(R.id.info_tv_workGroupName)
    TextView tvWorkGroupName; // 检修班组
    @BindView(R.id.info_tv_userType)
    TextView tvUserType; // 用户类型

    @Override
    protected int getLayoutId() {
        return R.layout.common_activity_self_info;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tg_common_view_title_bar_tvTitle, R.id.tg_common_view_title_bar_ivLeftBut, getString(R.string.title_common_self_info)); // 顶部工具条
        tvRealname.setText(TgApplication.getTgUserPreferences().getRealname()); //用户真实姓名
        tvTel.setText(TgApplication.getTgUserPreferences().getCellphoneNumber()); //用户手机号
        tvEMail.setText(TgApplication.getTgUserPreferences().getEmail()); //用户邮箱
        tvWorkGroupName.setText(TgApplication.getTgUserPreferences().getWorkGroupName()); // 检修班组
        tvUserType.setText(BtsRole.getRoleMap().get(TgApplication.getTgUserPreferences().getRoleCodeNum()));
    }

    /**
     * onClick
     */
    @OnClick({R.id.updata_password, R.id.logoff})
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.updata_password:
                TgSystemHelper.handleIntent(TgArouterCommonPaths.COMMON_CHANGE_PASSWORD); // 跳转到修改密码页面
                break;
            case R.id.logoff:
                TgDialogUtil.showDialog(this, getString(R.string.string_logoff_hint), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        TgSystemHelper.logoff(TgSelfInfoActivity.this); // 注销并跳转到登录页面

                    }
                });
                break;
            default:
                break;
        }
    }
}
