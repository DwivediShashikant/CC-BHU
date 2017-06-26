package com.example.shashikant.bhuconnect.EventsAndNews;

/**
 * Created by Shashikant on 4/16/2017.
 */

public class news {
    public String content;
    public String date;
    public String provider;
    int newsId;
    public news(String content,String date,String provider, int newsId){
        this.content = content;
        this.date = date;
        this.provider = provider;
        this.newsId = newsId;
    }
    public String getContent(){
        return content;
    }
    public String getDate(){
        return date;
    }
    public String getProvider(){
        return provider;
    }
    public int getNewsId() { return  newsId; }
}
