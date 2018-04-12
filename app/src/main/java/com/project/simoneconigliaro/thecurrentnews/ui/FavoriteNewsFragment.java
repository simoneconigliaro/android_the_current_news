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
import com.project.simoneconigliaro.thecurrentnews.data.FavoriteCursorLoader;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteNewsFragment extends Fragment {

    @BindView(R.id.recycler_view_favorite)
    RecyclerView mRecyclerView;

    public static final int ID_FAVORITES_LOADER = 11;

    public FavoriteNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_favorite_news, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    public void initViews(){
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        FavoritesAdapter favoritesAdapter = new FavoritesAdapter();
        mRecyclerView.setAdapter(favoritesAdapter);
        getActivity().getSupportLoaderManager().initLoader(
                ID_FAVORITES_LOADER, null, new FavoriteCursorLoader(getContext(), favoritesAdapter));
    }
}
