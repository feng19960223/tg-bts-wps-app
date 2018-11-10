package com.turingoal.bts.wps.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.turingoal.bts.common.android.biz.domain.DispatchTaskItem;
import com.turingoal.bts.common.android.biz.domain.SchedulingTask;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.service.TaskService;
import com.turingoal.bts.wps.ui.adapter.DispatchTaskItemAdapter;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.constants.TgConstantYesNo;
import com.turingoal.common.android.util.lang.TgDateUtil;
import com.turingoal.common.android.util.lang.TgStringUtil;

import java.util.List;

import butterknife.BindView;

/**
 * 任务管理详情
 */
@Route(path = TgArouterPaths.SCHEDULING_TASK_DETAIL)
public class SchedulingTaskDetailActivity extends TgBaseActivity {
    @BindView(R.id.tv_date)
    TextView tvDate; // 任务日期
    @BindView(R.id.tvTrainSetCodeNum)
    TextView tvTrainSetCodeNum; // 车号
    @BindView(R.id.tvTrackRowNumAndTrackRowNum)
    TextView tvTrackRowNumAndTrackRowNum; // 股道
    @BindView(R.id.tvContactPersonRealname)
    TextView tvContactPersonRealname; // 创建人
    @BindView(R.id.tvArriveTime)
    TextView tvArriveTime; // 入库时间
    @BindView(R.id.tvMaintenanceTask)
    TextView tvMaintenanceTask; // 检修任务
    @BindView(R.id.tvMaintenanceTaskItem)
    TextView tvMaintenanceTaskItem; // 检修项目
    @BindView(R.id.tvDispatchPlanStartTime)
    TextView tvDispatchPlanStartTime; // 计划开始
    @BindView(R.id.tvDispatchPlanFinishTime)
    TextView tvDispatchPlanFinishTime; // 计划结束
    @BindView(R.id.tvDispatchStatus)
    TextView tvDispatchStatus; // 派工状态
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView; // RecyclerView
    DispatchTaskItemAdapter mAdapter = new DispatchTaskItemAdapter(); // adapter
    @Autowired
    SchedulingTask schedulingTask; // ARouter自动解析参数对象

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_scheduling_task_detail;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tv_title, R.id.iv_back, "任务详情"); // 顶部工具条
        tvDate.setText(TgDateUtil.date2String(schedulingTask.getTaskDate(), TgDateUtil.FORMAT_YYYY_MM_DD_ZH)); // 日期
        tvTrainSetCodeNum.setText("" + schedulingTask.getTrainSetCodeNum()); // 车号
        tvTrackRowNumAndTrackRowNum.setText("" + schedulingTask.getTrackCodeNum() + "-" + schedulingTask.getTrackRowNum()); // 股道
        tvContactPersonRealname.setText("" + schedulingTask.getContactPersonRealname()); // 创建人
        String arriveTime = TgDateUtil.date2String(schedulingTask.getArriveTime(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM);
        tvArriveTime.setText(TgStringUtil.isBlank(arriveTime) ? "---" : arriveTime); // 入库时间
        tvMaintenanceTask.setText("" + schedulingTask.getMaintenanceTask()); // 检修任务
        tvMaintenanceTaskItem.setText("" + schedulingTask.getMaintenanceTaskItem()); // 检修项目
        tvDispatchPlanStartTime.setText(TgDateUtil.date2String(schedulingTask.getDispatchPlanStartTime(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS_ZH)); // 计划开始
        tvDispatchPlanFinishTime.setText(TgDateUtil.date2String(schedulingTask.getDispatchPlanFinishTime(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS_ZH)); // 计划结束
        if (schedulingTask.getDispatchStatus() == TgConstantYesNo.NO) {
            tvDispatchStatus.setText("未派工");
        } else {
            initAdapter();
            getData();
        }
    }

    /**
     * adapter
     */
    private void initAdapter() {
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.openLoadAnimation(); // 动画
        mAdapter.setEmptyView(getNotDataView((ViewGroup) recyclerView.getParent())); // 设置空白view
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * 得数据 完成打分
     */
    private void getData() {
        TgApplication.getRetrofit().create(TaskService.class)
                .findDispatchTaskItemsByTask(schedulingTask.getCodeNum())
                .enqueue(new TgRetrofitCallback<List<DispatchTaskItem>>(this, true, true) {
                    @Override
                    public void successHandler(List<DispatchTaskItem> list) {
                        mAdapter.setNewData(list);
                    }
                });
    }
}
