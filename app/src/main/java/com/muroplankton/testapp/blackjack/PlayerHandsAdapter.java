package com.muroplankton.testapp.blackjack;

import android.content.Context;
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
    private List<List<Pair<String, String>>> cardHands = new ArrayList<>();
    private List<Integer> totalOfEachHand = new ArrayList<>();
    private List<BlackjackHandFragment> handFragments = new ArrayList<>();
    private Context context;

    public PlayerHandsAdapter(@NonNull FragmentActivity fragmentActivity, Context ctx) {
        super(fragmentActivity);
        context = ctx;
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
    }

    public void updateHandBeingPlayed(List<Pair<String, String>> updatedCards, int newScore) {
        cardHands.set(activeHandIndex, updatedCards);
        totalOfEachHand.set(activeHandIndex, newScore);

        handFragments.get(activeHandIndex).setListOfCards(updatedCards);
        handFragments.get(activeHandIndex).setScore(newScore);
    }

    @Override
    public int getItemCount() {
        return cardHands.size();
    }
}
