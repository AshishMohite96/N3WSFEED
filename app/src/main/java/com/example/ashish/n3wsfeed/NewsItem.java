package com.example.ashish.n3wsfeed;

public class NewsItem {

    private String imageUrl;
    private String titleOfNews;
    private String descriptionOfNews;

    public NewsItem(String imageUrl, String titleOfNews, String descriptionOfNews){

        this.imageUrl = imageUrl;
        this.titleOfNews = titleOfNews;
        this.descriptionOfNews = descriptionOfNews;

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitleOfNews() {
        return titleOfNews;
    }

    public void setTitleOfNews(String titleOfNews) {
        this.titleOfNews = titleOfNews;
    }

    public String getDescriptionOfNews() {
        return descriptionOfNews;
    }

    public void setDescriptionOfNews(String descriptionOfNews) {
        this.descriptionOfNews = descriptionOfNews;
    }
}
