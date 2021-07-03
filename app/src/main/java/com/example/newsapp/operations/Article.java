package com.example.newsapp.operations;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "saved_articles")
public class Article {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @Ignore
    private Source source;

    @ColumnInfo(name = "author")
    public String author;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "url")
    public String url;

    @ColumnInfo(name = "urlToImage")
    public String urlToImage;

    @ColumnInfo(name = "published_at")
    public String publishedAt;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "resource_name")
    public String resourceName;

    public Source getSource() {
        return source;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getContent() {
        return content;
    }

    public String getResourceName() {return resourceName;}


    @Override
    public String toString() {
        return "Article{" +
                "source: " + resourceName +
                '}';
    }
}
