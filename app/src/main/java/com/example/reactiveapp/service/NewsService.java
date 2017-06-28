package com.example.reactiveapp.service;

import com.example.reactiveapp.model.NewsModel;

/**
 * Created by mario on 27.6.2017..
 */

public class NewsService {
    private NewsService() {
    }

    private static NewsService instance;

    public static NewsService getInstance() {
        if (instance == null) {
            instance = new NewsService();
        }
        return instance;
    }

    public static int LOADED_NEWS_ITEMS = 0;

    private NewsModel activeNews;

    public NewsModel getActiveNews() {
        return activeNews;
    }

    public void setActiveNews(NewsModel activeNews) {
        this.activeNews = activeNews;
    }
}
