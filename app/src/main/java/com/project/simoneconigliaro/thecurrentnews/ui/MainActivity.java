package com.project.simoneconigliaro.thecurrentnews.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.project.simoneconigliaro.thecurrentnews.BuildConfig;
import com.project.simoneconigliaro.thecurrentnews.R;
import com.project.simoneconigliaro.thecurrentnews.api.FetchArticlesTask;
import com.project.simoneconigliaro.thecurrentnews.utils.InternetUtils;
import com.project.simoneconigliaro.thecurrentnews.widget.NewsWidgetProvider;


import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private int positionTab;
    private final static String POSITION_TAB_KEY = "position_tab";

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState != null){
            savedInstanceState.getInt(POSITION_TAB_KEY);
            viewPager.setCurrentItem(savedInstanceState.getInt(POSITION_TAB_KEY));
        }

        viewPager.setCurrentItem(positionTab);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new GlobalNewsFragment(), getString(R.string.global));
        viewPagerAdapter.addFragment(new LocalNewsFragment(), getString(R.string.local));
        viewPagerAdapter.addFragment(new FavoriteNewsFragment(), getString(R.string.favorite));
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        NewsWidgetProvider.sendRefreshBroadcast(this);
        InternetUtils.checkConnection(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        positionTab = viewPager.getCurrentItem();
        outState.putInt(POSITION_TAB_KEY, viewPager.getCurrentItem());
    }



}
