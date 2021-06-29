package com.example.flickr_mvp.Activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flickr_mvp.Adapters.ImageAdapter;
import com.example.flickr_mvp.Objects.Image;
import com.example.flickr_mvp.Presenters.SearchPresenter;
import com.example.flickr_mvp.R;
import com.example.flickr_mvp.Services.SearchService;
import com.example.flickr_mvp.Utils.SuggestionProvider;
import com.example.flickr_mvp.Views.SearchView;
import java.util.ArrayList;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchActivity extends Activity {

    private Context context;
    private SearchPresenter searchPresenter;
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private Integer pageNumber;
    private String text;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.search_results);
        init();
        handleIntent(getIntent());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy!=0 && !recyclerView.canScrollVertically(1)) loadData();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void init() {
        pageNumber = 1;

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        imageAdapter = new ImageAdapter(new ArrayList<>(), context);
        recyclerView.setAdapter(imageAdapter);

        searchPresenter = new SearchPresenter(
                Schedulers.io(), AndroidSchedulers.mainThread(),
                new SearchView(context),
                new SearchService(context.getString(R.string.searchUrl))
        );
    }

    public void applyChanges(List<Image> images) {
        this.imageAdapter.addAll(images);
        incrementPageNumber();
    }

    private void handleIntent(@NonNull Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            text = intent.getStringExtra(SearchManager.QUERY).trim();
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SuggestionProvider.AUTHORITY, SuggestionProvider.MODE);
            suggestions.saveRecentQuery(text, null);
            loadData();
        }
    }

    private void loadData() {
        searchPresenter.loadSearchTerm(context.getString(R.string.searchApiKey), pageNumber, text);
    }

    private void incrementPageNumber() {
        pageNumber++;
    }
}
