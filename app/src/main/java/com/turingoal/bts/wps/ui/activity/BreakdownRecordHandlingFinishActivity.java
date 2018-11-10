package com.turingoal.bts.wps.ui.activity;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.turingoal.bts.common.android.biz.domain.BreakdownRecordItem;
import com.turingoal.bts.common.android.constants.ConstantMontorBreakdownPattern;
import com.turingoal.bts.common.android.constants.ConstantMontorBreakdownSource;
import com.turingoal.bts.common.android.constants.ConstantMontorBreakdownSystemTypes;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.service.BreakdownRecordItemService;
import com.turingoal.bts.wps.ui.adapter.PhotoDetailAdapter;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.ui.layout.TgTitleContextLinearLayout;
import com.turingoal.common.android.util.lang.TgDateUtil;
import com.turingoal.common.android.util.lang.TgStringUtil;
import com.turingoal.common.android.util.ui.TgToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * [故障处理结束]
 */
@Route(path = TgArouterPaths.BREAKDOWN_HANDLING_FINISH)
public class BreakdownRecordHandlingFinishActivity extends TgBaseActivity {
    @BindView(R.id.tclStatus)
    TgTitleContextLinearLayout tclStatus; // 故障状态
    @BindView(R.id.tclArriveTime)
    TgTitleContextLinearLayout tclArriveTime; // 创建时间
    @BindView(R.id.tclSource)
    TgTitleContextLinearLayout tclSource; // 信息来源
    @BindView(R.id.tclTrainFrequency)
    TgTitleContextLinearLayout tclTrainFrequency; // 故障车次
    @BindView(R.id.tclTrainSetCodeNum)
    TgTitleContextLinearLayout tclTrainSetCodeNum; // 故障车组
    @BindView(R.id.tclSystemType)
    TgTitleContextLinearLayout tclSystemType; // 故障类型
    @BindView(R.id.tclPattern)
    TgTitleContextLinearLayout tclPattern; // 故障模式
    @BindView(R.id.tclCarriage)
    TgTitleContextLinearLayout tclCarriage; // 故障车厢
    @BindView(R.id.tclCode)
    TgTitleContextLinearLayout tclCode; // 故障代码
    @BindView(R.id.tclDiscoveryTime)
    TgTitleContextLinearLayout tclDiscoveryTime; // 发现时间
    @BindView(R.id.tclDiscoveryRealname)
    TgTitleContextLinearLayout tclDiscoveryRealname; // 发现人员
    @BindView(R.id.tvDiscoveryDesc)
    TextView tvDiscoveryDesc; // 问题描述
    @BindView(R.id.tclDispatchTime)
    TgTitleContextLinearLayout tclDispatchTime; // 派工时间
    @BindView(R.id.tclDispatchRealname)
    TgTitleContextLinearLayout tclDispatchRealname; // 派工人员
    @BindView(R.id.tclDispatchPlanStartTime)
    TgTitleContextLinearLayout tclDispatchPlanStartTime; // 计划开始
    @BindView(R.id.tclDispatchPlanFinishTime)
    TgTitleContextLinearLayout tclDispatchPlanFinishTime; // 计划结束
    @BindView(R.id.tclHandlingStartTime)
    TgTitleContextLinearLayout tclHandlingStartTime; // 维修开始
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView; // recyclerView
    @BindView(R.id.etHandlingDesc)
    EditText etHandlingDesc; // 处理描述
    @BindView(R.id.tvHandlingDescCount)
    TextView tvHandlingDescCount; // 处理描述字数
    private static final int SPAN_COUNT = 3; // 照片列数
    @Autowired
    BreakdownRecordItem breakdownRecordItem; // ARouter自动解析参数对象

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_breakdown_handling_finish;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tv_title, R.id.iv_back, "结束故障检修");
        tclStatus.setContextTextColor(Color.RED); // 红色
        tclArriveTime.setContextStr(TgDateUtil.date2String(breakdownRecordItem.getCreateTime(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS_ZH));
        tclSource.setContextStr("" + ConstantMontorBreakdownSource.getStr(breakdownRecordItem.getSource()));
        tclTrainFrequency.setContextStr("" + breakdownRecordItem.getTrainFrequency());
        tclTrainSetCodeNum.setContextStr("" + breakdownRecordItem.getTrainSetCodeNum());
        tclSystemType.setContextStr("" + ConstantMontorBreakdownSystemTypes.getStr(breakdownRecordItem.getSystemType()));
        tclPattern.setContextStr("" + ConstantMontorBreakdownPattern.getStr(breakdownRecordItem.getPattern()));
        tclCarriage.setContextStr("" + breakdownRecordItem.getCarriage());
        tclCode.setContextStr("" + breakdownRecordItem.getBreakdownCode());
        tclDiscoveryTime.setContextStr(TgDateUtil.date2String(breakdownRecordItem.getDiscoveryTime(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS_ZH));
        tclDiscoveryRealname.setContextStr("" + breakdownRecordItem.getCreateUserRealname());
        tvDiscoveryDesc.setText("" + breakdownRecordItem.getDescription());
        tclDispatchTime.setContextStr(TgDateUtil.date2String(breakdownRecordItem.getDispatchTime(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS_ZH));
        tclDispatchRealname.setContextStr("" + breakdownRecordItem.getDispatchUserRealname());
        tclDispatchPlanStartTime.setContextStr(TgDateUtil.date2String(breakdownRecordItem.getDispatchPlanStartTime(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS_ZH));
        tclDispatchPlanFinishTime.setContextStr(TgDateUtil.date2String(breakdownRecordItem.getDispatchPlanFinishTime(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS_ZH));
        tclHandlingStartTime.setContextStr(TgDateUtil.date2String(breakdownRecordItem.getHandlingStartTime(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS_ZH));
        etHandlingDesc.addTextChangedListener(countWatcher);
        TgApplication.getRetrofit().create(BreakdownRecordItemService.class)
                .get(breakdownRecordItem.getId())
                .enqueue(new TgRetrofitCallback<BreakdownRecordItem>(this, true, true) {
                    @Override
                    public void successHandler(BreakdownRecordItem breakdownRecordItem) {
                        if (breakdownRecordItem.getFilePaths() != null) {
                            if (breakdownRecordItem.getFilePaths().size() > 0) {
                                recyclerView.setLayoutManager(new GridLayoutManager(BreakdownRecordHandlingFinishActivity.this, SPAN_COUNT));
                                recyclerView.setNestedScrollingEnabled(false);
                                recyclerView.setAdapter(new PhotoDetailAdapter(breakdownRecordItem.getFilePaths()));
                            } else {
                                recyclerView.setVisibility(View.GONE);
                            }
                        } else {
                            recyclerView.setVisibility(View.GONE);
                        }
                    }
                });
    }

    /**
     * 问题描述内容长度监听
     */
    private TextWatcher countWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
        }

        @Override
        public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
        }

        @Override
        public void afterTextChanged(final Editable editable) {
            tvHandlingDescCount.setText("" + editable.toString().trim().length() + "/140"); // 文字计数 140个
        }
    };

    @OnClick(R.id.btnFinish)
    public void breakdownHandlingFinish() {
        String handlingDesc = etHandlingDesc.getText().toString().trim();
        if (TgStringUtil.isBlank(handlingDesc)) {
            TgToastUtil.showLong("请填写处理描述！");
            return;
        }
        TgApplication.getRetrofit().create(BreakdownRecordItemService.class)
                .finish(breakdownRecordItem.getId(), handlingDesc)
                .enqueue(new TgRetrofitCallback<Object>(this, true, true) {
                    @Override
                    public void successHandler(final Object obj) {
                        TgToastUtil.showLong("结束故障检修作业成功！");
                        EventBus.getDefault().post("UpDataSuccess");
                        defaultFinish();
                    }
                });
    }
}
