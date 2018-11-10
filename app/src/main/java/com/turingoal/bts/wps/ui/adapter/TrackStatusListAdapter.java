package com.turingoal.bts.wps.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.turingoal.bts.common.android.constants.ConstantTrackLengthTypes;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.bean.Track;
import com.turingoal.common.android.constants.TgConstantYesNo;
import com.turingoal.common.android.util.lang.TgDateUtil;

/**
 * 股道状态适配器
 */
public class TrackStatusListAdapter extends BaseMultiItemQuickAdapter<Track, BaseViewHolder> {
    public TrackStatusListAdapter() {
        super(null);
        //添加不同的item条目
        addItemType(ConstantTrackLengthTypes.SHORT, R.layout.app_item_track_status);
        addItemType(ConstantTrackLengthTypes.LONG, R.layout.app_item_track_status_long);
    }

    /**
     * 1列位有车
     */
    private void row1Busy(final BaseViewHolder holder, final Track item) {
        holder.getView(R.id.track_bg1).setBackgroundResource(R.mipmap.app_ic_query_blue); // 蓝色
        holder.getView(R.id.track_row_bg1).setBackgroundResource(R.drawable.query_lw_nofree_bg); // 蓝色
        holder.getView(R.id.isfree1).setVisibility(View.VISIBLE); // 繁忙
        holder.getView(R.id.free1).setVisibility(View.GONE); // 隐藏
        holder.setText(R.id.train_set_code_num1, item.getTrainSetCodeNumRow1()); // 车号
        holder.setText(R.id.arrive_time1, TgDateUtil.date2String(item.getArriveTimeRow1(), TgDateUtil.FORMAT_MM_DD_HH_MM_ZH)); //进站时间
    }

    /**
     * 1列位空闲
     */
    private void row1Free(final BaseViewHolder holder) {
        holder.getView(R.id.track_bg1).setBackgroundResource(R.mipmap.app_ic_query_green); // 绿色
        holder.getView(R.id.track_row_bg1).setBackgroundResource(R.drawable.query_lw_free_bg); // 绿色
        holder.getView(R.id.isfree1).setVisibility(View.GONE); // 隐藏
        holder.getView(R.id.free1).setVisibility(View.VISIBLE); // 空闲
    }

    /**
     * 2列位有车
     */
    private void row2Busy(final BaseViewHolder holder, final Track item) {
        holder.getView(R.id.track_bg2).setBackgroundResource(R.mipmap.app_ic_query_blue); // 蓝色
        holder.getView(R.id.track_row_bg2).setBackgroundResource(R.drawable.query_lw_nofree_bg); // 蓝色
        holder.getView(R.id.isfree2).setVisibility(View.VISIBLE); // 繁忙
        holder.getView(R.id.free2).setVisibility(View.GONE); // 隐藏
        holder.setText(R.id.train_set_code_num2, item.getTrainSetCodeNumRow2()); // 车号
        holder.setText(R.id.arrive_time2, TgDateUtil.date2String(item.getArriveTimeRow2(), TgDateUtil.FORMAT_MM_DD_HH_MM_ZH)); //进站时间
    }

    /**
     * 2列位空闲
     */
    private void row2Free(final BaseViewHolder holder) {
        holder.getView(R.id.track_bg2).setBackgroundResource(R.mipmap.app_ic_query_green); // 绿色
        holder.getView(R.id.track_row_bg2).setBackgroundResource(R.drawable.query_lw_free_bg); // 绿色
        holder.getView(R.id.isfree2).setVisibility(View.GONE); // 隐藏
        holder.getView(R.id.free2).setVisibility(View.VISIBLE); // 空闲
    }


    @Override
    protected void convert(final BaseViewHolder holder, final Track item) {
        holder.setText(R.id.query_track_num, "" + item.getCodeNum()); // 股道
        if (item.getStatusRow1() == TgConstantYesNo.NO) { // 1列位无车
            row1Free(holder); // 1列位空闲
            // 2列位
            if (item.getStatusRow2() == TgConstantYesNo.YES) { // 有车
                row2Busy(holder, item); // 2列位有车
            } else { // 2列位空闲
                row2Free(holder); //2列位空闲
            }
        } else { // 1列位有车
            row1Busy(holder, item);  // 1列位有车
            switch (item.getTrainSetLengthTypeRow2()) {
                case ConstantTrackLengthTypes.LONG:
                    holder.getView(R.id.track_bg1).setBackgroundResource(R.mipmap.app_ic_query_blue); // 蓝色
                    holder.setText(R.id.train_set_code_num1, item.getTrainSetCodeNumRow1());
                    holder.setText(R.id.arrive_time1, TgDateUtil.date2String(item.getArriveTimeRow1(), TgDateUtil.FORMAT_MM_DD_HH_MM_ZH));
                    break;
                case ConstantTrackLengthTypes.SHORT:
                    // 2列位
                    if (item.getStatusRow2() == TgConstantYesNo.YES) { // 有车
                        row2Busy(holder, item); // 2列位有车
                    } else { // 2列位空闲
                        row2Free(holder); //2列位空闲
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
