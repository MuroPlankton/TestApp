package com.choicely.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabsAdapter extends FragmentStateAdapter {
    public TabsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        TabFragment fragment = new TabFragment();
        Bundle data = new Bundle();
        data.putInt("fragment_pos_number", position);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
