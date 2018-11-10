package com.turingoal.bts.wps.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.turingoal.bts.common.android.biz.domain.DispatchTaskItem;
import com.turingoal.bts.common.android.biz.domain.WorkRecordItem;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.service.WorkRecordService;
import com.turingoal.bts.wps.ui.adapter.WorkRecordDetailAdapter;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.util.lang.TgDateUtil;

import java.util.List;

import butterknife.BindView;

/**
 * 作业记录详情
 */
@Route(path = TgArouterPaths.WORKRECORD_DETAIL)
public class WorkRecordDetailActivity extends TgBaseActivity {
    @BindView(R.id.tv_date)
    TextView tvDate; // 日期
    @BindView(R.id.record_realname)
    TextView tvRealname; // 真实姓名
    @BindView(R.id.record_user_num)
    TextView tvUserNum; //  用户编号
    @BindView(R.id.record_detail_start_time)
    TextView tvStartTime; // 开始时间
    @BindView(R.id.record_detail_end_time)
    TextView tvEndTime; // 结束时间
    @BindView(R.id.record_train_set_code_num)
    TextView tvTrainSetCodeNum; // 车号
    @BindView(R.id.record_detail_track)
    TextView tvTrack; // 股道
    @BindView(R.id.record_maintenance_task)
    TextView tvMaintenanceTask; // 任务
    @BindView(R.id.record_maintenance_task_item)
    TextView tvMaintenanceTaskItem; // 项目
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView; // RecyclerView
    private WorkRecordDetailAdapter mAdapter = new WorkRecordDetailAdapter();
    @Autowired
    DispatchTaskItem dispatchTaskItem; // ARouter自动解析参数对象

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_work_record_detail;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tv_title, R.id.iv_back, "作业记录详情"); // 顶部工具条
        tvDate.setText(TgDateUtil.date2String(dispatchTaskItem.getTaskDate(), TgDateUtil.FORMAT_YYYY_MM_DD_ZH)); // 时间
        tvRealname.setText("" + dispatchTaskItem.getWorkUserRealname()); // 真实姓名
        tvUserNum.setText("" + dispatchTaskItem.getWorkNum()); // 作业编号
        tvTrainSetCodeNum.setText("" + dispatchTaskItem.getTrainSetCodeNum()); // 车号
        tvTrack.setText(dispatchTaskItem.getTrackCodeNum() + "-" + dispatchTaskItem.getTrackRowNum()); //股道
        tvMaintenanceTask.setText("" + dispatchTaskItem.getMaintenanceTask()); // 任务
        tvMaintenanceTaskItem.setText("" + dispatchTaskItem.getMaintenanceTaskItem()); // 项目
        tvStartTime.setText(TgDateUtil.date2String(dispatchTaskItem.getStartTime(), TgDateUtil.FORMAT_HH_MM_SS)); // 开始时间
        tvEndTime.setText(TgDateUtil.date2String(dispatchTaskItem.getFinishTime(), TgDateUtil.FORMAT_HH_MM_SS)); //结束时间
        initAdapter();
        getData(); // 获取数据
    }

    /**
     * 获取适配器
     */
    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.openLoadAnimation(); // 动画
        mAdapter.setEmptyView(getNotDataView((ViewGroup) mRecyclerView.getParent()));
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 获取数据
     */
    private void getData() {
        TgApplication.getRetrofit().create(WorkRecordService.class)
                .findItems(dispatchTaskItem.getId())
                .enqueue(new TgRetrofitCallback<List<WorkRecordItem>>(this, true, true) {
                    @Override
                    public void successHandler(List<WorkRecordItem> list) {
                        mAdapter.setNewData(list);
                    }
                });
    }
}
