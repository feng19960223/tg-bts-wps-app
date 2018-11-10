package com.turingoal.bts.wps.ui.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.app.TgSystemHelper;
import com.turingoal.bts.wps.ui.activity.common.TgVideoPlayerActivity;
import com.turingoal.common.android.base.TgBaseViewHolder;
import com.turingoal.common.android.bean.TgPhotoGroupBean;
import com.turingoal.common.android.util.media.TgGlideUtil;
import com.turingoal.common.android.util.media.TgMediaUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 故障发布照片adapter
 */

public class PhotoDetailAdapter extends BaseQuickAdapter<String, TgBaseViewHolder> {
    public PhotoDetailAdapter(List<String> data) {
        super(R.layout.app_item_photo_detail, data);
    }

    class NetWorkAsyncTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public NetWorkAsyncTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            return TgMediaUtil.getNetVideoBitmap(strings[0]); // 耗时操作
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (mContext instanceof Activity) {
                if (!((Activity) mContext).isFinishing()) { // mContext存活
                    TgGlideUtil.load(mContext, bitmap, imageView);
                }
            }
        }
    }

    @Override
    protected void convert(final TgBaseViewHolder holper, final String dataUrl) {
        if (dataUrl.endsWith(".mp4")) {
            new NetWorkAsyncTask((ImageView) holper.getView(R.id.ivPhoto)).execute(dataUrl);
            holper.setGone(R.id.ivVideo, true); // 显示播放按钮
        } else {
            TgGlideUtil.load(mContext, dataUrl, (ImageView) holper.getView(R.id.ivPhoto)); // 设置图片
            if (dataUrl.startsWith("content")) {
                holper.setGone(R.id.ivVideo, true); // 显示播放按钮
            } else {
                holper.setGone(R.id.ivVideo, false);
            }
        }
        // 点击事件
        holper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (dataUrl.startsWith("content") || dataUrl.endsWith(".mp4")) { // 查看视频，以content开头就是视频
                    TgVideoPlayerActivity.actionStart(mContext, dataUrl);
                } else { // 查看图片
                    List<String> photos = new ArrayList<>(mData);
                    Iterator<String> it = photos.iterator();
                    while (it.hasNext()) { // 删除content开头的数据
                        String x = it.next();
                        if (x.startsWith("content") || x.endsWith(".mp4")) {
                            it.remove();
                        }
                    }
                    TgPhotoGroupBean tgPhotoGroupBean = new TgPhotoGroupBean();
                    tgPhotoGroupBean.setIndexNum(photos.indexOf(dataUrl));
                    tgPhotoGroupBean.setPhotos(photos);
                    TgSystemHelper.handleIntentWithObj(TgArouterPaths.PHOTO_SHOW, "tgPhotoGroupBean", tgPhotoGroupBean); // 参考图片
                }
            }
        });
    }
}
