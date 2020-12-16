package com.choicely.myapplication.blackjack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.choicely.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class BlackJackActivity extends AppCompatActivity {

    private static final String PLAYER_BET = "player_bet";
    private static final String TAG = "BlackJackActivity";
    private TextView bankValueText, betText;
    private Button tenButton, hundredButton, twoHundredButton, fiveHundredButton, playButton;
    private int currentBet;
    private View.OnClickListener onClickListener = v -> {
        int addToBet = 0;

        if (v.getId() == R.id.activity_blackjack_plus_ten) {
            addToBet = 10;
        } else if (v.getId() == R.id.activity_blackjack_plus_hundred) {
            addToBet = 100;
        } else if (v.getId() == R.id.activity_blackjack_plus_two_hundred) {
            addToBet = 200;
        } else if (v.getId() == R.id.activity_blackjack_plus_half_a_thou) {
            addToBet = 500;
        }

        if (addToBet != 0) {
            if (BlackjackBank.takeAmountFromBank(addToBet)) {
                currentBet += addToBet;
                betText.setText(currentBet + "€");
                bankValueText.setText(BlackjackBank.getMoneyInBank() + "€");
                updateBetAmountButtons();
            }
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack);

        BlackjackBank.setMoneyInBank(1000);
        BlackjackDeckSimulator deckSimulator = BlackjackDeckSimulator.getDeckSimulator(this);

        bankValueText = findViewById(R.id.activity_blackjack_bank_amount);
        betText = findViewById(R.id.activity_blackjack_bet_amount);
        bankValueText.setText(String.format("%d€", BlackjackBank.getMoneyInBank()));
        betText.setText("0€");

        tenButton = findViewById(R.id.activity_blackjack_plus_ten);
        hundredButton = findViewById(R.id.activity_blackjack_plus_hundred);
        twoHundredButton = findViewById(R.id.activity_blackjack_plus_two_hundred);
        fiveHundredButton = findViewById(R.id.activity_blackjack_plus_half_a_thou);

        tenButton.setOnClickListener(onClickListener);
        hundredButton.setOnClickListener(onClickListener);
        twoHundredButton.setOnClickListener(onClickListener);
        fiveHundredButton.setOnClickListener(onClickListener);

        playButton = findViewById(R.id.activity_blackjack_play);
        playButton.setOnClickListener(v -> {
            Log.d(TAG, "amount of cards left in shoe: " + deckSimulator.getTotalCards());
            if (deckSimulator.getTotalCards() < 70) {
                Toast.makeText(this, R.string.card_deck_shuffle, Toast.LENGTH_SHORT).show();
                deckSimulator.clearDecks();
                deckSimulator.generateDecks();
            }
            Intent intent = new Intent(this, BlackjackGameActivity.class);
            intent.putExtra(PLAYER_BET, currentBet);
            startActivity(intent);
        });
    }

    private void updateBetAmountButtons() {
        int bankValue = BlackjackBank.getMoneyInBank();
        tenButton.setEnabled(bankValue >= 10);
        hundredButton.setEnabled(bankValue >= 100);
        twoHundredButton.setEnabled(bankValue >= 200);
        fiveHundredButton.setEnabled(bankValue >= 500);
    }

    //TODO: the game itself with multiple recyclerviews or some similar things
}
