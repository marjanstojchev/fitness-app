package com.example.marjan.testblankactivity1;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.marjan.testblankactivity1.database.UserDbHelper;

import java.util.concurrent.TimeUnit;

public class WorkoutFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, LocationListener {

    Chronometer chronometer;
    long elapsedMillis;
    long base;
    boolean paused;

    ImageButton startButton;
    ImageButton stopButton;
    ImageButton pauseButton;

    double distance;
    double oldDistance;
    double oldLon;
    double oldLat;
    double newLat;
    double newLon;
    double differenceLon;
    double differneceLat;

    double calCoeff;


    TextView dispDistance;
    TextView dispCalories;

    LocationManager locationManager;
    Location initialLocation;

//    Button timeButton;

    Spinner choseWorkoutSpinner;
    ArrayAdapter choseWorkoutAdapter;

    private double time;
    private double kCal;
    private int weigth;

    Context context = getActivity();

    UserDbHelper userDbHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        choseWorkoutSpinner = (Spinner) getActivity().findViewById(R.id.chose_workout_spinner);
        choseWorkoutAdapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.chose_workout, android.R.layout.simple_spinner_item);
        choseWorkoutSpinner.setAdapter(choseWorkoutAdapter);
        choseWorkoutSpinner.setOnItemSelectedListener(this);

        startButton = (ImageButton) getActivity().findViewById(R.id.start_button);
        stopButton = (ImageButton) getActivity().findViewById(R.id.stop_button);
        pauseButton = (ImageButton) getActivity().findViewById(R.id.pause_button);

//        timeButton = (Button) getActivity().findViewById(R.id.time_button);
//        timeButton.setOnClickListener(this);

        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);

        dispDistance = (TextView) getActivity().findViewById(R.id.distance_value);

        dispCalories = (TextView) getActivity().findViewById(R.id.calories_value);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        oldDistance = 0;
        newLat = 0;
        newLon = 0;
        differenceLon = 0;
        differneceLat = 0;
        kCal = 0;
        distance = 0;
        time = 0;

        initialLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
        try {
            if (initialLocation != null) {
                oldLat = initialLocation.getLatitude();
                oldLon = initialLocation.getLongitude();


            }
        } catch (Exception e) {
        }

        chronometer = (Chronometer) getActivity().findViewById(R.id.chronometer);
        paused = false;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workout_fragment, container, false);
        return view;


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.start_button:

                SharedPreferences readPreference = getActivity().getSharedPreferences("Personalization", Context.MODE_PRIVATE);
                weigth = readPreference.getInt("weigth", 75);

                calCoeff = getCalCoeff(choseWorkoutSpinner);


//
// chronometer.setBase(SystemClock.elapsedRealtime());
                if (paused==true) {

                    long intervalOnPause = (SystemClock.elapsedRealtime() - base);
                    chronometer.setBase( chronometer.getBase() + intervalOnPause );
                    chronometer.start();

                }
                else {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                }

                paused=false;

                initialLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                try {
                    if (initialLocation != null) {
                        oldLat = initialLocation.getLatitude();
                        oldLon = initialLocation.getLongitude();


                    }
                } catch (Exception e) {
                }

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);


                break;

            case R.id.pause_button:

                base = SystemClock.elapsedRealtime();
                chronometer.stop();
                paused = true;

                locationManager.removeUpdates(this);

                break;

            case R.id.stop_button:

                try {
                    Log.e("DATABASE OPERATIONS", "Press stop button ...");
                    userDbHelper = new UserDbHelper(getActivity());
                    sqLiteDatabase = userDbHelper.getWritableDatabase();

                    String du = convertSecondsToHMmSs(time);
                    String dist = Double.toString(distance);
                    String cal = Double.toString(kCal);

                    userDbHelper.addInformation(dist, du, cal, sqLiteDatabase);

                    userDbHelper.close();
                } catch (Exception e) {
                    Log.e("DATABASE OPERATION", "", e);
                }
                paused = false;
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.stop();

                locationManager.removeUpdates(this);

                oldDistance = 0;
                kCal = 0;

                dispDistance.setText(String.valueOf(oldDistance));
                dispCalories.setText(String.valueOf(kCal));

                break;
//
//            case R.id.time_button:
//
//
//                break;

        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onLocationChanged(Location location) {

        newLat = location.getLatitude();
        newLon = location.getLongitude();

        distance = distFrom(newLat, newLon, oldLat, oldLon) + oldDistance;
        dispDistance.setText(String.valueOf(distance));

        elapsedMillis = (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000;
        time = (double) elapsedMillis;

        kCal = weigth * time / 60 * calCoeff;

        dispCalories.setText(String.valueOf(kCal));

        oldLat = newLat;
        oldLon = newLon;
        oldDistance = distance;

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        return dist;
    }

    public double getCalCoeff(Spinner spinner) {

        int selectedItem = choseWorkoutSpinner.getSelectedItemPosition();

        if (selectedItem == 0) {
            calCoeff = 0.07;
        }
        if (selectedItem == 1) {
            calCoeff = 0.16;
        } else if (selectedItem == 2) {
            calCoeff = 0.14;
        }

        return calCoeff;
    }

    public static String convertSecondsToHMmSs(double seconds) {
        Log.e("DATABASE OPERATION", "Test: " + seconds);
        return String.format("%02d:%02d", (int)seconds / 60, (int)seconds % 60);
    }
}
