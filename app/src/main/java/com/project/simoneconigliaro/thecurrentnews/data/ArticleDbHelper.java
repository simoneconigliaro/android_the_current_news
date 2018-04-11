package com.project.simoneconigliaro.thecurrentnews.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.project.simoneconigliaro.thecurrentnews.data.ArticleContract.ArticleEntry;

public class ArticleDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "articlesDb.db";

    private static final int VERSION = 1;

    public ArticleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String CREATE_TABLE = "CREATE TABLE " + ArticleEntry.TABLE_NAME + " (" +
                ArticleEntry._ID                + "INTEGER PRIMARY KEY, " +
                ArticleEntry.COLUMN_TITLE       + " TEXT NOT NULL, " +
                ArticleEntry.COLUMN_NAME        + " TEXT NOT NULL, " +
                ArticleEntry.COLUMN_DATE        + " TEXT NOT NULL, " +
                ArticleEntry.COLUMN_URL         + " TEXT NOT NULL, " +
                ArticleEntry.COLUMN_URL_IMAGE   + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ArticleEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
