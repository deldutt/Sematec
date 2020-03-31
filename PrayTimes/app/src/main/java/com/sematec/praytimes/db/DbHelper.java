package com.sematec.praytimes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pray_times";
    private static final int DATABASE_OLD_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_OLD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY =
                "CREATE TABLE Times (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "UPDATE_DATE TEXT," +
                        "FAJR_TIME TEXT, " +
                        "DHUHR_TIME TEXT, " +
                        "ASR_TIME TEXT, " +
                        "MAGHRIB_TIME TEXT, " +
                        "ISHA_TIME TEXT)";

        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<String> getPrayTimes() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> prayTimes = new ArrayList<>();
        String fajrTime = "";
        String dhuhrTime = "";
        String asrTime = "";
        String maghribTime = "";
        String ishaTime = "";

        Cursor cursor = db.query("Times",
                new String[]{
                        "FAJR_TIME",
                        "DHUHR_TIME",
                        "ASR_TIME",
                        "MAGHRIB_TIME",
                        "ISHA_TIME"
                },
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            fajrTime = cursor.getString(0);
            dhuhrTime = cursor.getString(1);
            asrTime = cursor.getString(2);
            maghribTime = cursor.getString(3);
            ishaTime = cursor.getString(4);
        }

        prayTimes.add(fajrTime);
        prayTimes.add(dhuhrTime);
        prayTimes.add(asrTime);
        prayTimes.add(maghribTime);
        prayTimes.add(ishaTime);

        cursor.close();
        db.close();

        return prayTimes;
    }

    public void savePrayTimesToDatabase(
            boolean isUpdateOperation,
            String fajrTime,
            String dhuhrTime,
            String asrTime,
            String maghribTime,
            String ishaTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues prayTimesValues = new ContentValues();

        prayTimesValues.put("UPDATE_DATE", getTodayDate());
        prayTimesValues.put("FAJR_TIME", fajrTime);
        prayTimesValues.put("DHUHR_TIME", dhuhrTime);
        prayTimesValues.put("ASR_TIME", asrTime);
        prayTimesValues.put("MAGHRIB_TIME", maghribTime);
        prayTimesValues.put("ISHA_TIME", ishaTime);

        if (isUpdateOperation) {
            db.update("Times", prayTimesValues, "_id = ?", new String[]{"1"});
        } else {
            db.insert("Times", null, prayTimesValues);
        }

        db.close();
    }

    public String getLatestUpdateDate() {
        String updateDate = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Times",
                new String[]{
                        "UPDATE_DATE"
                },
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            updateDate = cursor.getString(0);
        }

        cursor.close();
        db.close();

        return updateDate;
    }

    public String getTodayDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date todayDate = new Date();
//        return dateFormat.format(todayDate);
        return "2020-01-02";
    }

}
