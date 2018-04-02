package com.project.simoneconigliaro.thecurrentnews.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.simoneconigliaro.thecurrentnews.R;
import com.project.simoneconigliaro.thecurrentnews.api.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String GLOBAL_NEWS = "Global";
    public static final String LOCAL_NEWS = "Local";
    public static final String FAVORITE_NEWS = "Favorite";

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new GlobalNewsFragment(), GLOBAL_NEWS);
        viewPagerAdapter.addFragment(new LocalNewsFragment(), LOCAL_NEWS);
        viewPagerAdapter.addFragment(new FavoriteNewsFragment(), FAVORITE_NEWS);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        NetworkUtils.buildGlobalNewsURL();
        NetworkUtils.buildLocalNewsURL("it");

    }
}
