package com.example.shashikant.bhuconnect;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Shashikant on 4/4/2017.
 */

public class sendMailUtils  {

    public static String LOG_TAG = sendMailUtils.class.getSimpleName();

    public static String fetchMail(String mailUrl,String complain,String place,String name, String mobile, String email) {
        if(mailUrl==null){
            return null;
        }
        try {
            URL url = new URL(mailUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter( new OutputStreamWriter(outputStream,"UTF-8"));
            String post_data = URLEncoder.encode("complain","UTF-8")
                               +"="+URLEncoder.encode(complain,"UTF-8")
                    +"&"+URLEncoder.encode("place","UTF-8")
                    +"="+URLEncoder.encode(place,"UTF-8")
                    +"&"+URLEncoder.encode("name","UTF-8")
                    +"="+URLEncoder.encode(name,"UTF-8")
                    +"&"+URLEncoder.encode("mobile","UTF-8")
                    +"="+URLEncoder.encode(mobile,"UTF-8")
                    +"&"+URLEncoder.encode("email","UTF-8")
                    +"="+URLEncoder.encode(email,"UTF-8");
            bufferedWriter.write(post_data);

            bufferedWriter.flush();
            bufferedWriter.close();

            // for server reply
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            StringBuilder output = new StringBuilder();
            String line= bufferedReader.readLine();
            while(line!=null){
                output.append(line);
                line =bufferedReader.readLine();
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return output.toString();
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG,"malformed url, please check your url");
        } catch (IOException e) {
            Log.e(LOG_TAG,"error in making the connection");
        }
        return  null;
    }
}
