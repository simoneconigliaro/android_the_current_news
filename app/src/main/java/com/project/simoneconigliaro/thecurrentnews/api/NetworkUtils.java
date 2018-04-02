package com.project.simoneconigliaro.thecurrentnews.api;

import android.net.Uri;
import android.util.Log;

import com.project.simoneconigliaro.thecurrentnews.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the API from newsapi.org.
 */
public class NetworkUtils {

    private static String LOG_TAG = NetworkUtils.class.getSimpleName();
    final static String apiKey = BuildConfig.NEWS_API_KEY;
    final static String language = "en";
    final static String LANGUAGE_PARAM = "language";
    final static String COUNTRY_PARAM = "country";
    final static String KEY_PARAM = "apiKey";

    /**
     * Builds the url to get the global news from the server.
     *
     * @return the url used to get the data from the server.
     */
    public static URL buildGlobalNewsURL(){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("newsapi.org")
                .appendPath("v2")
                .appendPath("top-headlines")
                .appendQueryParameter(LANGUAGE_PARAM, language)
                .appendQueryParameter(KEY_PARAM, apiKey)
                .build();
        URL url = null;
        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(LOG_TAG, url.toString());
        return url;
    }

    /**
     * Builds the url to get the news from a specific country from the server.
     *
     * @param country used to get news from a specific country.
     * @return the url used to get the data from the server.
     */
    public static URL buildLocalNewsURL(String country){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("newsapi.org")
                .appendPath("v2")
                .appendPath("top-headlines")
                .appendQueryParameter(COUNTRY_PARAM, country)
                .appendQueryParameter(LANGUAGE_PARAM, language)
                .appendQueryParameter(KEY_PARAM, apiKey)
                .build();
        URL url = null;
        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(LOG_TAG, url.toString());
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream response = urlConnection.getInputStream();
            Scanner scanner = new Scanner(response);

            /* The \\A delimiter is a handy way of converting a stream into a String. Basically it matches the entire stream and creates a string out of it*/
            scanner.useDelimiter("\\A");

            /* hasNext() returns true if this scanner has another token in its input */
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                /* next() method finds and returns the next complete token from this scanner. */
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }




}
