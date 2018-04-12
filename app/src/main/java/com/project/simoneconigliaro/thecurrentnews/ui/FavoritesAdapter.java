package com.project.simoneconigliaro.thecurrentnews.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.simoneconigliaro.thecurrentnews.R;
import com.project.simoneconigliaro.thecurrentnews.data.Article;
import com.project.simoneconigliaro.thecurrentnews.data.ArticleContract;
import com.project.simoneconigliaro.thecurrentnews.data.DateUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteAdapterViewHolder> {

    private final static String ARTICLE_KEY = "article";
    private Cursor cursor;

    public FavoritesAdapter() {
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }

    public class FavoriteAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_article)
        ImageView articleImageView;
        @BindView(R.id.tv_title)
        TextView titleTextView;
        @BindView(R.id.tv_name)
        TextView nameTextView;
        @BindView(R.id.tv_date)
        TextView dateTextView;


        public FavoriteAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), DetailArticleActivity.class);
            cursor.moveToPosition(getAdapterPosition());
            Article currentArticle = getArticleFromCursor();
            intent.putExtra(ARTICLE_KEY, currentArticle);
            view.getContext().startActivity(intent);

        }

        private Article getArticleFromCursor() {
            String name = cursor.getString(cursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_NAME));
            String title = cursor.getString(cursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_TITLE));
            String url = cursor.getString(cursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_URL));
            String urlToImage = cursor.getString(cursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_URL_IMAGE));
            String publishedAt = cursor.getString(cursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_DATE));
            Article currentArticle = new Article(name, title,  url, urlToImage, publishedAt);
            return currentArticle;
        }
    }

    @NonNull
    @Override
    public FavoriteAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.article_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new FavoriteAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapterViewHolder holder, int position) {

        cursor.moveToPosition(position);
        String name = cursor.getString(cursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_NAME));
        String title = cursor.getString(cursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_TITLE));
        String urlToImage = cursor.getString(cursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_URL_IMAGE));
        String publishedAt = cursor.getString(cursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_DATE));

        holder.titleTextView.setText(title);
        holder.nameTextView.setText(name);
        holder.dateTextView.setText(DateUtils.dateFormatter(publishedAt));
        Picasso.get().load(urlToImage).error(R.drawable.placeholder_error).into(holder.articleImageView);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }
}
