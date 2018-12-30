package com.fatimamostafa.app.newsviewsv2.utilities;

public class Constants {

    public static final String APP_LAUNCHED_SECOND_TIME = "APP_LAUNCHED_SECOND_TIME";

    public static class API {
        public static final String NEWS_API_BASE_URL = "https://newsapi.org/v2/";
        public static final String SEARCH_API_BASE_URL = "http://numbersapi.com/";
        public static String NEWS_API_KEY = "653a9bead9994c1892bf67af5eb5aae7";

    }

    public class Fragments {
        public static final String HOME = "com.fatimamostafa.app.newsviewsv2.ui.home.HomeFragment";
        public static final String ABOUT = "com.fatimamostafa.app.newsviewsv2.ui.about.AboutFragment";
    }

    public class Intents {
        public static final String ARTICLE_LIST = "ARTICLE_LIST";
        public static final String NEWS_TYPE = "NEWS_TYPE";
        public static final String ARTICLE = "ARTICLE";
    }
}
