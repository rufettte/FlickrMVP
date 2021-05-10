package com.example.flickr_mvp.Presenters;

import com.example.flickr_mvp.Configs.SearchConfig;
import com.example.flickr_mvp.Objects.Response;
import com.example.flickr_mvp.Views.SearchView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import rx.Observable;
import rx.schedulers.Schedulers;


import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class SearchPresenterTest {

    @Mock
    SearchConfig searchConfig;

    @Mock
    SearchView searchView;

    private SearchPresenter searchPresenter;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.searchPresenter = new SearchPresenter(Schedulers.immediate(), Schedulers.immediate());
    }

    @Test
    public void testTheOrderOfProcessingForValidResponse() {
        /*Assumed scenario*/
        Response response = new Response();
        when(searchConfig.buildParams()).thenReturn(Observable.just(response));

        /*TODO*/
        searchPresenter.loadData(searchView, searchConfig);

        /*expectations*/
        InOrder inOrder = Mockito.inOrder(searchView);
        inOrder.verify(searchView, times(1)).onStart();
        inOrder.verify(searchView, times(1)).onSuccess(response);
        inOrder.verify(searchView, times(1)).onComplete();
        verify(searchView, never()).onError(new Exception());
    }

    @Test
    public void testTheOrderOfProcessingWhenErrorOccurs() {
        /*Assumed scenario*/
        Exception e = new Exception("Error occurred during loading search results!");
        when(searchConfig.buildParams()).thenReturn(Observable.error(e));

        /*TODO*/
        searchPresenter.loadData(searchView, searchConfig);

        /*expectations*/
        InOrder inOrder = Mockito.inOrder(searchView);
        inOrder.verify(searchView, times(1)).onStart();
        inOrder.verify(searchView, times(1)).onError(e);
        verify(searchView, never()).onComplete();
    }
}