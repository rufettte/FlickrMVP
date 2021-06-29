package com.example.flickr_mvp.Services;

import com.example.flickr_mvp.APIs.SearchAPI;
import com.example.flickr_mvp.Objects.Response;
import com.example.flickr_mvp.Utils.ConfigAPI;
import rx.Observable;

public class SearchService implements SearchAPI {

    private final SearchAPI searchAPI;

    public SearchService(String url) {
        searchAPI = ConfigAPI.buildAPIService(url);
    }

    @Override
    public Observable<Response> getImages(String apiKey, Integer pageNumber, String text) {
        return searchAPI.getImages(apiKey, pageNumber, text);
    }
}
