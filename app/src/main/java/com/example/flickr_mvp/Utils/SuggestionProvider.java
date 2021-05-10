package com.example.flickr_mvp.Utils;

import android.content.SearchRecentSuggestionsProvider;

/* This class is recommended to use for achieving search history in the search view. */
public class SuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.flickr_mvp.SuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}