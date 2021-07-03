package com.example.newsapp.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;

import java.util.ArrayList;
import java.util.List;

public class NewsCategoriesAdatper extends RecyclerView.Adapter<NewsCategoriesAdatper.ViewHolder>{
    private final Context context;
    private List<Category> categories = new ArrayList<>();
    public NewsCategoriesAdatper(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsCategoriesAdatper.ViewHolder holder, int position) {
        holder.categoryTitle.setText(categories.get(position).name);
        Glide.with(context).asBitmap().load(categories.get(position).image).into(holder.categoryBackground);
        holder.categoryContainer.setOnClickListener(v -> {
            Intent intent = new Intent(context,MainActivity.class);
            intent.putExtra("searchCategory", categories.get(position).name);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryTitle;
        private final ImageView categoryBackground;
        private final CardView categoryContainer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.categoryTitle);
            categoryBackground = itemView.findViewById(R.id.categoryImage);
            categoryContainer = itemView.findViewById(R.id.categoryContainer);
        }
    }
}
