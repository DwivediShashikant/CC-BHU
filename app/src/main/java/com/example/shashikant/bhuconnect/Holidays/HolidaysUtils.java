package com.example.shashikant.bhuconnect.Holidays;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Shashikant on 4/6/2017.
 */

public class HolidaysUtils {
    static ArrayList<holidays> list = new ArrayList<>();
    public static final String LOG_TAG = HolidaysUtils.class.getSimpleName();
    public static List<holidays> fetchHolidays(String requestUrl){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         URL url = createUrl(requestUrl);
         String jsonFormat = null;
        try {
            jsonFormat = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        extractHolidays(jsonFormat);
        return HolidayList.list ;
    }
    public static URL  createUrl(String requestUrl){
        URL url=null;
        try {
             url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG,"Url is malformed ");
        }
        return  url;
    }
    public static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        if(url==null){
            return jsonResponse;
        }
        HttpURLConnection httpURLConnection=null;
        InputStream inputStream= null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if(httpURLConnection.getResponseCode()==200){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
           if (httpURLConnection!=null){
               httpURLConnection.disconnect();
           }
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return  jsonResponse;
    }

    public static String  readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while(line!=null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }
    public static void extractHolidays(String jsonResponse){
        try {
            JSONArray jsonArray = new JSONArray(jsonResponse);
            int json_len = jsonArray.length();
            Log.e(LOG_TAG," "+json_len);
            if(json_len>0){
                for(int i=0;i<json_len;i++){
                    JSONObject jsonHolidayObject = jsonArray.getJSONObject(i);
                    String HolidayName = jsonHolidayObject.getString("Holiday_Name");
                    String Days = jsonHolidayObject.getString("Days");
                    String Week = jsonHolidayObject.getString("Week");
                    String Date = jsonHolidayObject.getString("Date");
                      HolidayList.list.add(new holidays(HolidayName,Date,Week,Days));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
