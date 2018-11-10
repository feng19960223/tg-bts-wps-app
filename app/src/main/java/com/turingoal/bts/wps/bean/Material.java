package com.turingoal.bts.wps.bean;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 物料
 */
@Data
public class Material {
    private String id; // 物料
    private String codeNum; // 编码
    private String productCodeNum; // 产品编码
    private String title; // 名称
    private String materialType; // 物料类型。配件 大物料 小物料
    private String categoryCodeNum; // 物料分类编码
    private String categoryName; // 物料分类名称
    private String specification; // 规格
    private String unit; // 单位
    private BigDecimal price; // 单价
    private BigDecimal priceAverage; // 平均单价
    private String brand; // 品牌
    private String agent; // 代理商
    private String description; // 描述
    private Long maxNum; // 最大保有量
    private Long minNum; // 最小保有量
    private Long validityDaysDefault; // 默认有效天数
    private String libDefaultId; // 默认库
    private String libDefaultCodeNum; // 默认库编码
    private String libBoxDefaultId; // 默认存储位置
    private String libBoxDefaultCodeNum; // 默认存储位置编码
    private String countNum;   // 物料统计数量
    private String financeItemCodeNum; // 支出科目编码
    private String financeItemName; // 支出科目名称
    private BigDecimal quantity = new BigDecimal(1); // 选择的数量

}
