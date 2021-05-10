package com.example.flickr_mvp.Objects;

import android.text.Html;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class Image implements Serializable {
    private String id;
    private String owner;
    private String secret;
    private String server;
    private int farm;
    private String title;

    @SerializedName("ispublic")
    private int isPublic;
    @SerializedName("isfriend")
    private int isFriend;
    @SerializedName("isfamily")
    private int isFamily;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return Html.fromHtml(title).toString();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public int getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }

    public int getIsFamily() {
        return isFamily;
    }

    public void setIsFamily(int isFamily) {
        this.isFamily = isFamily;
    }

    /*
     * This function creates the url of the image so as to be able to present it
     * to the user through it.
     * */
    public String getUrl() {
        return "https://farm"
                + String.valueOf(getFarm())
                + ".static.flickr.com/"
                + getServer()
                + "/"
                + getId()
                + "_"
                + getSecret()
                + ".jpg";
    }


    @Override
    public String toString() {
        return "Image{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", secret='" + secret + '\'' +
                ", server='" + server + '\'' +
                ", farm=" + farm +
                ", title='" + title + '\'' +
                ", isPublic=" + isPublic +
                ", isFriend=" + isFriend +
                ", isFamily=" + isFamily +
                '}';
    }
}
