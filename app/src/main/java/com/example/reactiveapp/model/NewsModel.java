package com.example.reactiveapp.model;

/**
 * Created by mario on 27.6.2017..
 */

public class NewsModel {
    private String user;
    private String title;
    private String content;

    public NewsModel(String user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

}
