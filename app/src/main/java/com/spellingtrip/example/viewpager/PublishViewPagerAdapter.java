package com.spellingtrip.example.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class PublishViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private  List<String> titles;

    public PublishViewPagerAdapter(android.support.v4.app.FragmentManager supportFragmentManager, List<Fragment> list, List<String> titles) {
        super(supportFragmentManager);
        this.list = list;
        this.titles=titles;

    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titles.get(position);
    }
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}

