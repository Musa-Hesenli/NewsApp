package com.example.newsapp.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newsapp.operations.Article;

import java.util.List;

public class SavedNewsViewModel extends AndroidViewModel {
    private final SavedArticlesRepository savedArticlesRepository;
    private final LiveData<List<Article>> allArticles;
    public SavedNewsViewModel(Application application) {
        super(application);
        savedArticlesRepository = new SavedArticlesRepository(application);
        allArticles = savedArticlesRepository.getAllArticles();
    }
    public LiveData<List<Article>> getAllArticles() {
        return allArticles;
    }
    public void insert(Article article) {
        savedArticlesRepository.insert(article);
    }

    public LiveData<Integer> checkArticle(Article article) {
        return savedArticlesRepository.checkItem(article);
    }
    public void removeFromLocal(Article article){
        savedArticlesRepository.delete(article);
    }
}
