package com.turingoal.bts.wps.bean;

import com.turingoal.common.android.bean.TgKeyValueBean;
import com.turingoal.common.android.util.lang.TgStringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 物料分类
 */
public final class ConstantMaterialTypes {
    private ConstantMaterialTypes() {
        throw new Error("工具类不能实例化！");
    }

    public static final String SMALL_MATERIAL = "smallMaterial"; // 小物料。一类一码
    public static final String LARGE_MATERIAL = "largeMaterial"; // 大物料。一物一码
    public static final String FITTINGS_MATERIAL = "fittings"; // 配件。一物一码
    private static Map<String, String> map;
    public static final List<TgKeyValueBean> DATA_LIST = new ArrayList<>();

    static {
        map = new HashMap<>();
        map.put(SMALL_MATERIAL, "小物料");
        map.put(LARGE_MATERIAL, "大物料");
        map.put(FITTINGS_MATERIAL, "配件");
        DATA_LIST.add(new TgKeyValueBean(SMALL_MATERIAL, map.get(SMALL_MATERIAL)));
        DATA_LIST.add(new TgKeyValueBean(LARGE_MATERIAL, map.get(LARGE_MATERIAL)));
        DATA_LIST.add(new TgKeyValueBean(FITTINGS_MATERIAL, map.get(FITTINGS_MATERIAL)));
    }

    /**
     * getStr
     */
    public static String getStr(final String type) {
        return TgStringUtil.null2Length0(map.get(type));
    }
}
