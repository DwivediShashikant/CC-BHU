package com.example.shashikant.bhuconnect.Holidays;

/**
 * Created by Shashikant on 4/6/2017.
 */

public class holidays {
    private String holidayName;
    private String holidayDate;
    private String weekDay;
    private String days;

    public holidays(String holidayName, String holidayDate,String weekDay,String days){
        this.holidayName = holidayName;
        this.holidayDate = holidayDate;
        this.weekDay = weekDay;
        this.days = days;
    }

    public String getHolidayName(){
        return holidayName;
    }
    public String getHolidayDate(){
        return holidayDate;
    }
    public String getHolidayWeek(){
        return weekDay;
    }
    public String getHolidayDays() { return  days; }
}
