package com.choicely.myapplication.blackjack;

import android.content.Context;
import android.util.Pair;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class Player extends CommonLogic {

    private Context context;
    private List<List<Pair<String, String>>> hands = new ArrayList<>();
    private playerHandUpdater updater;

    public Player(Context ctx, playerHandUpdater updater) {
        this.context = ctx;
        List<Pair<String, String>> newHand = new ArrayList<>();
        newHand.add(BlackjackDeckSimulator.getDeckSimulator(context).getRandomCardFromDeck());
        newHand.add(BlackjackDeckSimulator.getDeckSimulator(context).getRandomCardFromDeck());
        hands.add(newHand);
        this.updater = updater;
        updateFragment();
    }

    private void updateFragment() {
        BlackjackHandFragment playerHandOnScreen = new BlackjackHandFragment();
        playerHandOnScreen.setListOfCards(hands.get(0));
        playerHandOnScreen.setScore(super.getHandTotal(hands.get(0)));
        updater.onPlayerFragmentReplaceNeeded(playerHandOnScreen);
    }

    public void hit() {
        hands.get(0).add(BlackjackDeckSimulator.getDeckSimulator(context).getRandomCardFromDeck());
        updateFragment();
    }

    public String checkAgainstRules() {
        return super.checkAgainstRules(hands.get(0));
    }

    public playerHandUpdater getUpdater() {
        return updater;
    }

    public int getHandTotal() {
        return super.getHandTotal(hands.get(0));
    }

    public interface playerHandUpdater {
        void onPlayerFragmentReplaceNeeded(Fragment playerHand);
    }
}
