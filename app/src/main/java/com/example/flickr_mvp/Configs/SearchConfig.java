package com.example.flickr_mvp.Configs;

import android.content.Context;
import com.example.flickr_mvp.Objects.Response;
import com.example.flickr_mvp.ParamsBuilder.RequestParamsBuilder;
import com.example.flickr_mvp.R;
import com.example.flickr_mvp.APIs.APISearch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/* This class configures the API for the search operation and builds the parameters
* (page and text) required to send via GET method. */
public class SearchConfig implements APISearch, RequestParamsBuilder {

    APISearch APISearch;
    private int page;
    private String text;
    private Context context;

    public SearchConfig(String text, Context context) {
        this.page = 1;
        this.text = text;
        this.context = context;
    }

    public void implementApi() {
        /*an instance of gson is created*/
        Gson gson = new GsonBuilder().setLenient().create();

        /*Logging the request process...*/
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        /*build a retrofit instance based on configurations listed as below*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.searchUrl)).client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        /*Create an implementation of the API endpoints defined by the service interface.*/
        this.APISearch = retrofit.create(APISearch.class);
    }

    /*increments the page number*/
    public void incrementPage() {
        this.page++;
    }


    /* This function is called to send the request.
     * Firstly, the parameters are prepared and then, getImages() is called
     * to start observing a Response object.*/
    @Override
    public Observable<Response> buildParams() {
        String url = context.getString(R.string.searchGetParams);
        Map<String, String> params = new HashMap<String, String>();
        params.put(context.getString(R.string.page), String.valueOf(this.page));
        params.put(context.getString(R.string.text), this.text);
        return getImages(url, params);
    }

    @Override
    public Observable<Response> getImages(String url, Map<String, String> params) {
        return this.APISearch.getImages(url, params);
    }
}
