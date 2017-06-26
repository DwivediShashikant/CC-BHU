package com.example.shashikant.bhuconnect;

/**
 * Created by Shashikant on 4/19/2017.
 */

public  class EmpName {
    private static  String empName;
    public static void setName(String name){
        empName = name;
    }
    public static String getName(){
        return empName;
    }
}
