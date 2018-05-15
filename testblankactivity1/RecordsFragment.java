package com.example.marjan.testblankactivity1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.marjan.testblankactivity1.database.DataProvider;
import com.example.marjan.testblankactivity1.database.ListDataAdapter;
import com.example.marjan.testblankactivity1.database.UserDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marjan on 6/24/2015.
 */
public class RecordsFragment extends Fragment {

  Context context;
    ListView listView;
    Cursor cursor;
    SQLiteDatabase sqLiteDatabase;
    UserDbHelper userDbHelper;
    ListDataAdapter listDataAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.records_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView) getActivity().findViewById(R.id.listView);
        List<DataProvider> datas = new ArrayList<DataProvider>();
        listDataAdapter = new ListDataAdapter(getActivity(), datas);
        listView.setAdapter(listDataAdapter);

        userDbHelper = new UserDbHelper(getActivity().getApplicationContext());
        sqLiteDatabase = userDbHelper.getReadableDatabase();

        cursor = userDbHelper.getInfromations(sqLiteDatabase);

        if(cursor.moveToFirst()) {
            do {
                String distance, duration, calories;
                distance=cursor.getString(0);
                duration=cursor.getString(1);
                calories=cursor.getString(2);
                Log.e("DATABASE OPERATIONS", "add " + distance + " " + duration + " " + calories);
                DataProvider dataProvider= new DataProvider(distance,duration, calories);
                datas.add(dataProvider);


            } while (cursor.moveToNext());
            listDataAdapter.notifyDataSetChanged();
        }

    }
}
