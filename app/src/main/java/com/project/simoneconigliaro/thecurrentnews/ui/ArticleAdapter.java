package com.project.simoneconigliaro.thecurrentnews.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.project.simoneconigliaro.thecurrentnews.R;
import com.project.simoneconigliaro.thecurrentnews.data.Article;
import com.project.simoneconigliaro.thecurrentnews.data.DateUtils;
import com.squareup.picasso.Picasso;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>{

    private List<Article> mArticles;
    ArticleAdapterOnClickHandler mClickHandler;

    public ArticleAdapter(ArticleAdapterOnClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
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
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {

        holder.titleTextView.setText(mArticles.get(position).getTitle());
        holder.nameTextView.setText(mArticles.get(position).getName());
        holder.dateTextView.setText(DateUtils.dateFormatter(mArticles.get(position).getPublishedAt()));
        Picasso.get().load(mArticles.get(position).getUrlToImage()).error(R.drawable.placeholder_error).into(holder.articleImageView);
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

}
