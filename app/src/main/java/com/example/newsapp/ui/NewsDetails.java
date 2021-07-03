package com.example.newsapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.models.SavedNewsViewModel;
import com.example.newsapp.operations.Article;

public class NewsDetails extends AppCompatActivity {
    SavedNewsViewModel savedNewsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        Intent intent = getIntent();
        TextView title = findViewById(R.id.newsDetailsTitle);
        title.setText(intent.getStringExtra("title"));
        TextView description = findViewById(R.id.newsDetailsDescription);
        description.setText(intent.getStringExtra("description"));
        TextView content = findViewById(R.id.newsDetailsContent);
        content.setText(intent.getStringExtra("content"));
        ImageView imageView = findViewById(R.id.newsDetailsImage);
        Glide.with(this).asBitmap().load(intent.getStringExtra("image")).into(imageView);
        TextView author = findViewById(R.id.author);
        author.setText("Author: " + intent.getStringExtra("author"));
        TextView sourceText = findViewById(R.id.sourceName);
        sourceText.setText(intent.getStringExtra("source"));
        if (intent.getStringExtra("description") == null) {
            description.setVisibility(View.GONE);
        }
        if (intent.getStringExtra("content") == null) {
            content.setVisibility(View.GONE);
        }
        if (intent.getStringExtra("image") == null) {
            imageView.setVisibility(View.GONE);
        }
        if (intent.getStringExtra("author") == null) {
            author.setVisibility(View.GONE);
        }

        savedNewsViewModel = new ViewModelProvider(this).get(SavedNewsViewModel.class);


        Toolbar toolbar = findViewById(R.id.appToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if (getIntent().getStringExtra("localMenu") == null) {
            menuInflater.inflate(R.menu.options_menu, menu);
        } else {
            menuInflater.inflate(R.menu.options_menu_local, menu);
        }
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveLocal:
                saveLocal(getIntent());
                break;
            case R.id.openInBrower:
                String url = getIntent().getStringExtra("url");
                if (url == null) break;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            case R.id.shareNews:
                shareNews(getIntent().getStringExtra("url"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void saveLocal(Intent intent) {
        Article article = new Article();
        article.title = intent.getStringExtra("title");
        article.description = intent.getStringExtra("description");
        article.content = intent.getStringExtra("content");
        article.urlToImage = intent.getStringExtra("image");
        article.resourceName = intent.getStringExtra("source");
        article.url = intent.getStringExtra("url");
        savedNewsViewModel.checkArticle(article).observe(this, integer -> {
            if (integer > 0) {
                Toast.makeText(this, "Already exists", Toast.LENGTH_SHORT).show();
            } else {
                savedNewsViewModel.insert(article);
                Toast.makeText(this, "Article saved successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void shareNews(String newsLink) {
        if (newsLink == null) {
          Toast.makeText(this, "No link found", Toast.LENGTH_SHORT).show();
          return;
        };
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, newsLink);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    private void removeFromLocal(Intent intent) {
        Article article = new Article();
        article.title = intent.getStringExtra("title");
        article.description = intent.getStringExtra("description");
        article.content = intent.getStringExtra("content");
        article.urlToImage = intent.getStringExtra("image");
        savedNewsViewModel.checkArticle(article).observe(this, integer -> {
            if (integer > 0) {
                Thread removeThread = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        savedNewsViewModel.removeFromLocal(article);
                        Log.i("Articles", "removed");
                    }
                };
                removeThread.start();
            } else {
                Toast.makeText(this, "You have not saved this article", Toast.LENGTH_SHORT).show();
            }
        });
    }


}