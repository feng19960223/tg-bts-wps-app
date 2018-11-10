package com.turingoal.bts.wps.ui.activity;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.turingoal.bts.common.android.biz.domain.BreakdownRecordItem;
import com.turingoal.bts.common.android.constants.ConstantMontorBreakdownPattern;
import com.turingoal.bts.common.android.constants.ConstantMontorBreakdownSource;
import com.turingoal.bts.common.android.constants.ConstantMontorBreakdownSystemTypes;
import com.turingoal.bts.common.android.constants.ConstantStatus4Work;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.service.BreakdownRecordItemService;
import com.turingoal.bts.wps.ui.adapter.PhotoDetailAdapter;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.constants.TgConstantYesNo;
import com.turingoal.common.android.ui.layout.TgTitleContextLinearLayout;
import com.turingoal.common.android.util.lang.TgDateUtil;

import butterknife.BindView;

/**
 * [故障详情]
 */
@Route(path = TgArouterPaths.BREAKDOWN_DETAIL)
public class BreakdownRecordDetailActivity extends TgBaseActivity {
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
    @BindView(R.id.ll_dispatch)
    LinearLayout llDipatch; // 如果没有派工，隐藏
    @BindView(R.id.tclDispatchTime)
    TgTitleContextLinearLayout tclDispatchTime; // 派工时间
    @BindView(R.id.tclDispatchRealname)
    TgTitleContextLinearLayout tclDispatchRealname; // 派工人员
    @BindView(R.id.tclDispatchPlanStartTime)
    TgTitleContextLinearLayout tclDispatchPlanStartTime; // 计划开始
    @BindView(R.id.tclDispatchPlanFinishTime)
    TgTitleContextLinearLayout tclDispatchPlanFinishTime; // 计划结束
    @BindView(R.id.ll_handler)
    LinearLayout llHandler; // 如果没有处理，隐藏
    @BindView(R.id.tclHandlerRealname)
    TgTitleContextLinearLayout tclHandlerRealname; // 维修人员
    @BindView(R.id.tclQcRealname)
    TgTitleContextLinearLayout tclQcRealname; // 质检人员
    @BindView(R.id.tclHandlingStartTime)
    TgTitleContextLinearLayout tclHandlingStartTime; // 维修开始
    @BindView(R.id.tclHandlingFinishTime)
    TgTitleContextLinearLayout tclHandlingFinishTime; // 维修结束
    @BindView(R.id.tvHandlingDesc)
    TextView tvHandlingDesc; // 处理描述
    @BindView(R.id.ll_qc)
    LinearLayout llQc; // 如果没有质检，隐藏
    @BindView(R.id.tclQcStartTime)
    TgTitleContextLinearLayout tclQcStartTime; // 质检开始
    @BindView(R.id.tclQcFinishTime)
    TgTitleContextLinearLayout tclQcFinishTime; // 质检结束
    @BindView(R.id.tvQcDesc)
    TextView tvQcDesc; // 质检描述
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView; // recyclerView
    private static final int SPAN_COUNT = 3; // 照片列数
    @Autowired
    BreakdownRecordItem breakdownRecordItem; // ARouter自动解析参数对象

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_breakdown_detail;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tv_title, R.id.iv_back, getString(R.string.title_breakdown_detail));
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
        tclHandlerRealname.setContextStr("" + breakdownRecordItem.getHandlerUserRealname());
        tclQcRealname.setContextStr("" + breakdownRecordItem.getQcUserRealname());
        tclHandlingStartTime.setContextStr(TgDateUtil.date2String(breakdownRecordItem.getHandlingStartTime(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS_ZH));
        tclHandlingFinishTime.setContextStr(TgDateUtil.date2String(breakdownRecordItem.getHandlingFinishTime(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS_ZH));
        tvHandlingDesc.setText("" + breakdownRecordItem.getHandlingDesc());
        tclQcStartTime.setContextStr(TgDateUtil.date2String(breakdownRecordItem.getQcStartTime(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS_ZH));
        tclQcFinishTime.setContextStr(TgDateUtil.date2String(breakdownRecordItem.getQcFinishTime(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS_ZH));
        tvQcDesc.setText("" + breakdownRecordItem.getQcDesc());
        tclStatus.setContextTextColor(Color.RED); // 红色
        if (breakdownRecordItem.getDispatchStatus() == TgConstantYesNo.NO) { // 未派工隐藏
            llDipatch.setVisibility(View.GONE);
            tclStatus.setContextStr("未派工");
        } else {
            llDipatch.setVisibility(View.VISIBLE);
            tclStatus.setContextStr("维修" + ConstantStatus4Work.getStr(breakdownRecordItem.getHandlingStatus()));
            if (ConstantStatus4Work.FINISHED.equals(breakdownRecordItem.getHandlingStatus())) { // 故障已经处理
                llHandler.setVisibility(View.VISIBLE);
                tclStatus.setContextStr("质检" + ConstantStatus4Work.getStr(breakdownRecordItem.getQcStatus()));
                if (ConstantStatus4Work.FINISHED.equals(breakdownRecordItem.getQcStatus())) { // 质检已经处理
                    llQc.setVisibility(View.VISIBLE);
                    tclStatus.setContextTextColor(Color.GREEN); // 绿色
                } else {
                    llQc.setVisibility(View.GONE);
                }
            } else {
                llHandler.setVisibility(View.GONE);
            }
        }
        TgApplication.getRetrofit().create(BreakdownRecordItemService.class)
                .get(breakdownRecordItem.getId())
                .enqueue(new TgRetrofitCallback<BreakdownRecordItem>(BreakdownRecordDetailActivity.this, true, true) {
                    @Override
                    public void successHandler(BreakdownRecordItem breakdownRecordItem) {
                        if (breakdownRecordItem.getFilePaths() != null) {
                            if (breakdownRecordItem.getFilePaths().size() > 0) {
                                recyclerView.setLayoutManager(new GridLayoutManager(BreakdownRecordDetailActivity.this, SPAN_COUNT));
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
}
