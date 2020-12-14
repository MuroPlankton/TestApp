package com.choicely.myapplication.blackjack;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class PlayerHandsAdapter extends FragmentStateAdapter {

    List<List<Pair<String, String>>> cardHands = new ArrayList<>();
    List<BlackjackHandFragment> handFragments = new ArrayList<>();

    public PlayerHandsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        BlackjackHandFragment playerHandFragment;
        return null;
    }

    public void updateHand(List<Pair<String, String>> cards, int totalScore, int position) {

    }

    @Override
    public int getItemCount() {
        return cardHands.size();
    }
}
