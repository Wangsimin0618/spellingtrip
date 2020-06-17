package com.spellingtrip.example.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private String[]titles;
    public ViewPagerAdapter(FragmentManager  supportFragmentManager, List<Fragment> list,String[]titles) {
        super(supportFragmentManager);
        this.list = list;
        this.titles=titles;

    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
    /**
     * //此方法用来显示tab上的名字
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
