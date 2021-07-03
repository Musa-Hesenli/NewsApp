package com.example.newsapp.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.operations.Article;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsViewAdapter extends RecyclerView.Adapter<NewsViewAdapter.ViewHolder>{
    private final Context context;
    private List<Article> articles = new ArrayList<>();
    public NewsViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewAdapter.ViewHolder holder, int position) {
        holder.newsTitle.setText(articles.get(position).getTitle());
        holder.newsDesc.setText(convertDate(articles.get(position).getPublishedAt()));
        String image_url = articles.get(position).getUrlToImage();
        holder.sourceName.setText(articles.get(position).getSource().getName());
        if (image_url == null) {
            image_url = "https://www.ntu.ac.uk/__data/assets/image/0026/271817/Default-news.jpg";
        }
        Glide.with(context).asBitmap().load(image_url).into(holder.newsImage);
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, NewsDetails.class);
            intent.putExtra("image", articles.get(position).getUrlToImage());
            intent.putExtra("title", articles.get(position).getTitle());
            intent.putExtra("content", articles.get(position).getContent());
            intent.putExtra("description", articles.get(position).getDescription());
            intent.putExtra("author", articles.get(position).getAuthor());
            intent.putExtra("url", articles.get(position).getUrl());
            intent.putExtra("source", articles.get(position).getSource().getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView newsTitle;
        private final TextView newsDesc;
        private final ImageView newsImage;
        private final TextView sourceName;
        private final CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            newsDesc = itemView.findViewById(R.id.newsDescription);
            newsImage = itemView.findViewById(R.id.newsImage);
            sourceName = itemView.findViewById(R.id.sourceName);
            cardView = itemView.findViewById(R.id.news_container);
        }
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    private String convertDate(String date) {
        String time = null;
        PrettyTime prettyTime = new PrettyTime(new Locale(getCountry()));
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date date1 = simpleDateFormat.parse(date);
            time = prettyTime.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
    public String getCountry() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}
