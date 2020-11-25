package com.choicely.myapplication.blackjack;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.choicely.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class BlackJackActivity extends AppCompatActivity {

    private TextView generalInfo;
    private Button buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive;
    private TextView currentBet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack);


    }
}
