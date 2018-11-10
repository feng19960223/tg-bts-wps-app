package com.turingoal.bts.wps.bean;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import lombok.Data;

/**
 * 股道，数据库专用
 */
@Data
@Entity
public class TrackDb {
    @Id
    public long entityId; // objectBox 的id需要为Long 类型
    public String id;
    public String codeNum;
    public Integer workType;
    public Integer outType;
    public Integer lengthType;
    public Integer statusRow1;
    public Integer statusRow2;
    public String trainSetCodeNumRow1;
    public String trainSetCodeNumRow2;
    public Date arriveTimeRow1;
    public Date arriveTimeRow2;
    public Date outboundTimeRow1;
    public Date outboundTimeRow2;
    public Integer trainSetLengthTypeRow2;
    public String description;
    public Integer sortOrder;
    public Integer enabled;

    public TrackDb() {

    }
}
