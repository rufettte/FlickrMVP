package com.example.flickr_mvp.Presenters;

import androidx.annotation.NonNull;
import com.example.flickr_mvp.Configs.SearchConfig;
import com.example.flickr_mvp.Contracts.SearchContract;
import com.example.flickr_mvp.Objects.Response;
import com.example.flickr_mvp.Views.SearchView;
import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/*The main class that starts and handles search request operation.*/
public class SearchPresenter implements SearchContract.Presenter{

    @NonNull
    private CompositeSubscription subscriptions;
    @NonNull
    private Scheduler background;
    @NonNull
    private Scheduler main;

    public SearchPresenter(Scheduler background, Scheduler main) {
        this.background = background;
        this.main = main;
        this.subscriptions = new CompositeSubscription();
    }

    @Override
    public void loadData(SearchView searchView, SearchConfig config) {
        searchView.onStart();
        subscriptions.clear();

        /* Starts to load Response data from the server.
        *  Processes in background thread (or another thread)
        *  Observes from the main thread.
        *
        * A response object is expected. Otherwise, there is an error.
        * onCompleted() is invoked after onNext(), iff the expected response is achieved.*/
        Subscription subscription = config.buildParams()
                .subscribeOn(this.background)
                .observeOn(this.main)
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onCompleted() {
                        searchView.onComplete();
                    }
                    @Override
                    public void onError(Throwable t) {
                        searchView.onError(t);
                    }

                    @Override
                    public void onNext(Response response) {
                        searchView.onSuccess(response);
                    }
                });

        subscriptions.add(subscription);
    }
}