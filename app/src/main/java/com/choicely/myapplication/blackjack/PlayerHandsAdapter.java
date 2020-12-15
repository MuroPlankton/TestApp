package com.choicely.myapplication.blackjack;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class PlayerHandsAdapter extends FragmentStateAdapter {

    private static final int INDEX_OF_ACTIVE_HAND = 0;

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
        return playerHandInAdapter;
    }

    public void addHand(List<Pair<String, String>> cardsOfHand, int totalScore) {
        cardHands.add(cardsOfHand);
        totalOfEachHand.add(totalScore);
        notifyDataSetChanged();
    }

    public void updateHandBeingPlayed(List<Pair<String, String>> updatedCards, int newScore) {
        cardHands.set(INDEX_OF_ACTIVE_HAND, updatedCards);
        totalOfEachHand.set(INDEX_OF_ACTIVE_HAND, newScore);
        notifyDataSetChanged();
    }

    public void standActiveHand() {
        cardHands.remove(INDEX_OF_ACTIVE_HAND);
        totalOfEachHand.remove(INDEX_OF_ACTIVE_HAND);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cardHands.size();
    }
}
