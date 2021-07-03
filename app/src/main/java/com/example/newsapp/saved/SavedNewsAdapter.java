package com.example.newsapp.saved;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.operations.Article;
import com.example.newsapp.operations.News;

import java.util.ArrayList;
import java.util.List;

public class SavedNewsAdapter extends RecyclerView.Adapter<SavedNewsAdapter.ViewHolder> {
    private final Context context;
    private List<Article> items = new ArrayList<>();
    public SavedNewsAdapter(Context context, List<Article> items) {
        this.context = context;
        this.items = items;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.linear_news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SavedNewsAdapter.ViewHolder holder, int position) {
        holder.newsTitle.setText(items.get(position).getTitle());
        Glide.with(context).asBitmap().load(items.get(position).getUrlToImage()).into(holder.newsImage);
        holder.resourceName.setText(items.get(position).getResourceName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView newsTitle;
        private final ImageView newsImage;
        private final TextView resourceName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.savedNewsTitle);
            newsImage = itemView.findViewById(R.id.savedNewsImage);
            resourceName = itemView.findViewById(R.id.resourceName);
        }
    }
}
