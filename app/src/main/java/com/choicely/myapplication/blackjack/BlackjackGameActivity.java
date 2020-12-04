package com.choicely.myapplication.blackjack;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.choicely.myapplication.R;

public class BlackjackGameActivity extends AppCompatActivity {

    private static final String PLAYER_BET = "player_bet";

    RecyclerView dealerHand;
    TextView dealerScore, betValueText;
    RecyclerView playerHands, singlePlayerHand, controls;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack_game);

        dealerHand = findViewById(R.id.activity_blackjack_game_dealer_recycler);
        BlackjackHandOfCardsAdapter dealerCardsAdapter = new BlackjackHandOfCardsAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        dealerHand.setLayoutManager(linearLayoutManager);
        dealerHand.setAdapter(dealerCardsAdapter);

        //temporary hard-coded test
        BlackjackDeckSimulator simulator = BlackjackDeckSimulator.getDeckSimulator(this);
        dealerCardsAdapter.addCard(simulator.getRandomCardFromDeck());
        dealerCardsAdapter.addCard(simulator.getRandomCardFromDeck());
        dealerCardsAdapter.addCard(simulator.getRandomCardFromDeck());

        dealerScore = findViewById(R.id.activity_blackjack_game_dealer_score);
        dealerScore.setText("16");

        playerHands = findViewById(R.id.activity_blackjack_game_player_recycler);
        singlePlayerHand = findViewById(R.id.blackjack_player_hand_card_recycler);

        betValueText = findViewById(R.id.activity_blackjack_game_bet);
        int playerBet = getIntent().getIntExtra(PLAYER_BET, -1);
        betValueText.setText(playerBet + "€");

        controls = findViewById(R.id.activity_blackjack_game_controls);
        //TODO: make recycler for cards, hands and controls
    }
}
