package com.example.android.mysportapplication;


public class MyNews {
    private String mTitle;
    private String mSectionName;
    private String mDate;
    private String mTime;
    private String mUrl;
    private String mImageUrl;
    private String mAuthor;

    public MyNews(String title, String sectionName, String date, String time, String url, String imageUrl, String author){
        mTitle = title;
        mSectionName = sectionName;
        mDate = date;
        mTime = time;
        mUrl = url;
        mImageUrl = imageUrl;
        mAuthor = author;
    }

    public String getTitle(){return mTitle;}

    public String getSectionName() {
        return mSectionName;
    }

    public String getDate() {
        return mDate;
    }

    public String getTime() {
        return mTime;
    }

    public String getUrl(){
        return mUrl;
    }

    public String getImageUrl(){
        return mImageUrl;
    }

    public String getAuthor(){
        return mAuthor;
    }
}
