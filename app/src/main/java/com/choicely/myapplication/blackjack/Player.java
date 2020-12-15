package com.choicely.myapplication.blackjack;

import android.content.Context;
import android.util.Pair;

import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class Player extends CommonLogic {

    private static final int INDEX_OF_ACTIVE_HAND = 0;

    private Context context;
    private List<List<Pair<String, String>>> hands = new ArrayList<>();
    private List<Integer> totalOfEachHand = new ArrayList<>();
    private List<Integer> betOfEachHand = new ArrayList<>();
    private activePlayerHandPossibilityUpdater updater;
    private PlayerHandsAdapter handsAdapter;

    public Player(Context ctx, activePlayerHandPossibilityUpdater updater, PlayerHandsAdapter adapter, int startingBet) {
        this.context = ctx;
        List<Pair<String, String>> newHand = new ArrayList<>();
        newHand.add(BlackjackDeckSimulator.getDeckSimulator(context).getRandomCardFromDeck());
        newHand.add(BlackjackDeckSimulator.getDeckSimulator(context).getRandomCardFromDeck());

        int handTotal = super.getHandTotal(newHand);
        totalOfEachHand.add(handTotal);

        checkForSplitAndDouble(newHand, handTotal);

        betOfEachHand.add(startingBet);

        hands.add(newHand);
        this.updater = updater;
        this.handsAdapter = adapter;
    }

    public void hit() {
        List<Pair<String, String>> handToUpdate = hands.get(INDEX_OF_ACTIVE_HAND);
        handToUpdate.add(BlackjackDeckSimulator.getDeckSimulator(context).getRandomCardFromDeck());
        totalOfEachHand.set(INDEX_OF_ACTIVE_HAND, super.getHandTotal(handToUpdate));
        handsAdapter.updateHandBeingPlayed(handToUpdate, totalOfEachHand.get(INDEX_OF_ACTIVE_HAND));
    }

    public boolean isAbleToHit() {
        int handTotal = super.getHandTotal(hands.get(INDEX_OF_ACTIVE_HAND));
        return (handTotal < 21);
    }

    public activePlayerHandPossibilityUpdater getUpdater() {
        return updater;
    }

    public int getHandTotal() {
        return super.getHandTotal(hands.get(INDEX_OF_ACTIVE_HAND));
    }

    public void doubleDown() {
        int betToDouble = betOfEachHand.get(INDEX_OF_ACTIVE_HAND);
        betToDouble = (BlackjackBank.takeAmountFromBank(betToDouble)) ? betToDouble * 2 : betToDouble + BlackjackBank.emptyBank();

        betOfEachHand.set(INDEX_OF_ACTIVE_HAND, betToDouble);
    }

    public int getCurrentHandBet() {
        return betOfEachHand.get(INDEX_OF_ACTIVE_HAND);
    }

    public void split() {
        List<Pair<String, String>> firstHand = hands.get(INDEX_OF_ACTIVE_HAND);
        Pair secondCard = firstHand.remove(1);
        firstHand.add(BlackjackDeckSimulator.getDeckSimulator(context).getRandomCardFromDeck());
        hands.set(INDEX_OF_ACTIVE_HAND, firstHand);
        totalOfEachHand.set(INDEX_OF_ACTIVE_HAND, super.getHandTotal(firstHand));
        handsAdapter.updateHandBeingPlayed(firstHand, totalOfEachHand.get(INDEX_OF_ACTIVE_HAND));
        checkForSplitAndDouble(firstHand, totalOfEachHand.get(INDEX_OF_ACTIVE_HAND));

        List<Pair<String, String>> secondHand = new ArrayList<>();
        secondHand.add(secondCard);
        secondHand.add(BlackjackDeckSimulator.getDeckSimulator(context).getRandomCardFromDeck());
        hands.add(secondHand);
        totalOfEachHand.add(super.getHandTotal(secondHand));
        handsAdapter.addHand(secondHand, totalOfEachHand.size() -1);
    }

    public void stand() {
        if (hands.size() > 1) {
            hands.remove(INDEX_OF_ACTIVE_HAND);
            totalOfEachHand.remove(INDEX_OF_ACTIVE_HAND);
            betOfEachHand.remove(INDEX_OF_ACTIVE_HAND);
            handsAdapter.standActiveHand();
        }
    }

    private void checkForSplitAndDouble(List<Pair<String, String>> hand, int handTotal) {
        if (hand.get(0) == hand.get(1) && betOfEachHand.get(hands.indexOf(hand)) <= BlackjackBank.getMoneyInBank()) {
            updater.onSplitPossible();
        }

        if (handTotal >= 9 && handTotal <= 11) {
            updater.onDoublePossible();
        }
    }

    public interface activePlayerHandPossibilityUpdater {
        void onSplitPossible();

        void onDoublePossible();
    }
}
