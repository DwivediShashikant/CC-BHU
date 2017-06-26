package com.example.shashikant.bhuconnect.EventsAndNews;

/**
 * Created by Shashikant on 4/16/2017.
 */

public class events {
    public String eventName;
    public String startDate;
    public String endDate;
    public String venue;

    public events(String eventName, String startDate, String endDate, String venue){
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.venue = venue;
    }

    public String getEventName(){ return eventName; }
    public String getStartDate(){ return startDate; }
    public String getEndDate(){ return endDate; }
    public String getVenue(){ return venue; }
}
