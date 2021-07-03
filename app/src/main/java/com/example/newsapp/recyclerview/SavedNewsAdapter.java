package com.example.newsapp.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.operations.Article;
import com.example.newsapp.ui.NewsDetails;

class NewsViewHolder extends RecyclerView.ViewHolder {
    private final TextView newsTitle;
    private final ImageView newsImage;
    private final TextView resourceName;
    private final RelativeLayout container;
    private NewsViewHolder(View itemView) {
        super(itemView);
        newsTitle = itemView.findViewById(R.id.savedNewsTitle);
        newsImage = itemView.findViewById(R.id.savedNewsImage);
        resourceName = itemView.findViewById(R.id.resourceName);
        container = itemView.findViewById(R.id.savedNewsContainer);
    }

    public void bind(String text, String imageUrl, String source, String content, String description, String  author, String url, Context context) {
        newsTitle.setText(text);
        Glide.with(context).asBitmap().load(imageUrl).into(newsImage);
        if (source != null) resourceName.setText(source);
        container.setOnClickListener(v -> {
            Intent intent = new Intent(context, NewsDetails.class);
            intent.putExtra("title", text);
            intent.putExtra("source", source);
            intent.putExtra("image", imageUrl);
            intent.putExtra("localMenu", "value");
            intent.putExtra("description", description);
            intent.putExtra("content", content);
            intent.putExtra("author", author);
            intent.putExtra("url", url);
            context.startActivity(intent);
        });
    }

    static NewsViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linear_news_item, parent, false);
        return new NewsViewHolder(view);
    }
}
public class SavedNewsAdapter extends ListAdapter<Article, NewsViewHolder> {
    private final Context context;
    public SavedNewsAdapter(@NonNull DiffUtil.ItemCallback<Article> diffCallback, Context context) {
        super(diffCallback);
        this.context = context;
    }
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return NewsViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        Article current = getItem(position);
        holder.bind(current.title, current.urlToImage, current.resourceName, current.content, current.description, current.author, current.url, context);
    }

    public static class WordDiff extends DiffUtil.ItemCallback<Article> {
        @Override
        public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.title.toLowerCase().trim().equals(newItem.title.toLowerCase().trim());
        }
        @Override
        public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.title.toLowerCase().trim().equals(newItem.title.toLowerCase().trim());
        }
    }
}