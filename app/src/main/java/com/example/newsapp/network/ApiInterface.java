package com.example.newsapp.network;

import com.example.newsapp.operations.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    Call<News> getHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    @GET("everything")
    Call<News> searchNews(
            @Query("q") String query,
            @Query("apiKey") String apiKey
    );

    @GET("top-headlines")
    Call<News> getNewsByCategory(
      @Query("country") String country,
      @Query("category") String category,
      @Query("apiKey") String apiKey
    );
}
