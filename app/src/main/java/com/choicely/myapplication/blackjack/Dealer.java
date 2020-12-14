package com.choicely.myapplication.blackjack;

import android.content.Context;
import android.util.Pair;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class Dealer extends CommonLogic {

    private static final String CARDS_TO_DISPLAY = "cards_to_display";
    private static final String HAND_TOTAL = "hand_total";
    private List<Pair<String, String>> dealerCards = new ArrayList<>();
    private Context context;
    private DealerFragmentUpdater updater;

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

    public String playForResult() {
        while (super.getHandTotal(dealerCards) < 17) {
            dealerCards.add(BlackjackDeckSimulator.getDeckSimulator(context).getRandomCardFromDeck());
        }

        updateFragment();

        return super.checkAgainstRules(dealerCards);
    }

    public int getHandTotal() {
        return super.getHandTotal(dealerCards);
    }

    public interface DealerFragmentUpdater {
        void onDealerFragmentReplaceNeeded(Fragment dealerHand);
    }

    //TODO: rulechecker that checks common rules first
}
