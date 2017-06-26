package com.example.shashikant.bhuconnect.Holidays;

import android.app.ProgressDialog;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import java.util.List;
/**
 * Created by Shashikant on 4/9/2017.
 */

public class HolidayLoader extends AsyncTaskLoader<List<holidays>> {
    private static String LOG_TAG = HolidaysActivity.class.getSimpleName();
    private String mUrl;
    public HolidayLoader(Context context, String url){
        super(context);
        mUrl = url;
    }
    @Override
    public void onStartLoading(){
        forceLoad();
    }
    @Override
    public List<holidays> loadInBackground() {
        if(mUrl==null){
            return null;
        }
        List<holidays> list;
            list = HolidaysUtils.fetchHolidays(mUrl);
            return list;
    }
}
