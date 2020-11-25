package com.choicely.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.choicely.myapplication.citysearch.CitySearchActivity;
import com.choicely.myapplication.timer.TimerActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button imageMove, boo, cards, viewPager, tabs, search, timer;

    private View.OnClickListener onClickListener = v -> {
        Intent intent;
        switch (v.getId()) {
            default:
            case R.id.activity_main_image_move_button:
                intent = new Intent(this, ImageMoveActivity.class);
                break;
            case R.id.activity_main_boo_button:
                intent = new Intent(this, BooActivity.class);
                break;
            case R.id.activity_main_cards_button:
                intent = new Intent(this, CardsActivity.class);
                break;
            case R.id.activity_main_view_pager_button:
                intent = new Intent(this, ViewPagerActivity.class);
                break;
            case R.id.activity_main_tabs_button:
                intent = new Intent(this, TabsActivity.class);
                break;
            case R.id.activity_main_search_button:
                intent = new Intent(this, CitySearchActivity.class);
                break;
            case R.id.activity_main_timer_button:
                intent = new Intent(this, TimerActivity.class);
        }
        startActivity(intent);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageMove = findViewById(R.id.activity_main_image_move_button);
        imageMove.setOnClickListener(onClickListener);

        boo = findViewById(R.id.activity_main_boo_button);
        boo.setOnClickListener(onClickListener);

        cards = findViewById(R.id.activity_main_cards_button);
        cards.setOnClickListener(onClickListener);

        viewPager = findViewById(R.id.activity_main_view_pager_button);
        viewPager.setOnClickListener(onClickListener);

        tabs = findViewById(R.id.activity_main_tabs_button);
        tabs.setOnClickListener(onClickListener);

        search = findViewById(R.id.activity_main_search_button);
        search.setOnClickListener(onClickListener);

        timer = findViewById(R.id.activity_main_timer_button);
        timer.setOnClickListener(onClickListener);
    }
}