package com.example.android.mysportapplication;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<MyNews>> {
    private String mUrl;

    public NewsLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<MyNews> loadInBackground() {
        if(mUrl == null){
            return null;
        }
        List<MyNews> news = QueryResource.fetchNewsData(mUrl);
        return news;
    }
}
