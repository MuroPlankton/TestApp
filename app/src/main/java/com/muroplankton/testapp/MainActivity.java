package com.muroplankton.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.muroplankton.testapp.blackjack.BlackJackActivity;
import com.muroplankton.testapp.citysearch.CitySearchActivity;
import com.muroplankton.testapp.imagegallery.GalleryActivity;
import com.muroplankton.testapp.randomlistgenerator.StringListGenerator;
import com.muroplankton.testapp.receiptsaver.ReceiptListActivity;
import com.muroplankton.testapp.sulkeiset.SulkeisetActivity;
import com.muroplankton.testapp.tabs.TabsActivity;
import com.muroplankton.testapp.timer.TimerActivity;
import com.muroplankton.testapp.viewpager.ViewPagerActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button imageMove, boo, cards, viewPager, tabs, search, timer, generator, gallery, blackjack, sulkeiset, receiptSaver;

    private View.OnClickListener onClickListener = v -> {
        Intent intent = null;
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
                break;
            case R.id.activity_main_generator_button:
                StringListGenerator.clearAndGenerateStrings();
                break;
            case R.id.activity_main_gallery_button:
                intent = new Intent(this, GalleryActivity.class);
                break;
            case R.id.activity_main_blackjack_button:
                intent = new Intent(this, BlackJackActivity.class);
                break;
            case R.id.activity_main_sulkeiset_button:
                intent = new Intent(this, SulkeisetActivity.class);
                break;
            case R.id.activity_main_receipt_saver_button:
                intent = new Intent(this, ReceiptListActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
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

        generator = findViewById(R.id.activity_main_generator_button);
        generator.setOnClickListener(onClickListener);

        gallery = findViewById(R.id.activity_main_gallery_button);
        gallery.setOnClickListener(onClickListener);

        blackjack = findViewById(R.id.activity_main_blackjack_button);
        blackjack.setOnClickListener(onClickListener);

        sulkeiset = findViewById(R.id.activity_main_sulkeiset_button);
        sulkeiset.setOnClickListener(onClickListener);

        receiptSaver = findViewById(R.id.activity_main_receipt_saver_button);
        receiptSaver.setOnClickListener(onClickListener);
    }
}