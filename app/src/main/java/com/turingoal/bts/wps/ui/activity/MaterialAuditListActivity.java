package com.turingoal.bts.wps.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.turingoal.bts.wps.R;
import com.turingoal.bts.wps.app.TgArouterPaths;
import com.turingoal.bts.wps.ui.adapter.MaterialAuditListPagerAdapter;
import com.turingoal.bts.wps.ui.fragment.MaterialAuditCompleteFragment;
import com.turingoal.bts.wps.ui.fragment.MaterialAuditWaitFragment;
import com.turingoal.common.android.base.TgBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 审核界面
 */
@Route(path = TgArouterPaths.MATERIAL_AUDIT_LIST)
public class MaterialAuditListActivity extends TgBaseActivity {
    @BindView(R.id.tlMaterialAuditList)
    TabLayout tlMaterialAuditList; // tab
    @BindView(R.id.vpMaterialAuditList)
    ViewPager vpMaterialAuditList; //　内容

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_material_audit_list;
    }

    @Override
    protected void initialized() {
        initToolbar(R.id.tg_common_view_title_bar_tvTitle, R.id.tg_common_view_title_bar_ivLeftBut, "审核列表");
        List<Fragment> fragments = new ArrayList<>(); // Fragment数据
        List<String> titles = new ArrayList<>(); // title数据
        titles.add("待审核");
        fragments.add(new MaterialAuditWaitFragment());
        titles.add("已审核");
        fragments.add(new MaterialAuditCompleteFragment());
        vpMaterialAuditList.setAdapter(new MaterialAuditListPagerAdapter(getSupportFragmentManager(), titles, fragments));
        tlMaterialAuditList.setupWithViewPager(vpMaterialAuditList);
    }
}
