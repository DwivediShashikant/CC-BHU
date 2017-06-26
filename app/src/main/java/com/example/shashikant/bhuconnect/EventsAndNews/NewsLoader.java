package com.example.shashikant.bhuconnect.EventsAndNews;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import java.util.List;

/**
 * Created by Shashikant on 4/16/2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<news>> {
    private static String LOG_TAG = NewsLoader.class.getSimpleName();
    private String mUrl;
    public NewsLoader(Context context, String url){
        super(context);
        mUrl = url;
    }
    @Override
    public void onStartLoading(){
        forceLoad();
    }
    @Override
    public List<news> loadInBackground() {
        if(mUrl==null){
            return null;
        }
        List<news> list;
        list = newsUtils.fetchNews(mUrl);
        return list;
    }
}
