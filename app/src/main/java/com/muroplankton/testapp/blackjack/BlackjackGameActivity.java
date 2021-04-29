package com.muroplankton.testapp.blackjack;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.muroplankton.testapp.R;

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
        PlayerHandsAdapter playerHandsAdapter = new PlayerHandsAdapter(this, this);
        playerPager.setAdapter(playerHandsAdapter);
        player = new Player(this, playerUpdater, playerHandsAdapter, startingBet);

        if (player.isAbleToHit()) {
            hitButton.setEnabled(true);
            standButton.setEnabled(true);
            insuranceButton.setEnabled(dealer.canBeInsured());
        } else {
            player.stand(playerPager);
        }

        playerPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                playerPager.setBackgroundResource((position == player.getActiveHandIndex()) ? R.color.white : R.color.transparent);
                betValueText.setText(Integer.toString(player.getCurrentHandBet()));
            }
        });

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
                player.stand(playerPager);
            }
        } else if (v.getId() == R.id.activity_blackjack_game_stand_button) {
            player.stand(playerPager);
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
            player.stand(playerPager);
        } else if (v.getId() == R.id.activity_blackjack_game_split_button) {
            player.split();
        }
    };

    Dealer.DealerFragmentUpdater dealerUpdater = dealerHand -> getSupportFragmentManager().beginTransaction().replace(R.id.activity_blackjack_game_dealer_frame, dealerHand).commit();

    private Player.playerSituationCallbacks playerUpdater = new Player.playerSituationCallbacks() {
        @Override
        public void onSplitPossible() {
            splitButton.setEnabled(true);
        }

        @Override
        public void onDoublePossible() {
            doubleButton.setEnabled(true);
        }

        @Override
        public void onLastHandPlayed() {
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            dealer.playForResult();
            dealer.payOutInsurance();
        }

        @Override
        public void onTimeToCompareCurrentHand() {
            compareCurrentPlayerHand();
        }

        @Override
        public void onGameFinished() {
            
        }
    };

    private void compareCurrentPlayerHand() {
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

        Toast.makeText(this, resultToBeDisplayed, Toast.LENGTH_SHORT).show();
    }
}
