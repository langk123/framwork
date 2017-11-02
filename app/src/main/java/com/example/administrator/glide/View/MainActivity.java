package com.example.administrator.glide.View;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;


import com.example.administrator.glide.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import java.util.List;


public class MainActivity extends AppCompatActivity {


    public static final String showapi_appid = "34276";
    public static final String showapi_sign = "731d68d6d56b4d789d6571f530ee28ef";
    public static final int num = 10;


    SwipeRefreshLayout swipeRefreshLayout;
    private TabLayout mTabTl;
    private ViewPager mContentVp;
    private List listFragment;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(0xf0ff4081);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        initView();
    }

    void initView(){
      final  GrilFragment fragment1 = new GrilFragment();
      final  GrilFragment fragment2 = new GrilFragment();
      final  GrilFragment fragment3 = new GrilFragment();

        listFragment= new ArrayList<Fragment>();
        listFragment.add(fragment1);
        listFragment.add(fragment2);
        listFragment.add(fragment3);
        FragmentPagerAdapter adapter = new MainFragmentAdapter(getSupportFragmentManager(),listFragment);
        mContentVp =  findViewById(R.id.vp_content);
        mContentVp.setAdapter(adapter);

        mTabTl = findViewById(R.id.tab_layout);
        mTabTl.setupWithViewPager(mContentVp);
        for (int i = 0; i < listFragment.size(); i++) {
            TabLayout.Tab itemTab = mTabTl.getTabAt(i);
            if (itemTab!=null){
                itemTab.setCustomView(R.layout.item_tab_layout_custom);
                TextView itemTv = itemTab.getCustomView().findViewById(R.id.tv_menu_item);
                itemTv.setText("美女");
            }
        }
        mTabTl.getTabAt(0).getCustomView().setSelected(true);
        swipeRefreshLayout = findViewById(R.id.refreshlayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(){
                EventBus.getDefault().post("refresh");
            }
        });
    }




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String string){
        if (string.equals("refreshOk"));
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除注册
        EventBus.getDefault().unregister(this);
    }

    class MainFragmentAdapter extends FragmentPagerAdapter {
        List<Fragment> list;
        public MainFragmentAdapter(FragmentManager fm, List<Fragment> list){
            super(fm);
            this.list=list;
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
}
