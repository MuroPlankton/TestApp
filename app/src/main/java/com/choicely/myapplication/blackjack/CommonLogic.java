package com.choicely.myapplication.blackjack;

import android.app.Application;
import android.util.Pair;

import com.choicely.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CommonLogic {

    public int getHandTotal(List<Pair<String, String>> handOfCards) {
        int handTotal = 0, valueToAdd;
        int amountOfAces = 0;
        for (Pair card : handOfCards) {
            try {
                valueToAdd = Integer.parseInt(card.second.toString());
            } catch (NumberFormatException e) {
                switch (card.second.toString()) {
                    default:
                    case "Ace":
                        valueToAdd = 11;
                        amountOfAces++;
                        break;
                    case "Jack":
                    case "Queen":
                    case "King":
                        valueToAdd = 10;
                        break;
                }
            }

            handTotal += valueToAdd;
            if (handTotal > 21) {
                if (amountOfAces > 0) {
                    handTotal -= 10;
                    amountOfAces--;
                } else if (valueToAdd == 11) {
                    handTotal -= 10;
                }
            }
        }
        return handTotal;
    }

    protected String checkAgainstRules(List<Pair<String, String>> hand) {
        int total = getHandTotal(hand);

        if (total < 21) {
            return "continue";
        } else if (total > 21) {
            return "bust";
        }

        if (total == 21 && hand.size() == 2) {
            return "blackjack";
        }

        return "done";
    }
}
