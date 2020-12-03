package com.choicely.myapplication.blackjack;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.choicely.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class BlackJackActivity extends AppCompatActivity {

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
                bankValueText.setText(BlackjackBank.moneyInBank + "€");
                updateBetAmountButtons();
            }
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack);

        BlackjackBank.setMoneyInBank(1000);

        bankValueText = findViewById(R.id.activity_blackjack_bank_amount);
        betText = findViewById(R.id.activity_blackjack_bet_amount);
        bankValueText.setText(String.format("%d€", BlackjackBank.moneyInBank));
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

        });
    }

    private void updateBetAmountButtons() {
        int bankValue = BlackjackBank.moneyInBank;
        tenButton.setEnabled(bankValue >= 10);
        hundredButton.setEnabled(bankValue >= 100);
        twoHundredButton.setEnabled(bankValue >= 200);
        fiveHundredButton.setEnabled(bankValue >= 500);
    }

    //TODO: the game itself with multiple recyclerviews or some similar things
    //TODO: simulating multiple card decks with maps and lists or something
}
