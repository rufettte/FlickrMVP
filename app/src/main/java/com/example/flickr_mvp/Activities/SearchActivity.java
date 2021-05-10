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
import com.example.flickr_mvp.Configs.SearchConfig;
import com.example.flickr_mvp.Objects.Image;
import com.example.flickr_mvp.Presenters.SearchPresenter;
import com.example.flickr_mvp.R;
import com.example.flickr_mvp.Utils.SuggestionProvider;
import com.example.flickr_mvp.Views.SearchView;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchActivity extends Activity {

    Context context;
    SearchView searchView;
    SearchConfig searchConfig;
    SearchPresenter searchPresenter;
    RecyclerView recyclerView;
    ImageAdapter imageAdapter;

    public void init() {
        /*initialize the context of the search activity*/
        context = this;
        /* Initialize recyclerview and its adapter */
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        imageAdapter = new ImageAdapter(new ArrayList<>(), this.context);
        recyclerView.setAdapter(imageAdapter);
        /* Initialize view for the search operation */
        searchView = new SearchView(context);
        /* Initialize presenter for the search operation */
        searchPresenter = new SearchPresenter(Schedulers.io(), AndroidSchedulers.mainThread());
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);
        init();
        handleIntent(getIntent());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                /* Endless scroll is handled if the condition stated below holds true.*/
                if (dy!=0 && !recyclerView.canScrollVertically(1))
                    searchPresenter.loadData(searchView, searchConfig);
            }
        });
    }


    /* Endlessly adds up to 20 search results and increments page by 1 such that,
      in the next (possible) load more operation, the next 20 results can be loaded.*/
    public void applyChanges(List<Image> images) {
        this.imageAdapter.addAll(images);
        searchConfig.incrementPage();
    }


    /*
     * This activity's launch mode is set to "singleTop". So, if it is re-launched again
     * its instance will not be created and its current state will be preserved.
     * At that time, the intent data will be received through the onNewIntent callback.
     * */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }


    /*
     * Gets the query written to the search dialog box,
     * Saves the text to the history.
     * Prepares the request and sends it to get the search results.
     * */
    private void handleIntent(@NonNull Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String text = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SuggestionProvider.AUTHORITY, SuggestionProvider.MODE);
            suggestions.saveRecentQuery(text, null);
            initConfiguration(text);
            searchPresenter.loadData(searchView, searchConfig);
        }
    }

    /* Removes the left and right blanks around the search term (to be in safe mode).
     * Makes the request configuration ready before loading the search results.*/
    private void initConfiguration(String text) {
        searchConfig = new SearchConfig(text.trim(),context);
        searchConfig.implementApi();
    }

}
