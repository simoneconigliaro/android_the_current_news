package com.project.simoneconigliaro.thecurrentnews.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class GlobalNewsFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private ArticleAdapter mArticleAdapter;

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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mArticleAdapter = new ArticleAdapter();
        mRecyclerView.setAdapter(mArticleAdapter);

        new FetchArticlesTask(mArticleAdapter).execute();
    }
}
