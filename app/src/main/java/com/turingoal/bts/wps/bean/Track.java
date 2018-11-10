package com.turingoal.bts.wps.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.turingoal.bts.common.android.constants.ConstantTrackLengthTypes;
import com.turingoal.bts.common.android.constants.ConstantTrainSetLengthTypes;
import com.turingoal.common.android.constants.TgConstantYesNo;

/**
 * 轨道，adapter专用
 */
public class Track extends com.turingoal.bts.common.android.biz.domain.Track implements MultiItemEntity {
    @Override
    public int getItemType() {
        // 1列位有车且是长编组
        if (getStatusRow1() == TgConstantYesNo.YES && getTrainSetLengthTypeRow2() == ConstantTrainSetLengthTypes.LONG) {
            return ConstantTrackLengthTypes.LONG;
        } else {
            return ConstantTrackLengthTypes.SHORT;
        }
    }
}
