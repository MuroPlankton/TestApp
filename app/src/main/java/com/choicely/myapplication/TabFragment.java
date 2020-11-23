package com.choicely.myapplication;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TabFragment extends Fragment {

    private static final String TAG = "TabFragment";

    private TextView title, text;
    private ImageView image;
    private RelativeLayout layout;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_test_fragment, container, false);

        layout = view.findViewById(R.id.activity_test_fragment_layout);
        title = view.findViewById(R.id.activity_test_fragment_title);
        image = view.findViewById(R.id.activity_test_fragment_image);
        text = view.findViewById(R.id.activity_test_fragment_text);

        updateContent();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        new TabLayoutMediator(TabsActivity.tabLayout, TabsActivity.viewPager2, (tab, position) -> tab.setText("Tab " + position)).attach();
    }

    private void updateContent() {
        int position = getArguments().getInt("fragment_pos_number", -1);

        switch (position) {
            case 0:
                title.setText(R.string.fragment_one_title);
                image.setImageResource(R.drawable.cool_view);
                text.setText(R.string.fragment_one_text);
                layout.setBackgroundResource(R.color.light_purple);
                title.setTextColor(getResources().getColor(R.color.black));
                text.setTextColor(getResources().getColor(R.color.black));
                break;
            case 1:
                title.setText(R.string.fragment_bankka_title);
                image.setImageResource(R.drawable.bankka);
                text.setText(R.string.bankka_text);
                layout.setBackgroundResource(R.color.light_yellow);
                title.setTextColor(getResources().getColor(R.color.black));
                text.setTextColor(getResources().getColor(R.color.black));
                break;
            case 2:
                title.setText(R.string.fragment_bee_title);
                image.setImageResource(R.drawable.bee);
                text.setText(R.string.fragment_bee_text);
                layout.setBackgroundResource(R.color.black);
                title.setTextColor(getResources().getColor(R.color.white));
                text.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }
}

