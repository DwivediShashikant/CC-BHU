package com.example.shashikant.bhuconnect;

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
 * Created by Shashikant on 3/31/2017.
 */

public class Utils {
    public static String LOG_TAG = Utils.class.getSimpleName();

    public  static String fetchLoginDetails(String requestUrl,String empID,String password){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(requestUrl == null){
            return null;
        }
        //convert string url to Real URL
        try{
            URL url = new URL(requestUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter( new OutputStreamWriter(outputStream,"UTF-8"));
            String post_data = URLEncoder.encode("empID","UTF-8")
                               +"="+URLEncoder.encode(empID,"UTF-8")
                               +"&"+URLEncoder.encode("password","UTF-8")
                               +"="+URLEncoder.encode(password,"UTF-8");

            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();

            //for server reply
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader( inputStream,"iso-8859-1");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuilder output = new StringBuilder();
            String line=  reader.readLine();
            while (line!=null){
                output.append(line);
                line = reader.readLine();
            }
            inputStream.close();
            reader.close();
            Log.e(LOG_TAG,"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%$$$$$$$$$$$$$$$$$$$$$"+output.toString());
            httpURLConnection.disconnect();
            return  output.toString();
        }catch(MalformedURLException e){
            Log.e(LOG_TAG,"URL is malformed, check URL");
        }catch(IOException e){
            Log.e(LOG_TAG,"error in connection");
        }
      return null;
    }
}
