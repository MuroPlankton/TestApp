package com.choicely.myapplication.blackjack;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class PlayerHandsAdapter extends FragmentStateAdapter {

    private static final String TAG = "PlayerHandsAdapter";

    private int activeHandIndex = 0;
    List<List<Pair<String, String>>> cardHands = new ArrayList<>();
    List<Integer> totalOfEachHand = new ArrayList<>();
    List<BlackjackHandFragment> handFragments = new ArrayList<>();

    public PlayerHandsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        BlackjackHandFragment playerHandInAdapter = new BlackjackHandFragment();
        playerHandInAdapter.setListOfCards(cardHands.get(position));
        playerHandInAdapter.setScore(totalOfEachHand.get(position));
        handFragments.add(playerHandInAdapter);
        return playerHandInAdapter;
    }

    public void addHand(List<Pair<String, String>> cardsOfHand, int totalScore) {
        cardHands.add(cardsOfHand);
        totalOfEachHand.add(totalScore);
        if (cardHands.size() - 1 != activeHandIndex) {
            if (cardHands.size() == handFragments.size()) {
                handFragments.get(handFragments.size() - 1).setActive(false);
            }
        }
    }

    public void updateHandBeingPlayed(List<Pair<String, String>> updatedCards, int newScore) {
        cardHands.set(activeHandIndex, updatedCards);
        totalOfEachHand.set(activeHandIndex, newScore);

        handFragments.get(activeHandIndex).setListOfCards(updatedCards);
        handFragments.get(activeHandIndex).setScore(newScore);
    }

    public void setActiveHand(int activeHand) {
        handFragments.get(activeHandIndex).setActive(false);
        activeHandIndex = activeHand;
        handFragments.get(activeHandIndex).setActive(true);
    }

    @Override
    public int getItemCount() {
        return cardHands.size();
    }
}
