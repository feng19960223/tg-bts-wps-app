package com.turingoal.bts.wps.ui.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.turingoal.android.photopicker.PhotoPicker;
import com.turingoal.bts.common.android.biz.domain.DispatchTaskItem;
import com.turingoal.bts.common.android.constants.ConstantMontorBreakdownPattern;
import com.turingoal.bts.common.android.constants.ConstantMontorBreakdownSource;
import com.turingoal.bts.common.android.constants.ConstantMontorBreakdownSystemTypes;
import com.turingoal.bts.common.android.constants.ConstantTrainSetLengthTypes;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.service.BreakdownRecordItemService;
import com.turingoal.bts.wps.ui.activity.common.TgVideoPlayerActivity;
import com.turingoal.bts.wps.ui.adapter.PhotoAddAdapter;
import com.turingoal.bts.wps.util.RetrofitUtils;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.bean.TgKeyValueBean;
import com.turingoal.common.android.bean.TgPhotoGroupBean;
import com.turingoal.common.android.ui.layout.TgFlowRadioGroupLayout;
import com.turingoal.common.android.util.io.TgUriUtil;
import com.turingoal.common.android.util.lang.TgDateUtil;
import com.turingoal.common.android.util.lang.TgStringUtil;
import com.turingoal.common.android.util.ui.TgDialogUtil;
import com.turingoal.common.android.util.ui.TgToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * [故障提报]
 */
