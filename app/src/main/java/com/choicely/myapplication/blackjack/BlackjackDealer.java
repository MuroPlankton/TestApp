package com.choicely.myapplication.blackjack;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.choicely.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class BlackjackDealer extends Fragment {

    View dealerHandView;
    TextView dealerScore;
    Button insuranceButton;
    List<Pair<String, String>> hand = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dealerHandView = inflater.inflate(R.layout)
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public BlackjackDealer() {
        dealerHand = findViewById(R.id.activity_blackjack_game_dealer_recycler);
        BlackjackHandOfCardsAdapter dealerCardsAdapter = new BlackjackHandOfCardsAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        dealerHand.setLayoutManager(linearLayoutManager);
        dealerHand.setAdapter(dealerCardsAdapter);

        BlackjackDeckSimulator deckSimulator = BlackjackDeckSimulator.getDeckSimulator(this);
        Pair firstCard = deckSimulator.getRandomCardFromDeck();
        dealerCardsAdapter.addCard(firstCard);
        hand.add(firstCard);

        String cardValue = firstCard.second.toString();
        int cardValueAsInt;
        switch (cardValue) {
            default:
            case "Ace":
                cardValueAsInt = 11;
                insuranceButton = findViewById(R.id.activity_blackjack_game_insurance_button);
                insuranceButton.setEnabled(true);
                break;
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "10":
                cardValueAsInt = Integer.parseInt(cardValue);
                break;
            case "Jack":
            case "Queen":
            case "King":
                cardValueAsInt = 10;
                break;
        }

        dealerScore = findViewById(R.id.activity_blackjack_game_dealer_score);
        dealerScore.setText("" + cardValueAsInt);
    }
}
