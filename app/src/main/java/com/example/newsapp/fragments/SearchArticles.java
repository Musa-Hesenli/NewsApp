package com.example.newsapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapp.R;
import com.example.newsapp.network.ApiClient;
import com.example.newsapp.operations.Article;
import com.example.newsapp.operations.News;
import com.example.newsapp.ui.NewsViewAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchArticles#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchArticles extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String apiKey = "0166ec0d1c9241a08f3a911042cf71ba";
    private final SwipeRefreshLayout swipeRefreshLayout;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchArticles(SwipeRefreshLayout swipeRefreshLayout) {
        // Required empty public constructor
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchArticles.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchArticles newInstance(String param1, String param2, SwipeRefreshLayout swipeRefreshLayout) {
        SearchArticles fragment = new SearchArticles(swipeRefreshLayout);
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
        View view = inflater.inflate(R.layout.fragment_search_articles, container, false);
        searchingStuff(view);

        return view;
    }


    private void searchingStuff(View view) {

        ProgressBar progressBar = view.findViewById(R.id.searchingProgressBar);
        long delay = 1000; // 1 seconds after user stops typing
        final long[] last_text_edit = {0};
        EditText editText = (EditText) view.findViewById(R.id.queryInput);
        RecyclerView recyclerView = view.findViewById(R.id.searchingArticlesRecyclerView);
        NewsViewAdapter adapter = new NewsViewAdapter(view.getContext());
        TextView noResultFound = view.findViewById(R.id.noResultFoundText);
        Handler handler = new Handler();
        Runnable input_finish_checker = () -> {
            if (System.currentTimeMillis() > (last_text_edit[0] + delay - 500)) {
                progressBar.setVisibility(View.VISIBLE);
                noResultFound.setVisibility(View.GONE);
                ApiClient.getInstance().getApi().searchNews(editText.getText().toString(), apiKey).enqueue(new Callback<News>() {
                    @Override
                    public void onResponse(Call<News> call, Response<News> response) {
                        List<Article> articleList = response.body().getArticles();
                        if (response.isSuccessful() &&  articleList != null) {
                                if (articleList.size() == 0) {
                                    noResultFound.setVisibility(View.VISIBLE);
                                }
                                adapter.setArticles(articleList);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

                        } else {
                            noResultFound.setVisibility(View.VISIBLE);
                            Toast.makeText(view.getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<News> call, Throwable t) {
                        Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        noResultFound.setVisibility(View.VISIBLE);
                    }
                });
            }
        };

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s,int start, int count,
                                           int after){
            }
            @Override
            public void onTextChanged ( final CharSequence s, int start, int before,
                                        int count){
                //You need to remove this to run only once
                handler.removeCallbacks(input_finish_checker);

            }
            @Override
            public void afterTextChanged ( final Editable s){
                //avoid triggering event when text is empty
                if (s.length() > 0) {
                    last_text_edit[0] = System.currentTimeMillis();
                    handler.postDelayed(input_finish_checker, delay);
                }
            }
        }

        );
    }
}