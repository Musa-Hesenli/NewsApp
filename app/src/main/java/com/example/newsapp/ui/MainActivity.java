package com.example.newsapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.newsapp.R;
import com.example.newsapp.fragments.Categories;
import com.example.newsapp.fragments.DiscoverFragment;
import com.example.newsapp.fragments.SavedArticles;
import com.example.newsapp.fragments.SearchArticles;
import com.example.newsapp.fragments.Settings;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppMainTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_in).replace(R.id.appContentFragment, new DiscoverFragment(findViewById(R.id.swipeRefreshLayout))).commit();
        }
        navigationView = findViewById(R.id.bottomNavigation);
        navigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.discoverMenu:
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.appContentFragment, new DiscoverFragment(findViewById(R.id.swipeRefreshLayout))).commit();
                    break;
                case R.id.categoriesMenu:
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.appContentFragment, new Categories()).commit();
                    break;
                case R.id.savedMenu:
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.appContentFragment, new SavedArticles()).commit();
                    break;
                case R.id.searchMenu:
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.appContentFragment, new SearchArticles(findViewById(R.id.swipeRefreshLayout))).commit();
                    break;
                case R.id.settingMenu:
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.appContentFragment, new Settings()).commit();
                    break;
            }
            return true;
        });

    }

}