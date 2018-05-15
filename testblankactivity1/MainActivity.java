package com.example.marjan.testblankactivity1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.marjan.testblankactivity1.swipetabs.SlidingTabLayout;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, LocationListener {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private MyPagerAdapter pagerAdapter;
    private SlidingTabLayout slidingTabLayout;

    private boolean isPersonalized;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences readPreference = getSharedPreferences("Personalization", Context.MODE_PRIVATE);
        isPersonalized = readPreference.getBoolean("personalized",false);



            setContentView(R.layout.activity_main);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            viewPager = (ViewPager) findViewById(R.id.view_pager);
            pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(pagerAdapter);
            slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tab_layout);
            slidingTabLayout.setDistributeEvenly(true);
            slidingTabLayout.setViewPager(viewPager);

        if(isPersonalized == false) {
            Intent intent = new Intent(this, PersonalizeActivity.class);
            startActivity(intent);
        }
//

//            startButton = (Button) findViewById(R.id.button);
//            startButton.setOnClickListener(this);
//
//            dispDistance = (TextView)findViewById(R.id.textView2);

//            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

            return true;
        }
        else
        if (id == R.id.personalize)
        {
            Intent intent = new Intent(this, PersonalizeActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View v) {
//        if(v.getId() == R.id.button){
//
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, this);
//
//
//        }

    }

    @Override
    public void onLocationChanged(Location location) {
//        distance = distance + 5;
//
//        dispDistance.setText(String.valueOf(distance));
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

    class MyPagerAdapter extends FragmentPagerAdapter {

        String[] pageTitles = new String[] {"Workout","Records"};
        private Context context;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0){
                WorkoutFragment workoutFragment = new WorkoutFragment();
                return workoutFragment;
            }
            else
            if (position == 1){
                RecordsFragment recordsFragment = new RecordsFragment();
                return recordsFragment;
            }
            else
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
         return pageTitles[position];
        }
    }
}
