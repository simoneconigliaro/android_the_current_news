package com.project.simoneconigliaro.thecurrentnews.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable {

    private String name;
    private String title;
    private String url;
    private String urlToImage;
    private String publishedAt;

    public Article(String name, String title,  String url, String urlToImage, String publishedAt) {
        this.name = name;
        this.title = title;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    private Article (Parcel in){
        this.name = in.readString();
        this.title = in.readString();
        this.url = in.readString();
        this.urlToImage = in.readString();
        this.publishedAt = in.readString();
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(title);
        parcel.writeString(url);
        parcel.writeString(urlToImage);
        parcel.writeString(publishedAt);
    }

    // Parcelable.Creator that is used to create an instance of the class from the Parcel data
    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel parcel) {
            return new Article(parcel);
        }

        @Override
        public Article[] newArray(int i) {
            return new Article[i];
        }
    };
}
