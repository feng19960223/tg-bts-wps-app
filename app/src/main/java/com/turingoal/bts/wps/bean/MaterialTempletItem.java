package com.turingoal.bts.wps.bean;

import java.math.BigDecimal;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/27 16:03.
 */
@Data
public class MaterialTempletItem {
    private String id; // 物料模板项-物料
    private String materialTempletId; // 物料模板id
    private String materialId; // 物料
    private String materialType; // 物料类型。配件 大物料 小物料
    private String codeNum; // 物料编码
    private BigDecimal quantity; // 数量
    private Integer sortOrder; // 排序
    private String title; // 名称
    private Integer sourceType; // 来源
    private String brand; // 品牌
    private String agent; // 代理商
    private String specification; // 规格
    private String unit; // 单位
    private BigDecimal price; // 单价
    private String description; // 描述
    private String remarks; // 备注
    private String typeId; // 分类
    private String typeTitle; // 分类名称
    private String categoryCodeNum; // 物料分类编码
    private String categoryName; // 物料分类名称
    private String financeItemCodeNum; // 支出科目编码
    private String financeItemName; // 支出科目名称
}
