package com.example.flickr_mvp.Presenters;

import androidx.annotation.NonNull;
import com.example.flickr_mvp.Contracts.SearchContract;
import com.example.flickr_mvp.Objects.Response;
import com.example.flickr_mvp.Services.SearchService;
import com.example.flickr_mvp.Views.SearchView;
import org.jetbrains.annotations.NotNull;
import rx.subscriptions.CompositeSubscription;
import rx.Observer;
import rx.Scheduler;
import rx.Subscription;


public class SearchPresenter implements SearchContract.Presenter{

    @NonNull
    private final CompositeSubscription subscriptions;

    @NonNull
    private final Scheduler background;

    @NonNull
    private final Scheduler main;

    @NonNull
    private final SearchView searchView;

    @NonNull
    private final SearchService searchService;

    public SearchPresenter(@NotNull Scheduler background, @NotNull Scheduler main,
                           @NotNull SearchView searchView, @NonNull SearchService searchService) {
        this.background = background;
        this.main = main;
        this.searchView = searchView;
        this.searchService = searchService;
        this.subscriptions = new CompositeSubscription();
    }

    @Override
    public void loadSearchTerm(String apiKey, Integer pageNumber, String text) {
        searchView.onStart();
        Subscription subscription = searchService.getImages(apiKey, pageNumber, text)
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