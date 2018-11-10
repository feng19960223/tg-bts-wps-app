package com.turingoal.bts.wps.bean;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 物料领用物料项
 */
@Data
public class MaterialUsageItem {
    private String id; // 物料领用项
    private String title; // 名称
    private String materialType; // 物料类型
    private String specification; // 规格
    private String unit; // 单位
    private String agent; // 代理商
    private String brand; // 品牌
    private String description; // 描述
    private String remarks; // 备注
    private BigDecimal quantity = new BigDecimal(1); // 数量
    private String materialId; // 物料
    private String materialCodeNum; // 物料编码
    private String materialItemId; // 物料库物料项id
    private String materialItemCodeNum; // 物料库物料项编码
    private String materialUsageId; // 物料领用id
    private String materialUsageCodeNum; // 物料领用编码
    private Date createTime; // 创建时间
    private Integer sortOrder; // 排序
    private String productCodeNum; // 产品编码
    private BigDecimal quantityOutbound; // 已出库数量
    private String categoryCodeNum; // 物料分类编码
    private String categoryName; // 物料分类名称
    private String financeItemCodeNum; // 支出科目编码
    private String financeItemName; // 支出科目名称
}
