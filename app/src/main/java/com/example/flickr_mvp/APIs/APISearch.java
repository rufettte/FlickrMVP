package com.example.flickr_mvp.APIs;
import com.example.flickr_mvp.Objects.Response;

import java.util.Map;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/*Search API*/
public interface APISearch {
    /* Response is Observable
    * url contains the default parameters
    * params variable represents the parameters which are determined dynamically (e.g., page and text)*/
    @GET
    Observable<Response> getImages(@Url String url, @QueryMap Map<String, String> params);
}