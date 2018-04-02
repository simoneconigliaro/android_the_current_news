package com.project.simoneconigliaro.thecurrentnews.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class GlobalNewsFragment extends Fragment {

    public GlobalNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_global_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new FetchArticlesTask().execute();
    }
}
