# Android The Current News App
Capstone project for Udacity Android Developer Nanodegree
## Description
- The Current News app shows the latest global and local news (using Google Location) from <a href="https://newsapi.org" target="_blank">News API</a> and allows the user to save their favourite ones.

- When the app is first launched it will show a viewpager with three tabs: list of global, local and user’s favourite news (if no favorites are present, the app will describe how to add them). Clicking on one of the bookmark icons will add the article associated with it to the favourite list.
- Clicking on one of the article from the list will send the user to a detail screen showing the article more in depth. The user can also share an article using the share fab button.
-	It  contains a widget that displays the user’s favourite news. Clicking on one of them will open the app showing the article in detail.
-	The app handles data persistence using a content provider and a sqlite database to store favorite news.
## Screenshot
<img src="https://github.com/simoneconigliaro/android_the_current_news/blob/master/Screenshot_1.png" width="270"/>&nbsp;
<img src="https://github.com/simoneconigliaro/android_the_current_news/blob/master/Screenshot_2.png" width="270"/>&nbsp;
<img src="https://github.com/simoneconigliaro/android_the_current_news/blob/master/Screenshot_3.png" width="270"/>

## Libraries
- [Picasso](http://square.github.io/picasso/) for image loading
- [Butterknife](https://jakewharton.github.io/butterknife/) for field and method binding for Android views

## Google Play Services
- Google Location to get the current location used to retrieve local news.
- Google Analytics to collect and analyze app usage data.

## Getting Started
In order to run the app you need an api key which you can get from <a href="https://newsapi.org/register" target="_blank">News API</a> and set it in gradle.properties.
