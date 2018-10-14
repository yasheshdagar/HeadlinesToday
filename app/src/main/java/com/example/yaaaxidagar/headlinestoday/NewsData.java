package com.example.yaaaxidagar.headlinestoday;

/**
 * Created by Yaaaxi Dagar on 11/4/2017.
 */

public class NewsData {

    private String title;
    private String content;
    private String imageUrl;
    private String contentUrl;
    private String date;

    public NewsData(String title,String content,String imageUrl,String contentUrl,String date){
        this.title=title;
        this.content=content;
        this.imageUrl=imageUrl;
        this.contentUrl=contentUrl;
        this.date=date;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDate() {
        return date;
    }

    public String getContentUrl() {
        return contentUrl;
    }
}
