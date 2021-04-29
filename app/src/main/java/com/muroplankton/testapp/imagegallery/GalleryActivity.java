package com.muroplankton.testapp.imagegallery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.muroplankton.testapp.R;

public class GalleryActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private Spinner formatSpinner;
    private Button addButton;
    private GalleryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        viewPager2 = findViewById(R.id.activity_gallery_view_pager);
        adapter = new GalleryAdapter(this);
        viewPager2.setAdapter(adapter);

        formatSpinner = findViewById(R.id.activity_gallery_format_spinner);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this, R.array.image_formats, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        formatSpinner.setAdapter(arrayAdapter);

        addButton = findViewById(R.id.activity_gallery_new_button);
        addButton.setOnClickListener(v -> {
            startActivity(new Intent(this, CheckUrlActivity.class));
        });
    }
}
