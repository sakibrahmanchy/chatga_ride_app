package com.demoriderctg.arif.demorider.models.ApiModels.NewsCardModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 3/20/2018.
 */

public class NewsCardResponse {
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("button_name_1")
    private String button1;
    @SerializedName("button_name_2")
    private String button2;
    @SerializedName("button_name_3")
    private String button3;
    @SerializedName("url1")
    private String url1;
    @SerializedName("url2")
    private String url2;
    @SerializedName("url3")
    private String url3;

    public NewsCardResponse(String title, String description, String imageUrl, String button1, String button2, String button3, String url1, String url2, String url3) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.button1 = button1;
        this.button2 = button2;
        this.button3 = button3;
        this.url1 = url1;
        this.url2 = url2;
        this.url3 = url3;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getButton1() {
        return button1;
    }

    public void setButton1(String button1) {
        this.button1 = button1;
    }

    public String getButton2() {
        return button2;
    }

    public void setButton2(String button2) {
        this.button2 = button2;
    }

    public String getButton3() {
        return button3;
    }

    public void setButton3(String button3) {
        this.button3 = button3;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getUrl3() {
        return url3;
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
    }
}
