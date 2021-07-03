package com.example.newsapp.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.newsapp.operations.Article;

import java.util.List;

@Dao
public interface SavedNewsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Article article);

    @Query("DELETE FROM saved_articles")
    void deleteAll();

    @Query("delete from saved_articles where title =:title and content =:content")
    void removeNews(String title, String content);

    @Query("SELECT * FROM saved_articles")
    LiveData<List<Article>> getAll();

    @Query("select Count() from saved_articles where title =:title and content =:content and description =:description")
    int checkArticle(String title, String content, String description);
}
