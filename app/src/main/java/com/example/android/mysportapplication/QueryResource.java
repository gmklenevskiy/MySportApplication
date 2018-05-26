package com.example.android.mysportapplication;


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

public class QueryResource {
    private static String LOG_TAG = QueryResource.class.getSimpleName();
    private QueryResource(){
    }


    public static List<MyNews> fetchNewsData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<MyNews> news = extractFeatureFromJson(jsonResponse);
        return news;
    }

    private static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line!=null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<MyNews> extractFeatureFromJson(String newsJSON){
        if (TextUtils.isEmpty(newsJSON)){
            return null;
        }
        List<MyNews> news = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            JSONObject response = baseJsonResponse.optJSONObject("response");
            JSONArray results = response.optJSONArray("results");
            for (int i = 0; i< results.length(); i++){
                JSONObject currentNews = results.optJSONObject(i);
                String title = currentNews.optString("webTitle");
                String topic = currentNews.optString("sectionName");
                String fullDate = currentNews.get("webPublicationDate").toString();
                String[] supportMassive = fullDate.split("T");
                String date = supportMassive[0];
                String time = supportMassive[1].substring(0, supportMassive[1].length() - 1);
                String url = currentNews.optString("webUrl");
                JSONObject imageReader = currentNews.optJSONObject("fields");
                String imageUrl = imageReader.optString("thumbnail");
                JSONArray authorReader = currentNews.optJSONArray("tags");
                String author = "";
                for (int j=0; j<authorReader.length();j++){
                    JSONObject authorJSONObject = authorReader.optJSONObject(j);
                    author = authorJSONObject.optString("webTitle");

                }
                MyNews myNews = new MyNews(title,topic,date,time,url,imageUrl,author);
                news.add(myNews);
            }
        }catch (JSONException e){
            Log.e("QueryResource", "Problem parsing the news JSON results", e);
        }
        return news;
    }
}
