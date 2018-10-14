package com.example.yaaaxidagar.headlinestoday;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;

import java.util.List;

/**
 * Created by Yaaaxi Dagar on 11/4/2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<NewsData>> {

   private String url=null;

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    public NewsLoader(Context context, String apiUrl) {
        super(context);
        url=apiUrl;
    }

    @Override
    public List<NewsData> loadInBackground() {

        if(url==null){
            return null;
        }

        return FetchJson.getNews(url);
    }
}
