package com.turingoal.bts.wps.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.turingoal.bts.common.android.biz.domain.TrackRecord;
import com.turingoal.bts.wps.R;
import com.turingoal.common.android.util.lang.TgDateUtil;

/**
 * 股道记录适配器
 */
public class TrackRecordListAdapter extends BaseQuickAdapter<TrackRecord, BaseViewHolder> {
    public TrackRecordListAdapter() {
        super(R.layout.app_item_track_record);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final TrackRecord item) {
        holder.setText(R.id.track_record_train_set_code_num, item.getTrainSetCodeNum()) //车号
                .setText(R.id.track_record_track, item.getTrackCodeNum() + " - " + item.getTrackRowNum()) //股道
                .setText(R.id.track_record_arrive_time, TgDateUtil.date2String(item.getArriveTime(), TgDateUtil.FORMAT_HH_MM_SS)) // 进站时间
                .setText(R.id.track_record_outbound_time, TgDateUtil.date2String(item.getOutboundTime(), TgDateUtil.FORMAT_HH_MM_SS)); // 出站时间
    }
}
