package com.example.flickr_mvp.ParamsBuilder;

/* Can be implemented to build parameters passed to the server.*/
public interface RequestParamsBuilder<T> {
    T buildParams();
}
