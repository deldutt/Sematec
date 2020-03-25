package com.sematec.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class RecyclerViewDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_demo);

        RecyclerView recycler = findViewById(R.id.recycler);
        ArrayList<String> names = new ArrayList<String>();
        names.add("Pouya Heydari");
        names.add("Ali Rostami");
        names.add("Arad Khanipour");
        names.add("Vahid Shokoohi");
        names.add("Yasin Gorgij");


        RecyclerViewAdapter adapter = new RecyclerViewAdapter(names);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(RecyclerViewDemoActivity.this, RecyclerView.VERTICAL, false));
    }
}
