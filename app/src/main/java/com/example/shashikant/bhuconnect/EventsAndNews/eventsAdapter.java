package com.example.shashikant.bhuconnect.EventsAndNews;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shashikant.bhuconnect.Holidays.holidayAdapter;
import com.example.shashikant.bhuconnect.Holidays.holidays;
import com.example.shashikant.bhuconnect.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static junit.runner.Version.id;

/**
 * Created by Shashikant on 4/16/2017.
 */

public class eventsAdapter extends ArrayAdapter<events> {
    public final String LOG_TAG = holidayAdapter.class.getSimpleName();

    public eventsAdapter(Activity context, ArrayList<events> list, int resource) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_evets, parent, false);
        }
        events currentEvents = getItem(position);

        TextView eventName = (TextView) listItemView.findViewById(R.id.event_name);
        eventName.setText(currentEvents.getEventName());

        TextView eventStartDate = (TextView) listItemView.findViewById(R.id.event_start_date);
        String StartDate = currentEvents.getStartDate();
        String sub1, sub2, sub3;
        sub1 = StartDate.substring(8, 10);
        sub2 = StartDate.substring(5, 7);
        sub3 = StartDate.substring(0, 4);
        eventStartDate.setText(sub1 + '-' + sub2 + '-' + sub3);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        Date startDate = null;
        try {
            startDate = simpleDateFormat.parse(StartDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long startDateMilli = startDate.getTime();

        TextView eventEndDate = (TextView) listItemView.findViewById(R.id.event_end_date);
        String endDate = currentEvents.getEndDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        Date endingDate = null;
        try {
            endingDate = dateFormat.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long endDateMilli = endingDate.getTime();
        sub1 = endDate.substring(8, 10);
        sub2 = endDate.substring(5, 7);
        sub3 = endDate.substring(0, 4);
        eventEndDate.setText(sub1 + '/' + sub2 + '/' + sub3);


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);

        long currentDateMilli = System.currentTimeMillis();
        formatter.setLenient(false);
        Date cDate = new Date(currentDateMilli);
        String cDateFormat = formatter.format(cDate);
        Log.e(LOG_TAG,"!!!!!! "+cDateFormat);
        Date dte = null;
        try {
            dte = formatter.parse(cDateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        currentDateMilli = dte.getTime();
        TextView eventVenue = (TextView) listItemView.findViewById(R.id.event_venue);
        eventVenue.setText(currentEvents.getVenue());
        ImageView eventsLogo = (ImageView)listItemView.findViewById(R.id.current_event_logo);
        if(currentDateMilli >= startDateMilli && currentDateMilli <=endDateMilli){
            eventsLogo.setImageResource(R.drawable.lightningbolt);
        }
        else if(startDateMilli > currentDateMilli){
            eventsLogo.setImageResource(R.drawable.clockforward);
        }
        else{
            eventsLogo.setImageResource(R.drawable.past_event);
        }

        return listItemView;
    }
}
