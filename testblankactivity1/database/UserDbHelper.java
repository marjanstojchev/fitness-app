package com.example.marjan.testblankactivity1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by KTT on 01.07.2015.
 */
public class UserDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "USERINFO.DB";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_QUERY =
            "CREATE TABLE " + UserContract.NewUserInfo.TABLE_NAME + "(" + UserContract.NewUserInfo.Ex_Distance + " TEXT," +
                    UserContract.NewUserInfo.Ex_Duration + " TEXT," + UserContract.NewUserInfo.Ex_Calories + " TEXT);";


    public UserDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DATABASE OPERATIONS", "Database created / opened ...");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_QUERY);
            Log.e("DATABASE OPERATIONS", "Table created ...");
        } catch (SQLException e) {
        }
    }

    public void addInformation(String dist, String dur, String cal, SQLiteDatabase db) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.NewUserInfo.Ex_Distance, dist);
        contentValues.put(UserContract.NewUserInfo.Ex_Duration, dur);
        contentValues.put(UserContract.NewUserInfo.Ex_Calories, cal);
        db.insert(UserContract.NewUserInfo.TABLE_NAME, null, contentValues);
        Log.e("DATABASE OPERATIONS", "Database inserted ...");

    }


    public Cursor getInfromations(SQLiteDatabase db) {

        Cursor cursor;
        String[] projections = {UserContract.NewUserInfo.Ex_Distance, UserContract.NewUserInfo.Ex_Duration, UserContract.NewUserInfo.Ex_Calories};

        cursor = db.query(UserContract.NewUserInfo.TABLE_NAME, projections, null, null, null, null, null);


        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
