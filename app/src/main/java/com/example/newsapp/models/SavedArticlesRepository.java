package com.example.newsapp.models;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newsapp.operations.Article;

import java.util.List;

public class SavedArticlesRepository {
    private final LiveData<List<Article>> allArticles;
    private final SavedNewsDAO savedNewsDAO;
    SavedArticlesRepository(Application application) {
        SavedNewsRoomDatabase db = SavedNewsRoomDatabase.getDatabase(application);
        savedNewsDAO = db.savedNewsDAO();
        allArticles = savedNewsDAO.getAll();
    }

    LiveData<List<Article>> getAllArticles() {
        return allArticles;
    }

    void insert(Article article) {
        SavedNewsRoomDatabase.databaseWriterExecutor.execute(() -> savedNewsDAO.insert(article));
    }

    LiveData<Integer> checkItem(Article article) {
        MutableLiveData<Integer> data = new MutableLiveData();
        SavedNewsRoomDatabase.databaseWriterExecutor.execute(() -> {
            int count = savedNewsDAO.checkArticle(article.title, article.content, article.description);
            data.postValue(count);
        });
        return data;
    }

    void delete(Article article) {
        savedNewsDAO.removeNews(article.title, article.content);
    }
}
