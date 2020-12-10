package com.choicely.myapplication.blackjack;

import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.choicely.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BlackjackGameActivity extends AppCompatActivity {

    private static final String PLAYER_BET = "player_bet";

    TextView betValueText;
    BlackjackHandFragment dealerHandOnScreen;
    List<Pair<String, String>> dealerCards = new ArrayList<>();
    int dealerTotal = 0;
    Stack<Pair<Fragment, List<Pair<String, String>>>> playerHands;
    Button hitButton, standButton, splitButton, insuranceButton, doubleButton;
    BlackjackDeckSimulator deckSimulator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack_game);

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

        deckSimulator = BlackjackDeckSimulator.getDeckSimulator(this);

        setupDealerAndCheckForInsurance();

        Pair playerFirstCard = deckSimulator.getRandomCardFromDeck();
        Pair playerSecondCard = deckSimulator.getRandomCardFromDeck();
        List<Pair<String, String>> playerInitialHandCards = new ArrayList<>();
        playerInitialHandCards.add(playerFirstCard);
        playerInitialHandCards.add(playerSecondCard);

        BlackjackHandFragment playerHandOne = new BlackjackHandFragment();
        playerHandOne.addCard(playerFirstCard);
        playerHandOne.addCard(playerSecondCard);
        playerHandOne.setTotalOnScreen(getHandTotal(playerInitialHandCards));

        //TODO: setup player with 2 cards and check against rules
        //TODO: give all of the buttons their functionality
    }

    private void setupDealerAndCheckForInsurance() {
        Pair<String, String> firstCard = deckSimulator.getRandomCardFromDeck();
        insuranceButton.setEnabled(firstCard.second.equals("Ace"));
        dealerCards.add(firstCard);

        getHandTotal(dealerCards);
        dealerTotal = getHandTotal(dealerCards);
        dealerHandOnScreen = new BlackjackHandFragment();

        dealerHandOnScreen.addCard(firstCard);
        dealerHandOnScreen.setTotalOnScreen(dealerTotal);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_blackjack_game_dealer_frame, dealerHandOnScreen)
                .addToBackStack(null).commit();
    }

    private int getHandTotal(List<Pair<String, String>> handOfCards) {
        int handTotal = 0, valueToAdd;
        int amountOfAces = 0;
        for (Pair card : handOfCards) {
            try {
                valueToAdd = Integer.parseInt(card.second.toString());
            } catch (NumberFormatException e) {
                switch (card.second.toString()) {
                    default:
                    case "Ace":
                        valueToAdd = 11;
                        amountOfAces++;
                        break;
                    case "Jack":
                    case "Queen":
                    case "King":
                        valueToAdd = 10;
                        break;
                }
            }

            handTotal += valueToAdd;
            if (handTotal > 21) {
                if (amountOfAces > 0) {
                    handTotal -= 10;
                } else if (valueToAdd == 11) {
                    handTotal -= 10;
                }
            }
        }
        return handTotal;
    }
}
