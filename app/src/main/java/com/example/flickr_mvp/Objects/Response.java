package com.example.flickr_mvp.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("photos")
    @Expose
    private ImagesResponse imagesResponse;

    private String stat;

    public ImagesResponse getImagesResponse() {
        return imagesResponse;
    }

    public void setImagesResponse(ImagesResponse imagesResponse) {
        this.imagesResponse = imagesResponse;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
