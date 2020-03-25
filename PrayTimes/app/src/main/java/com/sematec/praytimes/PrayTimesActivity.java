package com.sematec.praytimes;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sematec.praytimes.core.PrayTimeExtractor;
import com.sematec.praytimes.core.Timings;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class PrayTimesActivity extends AppCompatActivity {

    private Timings timings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pray_times);

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
}
