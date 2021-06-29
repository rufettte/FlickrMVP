package com.example.flickr_mvp.Contracts;

import com.example.flickr_mvp.Objects.Response;

public interface SearchContract {

    interface View {
        void onStart();
        void onComplete();
        void onSuccess(Response response);
        void onError(Throwable t);
    }

    interface Presenter {
        void loadSearchTerm(String apiKey, Integer pageNumber, String text);
    }
}