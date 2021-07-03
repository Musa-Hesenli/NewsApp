package com.example.newsapp.operations;



import java.util.List;
public class News {
    private int id;

    private String status;
    private String totalResults;
    private List<Article> articles;

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

}
