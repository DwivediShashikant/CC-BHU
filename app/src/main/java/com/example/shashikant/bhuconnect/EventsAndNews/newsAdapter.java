package com.example.shashikant.bhuconnect.EventsAndNews;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shashikant.bhuconnect.R;

import java.util.ArrayList;

/**
 * Created by Shashikant on 4/16/2017.
 */

public class newsAdapter extends ArrayAdapter<news> {
    public final String LOG_TAG = newsAdapter.class.getSimpleName();

    public newsAdapter(Activity context, ArrayList<news> list, int resource) {
        super(context, resource, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_news, parent, false);
        }
        news currentNews = getItem(position);

        TextView newsHeading = (TextView) listItemView.findViewById(R.id.newsHeading);
        newsHeading.setText(currentNews.getContent());

        TextView newsPublishedDate = (TextView) listItemView.findViewById(R.id.news_published_date);
        String Date = currentNews.getDate();
        String sub1, sub2, sub3;
        sub1 = Date.substring(8, 10);
        sub2 = Date.substring(5, 7);
        sub3 = Date.substring(0, 4);
        newsPublishedDate.setText(sub1 + '-' + sub2 + '-' + sub3);

        TextView provider = (TextView) listItemView.findViewById(R.id.news_provider);
        provider.setText("By: "+currentNews.getProvider());

        return listItemView;
    }
}
