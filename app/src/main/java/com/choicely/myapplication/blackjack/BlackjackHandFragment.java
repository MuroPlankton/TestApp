package com.choicely.myapplication.blackjack;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.choicely.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class BlackjackHandFragment extends Fragment {

    private static final String TAG = "BlackJackHandFragment";
    private View handView;
    private RecyclerView cardsRecycler;
    private BlackjackCardsInHandAdapter adapter = new BlackjackCardsInHandAdapter();
    private TextView score;
    private List<Pair<String, String>> hand = new ArrayList<>();
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        handView = inflater.inflate(R.layout.blackjack_hand, container, false);
        context.getApplicationContext();
        cardsRecycler = handView.findViewById(R.id.blackjack_hand_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        cardsRecycler.setLayoutManager(manager);
        cardsRecycler.setAdapter(adapter);
        Log.d(TAG, "Adapter set to recycler");
        score = handView.findViewById(R.id.activity_hand_score);

        return handView;
    }

    public void addCard(Pair<String, String> card) {
        adapter.addCard(card);
    }

    public void setTotalOnScreen(int total) {
        score.setText(total);
    }
}
