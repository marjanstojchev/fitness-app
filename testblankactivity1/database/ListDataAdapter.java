package com.example.marjan.testblankactivity1.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.marjan.testblankactivity1.R;

import java.lang.Object;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by KTT on 01.07.2015.
 */
public class ListDataAdapter extends ArrayAdapter<DataProvider> {

    private Context context;

    public ListDataAdapter(Context context,List<DataProvider> datas) {
        super(context, R.layout.raw_layout, datas);
        this.context = context;

    }

    static class LayoutHandler {

        TextView DISTANCE, DURATION, CALORIES;
    }
/*
    @Override
    public void add(Object object) {

        //super.add(object);
        list.add(object);
    }*/


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        LayoutHandler layoutHandler;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.raw_layout, parent, false);
            layoutHandler = new LayoutHandler();
            layoutHandler.DISTANCE = (TextView) row.findViewById(R.id.view_distance);
            layoutHandler.DURATION = (TextView) row.findViewById(R.id.view_duration);
            layoutHandler.CALORIES = (TextView) row.findViewById(R.id.view_calories);
            row.setTag(layoutHandler);

        } else {
            layoutHandler = (LayoutHandler) row.getTag();
            DataProvider dataprovider = (DataProvider) this.getItem(position);
            layoutHandler.DISTANCE.setText("Distance: " + dataprovider.getDistance() + " m");
            layoutHandler.DURATION.setText("Duration: " + dataprovider.getDuration());
            layoutHandler.CALORIES.setText("Calories: " + dataprovider.getCalories() + " cal");
            return row;


        }
        return row;
    }
}