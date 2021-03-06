package com.turingoal.bts.wps.ui.activity;

import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.FileIOUtils;
import com.github.barteksc.pdfviewer.PDFView;
import com.turingoal.bts.common.android.biz.domain.TechnicalDoc;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgAppConfig;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.service.DownloadFileService;
import com.turingoal.common.android.base.TgBaseActivity;
import com.turingoal.common.android.util.io.TgFileUtil;
import com.turingoal.common.android.util.ui.TgToastUtil;

import java.io.File;

import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 技术资料详情
 */
@Route(path = TgArouterPaths.WORD_DETAIL)

public class TechnicalDocDetailActivity extends TgBaseActivity {
    @BindView(R.id.technical_doc_detail_tvtitle)
    TextView tvTitle; // 标题
    @BindView(R.id.technical_doc_pdfView)
    public PDFView pdfView; // pdf组件
    @Autowired
    TechnicalDoc technicalDoc; // ARouter自动解析参数对象

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_technical_doc_detail;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tv_title, R.id.iv_back, "资料详情"); // 顶部工具条
        tvTitle.setText("" + technicalDoc.getTitle()); // 资料标题
        getFile(technicalDoc.getDocFileUrl());
    }

    /**
     * 获取文件
     */
    private void getFile(String url) {
        // 文件路径 + 截取最后一个/后面的所有字符
        final File file = new File(TgAppConfig.PDF_STORE_PATH + url.substring(url.lastIndexOf("/") + 1));
        if (TgFileUtil.isFileExists(file)) { // 如果文件存在本地
            pdfView.fromFile(file)
                    .defaultPage(0) // 默认页面
                    .enableSwipe(true) // 触摸滑动
                    .swipeHorizontal(false) // 水平滑动
                    .load();
        } else {
            TgApplication.getRetrofit().create(DownloadFileService.class)
                    .downloadFile(url)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                FileIOUtils.writeFileFromIS(file, response.body().byteStream()); // 保存文件，下次会优先使用本地pdf
                                pdfView.fromFile(file)
                                        .defaultPage(0) // 默认页面
                                        .enableSwipe(true) // 触摸滑动
                                        .swipeHorizontal(false) // 水平滑动
                                        .load();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            TgToastUtil.showLong("加载PDF文件失败");
                        }
                    });
        }
    }
}
