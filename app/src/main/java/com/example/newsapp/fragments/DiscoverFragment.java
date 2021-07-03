package com.example.newsapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapp.R;
import com.example.newsapp.network.ApiClient;
import com.example.newsapp.operations.Article;
import com.example.newsapp.operations.News;
import com.example.newsapp.ui.NewsViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscoverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoverFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String apiKey = "0166ec0d1c9241a08f3a911042cf71ba";
    private NewsViewAdapter adapter;
    List<Article> articles;
    RecyclerView newsRecyclerViewContainer;
    SwipeRefreshLayout swipeRefreshLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public DiscoverFragment(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiscoverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiscoverFragment newInstance(String param1, String param2, SwipeRefreshLayout swipeRefreshLayout) {
        DiscoverFragment fragment = new DiscoverFragment(swipeRefreshLayout);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        Intent intent = getActivity().getIntent();
        newsRecyclerViewContainer = view.findViewById(R.id.newsRecyclerViewContainer);
        TextView appTitle = view.findViewById(R.id.appTitle);
        if (intent.getStringExtra("searchCategory") != null) {
            appTitle.setText("Results for " + intent.getStringExtra("searchCategory"));
            searchCategory(intent.getStringExtra("searchCategory"), getCountry(), apiKey, view);
            swipeRefreshLayout.setOnRefreshListener(() -> searchCategory(intent.getStringExtra("searchCategory"), getCountry(), apiKey, view));
        } else {
            appTitle.setText("Top News");
            retrieveJson(getCountry(), apiKey, view.getContext());
            articles = new ArrayList<>();
            swipeRefreshLayout.setOnRefreshListener(() -> retrieveJson(getCountry(), apiKey, view.getContext()));
        }
        newsRecyclerViewContainer.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }

    public String getCountry() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }

    public void retrieveJson(String country, String apiKey, Context context) {
        swipeRefreshLayout.setRefreshing(true);
        Call<News> call = ApiClient.getInstance().getApi().getHeadlines(country, apiKey);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    articles.clear();
                    articles = response.body().getArticles();
                    Log.i("FETCH_RESULT", articles.toString());
                    adapter = new NewsViewAdapter(context);
                    adapter.setArticles(articles);
                    newsRecyclerViewContainer.setAdapter(adapter);
                } else {
                    Log.i("FETCH_RESULT", "No response found");
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchCategory(String category, String country, String apiKey, View context) {
        swipeRefreshLayout.setRefreshing(true);
        ApiClient.getInstance().getApi().getNewsByCategory(country, category, apiKey).enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    Log.i("Articles", response.body().getArticles().toString());
                    adapter = new NewsViewAdapter(context.getContext());
                    adapter.setArticles(response.body().getArticles());
                    if (response.body().getArticles().size() == 0) {
                        TextView noResultFind =  context.findViewById(R.id.noResultFoundText);
                        noResultFind.setVisibility(View.VISIBLE);
                    }
                    newsRecyclerViewContainer.setAdapter(adapter);
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}