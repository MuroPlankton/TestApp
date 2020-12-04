package com.choicely.myapplication.blackjack;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.choicely.myapplication.R;

public class BlackjackGameActivity extends AppCompatActivity {

    RecyclerView dealerHand;
    TextView dealerScore;
    RecyclerView playerHands, singlePlayerHand, controls;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack_game);

        dealerHand = findViewById(R.id.activity_blackjack_game_dealer_recycler);
        dealerScore = findViewById(R.id.activity_blackjack_game_dealer_score);

        playerHands = findViewById(R.id.activity_blackjack_game_player_recycler);
        singlePlayerHand = findViewById(R.id.blackjack_player_hand_card_recycler);

        controls = findViewById(R.id.activity_blackjack_game_controls);
        //TODO: make recycler for cards, hands and controls
    }
}
