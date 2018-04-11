package com.project.simoneconigliaro.thecurrentnews.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class ArticleContract {

    public static final String AUTHORITY = "com.project.simoneconigliaro.thecurrentnews";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_ARTICLES = "articles";


    public static final class ArticleEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ARTICLES).build();


        public static final String TABLE_NAME = "articles";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_DATE = "date";

        public static final String COLUMN_URL = "url";

        public static final String COLUMN_URL_IMAGE = "urlImage";

    }

}
