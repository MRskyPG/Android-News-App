package com.example.android_news_app;

public class News {
    private String title;
    private String url;
    private String image_url;

    public News(String title, String url, String image_url){

        this.title = title;
        this.url = url;
        this.image_url = image_url;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return this.image_url;
    }

    public void setImageUrl(String image_url) {
        this.image_url = image_url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
