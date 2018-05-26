package com.example.android.mysportapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<MyNews> {

    public NewsAdapter(Context context, List<MyNews> news){
        super(context,0,news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item,parent,false);
        }

        MyNews currentNews = getItem(position);

        ImageView iconView = (ImageView) listItemView.findViewById(R.id.icon);
        Glide.with(getContext()).load(currentNews.getImageUrl()).into(iconView);

        TextView titleView = (TextView)  listItemView.findViewById(R.id.title);
        String title = currentNews.getTitle();
        titleView.setText(title);

        TextView sectionView = (TextView)  listItemView.findViewById(R.id.section);
        String section = currentNews.getSectionName();
        sectionView.setText(section);

        TextView dateView  = (TextView)  listItemView.findViewById(R.id.date);
        String date = currentNews.getDate();
        dateView.setText(date);

        TextView timeView = (TextView)  listItemView.findViewById(R.id.time);
        String time = currentNews.getTime();
        timeView.setText(time);

        TextView authorView = (TextView) listItemView.findViewById(R.id.author);
        String author = currentNews.getAuthor();
        authorView.setText(author);

        return listItemView;
    }
}
