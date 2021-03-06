package com.example.shashikant.bhuconnect.EventsAndNews;

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
 * Created by Shashikant on 4/16/2017.
 */

public class eventUtils {
    static ArrayList<events> list = new ArrayList<>();
    public static final String LOG_TAG = eventUtils.class.getSimpleName();
    public static ArrayList<events> fetchEvents(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonFormat = null;
        try {
            jsonFormat = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        extractEvents(jsonFormat);
        return EventsList.eventsArrayList;
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
    public static void extractEvents(String jsonResponse){
        try {
            JSONArray jsonArray = new JSONArray(jsonResponse);
            int json_len = jsonArray.length();
            Log.e(LOG_TAG," "+json_len);
            if(json_len>0){
                for(int i=0;i<json_len;i++){
                    JSONObject jsonEventsObject = jsonArray.getJSONObject(i);
                    String eventsName = jsonEventsObject.getString("Event_Name");
                    String eventVenue = jsonEventsObject.getString("Venue");
                    String eventStartDate = jsonEventsObject.getString("Event_Start_Date");
                    String eventEndDate = jsonEventsObject.getString("Event_End_Date");
                    EventsList.eventsArrayList.add(new events(eventsName,eventStartDate,eventEndDate,eventVenue));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
