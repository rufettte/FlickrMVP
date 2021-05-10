package com.example.flickr_mvp.Contracts;

import com.example.flickr_mvp.Configs.SearchConfig;
import com.example.flickr_mvp.Objects.Response;
import com.example.flickr_mvp.Views.SearchView;


/*Contract between view and presenter which has to be implemented w.r.t the requirements.*/
public interface SearchContract {

    /* This interface should be implemented to provide info about the search request to the user.*/
    interface View {
        void onStart();
        void onComplete();
        void onSuccess(Response response);
        void onError(Throwable t);
    }

    /* This interface should be implemented to perform the search request in the background.*/
    interface Presenter {
        void loadData(SearchView view, SearchConfig config);
    }
}