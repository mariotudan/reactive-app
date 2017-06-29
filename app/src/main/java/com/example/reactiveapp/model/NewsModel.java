package com.example.reactiveapp.model;

/**
 * Created by mario on 27.6.2017..
 */

public class NewsModel {
    private String author;
    private String name;
    private String content;
    private String date;
    private String imageUrl;

    public NewsModel(String author, String name, String content, String date, String imageUrl) {
        this.author = author;
        this.name = name;
        this.content = content;
        this.date = date;
        this.imageUrl = imageUrl;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
