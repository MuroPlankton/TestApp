package com.choicely.myapplication.blackjack;

import android.util.Pair;

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
}
