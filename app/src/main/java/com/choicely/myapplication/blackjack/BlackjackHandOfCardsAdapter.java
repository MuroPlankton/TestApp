package com.choicely.myapplication.blackjack;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.choicely.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class BlackjackHandOfCardsAdapter extends RecyclerView.Adapter<BlackjackHandOfCardsAdapter.SingleCardViewHolder> {

    private final Context context;
    private List<Pair<String, String>> cards = new ArrayList<>();

    public BlackjackHandOfCardsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SingleCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SingleCardViewHolder(LayoutInflater.from(context).inflate(R.layout.blackjack_single_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SingleCardViewHolder holder, int position) {
        Pair<String, String> card = cards.get(position);
        holder.cardValueText.setText(card.second);
        switch (card.first) {
            default:
            case "Clubs":
                holder.cardSuiteVector.setImageResource(R.drawable.ic_card_club);
                break;
            case "Diamonds":
                holder.cardSuiteVector.setImageResource(R.drawable.ic_card_diamond);
                break;
            case "Hearts":
                holder.cardSuiteVector.setImageResource(R.drawable.ic_card_heart);
                break;
            case "Spades":
                holder.cardSuiteVector.setImageResource(R.drawable.ic_card_spade);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void addCard(Pair<String, String> newCard) {
        cards.add(newCard);
    }

    public void removeCard(int index) {
        cards.remove(index);
    }

    public class SingleCardViewHolder extends RecyclerView.ViewHolder {
        public TextView cardValueText;
        public ImageView cardSuiteVector;


        public SingleCardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardValueText = itemView.findViewById(R.id.blackjack_single_card_value);
            cardSuiteVector = itemView.findViewById(R.id.blackjack_single_card_vector);
        }
    }
}
