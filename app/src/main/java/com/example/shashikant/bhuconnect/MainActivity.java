package com.example.shashikant.bhuconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shashikant.bhuconnect.EventsAndNews.NewsEvents;
import com.example.shashikant.bhuconnect.Holidays.HolidayList;
import com.example.shashikant.bhuconnect.Holidays.HolidaysActivity;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout complainLinearLayout = (LinearLayout)findViewById(R.id.complain_LinearLayout);
        complainLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent complainIntent = new Intent(MainActivity.this, WriteComplain.class);
                startActivity(complainIntent);
            }
        });
        LinearLayout holiday = (LinearLayout)findViewById(R.id.holidays_LinearLayout);
        holiday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HolidaysActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                HolidayList.list.clear();
                startActivity(intent);
            }
        });
        LinearLayout employee = (LinearLayout)findViewById(R.id.employee);
        employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EmployeeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        LinearLayout newsEvents = (LinearLayout)findViewById(R.id.events_LinearLayout);
        newsEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewsEvents.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        final LinearLayout contacts = (LinearLayout)findViewById(R.id.contact_LinearLayout);
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(MainActivity.this, ContactActivity.class));
            }
        });
    }
}