@Route(path = TgArouterPaths.FASTWORK_BREAKDOWN_REPORT)
public class BreakdownFastWorkRecordReportActivity extends TgBaseActivity implements AdapterView.OnItemSelectedListener, TgFlowRadioGroupLayout.OnCheckedChangeListener {
    @BindView(R.id.tvDiscoveryTime)
    TextView tvDiscoveryTime; // 发现时间
    @BindView(R.id.tvArriveTime)
    TextView tvArriveTime; // 入库时间
    @BindView(R.id.tvTrainSetModelTrainSetLengthType)
    TextView tvTrainSetModelTrainSetLengthType; // 故障车型
    @BindView(R.id.tvTrainFrequency)
    TextView tvTrainFrequency; // 故障车次
    @BindView(R.id.tvTrainSetCodeNum)
    TextView tvTrainSetCodeNum; // 车组车号
    @BindView(R.id.tvTrackCodeNumTrackRowNum)
    TextView tvTrackCodeNumTrackRowNum; // 故障股道
    @BindView(R.id.spSource)
    Spinner spSource; // 故障来源
    @BindView(R.id.spSystemType)
    Spinner spSystemType; // 故障类型
    @BindView(R.id.spPattern)
    Spinner spPattern; // 故障模式
    @BindView(R.id.etCode)
    EditText etCode; // 故障代码
    @BindView(R.id.frgCarriage)
    TgFlowRadioGroupLayout frgCarriage; // 故障车厢
    @BindView(R.id.llCarriage)
    LinearLayout llCarriage; // 车厢视图控制器，如果是短编，隐藏，长编显示
    @BindView(R.id.etDiscoveryDesc)
    EditText etDiscoveryDesc; // 问题描述
    @BindView(R.id.tvDiscoveryDescCount)
    TextView tvDiscoveryDescCount; // 问题描述字数统计
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView; // recyclerView
    private PhotoAddAdapter adapter = new PhotoAddAdapter(); // adapter
    private static final int SPAN_COUNT = 3; // 照片列数
    private ItemTouchHelper mItemTouchHelper = null;
    private Date discoveryDate; // 发现时间
    private boolean isEdit = false; // 修改过数据,提示是否保存
    @Autowired
    DispatchTaskItem dispatchTaskItem; // ARouter自动解析参数对象

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_fast_work_breakdown_report;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tv_title, R.id.iv_back, getString(R.string.title_breakdown_report));
        setToobarLeftButClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEditDialog();
            }
        });
        discoveryDate = Calendar.getInstance().getTime();
        tvDiscoveryTime.setText(TgDateUtil.date2String(discoveryDate, TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_ZH));
        String arriveTime = TgDateUtil.date2String(dispatchTaskItem.getArriveTime(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_ZH);
        tvArriveTime.setText(TgStringUtil.isBlank(arriveTime) ? "---" : arriveTime);
        tvTrainSetModelTrainSetLengthType.setText("" + dispatchTaskItem.getTrainSetModel() + "（" + ConstantTrainSetLengthTypes.getShortStr(dispatchTaskItem.getTrainSetLengthType()) + "）");
        llCarriage.setVisibility(dispatchTaskItem.getTrainSetLengthType() == ConstantTrainSetLengthTypes.SHORT ? View.GONE : View.VISIBLE);
        tvTrainFrequency.setText("" + dispatchTaskItem.getTrackCodeNum());
        tvTrainSetCodeNum.setText("" + dispatchTaskItem.getTrainSetCodeNum());
        tvTrackCodeNumTrackRowNum.setText("" + dispatchTaskItem.getTrackCodeNum() + "-" + dispatchTaskItem.getTrackRowNum());
        List<String> sourceList = new ArrayList<>();
        for (TgKeyValueBean tgKeyValueBean : ConstantMontorBreakdownSource.DATA_LIST) {
            String stringValue = tgKeyValueBean.getStringValue();
            sourceList.add(stringValue);
        }
        spSource.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sourceList));
        spSource.setSelection(0, true); // 不要默认执行
        spSource.setOnItemSelectedListener(this);
        List<String> systemTypeList = new ArrayList<>();
        for (TgKeyValueBean tgKeyValueBean : ConstantMontorBreakdownSystemTypes.DATA_LIST) {
            String stringValue = tgKeyValueBean.getStringValue();
            systemTypeList.add(stringValue);
        }
        spSystemType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, systemTypeList));
        spSystemType.setSelection(0, true);
        spSystemType.setOnItemSelectedListener(this);
        List<String> patternList = new ArrayList<>();
        for (TgKeyValueBean tgKeyValueBean : ConstantMontorBreakdownPattern.DATA_LIST) {
            String stringValue = tgKeyValueBean.getStringValue();
            patternList.add(stringValue);
        }
        spPattern.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, patternList));
        spPattern.setSelection(0, true);
        spPattern.setOnItemSelectedListener(this);
        etCode.addTextChangedListener(codeWatcher);
        frgCarriage.setOnCheckedChangeListener(this);
        etDiscoveryDesc.addTextChangedListener(countWatcher);
        initAdapter();
    }

    @OnClick({R.id.btnReport, R.id.llDiscoveryTime})
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.llDiscoveryTime:
                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        isEdit = true;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        discoveryDate = calendar.getTime();
                        tvDiscoveryTime.setText(TgDateUtil.date2String(discoveryDate, TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_ZH));
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true).show();
                break;
            case R.id.btnReport:
                if (TextUtils.isEmpty(etCode.getText().toString().trim())) {
                    ToastUtils.showLong(R.string.string_breakdown_report_no_code_hint);
                    return;
                }
                final RadioButton radioButton = frgCarriage.findViewById(frgCarriage.getCheckedRadioButtonId());
                if (radioButton == null) {
                    ToastUtils.showLong(R.string.string_breakdown_report_no_carriage_hint);
                    return;
                }
                if (TextUtils.isEmpty(etDiscoveryDesc.getText().toString().trim())) {
                    ToastUtils.showLong(R.string.string_breakdown_report_no_discovery_desc_hint);
                    return;
                }
                TgDialogUtil.showDialog(this, getString(R.string.string_breakdown_report_confirm_hint), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull final MaterialDialog dialog, @NonNull final DialogAction which) {
                        List<File> files = new ArrayList<>();
                        for (String filePath : adapter.getData()) {
                            if (!PhotoAddAdapter.PICTURES_ADD.equals(filePath)) { // 去掉加号
                                File file;
                                if (filePath.startsWith("content")) { // 视频
                                    file = TgUriUtil.uri2File(Uri.parse(filePath), MediaStore.Video.Media.DATA);
                                } else { // 图片
                                    file = new File(filePath);
                                }
                                files.add(file);
                            }
                        }
                        Map<String, Object> map = new HashMap<>();
                        map.put("schedulingTaskCodeNum", "" + dispatchTaskItem.getSchedulingTaskCodeNum());
                        map.put("discoveryTime", TgDateUtil.date2String(discoveryDate, TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS)); // 故障发现时间,当前时间
                        String sourceStr = ConstantMontorBreakdownSource.CREW_LOG;
                        for (TgKeyValueBean tgKeyValueBean : ConstantMontorBreakdownSource.DATA_LIST) {
                            if (tgKeyValueBean.getStringValue().equals(spSource.getSelectedItem().toString())) {
                                sourceStr = tgKeyValueBean.getKey();
                            }
                        }
                        map.put("source", sourceStr);
                        String systemTypeStr = ConstantMontorBreakdownSystemTypes.QITA;
                        for (TgKeyValueBean tgKeyValueBean : ConstantMontorBreakdownSystemTypes.DATA_LIST) {
                            if (tgKeyValueBean.getStringValue().equals(spSystemType.getSelectedItem().toString())) {
                                systemTypeStr = tgKeyValueBean.getKey();
                            }
                        }
                        map.put("systemType", systemTypeStr);
                        String patternStr = ConstantMontorBreakdownPattern.OTHER;
                        for (TgKeyValueBean tgKeyValueBean : ConstantMontorBreakdownPattern.DATA_LIST) {
                            if (tgKeyValueBean.getStringValue().equals(spPattern.getSelectedItem().toString())) {
                                patternStr = tgKeyValueBean.getKey();
                            }
                        }
                        map.put("pattern", patternStr);
                        map.put("carriage", radioButton.getText().toString().trim());
                        map.put("breakdownCode", etCode.getText().toString().trim());
                        map.put("description", etDiscoveryDesc.getText().toString().trim());
                        TgApplication.getRetrofit().create(BreakdownRecordItemService.class)
                                .commit(RetrofitUtils.filesToMultipartBodyParts(files), RetrofitUtils.mapToRequestBodyMap(map))
                                .enqueue(new TgRetrofitCallback<Object>(BreakdownFastWorkRecordReportActivity.this, true, true) {
                                    @Override
                                    public void successHandler(final Object obj) {
                                        TgToastUtil.showLong(R.string.string_breakdown_report_add_success);
                                        defaultFinish();
                                    }
                                });
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * 初始化recyclerView和adapter
     */
    private void initAdapter() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        ItemDragAndSwipeCallback mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter) {
            @Override
            public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current, RecyclerView.ViewHolder target) {
                // 最后一个为加号，所以不支持【拖拽换位置】
                return target.getAdapterPosition() != recyclerView.getAdapter().getItemCount() - 1 && super.canDropOver(recyclerView, current, target);
            }
        };
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView); // 附加到RecyclerView
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                // 如果item不是最后一个，则可以拖拽【不是加号，可以拖拽】
                if (position != adapter.getData().size() - 1) {
                    mItemTouchHelper.startDrag(recyclerView.getChildViewHolder(view));
                }
                return false;
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String url = (String) adapter.getItem(position);
                if (PhotoAddAdapter.PICTURES_ADD.equals(url)) { // 加号
                    // 选择图片或者视频
                    List<String> items = new ArrayList<>();
                    items.add("照片");
                    items.add("视频");
                    new MaterialDialog.Builder(BreakdownFastWorkRecordReportActivity.this).items(items).itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                            if (position == 0) {
                                selectPhoto();
                            } else {
                                selectVideo();
                            }
                        }
                    }).show();

                } else {
                    if (url.startsWith("content")) { // 查看视频，以content开头就是视频
                        TgVideoPlayerActivity.actionStart(BreakdownFastWorkRecordReportActivity.this, url);
                    } else { // 查看图片
                        List<String> photos = new ArrayList<>(adapter.getData());
                        photos.remove(PhotoAddAdapter.PICTURES_ADD); // 删除最后一个加号
                        Iterator<String> it = photos.iterator();
                        while (it.hasNext()) { // 删除content开头的数据
                            String x = it.next();
                            if (x.startsWith("content")) {
                                it.remove();
                            }
                        }
                        TgPhotoGroupBean tgPhotoGroupBean = new TgPhotoGroupBean();
                        tgPhotoGroupBean.setIndexNum(photos.indexOf(url));
                        tgPhotoGroupBean.setPhotos(photos);
                        TgSystemHelper.handleIntentWithObj(TgArouterPaths.PHOTO_SHOW, "tgPhotoGroupBean", tgPhotoGroupBean); // 参考图片
                    }
                }
            }
        });
    }

    /**
     * 选择视频
     */
    private void selectVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 768000);  // 设置视频大小
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 45000); // 设置视频时间  毫秒单位
        startActivityForResult(intent, 1);
    }

    /**
     * 选择照片
     */
    private void selectPhoto() {
        List<String> photos = new ArrayList<>(adapter.getData());
        photos.remove(PhotoAddAdapter.PICTURES_ADD); // 删除最后一个加号
        int i = PhotoAddAdapter.PICTURES_MAX_COUNT; // 最多可以选择9个文件
        Iterator<String> it = photos.iterator();
        while (it.hasNext()) {  // 删除content开头的数据是视频
            String x = it.next();
            if (x.startsWith("content")) {
                it.remove();
                i--;
            }
        }
        String[] paths = photos.size() == 0 ? null : photos.toArray(new String[photos.size()]); // 已经选择的图片
        PhotoPicker.selectPics(this, i, paths, new PhotoPicker.PicCallBack() { // 加上视频最多选择9个
            @Override
            public void onPicSelected(final String[] paths) {
                List<String> pathList = new ArrayList<>();
                pathList.addAll(Arrays.asList(paths)); // UnsupportedOperationException
                List<String> data = adapter.getData();
                pathList.removeAll(data); // 新选择的里面删除原来已经有的
                data.remove(PhotoAddAdapter.PICTURES_ADD);// 删除最后一个加号
                data.addAll(pathList); // 原来已经有的，加上新选择的，保留顺序
                if (data.size() < PhotoAddAdapter.PICTURES_MAX_COUNT) { // 小于9个文件
                    data.add(PhotoAddAdapter.PICTURES_ADD); //　添加加号
                }
                adapter.setNewData(data);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == BreakdownFastWorkRecordReportActivity.RESULT_OK) { //    判断拍摄成功
                if (data != null) {
                    Uri uri = data.getData();//可以通过这个播放
                    if (uri != null) {
                        String uriStr = uri.toString();
                        if (!TextUtils.isEmpty(uriStr)) {
                            List<String> adapterData = new ArrayList<>(adapter.getData());
                            adapterData.remove(PhotoAddAdapter.PICTURES_ADD);
                            adapterData.add(uriStr);
                            if (adapterData.size() < PhotoAddAdapter.PICTURES_MAX_COUNT) {
                                adapterData.add(PhotoAddAdapter.PICTURES_ADD);
                            }
                            adapter.setNewData(adapterData);
                        }
                    }
                }
            }
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
            TgDialogUtil.showDialog(this, getString(R.string.stirng_breakdown_report_no_save_hint), new MaterialDialog.SingleButtonCallback() {
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
            isEdit = true;
            tvDiscoveryDescCount.setText("" + editable.toString().trim().length() + "/140"); // 文字计数 140个
        }
    };

    /**
     * 故障代码EditText是否编辑过监听
     */
    private TextWatcher codeWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
        }

        @Override
        public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
        }

        @Override
        public void afterTextChanged(final Editable editable) {
            isEdit = true;
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        isEdit = true;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onCheckedChanged(TgFlowRadioGroupLayout tgFlowRadioGroup, int i) {
        isEdit = true;
    }
}
