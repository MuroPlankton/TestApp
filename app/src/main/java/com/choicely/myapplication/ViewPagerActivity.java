package com.choicely.myapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class ViewPagerActivity extends AppCompatActivity {

    private final static String TAG = "ViewPagerActivity";

    private ViewPager2 viewPager2;
    private ViewPagerAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        viewPager2 = findViewById(R.id.activity_view_pager_pager);
        adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);
    }
}
