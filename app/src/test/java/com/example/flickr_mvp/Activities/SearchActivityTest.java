package com.example.flickr_mvp.Activities;

import com.example.flickr_mvp.Objects.Response;
import com.example.flickr_mvp.Presenters.SearchPresenter;
import com.example.flickr_mvp.Services.SearchService;
import com.example.flickr_mvp.Views.SearchView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import rx.Observable;
import rx.schedulers.Schedulers;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchActivityTest {

    @Mock
    private SearchView searchView;

    @Mock
    private SearchService searchService;

    private SearchPresenter searchPresenter;

    @Before
    public void setUp() {
        searchPresenter = new SearchPresenter(Schedulers.immediate(), Schedulers.immediate(), searchView, searchService);
    }

    @Test
    public void testTheOrderOfProcessingWhenTheSearchOperationIs_Successful() {
        when(searchService.getImages(any(String.class), any(Integer.class), any(String.class))).thenReturn(Observable.just(new Response()));
        searchPresenter.loadSearchTerm("", 0, "");

        InOrder inOrder = Mockito.inOrder(searchView);
        inOrder.verify(searchView, times(1)).onStart();
        inOrder.verify(searchView, times(1)).onSuccess(any(Response.class));
        inOrder.verify(searchView, times(1)).onComplete();
        verify(searchView, never()).onError(any(Throwable.class));
    }

    @Test
    public void testTheOrderOfProcessingWhenTheSearchOperationContains_Error() {
        when(searchService.getImages(any(String.class), any(Integer.class), any(String.class))).thenReturn(Observable.error(new Throwable()));
        searchPresenter.loadSearchTerm("", 0, "");

        InOrder inOrder = Mockito.inOrder(searchView);
        inOrder.verify(searchView, times(1)).onStart();
        inOrder.verify(searchView, times(1)).onError(any(Throwable.class));
        verify(searchView, never()).onComplete();
        verify(searchView, never()).onSuccess(any(Response.class));
    }
}