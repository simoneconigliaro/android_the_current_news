package com.project.simoneconigliaro.thecurrentnews.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.simoneconigliaro.thecurrentnews.R;
import com.project.simoneconigliaro.thecurrentnews.data.Article;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>{

    private List<Article> mArticles;

    public ArticleAdapter() {

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
        holder.dateTextView.setText(dateFormatter(mArticles.get(position).getPublishedAt()));
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

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        ImageView articleImageView;
        TextView titleTextView;
        TextView nameTextView;
        TextView dateTextView;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            articleImageView = itemView.findViewById(R.id.iv_article);
            titleTextView = itemView.findViewById(R.id.tv_title);
            nameTextView = itemView.findViewById(R.id.tv_name);
            dateTextView = itemView.findViewById(R.id.tv_date);
        }
    }

    public String dateFormatter(String dateString){
        // Get only the part of date with yyyy-MM-dd'T'HH:mm
        dateString = dateString.substring(0,16);
        // Create format with pattern yyyy-MM-dd'T'HH:mm
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        long time = 0;
        long currentTime = System.currentTimeMillis();
        try {
            // Analyze dateString to find a date with the pattern given to dateFormat and get the time in millisecond
            time = dateFormat.parse(dateString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Returns a string describing 'time' as a time relative to 'now'.
        CharSequence ago = DateUtils.getRelativeTimeSpanString(time, currentTime, DateUtils.MINUTE_IN_MILLIS);
        dateString = ago.toString();
        return dateString;
    }
}
