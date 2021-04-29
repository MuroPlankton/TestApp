package com.muroplankton.testapp.tabs;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.muroplankton.testapp.R;

public class TabsActivity extends AppCompatActivity {

    private static final String TAG = "TabsActivity";

    public static ViewPager2 viewPager2;
    private TabsAdapter adapter;
    public static TabLayout tabLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        getSupportFragmentManager();

        viewPager2 = findViewById(R.id.activity_tabs_pager);
        tabLayout = findViewById(R.id.activity_tabs_tab_layout);
        adapter = new TabsAdapter(this);
        viewPager2.setAdapter(adapter);
    }
}
