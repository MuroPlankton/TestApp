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

import com.choicely.myapplication.R;

import java.util.List;
import java.util.Stack;

public class BlackjackGameActivity extends AppCompatActivity {

    private static final String PLAYER_BET = "player_bet";

    TextView betValueText;
    Stack<Pair<Fragment, List<Pair<String, String>>>> playerHands;
    Button hitButton, standButton, splitButton, insuranceButton, doubleButton;
    BlackjackDeckSimulator deckSimulator;
    Dealer dealer;
    Player player;

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

        hitButton.setOnClickListener(onClickListener);
        standButton.setOnClickListener(onClickListener);

        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        splitButton.setEnabled(false);
        doubleButton.setEnabled(false);

        Dealer.DealerFragmentUpdater dealerUpdater = new Dealer.DealerFragmentUpdater() {
            @Override
            public void onDealerFragmentReplaceNeeded(Fragment dealerHand) {
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_blackjack_game_dealer_frame, dealerHand).commit();
            }
        };
        dealer = new Dealer(this, dealerUpdater);
        insuranceButton.setEnabled(dealer.canBeInsured());

        Player.playerHandUpdater playerUpdater = new Player.playerHandUpdater() {
            @Override
            public void onPlayerFragmentReplaceNeeded(Fragment playerHand) {
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_blackjack_game_player_frame, playerHand).commit();
            }
        };
        player = new Player(this, playerUpdater);

        checkAndUpdatePlayer();
        //TODO: setup player with 2 cards and check against rules
        //TODO: give all of the buttons their functionality
    }

    private View.OnClickListener onClickListener = v -> {
        insuranceButton.setEnabled(false);
        if (v.getId() == R.id.activity_blackjack_game_hit_button) {
            player.hit();
            checkAndUpdatePlayer();
        } else if (v.getId() == R.id.activity_blackjack_game_stand_button) {
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            decideWinner();
        }
    };

    private void decideWinner() {
        dealer.playForResult();
        String resultToBeDisplayed = "";
        int playerTotal = player.getHandTotal();
        int dealerTotal = dealer.getHandTotal();

        if (playerTotal > 21 && dealerTotal < 22) {
            resultToBeDisplayed = getResources().getString(R.string.blackjack_dealer_win);
        } else if (dealerTotal > playerTotal && dealerTotal < 22) {
            resultToBeDisplayed = getResources().getString(R.string.blackjack_dealer_win);
        } else if (playerTotal == dealerTotal || (playerTotal > 21 && dealerTotal > 21)) {
            resultToBeDisplayed = getResources().getString(R.string.blackjack_push);
        } else if (playerTotal > dealerTotal && playerTotal < 22) {
            resultToBeDisplayed = getResources().getString(R.string.blackjack_player_win);
        } else if (dealerTotal > 21 && playerTotal < 22) {
            resultToBeDisplayed = getResources().getString(R.string.blackjack_player_win);
        }

        Toast.makeText(this, resultToBeDisplayed, Toast.LENGTH_LONG).show();
    }

    private void checkAndUpdatePlayer() {
        hitButton.setEnabled(false);
        switch (player.checkAgainstRules()) {
            case "continue":
                hitButton.setEnabled(true);
                standButton.setEnabled(true);
                break;
            case "bust":
            case "blackjack":
            case "done":
                hitButton.setEnabled(false);
                standButton.setEnabled(false);
                decideWinner();
                break;
        }
    }
}
