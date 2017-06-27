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

    private NewsModel activeNews;

    public NewsModel getActiveNews() {
        return activeNews;
    }

    public void setActiveNews(NewsModel activeNews) {
        this.activeNews = activeNews;
    }
}
