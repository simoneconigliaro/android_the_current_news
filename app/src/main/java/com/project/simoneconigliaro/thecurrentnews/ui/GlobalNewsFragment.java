package com.project.simoneconigliaro.thecurrentnews.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.simoneconigliaro.thecurrentnews.R;
import com.project.simoneconigliaro.thecurrentnews.api.FetchArticlesTask;
import com.project.simoneconigliaro.thecurrentnews.api.NetworkUtils;
import com.project.simoneconigliaro.thecurrentnews.api.OpenArticleJsonUtils;
import com.project.simoneconigliaro.thecurrentnews.data.Article;

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

    private ArticleAdapter mArticleAdapter;

    private final static String ARTICLE_KEY = "article";

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
        initViews();
        new FetchArticlesTask(mArticleAdapter).execute();
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
        mArticleAdapter = new ArticleAdapter(this);
        mRecyclerView.setAdapter(mArticleAdapter);
    }
}
