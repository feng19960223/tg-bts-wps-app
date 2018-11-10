package com.turingoal.bts.wps.ui.activity.common;

import android.Manifest;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.turingoal.android.photopicker.permission.PermissionListener;
import com.turingoal.android.photopicker.permission.PermissionManager;
import com.turingoal.bts.wps.BuildConfig;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgAppConfig;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.service.UserService;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.common.android.app.TgArouterCommonPaths;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.bean.TgKeyValueBean;
import com.turingoal.common.android.util.lang.TgStringUtil;
import com.turingoal.common.android.util.system.TgAppUtil;
import com.turingoal.common.android.util.system.TgSystemUtil;
import com.turingoal.common.android.util.ui.TgDialogUtil;

import java.util.Map;

/**
 * 欢迎页
 */
public class TgWelcomeActivity extends TgBaseActivity {
    private PermissionManager helper; // 权限申请
    private static final int REQUEST_CODE = 1001;

    @Override
    protected int getLayoutId() {
        return R.layout.common_activity_welcome;
    }

    @Override
    protected void initialized() {
        helper = PermissionManager.with(this)
                .addRequestCode(REQUEST_CODE)  // 添加权限请求码
                .permissions( // 设置权限，可以添加多个权限
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                )
                .setPermissionsListener(new PermissionListener() { // 设置权限监听器
                    @Override
                    public void onGranted() {
                        checkVersion();
                    }

                    @Override
                    public void onDenied() {
                        defaultFinish();
                    }

                    @Override
                    public void onShowRationale(String[] permissions) {
                        helper.setIsPositive(true);
                        helper.request();
                    }
                }).request(); // 请求权限
    }

    /**
     * 请求权限结果处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                helper.onPermissionResult(permissions, grantResults);
                break;
        }
    }

    /**
     * 检查版本
     */
    private void checkVersion() {
        TgApplication.getRetrofit().create(UserService.class)
                .checkVersion(BuildConfig.VERSION_CODE, BuildConfig.APPLICATION_ID)
                .enqueue(new TgRetrofitCallback<TgKeyValueBean>(this, false, false) {
                    @Override
                    public void successHandler(final TgKeyValueBean bean) {
                        final String appDownloadUrl = bean.getStringValue();
                        if (TgStringUtil.isBlank(appDownloadUrl)) {
                            checkToken(); // 检查token
                        } else {
                            TgDialogUtil.showDialog(TgWelcomeActivity.this, "版本检查", "检查到有新版本，是否马上下载安装?",
                                    new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            TgSystemUtil.openWebSite(appDownloadUrl);
                                            defaultFinish();
                                            TgAppUtil.exitApp();
                                        }
                                    }, new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            checkToken(); // 检查token
                                        }
                                    });
                        }
                    }

                    @Override
                    public void failHandler(String msg) {
                        checkToken(); // 检查token
                    }
                });
    }

    /**
     * 检查token
     */
    private void checkToken() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String token = TgApplication.getTgUserPreferences().getToken(); // 获取token
                if (TextUtils.isEmpty(token)) {
                    TgSystemHelper.handleIntentAndFinish(TgArouterCommonPaths.COMMON_LOGIN, TgWelcomeActivity.this); //跳转到登录页面,关闭当前页面
                } else { // 有token
                    TgApplication.getRetrofit().create(UserService.class)
                            .checkToken()
                            .enqueue(new TgRetrofitCallback<Map<String, Object>>(TgWelcomeActivity.this, false, false) {
                                @Override
                                public void successHandler(Map<String, Object> userInfoMapt) {
                                    TgSystemHelper.setUserInfo(userInfoMapt); // 成功
                                    TgSystemHelper.handleIntentAndFinish(TgArouterCommonPaths.MAIN_INDEX, TgWelcomeActivity.this); // 跳转到主页面,关闭当前页面
                                }

                                @Override
                                public void failHandler(String msg) {
                                    TgSystemHelper.handleIntentAndFinish(TgArouterCommonPaths.COMMON_LOGIN, TgWelcomeActivity.this); //跳转到登录页面,关闭当前页面
                                }
                            });
                }
            }
        }, TgAppConfig.WELCOME_DELAY_TIME); //设置延迟，再进入正式界面
    }
}
