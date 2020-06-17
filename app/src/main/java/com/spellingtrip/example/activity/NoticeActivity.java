package com.spellingtrip.example.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.spellingtrip.example.R;
import com.spellingtrip.example.fragment.NoticeAnnouncemFragment;
import com.spellingtrip.example.fragment.NoticeMeassageFragment;
import com.spellingtrip.example.fragment.NoticeRemindFragment;
import com.spellingtrip.example.viewpager.PublishViewPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class NoticeActivity extends BaseActivity{
    @BindView(R.id.notice_tab)
    public TabLayout tabLayout;
    @BindView(R.id.notice_viewPager)
    public ViewPager viewPager;
    private String[]titles=new String[]{"消息","提醒","公告"};
    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice;
    }

    @Override
    protected void initView() {
        List fragments = new ArrayList<Fragment>();
        fragments.add(new NoticeMeassageFragment());
        fragments.add(new NoticeRemindFragment());
        fragments.add(new NoticeAnnouncemFragment());
        PublishViewPagerAdapter adapter = new PublishViewPagerAdapter(getSupportFragmentManager(),fragments, Arrays.asList(titles));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setData() {
        backClick();
        setCenterTitle("拼娃官方");
    }
}
