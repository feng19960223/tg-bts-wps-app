package com.turingoal.bts.wps.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.turingoal.bts.common.android.constants.ConstantWorkShiftTypes;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.bean.MaintenanceTaskDb;
import com.turingoal.bts.wps.bean.MaintenanceWorkItemDb;
import com.turingoal.bts.wps.bean.TrackDb;
import com.turingoal.bts.wps.bean.TrainSetDb;
import com.turingoal.bts.wps.manager.MaintenanceWorkItemManager;
import com.turingoal.bts.wps.service.TaskService;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.support.net.retrofit.TgHttpRetrofitCallback;
import com.turingoal.common.android.util.lang.TgDateUtil;
import com.turingoal.common.android.util.ui.TgToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 新增任务
 */
@Route(path = TgArouterPaths.SCHEDULING_TASK_ADD)
public class SchedulingTaskAddActivity extends TgBaseActivity {
    @BindView(R.id.tvDate)
    TextView tvDate; // 任务时间
    @BindView(R.id.spTrainSetCodeNum)
    Spinner spTrainSetCodeNum; // 任务车组
    @BindView(R.id.spTrackCodeNum)
    Spinner spTrackCodeNum; // 任务股道
    @BindView(R.id.spTrackRowNum)
    Spinner spTrackRowNum; // 列位
    @BindView(R.id.tvDay)
    TextView tvDay; // 白班
    @BindView(R.id.tvNight)
    TextView tvNight; // 夜班
    @BindView(R.id.spMaintenanceTask)
    Spinner spMaintenanceTask; // 检修任务
    @BindView(R.id.spMaintenanceTaskItem)
    Spinner spMaintenanceTaskItem; // 检修项目
    private boolean isDayOrNight = true; // 白班或夜班，默认白班
    private List<MaintenanceTaskDb> maintenanceTaskDbs; //  检修任务
    private Map<String, List<MaintenanceWorkItemDb>> map = new HashMap<>(); // 检修项目，key是进行任务的codeNum

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_scheduling_task_add;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tv_title, R.id.iv_back, "新建任务"); // 顶部工具条
        tvDate.setText(TgDateUtil.date2String(new Date(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS_ZH));
        List<TrainSetDb> trainSetDbs = TgApplication.getBoxStore().boxFor(TrainSetDb.class).getAll(); // 所有车组数据
        List<TrackDb> trackDbs = TgApplication.getBoxStore().boxFor(TrackDb.class).getAll(); // 所有股道数据
        maintenanceTaskDbs = TgApplication.getBoxStore().boxFor(MaintenanceTaskDb.class).getAll(); // 所有检修任务数据
        List<String> trainSetCodeNumList = new ArrayList<>();
        for (TrainSetDb trainSetDb : trainSetDbs) {
            trainSetCodeNumList.add(trainSetDb.codeNum);
        }
        spTrainSetCodeNum.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, trainSetCodeNumList));
        List<String> trackCodeNumList = new ArrayList<>();
        for (TrackDb trackDb : trackDbs) {
            trackCodeNumList.add(trackDb.codeNum);
        }
        spTrackCodeNum.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, trackCodeNumList));
        final List<String> maintenanceTaskTitleList = new ArrayList<>();
        for (MaintenanceTaskDb maintenanceTaskDb : maintenanceTaskDbs) {
            maintenanceTaskTitleList.add(maintenanceTaskDb.title);
        }
        spMaintenanceTask.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, maintenanceTaskTitleList));
        spMaintenanceTask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!maintenanceTaskDbs.isEmpty()) {
                    List<String> list = new ArrayList<>();
                    List<MaintenanceWorkItemDb> itemDbs = getMaintenanceWorkItemList(maintenanceTaskDbs.get(i).codeNum);
                    for (MaintenanceWorkItemDb maintenanceWorkItemDb : itemDbs) {
                        list.add(maintenanceWorkItemDb.title);
                    }
                    spMaintenanceTaskItem.setAdapter(new ArrayAdapter<>(SchedulingTaskAddActivity.this, android.R.layout.simple_spinner_dropdown_item, list));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    /**
     * 得到当前任务的项目
     */
    private List<MaintenanceWorkItemDb> getMaintenanceWorkItemList(String maintenanceTaskCodeNum) {
        if (TextUtils.isEmpty(maintenanceTaskCodeNum)) {
            return new ArrayList<>();
        }
        List<MaintenanceWorkItemDb> maintenanceWorkItems = map.get(maintenanceTaskCodeNum); // 先从map取
        if (maintenanceWorkItems == null) { // map没有保存
            maintenanceWorkItems = new ArrayList<>(new MaintenanceWorkItemManager().getMaintenanceWorkItemList(maintenanceTaskCodeNum)); // 把数据库查询出来的添加到map
            map.put(maintenanceTaskCodeNum, maintenanceWorkItems); // 把从数据库查出来的放map中，暂时保存
        }
        return maintenanceWorkItems;
    }

    @OnClick({R.id.tvDay, R.id.tvNight, R.id.btnAdd})
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.tvDay: // 白班
                if (isDayOrNight) {
                    return;
                }
                isDayOrNight = true;
                tvDay.setBackgroundResource(R.drawable.bg_main_head_switch_bule);
                tvNight.setBackgroundResource(R.drawable.bg_main_head_switch_transparent);
                break;
            case R.id.tvNight: // 夜班
                if (!isDayOrNight) {
                    return;
                }
                isDayOrNight = false;
                tvDay.setBackgroundResource(R.drawable.bg_main_head_switch_transparent);
                tvNight.setBackgroundResource(R.drawable.bg_main_head_switch_bule);
                break;
            case R.id.btnAdd: // 新建任务
                Map<String, Object> map = new HashMap<>();
                map.put("trainSetCodeNum", spTrainSetCodeNum.getSelectedItem().toString()); // 车组
                map.put("trackCodeNum", spTrackCodeNum.getSelectedItem().toString()); // 股道
                map.put("trackRowNum", Integer.parseInt(spTrackRowNum.getSelectedItem().toString())); // 列位
                map.put("workShiftType", isDayOrNight ? ConstantWorkShiftTypes.DAY_SHIFT : ConstantWorkShiftTypes.NIGHY_SHIFT); // 班次
                map.put("maintenanceTask", spMaintenanceTask.getSelectedItem().toString()); // 任务
                map.put("maintenanceTaskItem", spMaintenanceTaskItem.getSelectedItem().toString()); // 项目
                map.put("workGroupCodeNum", TgApplication.getTgUserPreferences().getWorkGroupCodeNum()); // 检修单位
                map.put("workGroupName", TgApplication.getTgUserPreferences().getWorkGroupName()); // 检修单位
                TgApplication.getRetrofit().create(TaskService.class)
                        .add(map)
                        .enqueue(new TgHttpRetrofitCallback<Object>(this, true, true) {
                            @Override
                            public void successHandler(Object o) {
                                TgToastUtil.showLong("新建任务成功！");
                                EventBus.getDefault().post("UpdateSuccess");
                                defaultFinish();
                            }
                        });
                break;
            default:
                break;
        }
    }
}
