package com.sematec.praytimes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sematec.praytimes.core.PrayTimeExtractor;
import com.sematec.praytimes.core.Timings;
import com.sematec.praytimes.db.DbHelper;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PrayTimesActivity extends AppCompatActivity {

    private Timings timings;
    final DbHelper dbHelper = new DbHelper(this, "Pray_Times",null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pray_times);
    }

    public void onOnlineTimesClicked(View view) {
        String address = "http://api.aladhan.com/v1/timingsByCity?city=Tehran&country=Iran&method=8";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(address, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Gson gson = new Gson();
                PrayTimeExtractor prayTimeExtractor = gson.fromJson(response.toString(), PrayTimeExtractor.class);
                timings = prayTimeExtractor.getData().getTimings();

                TextView fajrTextView = findViewById(R.id.fajrTextView);
                TextView dhuhrTextView = findViewById(R.id.dhuhrTextView);
                TextView asrTextView = findViewById(R.id.asrTextView);
                TextView maghribTextView = findViewById(R.id.maghribTextView);
                TextView ishaTextView = findViewById(R.id.ishaTextView);

                fajrTextView.setText(timings.getFajr());
                dhuhrTextView.setText(timings.getDhuhr());
                asrTextView.setText(timings.getAsr());
                maghribTextView.setText(timings.getMaghrib());
                ishaTextView.setText(timings.getIsha());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("TAG", throwable.getMessage());
            }
        });
    }

    public void onSaveTimesClicked(View view) {
        TextView fajrTextView = findViewById(R.id.fajrTextView);
        TextView dhuhrTextView = findViewById(R.id.dhuhrTextView);
        TextView asrTextView = findViewById(R.id.asrTextView);
        TextView maghribTextView = findViewById(R.id.maghribTextView);
        TextView ishaTextView = findViewById(R.id.ishaTextView);

        dbHelper.insertPrayTimes(
                fajrTextView.getText().toString(),
                dhuhrTextView.getText().toString(),
                asrTextView.getText().toString(),
                maghribTextView.getText().toString(),
                ishaTextView.getText().toString());
    }

    public void onOfflineTimesClicked(View view) {
        ArrayList<String> prayTimes = dbHelper.getPrayTimes();

        TextView fajrTextView = findViewById(R.id.fajrTextView);
        TextView dhuhrTextView = findViewById(R.id.dhuhrTextView);
        TextView asrTextView = findViewById(R.id.asrTextView);
        TextView maghribTextView = findViewById(R.id.maghribTextView);
        TextView ishaTextView = findViewById(R.id.ishaTextView);

        fajrTextView.setText(prayTimes.get(0));
        dhuhrTextView.setText(prayTimes.get(1));
        asrTextView.setText(prayTimes.get(2));
        maghribTextView.setText(prayTimes.get(3));
        ishaTextView.setText(prayTimes.get(4));
    }
}
