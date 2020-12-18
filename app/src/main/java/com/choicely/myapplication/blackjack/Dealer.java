package com.choicely.myapplication.blackjack;

import android.content.Context;
import android.util.Pair;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class Dealer extends CommonLogic {

    private static final String CARDS_TO_DISPLAY = "cards_to_display";
    private static final String HAND_TOTAL = "hand_total";
    private static final String TAG = "Dealer";
    private List<Pair<String, String>> dealerCards = new ArrayList<>();
    private Context context;
    private DealerFragmentUpdater updater;
    private int insuredAmount = 0;

    public Dealer(Context ctx, DealerFragmentUpdater fragmentUpdater) {
        this.context = ctx;
        dealerCards.add(BlackjackDeckSimulator.getDeckSimulator(context).getRandomCardFromDeck());
        updater = fragmentUpdater;
        updateFragment();
    }

    public boolean canBeInsured() {
        return (dealerCards.size() == 1 && super.getHandTotal(dealerCards) == 11);
    }

    private void updateFragment() {
        BlackjackHandFragment dealerHandOnScreen = new BlackjackHandFragment();
        dealerHandOnScreen.setListOfCards(dealerCards);
        dealerHandOnScreen.setScore(super.getHandTotal(dealerCards));
        updater.onDealerFragmentReplaceNeeded(dealerHandOnScreen);
    }

    public void playForResult() {
        int handTotal = super.getHandTotal(dealerCards);
        BlackjackDeckSimulator deckSimulator = BlackjackDeckSimulator.getDeckSimulator(context);
        while (handTotal < 17) {
            dealerCards.add(deckSimulator.getRandomCardFromDeck());
            handTotal = super.getHandTotal(dealerCards);
        }

        updateFragment();
    }

    public int getHandTotal() {
        return super.getHandTotal(dealerCards);
    }

    public void insure(int currentBet) {
        if (BlackjackBank.takeAmountFromBank(currentBet / 2)) {
            insuredAmount = currentBet / 2;
        } else {
            insuredAmount = BlackjackBank.emptyBank();
        }
    }

    public void payOutInsurance() {
        if (insuredAmount > 0 && super.getHandTotal(dealerCards) == 21 && dealerCards.size() == 2) {
            BlackjackBank.addAmountToBank(insuredAmount);
        }
    }

    public interface DealerFragmentUpdater {
        void onDealerFragmentReplaceNeeded(Fragment dealerHand);
    }
}
