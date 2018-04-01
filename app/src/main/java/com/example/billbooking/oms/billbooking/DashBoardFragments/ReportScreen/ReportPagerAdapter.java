package com.example.billbooking.oms.billbooking.DashBoardFragments.ReportScreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ReportPagerAdapter extends FragmentStatePagerAdapter {
    private final List<String> tabTitles = new ArrayList<String>() {{
        add("Item1");
        add("Item2");
    }};

    private List<Fragment> tabs = new ArrayList<>();

    public ReportPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

        initializeTabs();
    }

    private void initializeTabs() {
        tabs.add(new ReportScreen1());
        tabs.add(new ReportScreen2());
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }
}
