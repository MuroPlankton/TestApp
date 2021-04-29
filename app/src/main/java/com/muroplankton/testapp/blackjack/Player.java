package com.muroplankton.testapp.blackjack;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class Player extends CommonLogic {

    private static final String TAG = "Player";

    private Context context;
    private int activeHandIndex = 0;
    private List<List<Pair<String, String>>> hands = new ArrayList<>();
    private List<Integer> totalOfEachHand = new ArrayList<>();
    private List<Integer> betOfEachHand = new ArrayList<>();
    private playerSituationCallbacks updater;
    private PlayerHandsAdapter handsAdapter;

    public Player(Context ctx, playerSituationCallbacks updater, PlayerHandsAdapter adapter, int startingBet) {
        this.context = ctx;
        List<Pair<String, String>> newHand = new ArrayList<>();
        BlackjackDeckSimulator deckSimulator = BlackjackDeckSimulator.getDeckSimulator(context);
        newHand.add(deckSimulator.getRandomCardFromDeck());
        newHand.add(deckSimulator.getRandomCardFromDeck());

        int handTotal = super.getHandTotal(newHand);
        totalOfEachHand.add(handTotal);
        betOfEachHand.add(startingBet);

        hands.add(newHand);
        this.updater = updater;
        this.handsAdapter = adapter;
        handsAdapter.addHand(newHand, handTotal);

        checkForSplitAndDouble(newHand, handTotal);
    }

    public void hit() {
        List<Pair<String, String>> handToUpdate = hands.get(activeHandIndex);
        handToUpdate.add(BlackjackDeckSimulator.getDeckSimulator(context).getRandomCardFromDeck());
        totalOfEachHand.set(activeHandIndex, super.getHandTotal(handToUpdate));
        handsAdapter.updateHandBeingPlayed(handToUpdate, totalOfEachHand.get(activeHandIndex));
    }

    public boolean isAbleToHit() {
        int handTotal = super.getHandTotal(hands.get(activeHandIndex));
        return (handTotal < 21);
    }

    public int getHandTotal() {
        return super.getHandTotal(hands.get(activeHandIndex));
    }

    public void doubleDown() {
        int betToDouble = betOfEachHand.get(activeHandIndex);
        betToDouble = (BlackjackBank.takeAmountFromBank(betToDouble)) ? betToDouble * 2 : betToDouble + BlackjackBank.emptyBank();

        hit();

        betOfEachHand.set(activeHandIndex, betToDouble);
    }

    public int getCurrentHandBet() {
        return betOfEachHand.get(activeHandIndex);
    }

    public void split() {
        List<Pair<String, String>> firstHand = hands.get(activeHandIndex);
        Pair secondCard = firstHand.remove(1);
        firstHand.add(BlackjackDeckSimulator.getDeckSimulator(context).getRandomCardFromDeck());
        hands.set(activeHandIndex, firstHand);
        totalOfEachHand.set(activeHandIndex, super.getHandTotal(firstHand));
        handsAdapter.updateHandBeingPlayed(firstHand, totalOfEachHand.get(activeHandIndex));
        checkForSplitAndDouble(firstHand, totalOfEachHand.get(activeHandIndex));

        List<Pair<String, String>> secondHand = new ArrayList<>();
        secondHand.add(secondCard);
        secondHand.add(BlackjackDeckSimulator.getDeckSimulator(context).getRandomCardFromDeck());
        hands.add(secondHand);
        totalOfEachHand.add(super.getHandTotal(secondHand));
        handsAdapter.addHand(secondHand, totalOfEachHand.get(totalOfEachHand.size() - 1));
    }

    public void stand(ViewPager2 handPager) {
        if (hands.size() > 1) {
            if (activeHandIndex + 1 < hands.size()) {
                activeHandIndex++;
                Log.d(TAG, "item count in playeradapter: " + handsAdapter.getItemCount());
                handPager.setCurrentItem(activeHandIndex);
                checkForSplitAndDouble(hands.get(activeHandIndex), totalOfEachHand.get(activeHandIndex));
            } else {
                updater.onLastHandPlayed();
                for (activeHandIndex = 0; activeHandIndex < hands.size(); activeHandIndex++) {
                    handPager.setCurrentItem(activeHandIndex);
                    updater.onTimeToCompareCurrentHand();
                }
                updater.onGameFinished();
            }
        } else {
            updater.onTimeToCompareCurrentHand();
            updater.onGameFinished();
        }
    }

    private void checkForSplitAndDouble(List<Pair<String, String>> hand, int handTotal) {
        if (hand.get(0).second.equals(hand.get(1).second) && betOfEachHand.get(hands.indexOf(hand)) <= BlackjackBank.getMoneyInBank()) {
            updater.onSplitPossible();
        }

        if (handTotal >= 9 && handTotal <= 11) {
            updater.onDoublePossible();
        }
    }

    public int getActiveHandIndex() {
        return activeHandIndex;
    }

    public interface playerSituationCallbacks {
        void onSplitPossible();
        void onDoublePossible();

        void onLastHandPlayed();
        void onTimeToCompareCurrentHand();
        void onGameFinished();
    }
}
