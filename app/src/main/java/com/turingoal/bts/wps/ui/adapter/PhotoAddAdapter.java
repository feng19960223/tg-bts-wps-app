package com.turingoal.bts.wps.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.turingoal.bts.wps.R;
import com.turingoal.common.android.base.TgBaseViewHolder;
import com.turingoal.common.android.util.media.TgGlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 故障发布照片adapter
 */

public class PhotoAddAdapter extends BaseItemDraggableAdapter<String, TgBaseViewHolder> {
    public static final String PICTURES_ADD = "pictures_add"; // 加号
    public static final int PICTURES_MAX_COUNT = 9; // 最多选择数量

    public PhotoAddAdapter() {
        super(R.layout.app_item_photo_add, new ArrayList<String>());
        List<String> imgUrls = new ArrayList<>();
        imgUrls.add(PICTURES_ADD);
        setNewData(imgUrls);
    }

    @Override
    protected void convert(final TgBaseViewHolder holper, final String dataUrl) {
        if (PICTURES_ADD.equals(dataUrl)) { // 加号没有删除，不显示视频按钮
            holper.setImageResource(R.id.ivPhoto, R.drawable.ic_add_pictures);
            holper.setGone(R.id.ivPhotoDelete, false);
            holper.setGone(R.id.ivVideo, false);
        } else {
            TgGlideUtil.load(mContext, dataUrl, (ImageView) holper.getView(R.id.ivPhoto)); // 设置图片
            holper.setGone(R.id.ivPhotoDelete, true);
            if (dataUrl.startsWith("content")) { // 如果是视频
                holper.setGone(R.id.ivVideo, true);
            } else { // 如果是图片
                holper.setGone(R.id.ivVideo, false);
            }
        }
        holper.addOnClickListener(R.id.ivPhoto);
        // 删除
        holper.getView(R.id.ivPhotoDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mData.remove(dataUrl);
                if (!mData.contains(PICTURES_ADD)) { // 如果最后一个不是加号
                    mData.add(PICTURES_ADD); // 显示加号
                }
                notifyDataSetChanged();
            }
        });
    }
}
