package com.muroplankton.testapp.blackjack;

import android.os.Bundle;
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

import com.muroplankton.testapp.R;

import java.util.ArrayList;
import java.util.List;

public class BlackjackHandFragment extends Fragment {

    private static final String TAG = "BlackJackHandFragment";
    private static final String CARDS_TO_DISPLAY = "cards_to_display";
    private static final String HAND_TOTAL = "hand_total";
    private View handView;
    private RecyclerView cardsRecycler;
    private BlackjackCardsInHandAdapter adapter = new BlackjackCardsInHandAdapter();
    private TextView scoreText;
    private List<Pair<String, String>> hand = new ArrayList<>();

    private List<Pair<String, String>> cards = new ArrayList<>();
    private int score;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        handView = inflater.inflate(R.layout.blackjack_hand, container, false);

        cardsRecycler = handView.findViewById(R.id.blackjack_hand_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.HORIZONTAL, false);
        cardsRecycler.setLayoutManager(manager);
        cardsRecycler.setAdapter(adapter);

        adapter.replaceCards(cards);

        scoreText = handView.findViewById(R.id.blackjack_hand_score);
        scoreText.setText(Integer.toString(score));
        scoreText.setTextColor(inflater.getContext().getResources().getColor(R.color.black));

        return handView;
    }

    public void setListOfCards(List<Pair<String, String>> cards) {
        this.cards = cards;
        if (adapter != null) {
            adapter.replaceCards(cards);
        }
    }

    public void setScore(int handTotal) {
        this.score = handTotal;
        if (scoreText != null) {
            scoreText.setText(Integer.toString(handTotal));
        }
    }
}
