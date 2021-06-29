package com.example.flickr_mvp.APIs;

import com.example.flickr_mvp.Objects.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface SearchAPI {
    @GET("/services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1&safe_search=1&per_page=20")
    Observable<Response> getImages(
            @Query("api_key") String apiKey,
            @Query("page") Integer pageNumber,
            @Query("text") String text
    );
}