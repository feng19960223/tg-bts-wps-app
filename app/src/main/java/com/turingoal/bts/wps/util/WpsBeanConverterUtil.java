package com.turingoal.bts.wps.util;

import com.turingoal.bts.wps.bean.Material;
import com.turingoal.bts.wps.bean.MaterialTempletItem;
import com.turingoal.bts.wps.bean.MaterialUsageItem;

/**
 * Created by Administrator on 2018/9/27 16:13.
 */
public class WpsBeanConverterUtil {
    /**
     * 将模板的item（MaterialTempletItem）转化为物料的item（Material）
     */
    public static MaterialUsageItem materialTempletItem2Material(final MaterialTempletItem materialTempletItem) {
        MaterialUsageItem materialUsageItem = new MaterialUsageItem();
        materialUsageItem.setTitle(materialTempletItem.getTitle());
        materialUsageItem.setMaterialType(materialTempletItem.getMaterialType());
        materialUsageItem.setSpecification(materialTempletItem.getSpecification());
        materialUsageItem.setUnit(materialTempletItem.getUnit());
        materialUsageItem.setAgent(materialTempletItem.getAgent());
        materialUsageItem.setBrand(materialTempletItem.getBrand());
        materialUsageItem.setDescription(materialTempletItem.getDescription());
        materialUsageItem.setRemarks(materialTempletItem.getRemarks());
        materialUsageItem.setQuantity(materialTempletItem.getQuantity());
        materialUsageItem.setMaterialId(materialTempletItem.getMaterialId());
        materialUsageItem.setMaterialCodeNum(materialTempletItem.getCodeNum());
        materialUsageItem.setMaterialItemId(materialTempletItem.getId());
        materialUsageItem.setMaterialItemCodeNum(materialTempletItem.getCodeNum());
        materialUsageItem.setCategoryCodeNum(materialTempletItem.getCategoryCodeNum());
        materialUsageItem.setCategoryName(materialTempletItem.getCategoryName());
        materialUsageItem.setFinanceItemCodeNum(materialTempletItem.getFinanceItemCodeNum());
        materialUsageItem.setFinanceItemName(materialTempletItem.getFinanceItemName());
        return materialUsageItem;
    }

    /**
     * 将物料的item（Material）转化为领用的的item（MaterialUsageItem）
     *
     * @param material
     * @param materialItemId      物料模板的id
     * @param materialItemCodeNum 物料模板的codeNum
     * @param remarks             物料模板的备注
     * @return
     */
    public static MaterialUsageItem material2MaterialUsageItem(final Material material, final String materialItemId, final String materialItemCodeNum, final String remarks) {
        MaterialUsageItem materialUsageItem = new MaterialUsageItem();
        materialUsageItem.setTitle(material.getTitle());
        materialUsageItem.setMaterialType(material.getMaterialType());
        materialUsageItem.setSpecification(material.getSpecification());
        materialUsageItem.setUnit(material.getUnit());
        materialUsageItem.setAgent(material.getAgent());
        materialUsageItem.setBrand(material.getBrand());
        materialUsageItem.setDescription(material.getDescription());
        materialUsageItem.setRemarks(remarks); // ??备注
        materialUsageItem.setQuantity(material.getQuantity());
        materialUsageItem.setMaterialId(material.getId());
        materialUsageItem.setMaterialCodeNum(material.getCodeNum());
        materialUsageItem.setMaterialItemId(materialItemId); // ??物料库物料项id
        materialUsageItem.setMaterialItemCodeNum(materialItemCodeNum); // ??物料库物料项编码
        materialUsageItem.setCategoryCodeNum(material.getCategoryCodeNum());
        materialUsageItem.setCategoryName(material.getCategoryName());
        materialUsageItem.setFinanceItemCodeNum(material.getFinanceItemCodeNum());
        materialUsageItem.setFinanceItemName(material.getFinanceItemName());
        return materialUsageItem;
    }
}
