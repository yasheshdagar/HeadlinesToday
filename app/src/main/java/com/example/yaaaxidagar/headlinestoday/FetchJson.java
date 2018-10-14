package com.example.yaaaxidagar.headlinestoday;

import android.app.DownloadManager;
import android.net.Uri;
import android.text.TextUtils;
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
 * Created by Yaaaxi Dagar on 11/4/2017.
 */

public class FetchJson {

    private FetchJson(){}

    public static List<NewsData> getNews(String stringUrl) {
        URL url= null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String jsonResponse= null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentFromJson(jsonResponse);
    }

    public static List<NewsData> contentFromJson(String jsonString){

        List<NewsData> realList=new ArrayList<>();

        if(TextUtils.isEmpty(jsonString)){
            return null;
        }

        try {
            JSONObject jsonObject=new JSONObject(jsonString);
            JSONArray jsonArray=jsonObject.getJSONArray("articles");

            for(int i=0;i<jsonArray.length();i++){
                JSONObject JSONOBJECT=jsonArray.getJSONObject(i);
                String title=JSONOBJECT.getString("title");
                String content=JSONOBJECT.getString("description");
                String imageUrl=JSONOBJECT.getString("urlToImage");
                String contentUrl=JSONOBJECT.getString("url");
                String date=JSONOBJECT.getString("publishedAt");

                NewsData toBeAddedToRealList=new NewsData(title,content,imageUrl,contentUrl,date);
                realList.add(toBeAddedToRealList);
            }

        } catch (JSONException e) {
            Log.e("FetchJson","Error Parsing Json",e);
        }


        return realList;
    }

    public static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse="";

        if(url==null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;

        try {
            urlConnection= (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode()==200){
                inputStream=urlConnection.getInputStream();
                jsonResponse=readFromStream(inputStream);
            }

            else {
                Log.e("FetchJson","Error Response Code");
            }

        } catch (IOException e) {
            Log.e("FetchJson:","Unable to open Connection",e);
        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }

            if(inputStream!=null){
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder sb=new StringBuilder();

        if(inputStream!=null){
            BufferedReader bf=new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line=bf.readLine();

            while (line!=null){
                sb.append(line);
                line=bf.readLine();
            }
        }

       return sb.toString();

    }

}
