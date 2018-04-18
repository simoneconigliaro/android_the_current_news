package com.project.simoneconigliaro.thecurrentnews.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.simoneconigliaro.thecurrentnews.R;
import com.project.simoneconigliaro.thecurrentnews.data.Article;
import com.project.simoneconigliaro.thecurrentnews.data.ArticleContract;
import com.project.simoneconigliaro.thecurrentnews.data.ArticleDbUtils;
import com.project.simoneconigliaro.thecurrentnews.data.DateUtils;
import com.squareup.picasso.Picasso;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>{

    private List<Article> mArticles;
    private Context mContext;
    ArticleAdapterOnClickHandler mClickHandler;
    private boolean mIsArticleSaved;
    private String mIdArticle;

    private final static String ARTICLE_SAVED = "Article saved";
    private final static String ARTICLE_REMOVED = "Article removed";

    public ArticleAdapter(ArticleAdapterOnClickHandler clickHandler, Context context) {
        this.mClickHandler = clickHandler;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.article_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ArticleViewHolder holder, final int position) {
        mIdArticle = ArticleDbUtils.checkIfArticleIsAlreadyStored(mContext, mArticles.get(position));
        if (mIdArticle != null){
            holder.bookmarkImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_bookmark_blue_24dp));
        } else {
            holder.bookmarkImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_bookmark_border_blue_24dp));
        }

        holder.titleTextView.setText(mArticles.get(position).getTitle());
        holder.nameTextView.setText(mArticles.get(position).getName());
        holder.dateTextView.setText(DateUtils.dateFormatter(mArticles.get(position).getPublishedAt()));
        Picasso.get().load(mArticles.get(position).getUrlToImage()).error(R.drawable.placeholder_error).into(holder.articleImageView);

        holder.bookmarkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIdArticle = ArticleDbUtils.checkIfArticleIsAlreadyStored(mContext, mArticles.get(position));
                if(mIdArticle != null){
                    ArticleDbUtils.deleteFavoriteArticle(mContext, mIdArticle);
                    holder.bookmarkImageView.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.ic_bookmark_border_blue_24dp));
                    Toast.makeText(mContext, ARTICLE_REMOVED, Toast.LENGTH_SHORT).show();
                } else {
                    ArticleDbUtils.addFavoriteArticle(mContext, mArticles.get(position));
                    holder.bookmarkImageView.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.ic_bookmark_blue_24dp));
                    Toast.makeText(mContext, ARTICLE_SAVED, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mArticles == null ) return 0;
        return mArticles.size();
    }

    public void setArticlesData (List<Article> articles) {
        mArticles = articles;
        notifyDataSetChanged();
    }

    interface ArticleAdapterOnClickHandler {
        void onListItemClick (Article currentArticleObjectClicked);
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.iv_article)
        ImageView articleImageView;
        @BindView(R.id.tv_title)
        TextView titleTextView;
        @BindView(R.id.tv_name)
        TextView nameTextView;
        @BindView(R.id.tv_date)
        TextView dateTextView;
        @BindView(R.id.iv_bookmark)
        ImageView bookmarkImageView;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Article currentArticleClicked = mArticles.get(adapterPosition);
            mClickHandler.onListItemClick(currentArticleClicked);
        }
    }

    private String checkIfArticleIsAlreadyStored(Context context, Article article){
        Cursor cursor = context.getContentResolver().query(ArticleContract.ArticleEntry.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String url = cursor.getString(cursor.getColumnIndexOrThrow(ArticleContract.ArticleEntry.COLUMN_URL));
                if (url != null) {
                    if (url.equals(article.getUrl())) {
                        String idArticle = cursor.getString(cursor.getColumnIndexOrThrow(ArticleContract.ArticleEntry._ID));
                        return idArticle;
                    }
                }
            }
            cursor.close();
        }
        return null;
    }
}
