package com.choicely.myapplication.blackjack;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.choicely.myapplication.R;

import java.util.List;
import java.util.Stack;

public class BlackjackGameActivity extends AppCompatActivity {

    private static final String PLAYER_BET = "player_bet";

    private TextView betValueText;
    private Button hitButton, standButton, splitButton, insuranceButton, doubleButton;
    private Dealer dealer;
    private Player player;
    private ViewPager2 playerPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack_game);

        betValueText = findViewById(R.id.activity_blackjack_game_bet);
        int startingBet = getIntent().getIntExtra(PLAYER_BET, -1);
        betValueText.setText(String.format("Active bet: %s€", startingBet));

        hitButton = findViewById(R.id.activity_blackjack_game_hit_button);
        standButton = findViewById(R.id.activity_blackjack_game_stand_button);
        splitButton = findViewById(R.id.activity_blackjack_game_split_button);
        insuranceButton = findViewById(R.id.activity_blackjack_game_insurance_button);
        doubleButton = findViewById(R.id.activity_blackjack_game_double_button);

        hitButton.setOnClickListener(onClickListener);
        standButton.setOnClickListener(onClickListener);
        splitButton.setOnClickListener(onClickListener);
        insuranceButton.setOnClickListener(onClickListener);
        doubleButton.setOnClickListener(onClickListener);

        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        splitButton.setEnabled(false);
        doubleButton.setEnabled(false);
        insuranceButton.setEnabled(false);

        dealer = new Dealer(this, dealerUpdater);

        playerPager = findViewById(R.id.activity_blackjack_game_player_pager);
        PlayerHandsAdapter playerHandsAdapter = new PlayerHandsAdapter(this);
        playerPager.setAdapter(playerHandsAdapter);
        player = new Player(this, playerUpdater, playerHandsAdapter, startingBet);

        if (player.isAbleToHit()) {
            hitButton.setEnabled(true);
            standButton.setEnabled(true);
            insuranceButton.setEnabled(dealer.canBeInsured());
        } else {
            decideWinner();
        }

        //TODO: setup player with 2 cards and check against rules
        //TODO: give all of the buttons their functionality
    }

    private View.OnClickListener onClickListener = v -> {
        boolean isDoubleAvailable = doubleButton.isEnabled();

        insuranceButton.setEnabled(false);
        doubleButton.setEnabled(false);
        splitButton.setEnabled(false);

        String currentBetValueText = betValueText.getText().toString();
        if (v.getId() == R.id.activity_blackjack_game_hit_button) {
            player.hit();
            if (player.isAbleToHit()) {
                hitButton.setEnabled(true);
                standButton.setEnabled(true);
            } else {
                hitButton.setEnabled(false);
                standButton.setEnabled(false);
                decideWinner();
            }
        } else if (v.getId() == R.id.activity_blackjack_game_stand_button) {
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            decideWinner();
        } else if (v.getId() == R.id.activity_blackjack_game_insurance_button) {
            dealer.insure(player.getCurrentHandBet());
            int activeHandBet = player.getCurrentHandBet();
            betValueText.setText(String.format("%d€ + %d€", activeHandBet, activeHandBet / 2));
            doubleButton.setEnabled(isDoubleAvailable);
        } else if (v.getId() == R.id.activity_blackjack_game_double_button) {
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            player.doubleDown();
            betValueText.setText(currentBetValueText + " + (doubled)");
            decideWinner();
        } else if (v.getId() == R.id.activity_blackjack_game_split_button) {
            player.split();
        }
    };

    Dealer.DealerFragmentUpdater dealerUpdater = dealerHand -> getSupportFragmentManager().beginTransaction().replace(R.id.activity_blackjack_game_dealer_frame, dealerHand).commit();

    private Player.activePlayerHandPossibilityUpdater playerUpdater = new Player.activePlayerHandPossibilityUpdater() {
        @Override
        public void onSplitPossible() {
            splitButton.setEnabled(true);
        }

        @Override
        public void onDoublePossible() {
            doubleButton.setEnabled(true);
        }
    };

    private void decideWinner() {
        dealer.playForResult();
        String resultToBeDisplayed = "";
        int playerTotal = player.getHandTotal();
        int dealerTotal = dealer.getHandTotal();

        if ((playerTotal > 21 && dealerTotal < 22) || (dealerTotal > playerTotal && dealerTotal < 22)) {
            resultToBeDisplayed = getResources().getString(R.string.blackjack_dealer_win);
        } else if ((playerTotal == dealerTotal) || (playerTotal > 21 && dealerTotal > 21)) {
            resultToBeDisplayed = getResources().getString(R.string.blackjack_push);
            BlackjackBank.addAmountToBank(player.getCurrentHandBet());
        } else if ((playerTotal > dealerTotal && playerTotal < 22) || (dealerTotal > 21 && playerTotal < 22)) {
            resultToBeDisplayed = getResources().getString(R.string.blackjack_player_win);
            BlackjackBank.addAmountToBank(player.getCurrentHandBet() * 2);
        }

        player.stand(playerPager);
        dealer.payOutInsurance();

        Toast.makeText(this, resultToBeDisplayed, Toast.LENGTH_LONG).show();
    }
}
