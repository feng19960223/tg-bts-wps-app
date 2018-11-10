package com.turingoal.bts.wps.ui.activity.common;

import android.text.TextUtils;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.service.UserService;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.common.android.app.TgArouterCommonPaths;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.bean.TgValidateResultBean;
import com.turingoal.common.android.ui.widget.TgLongClickImageView;
import com.turingoal.common.android.util.math.TgEncryptUtil;
import com.turingoal.common.android.util.ui.TgToastUtil;
import com.turingoal.common.android.validatror.TgUsernamePasswordValidator;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录页
 */
@Route(path = TgArouterCommonPaths.COMMON_LOGIN)
public class TgLoginActivity extends TgBaseActivity {
    @BindView(R.id.iv_logo)
    TgLongClickImageView ivLogo; //logo图片
    @BindView(R.id.et_user)
    EditText etUsername; //用户名框
    @BindView(R.id.et_pass)
    EditText etPassword; // 密码框

    @Override
    protected int getLayoutId() {
        return R.layout.common_activity_login;
    }

    @Override
    protected void initialized() {
        // 长按logo进入服务器配置界面
        ivLogo.setClickListenere(new TgLongClickImageView.LongClickListener() {
            @Override
            public void longClick() {
                TgSystemHelper.handleIntent(TgArouterCommonPaths.COMMON_AMIN_CONFIG_SERVER); // 进入服务器配置界面
            }
        });
        String username = TgApplication.getTgUserPreferences().getUsername();
        if (!TextUtils.isEmpty(username)) {
            etUsername.setText(username);
            etPassword.requestFocus();
        }
    }

    /**
     * 登录
     */
    @OnClick(R.id.btn_login)
    public void goLogin() {
        String username = etUsername.getText().toString().trim(); // 用户名
        String userPass = etPassword.getText().toString().trim(); // 密码
        // 校验用户名和密码
        TgValidateResultBean validateResult = TgUsernamePasswordValidator.validateUsernameAndPassword(username, userPass);
        if (!validateResult.isSuccess()) { // 验证失败
            TgToastUtil.showLong(validateResult.getMsg());
        } else {
            loginRequest(username, userPass); // 登录请求
        }
    }

    /**
     * 登录请求
     */
    private void loginRequest(final String username, final String userPass) {
        String userPassStr = TgEncryptUtil.encryptMD5ToString("tg_pass" + userPass).toLowerCase();
        TgApplication.getRetrofit().create(UserService.class)
                .login(username, userPassStr)
                .enqueue(new TgRetrofitCallback<Map<String, Object>>(this, true, false) {
                    @Override
                    public void successHandler(Map<String, Object> userInfoMap) {
                        TgSystemHelper.setUserInfo(userInfoMap); // 保存用户信息
                        TgSystemHelper.handleIntentAndFinish(TgArouterCommonPaths.MAIN_INDEX, TgLoginActivity.this); // 跳转到主页面,关闭当前页面
                    }
                });
    }

    /**
     * 点击返回按钮
     */
    @Override
    public void onBackPressed() {
        TgSystemHelper.dbClickExit(); //  再按一次退出系统
    }
}
