package com.example.marjan.testblankactivity1.database;

/**
 * Created by KTT on 01.07.2015.
 */
public class DataProvider {

    private String distance;
    private String duration;
    private String calories;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public DataProvider(String d, String du, String ca){

        this.distance= d;

        this.duration= du;

        this.calories=ca;

    }

}
