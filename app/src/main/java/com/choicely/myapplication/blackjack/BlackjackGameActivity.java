package com.choicely.myapplication.blackjack;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.choicely.myapplication.R;

public class BlackjackGameActivity extends AppCompatActivity {

    private static final String PLAYER_BET = "player_bet";

    TextView betValueText;
    RecyclerView playerHands, singlePlayerHand;
    BlackjackDealer dealer;
    Button hitButton, standButton, splitButton, insuranceButton, doubleButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack_game);

        playerHands = findViewById(R.id.activity_blackjack_game_player_recycler);
        singlePlayerHand = findViewById(R.id.blackjack_player_hand_card_recycler);

        betValueText = findViewById(R.id.activity_blackjack_game_bet);
        int playerBet = getIntent().getIntExtra(PLAYER_BET, -1);
        betValueText.setText(playerBet + "€");

        hitButton = findViewById(R.id.activity_blackjack_game_hit_button);
        standButton = findViewById(R.id.activity_blackjack_game_stand_button);
        splitButton = findViewById(R.id.activity_blackjack_game_split_button);
        insuranceButton = findViewById(R.id.activity_blackjack_game_insurance_button);
        doubleButton = findViewById(R.id.activity_blackjack_game_double_button);

        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        splitButton.setEnabled(false);
        insuranceButton.setEnabled(false);
        doubleButton.setEnabled(false);

        dealer = new BlackjackDealer();

        //TODO: make game logic
        //TODO: continue with dealer class and make player class
        //TODO: fragment for a hand
    }
}
