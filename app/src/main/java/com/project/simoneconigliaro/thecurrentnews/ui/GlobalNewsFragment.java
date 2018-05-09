package com.project.simoneconigliaro.thecurrentnews.ui;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.project.simoneconigliaro.thecurrentnews.R;
import com.project.simoneconigliaro.thecurrentnews.api.FetchArticlesTask;
import com.project.simoneconigliaro.thecurrentnews.api.NetworkUtils;
import com.project.simoneconigliaro.thecurrentnews.api.OpenArticleJsonUtils;
import com.project.simoneconigliaro.thecurrentnews.data.Article;
import com.project.simoneconigliaro.thecurrentnews.utils.InternetUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class GlobalNewsFragment extends Fragment implements ArticleAdapter.ArticleAdapterOnClickHandler {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.srl_global_news)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ArticleAdapter mArticleAdapter;
    private final static String ARTICLE_KEY = "article";
    private final static String LAYOUT_STATE_KEY = "layout_state";
    private final static String GLOBAL = "Global";

    private Parcelable mLayoutState;


    public GlobalNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_global_news, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(savedInstanceState != null){
            mLayoutState = savedInstanceState.getParcelable(LAYOUT_STATE_KEY);
        }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                boolean isInternetAvailable = InternetUtils.checkConnection(getContext());
                if (isInternetAvailable) {
                    new FetchArticlesTask(mArticleAdapter, mRecyclerView, mLayoutState, GLOBAL).execute();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        initViews();

    }

    @Override
    public void onListItemClick(Article currentArticleObjectClicked) {
        Intent intent = new Intent(getContext(), DetailArticleActivity.class);
        intent.putExtra(ARTICLE_KEY, currentArticleObjectClicked);
        startActivity(intent);
    }

    public void initViews(){
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mArticleAdapter = new ArticleAdapter(this, getContext());
        mRecyclerView.setAdapter(mArticleAdapter);
        new FetchArticlesTask(mArticleAdapter, mRecyclerView, mLayoutState, GLOBAL).execute();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            if(mArticleAdapter != null) {
                mArticleAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mRecyclerView != null) {
            mLayoutState = mRecyclerView.getLayoutManager().onSaveInstanceState();
            outState.putParcelable(LAYOUT_STATE_KEY, mLayoutState);
        }
    }


}
