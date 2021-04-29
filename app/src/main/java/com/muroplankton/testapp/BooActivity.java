package com.muroplankton.testapp;

import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class BooActivity extends AppCompatActivity {

    private final static String TAG = "BooActivity";

    private LinearLayout booLayout;
    private TextView booText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boo);

        booLayout = findViewById(R.id.activity_boo_layout);
        booText = findViewById(R.id.activity_boo_text);

        booLayout.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_boo_enlarge);
            booText.startAnimation(animation);
        });
    }
}
