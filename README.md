# Androd The Current News App
- Capston project for Udacity Android Developer Nanodegree
## Description
-	The Current News app shows the latest global and local news from <a href="https://newsapi.org" target="_blank">News API</a> and allows the user to save their favourite ones.
-	Clicking on one of the articles from the list will send the user to a detail screen showing the article more in depth.
-	It  contains a widget that displays the user’s favourite news. Clicking on one of them will open the app showing the article in detail.
-	The app handles data persistence using a content provider and a sqlite database to store favorite news.
## Screenshot
<img src="https://github.com/simoneconigliaro/android_the_current_news/blob/master/Screenshot_1563307305.png" width="280"/> <img src="https://github.com/simoneconigliaro/android_the_current_news/blob/master/Screenshot_1563307317.png" width="280"/> <img src="https://github.com/simoneconigliaro/android_the_current_news/blob/master/Screenshot_1563305958.png" width="280"/>
## Google Play Services
- Google Location to get the current location used to retrieve local news.
- Google Analytics to collect and analyze app usage data.

## Getting Started
In order to run the app you need an api key which you can get <a href="https://newsapi.org/register" target="_blank">here</a> and set it in gradle.properties.
