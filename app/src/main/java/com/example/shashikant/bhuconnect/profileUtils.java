package com.example.shashikant.bhuconnect;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shashikant on 4/19/2017.
 */

public class profileUtils {
    public static String LOG_TAG = profileUtils.class.getSimpleName();
    public static String employeeID = null;
    public static ArrayList<String> fetchProfileInfo(String requestUrl,String empID){
        Log.e(LOG_TAG,"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^In profile utils");
        employeeID = empID;
        URL url = createUrl(requestUrl);
        String jsonFormat = null;
        try {
            jsonFormat = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extractHolidays(jsonFormat);
    }
    public static URL  createUrl(String requestUrl){
        Log.e(LOG_TAG,"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^creating url");
        URL url=null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG,"Url is malformed ");
        }
        return  url;
    }
    public static String makeHttpRequest(URL url) throws IOException{
        Log.e(LOG_TAG,"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^making http connection");
        String jsonResponse = "";
        if(url==null){
            return jsonResponse;
        }
        HttpURLConnection httpURLConnection=null;
        InputStream inputStream= null;
        OutputStream outputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            outputStream = httpURLConnection.getOutputStream();
            writeToStream(outputStream);
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode()==200){
                httpURLConnection.setRequestMethod("GET");
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
                outputStream.close();
                inputStream.close();
            }
        }
        return  jsonResponse;
    }

    public static void writeToStream(OutputStream outputStream) throws  IOException{
        Log.e(LOG_TAG,"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^writin to stream");
        BufferedWriter bufferedWriter = new BufferedWriter( new OutputStreamWriter(outputStream,"UTF-8"));
        String post_data = URLEncoder.encode("empID","UTF-8")
                +"="+URLEncoder.encode(employeeID,"UTF-8");

        bufferedWriter.write(post_data);
        bufferedWriter.flush();
        bufferedWriter.close();
    }
    public static String  readFromStream(InputStream inputStream) throws IOException{
        Log.e(LOG_TAG,"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^reading from stream");
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
    public static ArrayList<String> extractHolidays(String jsonResponse){
        Log.e(LOG_TAG,"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^extracting emp info");
        try {
            JSONArray jsonArray = new JSONArray(jsonResponse);
            int json_len = jsonArray.length();
            Log.e(LOG_TAG," "+json_len);
            if(json_len>0){
                ArrayList<String> result = new ArrayList<>();
                for(int i=0;i<json_len;i++){
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String EmpName = obj.getString("Name");
                    Log.e(LOG_TAG,"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^&&&&&&&&&&&&&&&&&&&&&&&&extracting" + EmpName);
                    String ID = obj.getString("ID");
                    String designation = obj.getString("Designation");
                    String Institute = obj.getString("Institute");
                    String email = obj.getString("Email");
                    String mob = obj.getString("MobileNo");
                    String PAN = obj.getString("PAN");
                    String retDate = obj.getString("RetierementDate");
                    result.add(EmpName);
                    result.add(ID);
                    result.add(designation);
                    result.add(Institute);
                    result.add(email);
                    result.add(mob);
                    result.add(PAN);
                    result.add(retDate);
                }
                Log.e(LOG_TAG,"*****************************************************************"+result.get(0));
                return result;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
