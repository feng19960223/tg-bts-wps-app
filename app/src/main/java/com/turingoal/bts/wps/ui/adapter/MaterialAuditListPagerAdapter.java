package com.turingoal.bts.wps.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 物料审核列表的pagerAdapter
 */
public class MaterialAuditListPagerAdapter extends FragmentPagerAdapter {
    private List<String> titles; // title
    private List<Fragment> fragments; // 创建一个List<Fragment>

    public MaterialAuditListPagerAdapter(FragmentManager fragmentManager, List<String> titles, List<Fragment> fragments) {
        super(fragmentManager);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
