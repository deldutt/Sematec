package com.sematec.praytimes.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE Pray_Times (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fajrTime TEXT, " +
                "dhuhrTime TEXT, " +
                "asrTime TEXT, " +
                "maghribTime TEXT, " +
                "ishaTime TEXT)";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<String> getPrayTimes() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> prayTimes = new ArrayList<>();
        String fajrTime;
        String dhuhrTime;
        String asrTime;
        String maghribTime;
        String ishaTime;

        String GET_FAJR_TIMES = "SELECT fajrTime FROM Pray_Times";
        String GET_DHUHR_TIMES = "SELECT dhuhrTime FROM Pray_Times";
        String GET_ASR_TIMES = "SELECT asrTime FROM Pray_Times";
        String GET_MAGHRIB_TIMES = "SELECT maghribTime FROM Pray_Times";
        String GET_ISHA_TIMES = "SELECT ishaTime FROM Pray_Times";


        Cursor fajrCursor = db.rawQuery(GET_FAJR_TIMES, null);
        Cursor dhuhrCursor = db.rawQuery(GET_DHUHR_TIMES, null);
        Cursor asrCursor = db.rawQuery(GET_ASR_TIMES, null);
        Cursor maghribCursor = db.rawQuery(GET_MAGHRIB_TIMES, null);
        Cursor ishaCursor = db.rawQuery(GET_ISHA_TIMES, null);

        fajrCursor.moveToFirst();
        dhuhrCursor.moveToFirst();
        asrCursor.moveToFirst();
        maghribCursor.moveToFirst();
        ishaCursor.moveToFirst();

        fajrTime = fajrCursor.getString(0);
        dhuhrTime = dhuhrCursor.getString(0);
        asrTime = asrCursor.getString(0);
        maghribTime = maghribCursor.getString(0);
        ishaTime = ishaCursor.getString(0);

        db.close();

        prayTimes.add(fajrTime);
        prayTimes.add(dhuhrTime);
        prayTimes.add(asrTime);
        prayTimes.add(maghribTime);
        prayTimes.add(ishaTime);

        return prayTimes;
    }

    public void insertPrayTimes(String fajrTime, String dhuhrTime, String asrTime, String maghribTime, String ishaTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        String INSERT_PRAY_TIMES_QUERY = "INSERT INTO Pray_Times " +
                "(fajrTime, dhuhrTime, asrTime, maghribTime, ishaTime) VALUES (" +
                "'" + fajrTime + "', " +
                "'" + dhuhrTime + "', " +
                "'" + asrTime + "', " +
                "'" + maghribTime + "', " +
                "'" + ishaTime + "')";

        db.execSQL(INSERT_PRAY_TIMES_QUERY);
        db.close();
    }
}
