package com.example.billbooking.oms.billbooking.DashBoardFragments.ReportScreen;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.billbooking.oms.billbooking.DashBoard;
import com.example.billbooking.oms.billbooking.R;

public class ReportScreen extends Fragment {

    ReportPagerAdapter customPagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_screen, container, false);
        ((DashBoard) getActivity()).getSupportActionBar().setTitle("Reports");
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        customPagerAdapter = new ReportPagerAdapter(getActivity().getSupportFragmentManager());

        // increase this limit if you have more tabs!
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(customPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF0000"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));


        return view;
    }


}
