package com.example.shashikant.bhuconnect.Holidays;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shashikant.bhuconnect.R;
import com.example.shashikant.bhuconnect.Holidays.holidays;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shashikant on 4/6/2017.
 */

public class holidayAdapter extends ArrayAdapter<holidays> {
    public final String LOG_TAG = holidayAdapter.class.getSimpleName();
    public holidayAdapter(Activity context, ArrayList<holidays> list,int resource){
        super(context,0,list);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_holidays,parent,false);
        }
        holidays currentHolidays = getItem(position);

        TextView holidayName =  (TextView)listItemView.findViewById(R.id.holiday_name);
        holidayName.setText(currentHolidays.getHolidayName());

        TextView holidayDate =  (TextView)listItemView.findViewById(R.id.holiday_date);
        String Date = currentHolidays.getHolidayDate();
        String sub1,sub2,sub3;
        sub1 = Date.substring(8,10);
        sub2 = Date.substring(5,7);
        sub3 = Date.substring(0,4);
        holidayDate.setText(sub1+'-'+sub2+'-'+sub3);

        TextView holidayWeek =  (TextView)listItemView.findViewById(R.id.holiday_week);
        holidayWeek.setText(currentHolidays.getHolidayWeek());

        TextView holidayDay =  (TextView)listItemView.findViewById(R.id.holiday_day);
        holidayDay.setText(currentHolidays.getHolidayDays()+" Days");

      return  listItemView;
    }
}
