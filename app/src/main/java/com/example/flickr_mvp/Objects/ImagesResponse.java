package com.example.flickr_mvp.Objects;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ImagesResponse {

    private int page;

    private int pages;

    private String total;

    @SerializedName("perpage")
    private int perPage;

    @SerializedName("photo")
    private List<Image> images;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
