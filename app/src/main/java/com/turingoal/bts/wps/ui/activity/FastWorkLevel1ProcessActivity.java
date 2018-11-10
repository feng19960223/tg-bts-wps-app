package com.turingoal.bts.wps.ui.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.turingoal.android.photopicker.util.CropUtil;
import com.turingoal.bts.common.android.biz.domain.DispatchTaskItem;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgAppConfig;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.bean.WorkConfigDb;
import com.turingoal.bts.wps.constants.ConstantWpsCommon;
import com.turingoal.bts.wps.manager.WorkConfigManager;
import com.turingoal.bts.wps.service.FastWorkService;
import com.turingoal.bts.wps.util.RetrofitUtils;
import com.turingoal.bts.wps.util.TgImageUtil;
import com.turingoal.bts.wps.util.TgRetrofitCallback;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.util.lang.TgDateUtil;
import com.turingoal.common.android.util.media.TgGlideUtil;
import com.turingoal.common.android.util.ui.TgDialogUtil;
import com.turingoal.common.android.util.ui.TgToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作业过程
 */
@Route(path = TgArouterPaths.FASTWORK_LEVEL1_PROCESS)
public class FastWorkLevel1ProcessActivity extends TgBaseActivity {
    @BindView(R.id.tv_num)
    TextView tvNum; // 作业信息
    @BindView(R.id.tv_name)
    TextView tvName; // 股道信息
    @BindView(R.id.tv_time)
    TextView tvTime; // 计时
    @BindView(R.id.tv_count)
    TextView tvCount; // 计数
    @BindView(R.id.tv_item_title)
    TextView tvItemTitle; // 作业节点标题
    @BindView(R.id.tv_item_desc)
    TextView tvItemDesc; // 作业描述
    @BindView(R.id.iv_standard)
    ImageView ivStandard; // 标准图片
    @BindView(R.id.iv_camera)
    ImageView ivCamera; // 相机
    @BindView(R.id.tv_result)
    TextView tvTesult; // 对比结果
    @BindView(R.id.ll_tv_carriage_a)
    LinearLayout llcarriagea;
    @BindView(R.id.ll_tv_carriage_b)
    LinearLayout llcarriageb;
    //车厢显示
    @BindView(R.id.tv_carriage1)
    TextView tvCarriage1;
    @BindView(R.id.tv_carriage2)
    TextView tvCarriage2;
    @BindView(R.id.tv_carriage3)
    TextView tvCarriage3;
    @BindView(R.id.tv_carriage4)
    TextView tvCarriage4;
    @BindView(R.id.tv_carriage5)
    TextView tvCarriage5;
    @BindView(R.id.tv_carriage6)
    TextView tvCarriage6;
    @BindView(R.id.tv_carriage7)
    TextView tvCarriage7;
    @BindView(R.id.tv_carriage8)
    TextView tvCarriage8;
    @BindView(R.id.tv_carriage9)
    TextView tvCarriage9;
    @BindView(R.id.tv_carriage10)
    TextView tvCarriage10;
    @BindView(R.id.tv_carriage11)
    TextView tvCarriage11;
    @BindView(R.id.tv_carriage12)
    TextView tvCarriage12;
    @BindView(R.id.tv_carriage13)
    TextView tvCarriage13;
    @BindView(R.id.tv_carriage14)
    TextView tvCarriage14;
    @BindView(R.id.tv_carriage15)
    TextView tvCarriage15;
    @BindView(R.id.tv_carriage16)
    TextView tvCarriage16;
    @BindView(R.id.iv_right)
    ImageView ivBreakdownRecordReport; // 故障提报
    @Autowired
    DispatchTaskItem dispatchTaskItem; // ARouter自动解析参数对象
    private List<WorkConfigDb> workConfiglist; // 作业列表
    private WorkConfigDb curenWorkConfig; // 当前作业节点配置
    private Integer itemCount; // 作业总数
    private String workNum; // 作业分组
    private String thumbnailPath = TgAppConfig.IMG_STORE_PATH + "thumbnail.jpg"; // 缩略图临时存放路径
    private boolean havePic; // 是否已拍照
    private double compareResult; // 对比结果
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    tvTime.setText(TgDateUtil.timing(workStartTime));
                    handler.sendEmptyMessageDelayed(1, 1000);
                    break;
                default:
                    break;
            }
            return false;
        }
    });
    private Date workStartTime; // 作业开始时间
    private DecimalFormat df = new DecimalFormat("######0.00");
    private String mCamImageName;
    private static final int REQUEST_CODE = 1002;

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_fast_work_process;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tv_title, R.id.iv_back, "作业过程"); // 顶部工具条
        String trainSetModel = dispatchTaskItem.getTrainSetModel();
        workNum = dispatchTaskItem.getWorkNum(); // 作业分组
        tvName.setText("股道：" + dispatchTaskItem.getTrackCodeNum() + "-" + dispatchTaskItem.getTrackRowNum());
        tvNum.setText("车号：" + dispatchTaskItem.getTrainSetCodeNum());
        WorkConfigManager workConfigManager = new WorkConfigManager();
        workConfiglist = workConfigManager.getWorkConfigList(trainSetModel, workNum, dispatchTaskItem.getMaintenanceTaskItem()); //作业列表
        if (workConfiglist.size() == 0) {
            TgToastUtil.showLong("作业配置存在问题，请联系管理员！");
            finish();
        } else {
            itemCount = workConfiglist.size(); // 作业总数
            workStartTime = dispatchTaskItem.getStartTime(); // 作业开始时间
            handler.sendEmptyMessageDelayed(1, 1000);
            getCurrentWorkItemConfig(); // 获取当前作业内容
            ivBreakdownRecordReport.setVisibility(View.VISIBLE); // 像是故障提报按钮
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeMessages(1);
        super.onDestroy();
    }

    /**
     * onClick
     */
    @OnClick({R.id.iv_camera, R.id.btn_submit, R.id.iv_cookbook, R.id.iv_right})
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.iv_right:
                TgSystemHelper.handleIntentWithObj(TgArouterPaths.FASTWORK_BREAKDOWN_REPORT, "dispatchTaskItem", dispatchTaskItem); //跳转到故障提报页面
                break;
            case R.id.iv_cookbook:
                TgSystemHelper.handleIntentWithObj(TgArouterPaths.FASTWORK_COOKBOOK, "dispatchTaskItem", dispatchTaskItem); //跳转到作业指导书页面
                break;
            case R.id.iv_camera:
                toOpenCamera();
                break;
            case R.id.btn_submit:
                if (!havePic) { // 没有拍照
                    TgDialogUtil.showDialog(this, "还未拍摄照片，确定要提交作业吗？", new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(final @NonNull MaterialDialog materialDialog, final @NonNull DialogAction dialogAction) {
                            submitRequest(); // 提交请求
                        }
                    });
                } else if (compareResult < ConstantWpsCommon.PASS_SCORE) {
                    TgDialogUtil.showDialog(this, "图片不合格，确定要提交作业吗？", new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(final @NonNull MaterialDialog materialDialog, final @NonNull DialogAction dialogAction) {
                            submitRequest(); // 提交请求
                        }
                    });
                } else {
                    TgDialogUtil.showDialog(this, "确定要提交当前作业吗？", new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(final @NonNull MaterialDialog materialDialog, final @NonNull DialogAction dialogAction) {
                            submitRequest(); // 提交请求
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    /**
     * 开启相机拍照
     */
    private void toOpenCamera() {
        // 判断是否挂载了SD卡
        mCamImageName = null;
        String savePath = "";
        if (CropUtil.hasSDCard()) {
            savePath = CropUtil.getCameraPath();
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                boolean mkdirs = saveDir.mkdirs();
            }
        }
        // 没有挂载SD卡，无法保存文件
        if (TextUtils.isEmpty(savePath)) {
            Toast.makeText(this, getString(R.string.photo_picker_lib_save_hint), Toast.LENGTH_LONG).show();
            return;
        }
        mCamImageName = CropUtil.getSaveImageFullName();
        File out = new File(savePath, mCamImageName);
        // android N 系统适配
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, out.getAbsolutePath());
            uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        } else {
            uri = Uri.fromFile(out);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == REQUEST_CODE) {
            if (mCamImageName == null)
                return;
            // 刷新显示
            final String path = CropUtil.getCameraPath() + mCamImageName;
            // 通知系统相册更新
            Uri localUri = Uri.fromFile(new File(path));
            Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
            sendBroadcast(localIntent);
            //拍摄完成后解除占用，置为未占用
            File picFile = new File(path);
            if (picFile.exists()) {
                havePic = true;
                ivCamera.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                TgImageUtil.compressAndSaveWithTimeWatermark(path, 640, 480, thumbnailPath); // 生成缩略图加水印
                ivCamera.setImageBitmap(TgImageUtil.getBitmap(thumbnailPath)); // 这里显示缩略图,用Glide有缓存问题
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        compareResult = TgImageUtil.matchByHist(curenWorkConfig.getStandardImgPathLocal(), path); // 对比的时候用原图
                        runOnUiThread(new Runnable() { // 运行在主线程
                            @Override
                            public void run() {
                                if (compareResult < ConstantWpsCommon.PASS_SCORE) {
                                    tvTesult.setText("图片不合格  " + Double.parseDouble(df.format(compareResult)));
                                    tvTesult.setTextColor(Color.RED);
                                } else {
                                    tvTesult.setText("图片合格  " + Double.parseDouble(df.format(compareResult)));
                                    tvTesult.setTextColor(Color.GREEN);
                                }
                            }
                        });
                    }
                }).start();
            }
        }
    }

    /**
     * 获得当前作业内容
     */
    private void getCurrentWorkItemConfig() {
        if (dispatchTaskItem.getStep() == workConfiglist.size()) {
            TgSystemHelper.handleIntentWithObjAndFinish(TgArouterPaths.FASTWORK_LEVEL1_FINISH, "dispatchTaskItem", dispatchTaskItem, this); //跳转到完成页面，关闭当前页面
        } else {
            dispatchTaskItem.setStep(dispatchTaskItem.getStep() + 1); // 下一个作业
            curenWorkConfig = workConfiglist.get(dispatchTaskItem.getStep() - 1);
            getCarriage();
            TgGlideUtil.load(this, curenWorkConfig.getStandardImgPathLocal(), ivStandard); // 标准作业图片
            tvCount.setText(dispatchTaskItem.getStep() + "/" + itemCount); // 计数
            tvItemTitle.setText(curenWorkConfig.getWorkItem()); // 作业点
            tvItemDesc.setText(curenWorkConfig.getWorkDesc()); // 作业描述
        }
    }

    /**
     * 提交请求
     */
    private void submitRequest() {
        File file = new File(thumbnailPath);
        Map<String, Object> map = new HashMap<>();
        map.put("userId", TgApplication.getTgUserPreferences().getUserId());
        map.put("dispatchTaskItemId", dispatchTaskItem.getId());
        map.put("compareResult", new BigDecimal(compareResult).setScale(2, RoundingMode.UP).toString());
        map.put("configId", curenWorkConfig != null ? curenWorkConfig.getId() : "");
        TgApplication.getRetrofit().create(FastWorkService.class)
                .submitItem(RetrofitUtils.fileToMultipartBodyPart(file, "imgWork"), RetrofitUtils.mapToRequestBodyMap(map))
                .enqueue(new TgRetrofitCallback<Object>(this, true, true) {
                    @Override
                    public void successHandler(Object obj) {
                        EventBus.getDefault().post("UpdateSuccess");
                        TgToastUtil.showLong("作业提交成功！"); // 弹出信息
                        // 还原数据
                        compareResult = 0;
                        havePic = false;
                        tvTesult.setText("");
                        // 还原拍照按钮
                        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(160, 160);
                        rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
                        ivCamera.setLayoutParams(rlp);
                        Glide.with(FastWorkLevel1ProcessActivity.this).load(R.mipmap.app_ic_fast_camera).into(ivCamera);
                        // 更新任务
                        getCurrentWorkItemConfig();
                    }
                });
    }

    /**
     * 获得所在车厢
     */
    private void getCarriage() {
        int carriage = Integer.parseInt(curenWorkConfig.getCarriage());
        LinkedList<TextView> carriageList = new LinkedList<>();
        //将布局textview 添加进集合
        carriageList.add(tvCarriage1);
        carriageList.add(tvCarriage2);
        carriageList.add(tvCarriage3);
        carriageList.add(tvCarriage4);
        carriageList.add(tvCarriage5);
        carriageList.add(tvCarriage6);
        carriageList.add(tvCarriage7);
        carriageList.add(tvCarriage8);
        carriageList.add(tvCarriage9);
        carriageList.add(tvCarriage10);
        carriageList.add(tvCarriage11);
        carriageList.add(tvCarriage12);
        carriageList.add(tvCarriage13);
        carriageList.add(tvCarriage14);
        carriageList.add(tvCarriage15);
        carriageList.add(tvCarriage16);
        if ("B1".equals(workNum) || "B2".equals(workNum) || "B3".equals(workNum) || "B4".equals(workNum)) { //如果是B工作组，将显示9到16节车厢
            llcarriagea.setVisibility(View.GONE);
            llcarriageb.setVisibility(View.VISIBLE);
        }
        TextView temporary;
        for (int i = 1; i < carriageList.size(); i++) {
            if (i == carriage) {
                temporary = carriageList.get(i - 1);
                temporary.setBackgroundResource(R.drawable.textview_border);
            } else {
                temporary = carriageList.get(i - 1);
                temporary.setBackgroundResource(R.drawable.textview_border_back);
            }
        }
    }
}
