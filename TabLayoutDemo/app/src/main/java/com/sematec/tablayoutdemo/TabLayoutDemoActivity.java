package com.sematec.tablayoutdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class TabLayoutDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout_demo);

        ViewPager pager = findViewById(R.id.pager);
        TabLayout tab = findViewById(R.id.tab);

        TestViewPagerAdapter adapter = new TestViewPagerAdapter(
          getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        );
        pager.setAdapter(adapter);
        tab.setupWithViewPager(pager);
    }
}
