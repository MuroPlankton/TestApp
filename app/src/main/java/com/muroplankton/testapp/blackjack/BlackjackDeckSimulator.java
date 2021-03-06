package com.muroplankton.testapp.blackjack;

import android.content.Context;
import android.util.Pair;

import com.muroplankton.testapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BlackjackDeckSimulator {
    private static final String TAG = "BlackjackDeckSimulator";
    private List<Map<String, List<String>>> cardDecks = new ArrayList<>();
    private int totalCards;
    private Context context;
    private static BlackjackDeckSimulator deckSimulator;

    private BlackjackDeckSimulator(Context ctx) {
        context = ctx;
    }

    public static BlackjackDeckSimulator getDeckSimulator(Context ctx) {
        if (deckSimulator == null) {
            deckSimulator = new BlackjackDeckSimulator(ctx.getApplicationContext());
            return deckSimulator;
        }
        return deckSimulator;
    }

    public void generateDecks() {
        for (int deckIndex = 0; deckIndex < 6; deckIndex++) {
            cardDecks.add(generateSingleDeck());
        }
    }

    private Map<String, List<String>> generateSingleDeck() {
        Map<String, List<String>> cardDeck = new HashMap<>();
        String[] suites = context.getResources().getStringArray(R.array.french_suits);
        for (String suite : suites) {
            cardDeck.put(suite, generateCardsForSuite());
        }
        return cardDeck;
    }

    private List<String> generateCardsForSuite() {
        List<String> cardsAsList = new ArrayList<>();
        String[] cardsAsArray = context.getResources().getStringArray(R.array.cards);
        for (String card : cardsAsArray) {
            cardsAsList.add(card);
            totalCards++;
        }
        return cardsAsList;
    }

    public void clearDecks() {
        cardDecks.clear();
    }

    public Pair<String, String> getRandomCardFromDeck() {
        Random random = new Random();
        int deckToGetFrom = random.nextInt(cardDecks.size());
        Map<String, List<String>> deck = cardDecks.remove(deckToGetFrom);

        int suiteToGetFrom = -1;
        List<String> suiteNames = Arrays.asList(context.getResources().getStringArray(R.array.french_suits));
        String suiteName = null;
        while (deck.get(suiteName) == null) {
            if (suiteToGetFrom == -1) {
                suiteToGetFrom = random.nextInt(deck.size());
            } else {
                suiteToGetFrom = (suiteToGetFrom == deck.size()) ? suiteToGetFrom = 0 : suiteToGetFrom + 1;
            }
            suiteName = suiteNames.get(suiteToGetFrom);
        }
        List<String> cardsInSelectedSuite = deck.remove(suiteName);

        int randomCardIndex = random.nextInt(cardsInSelectedSuite.size());
        String selectedCard = cardsInSelectedSuite.remove(randomCardIndex);

        deck.put(suiteName, cardsInSelectedSuite);
        cardDecks.add(deck);

        if (cardsInSelectedSuite.size() < 1) {
            deck.remove(suiteName);
            if (deck.size() < 1) {
                cardDecks.remove(deckToGetFrom);
            }
        }
        totalCards--;

        return new Pair(suiteName, selectedCard);
    }

    public int getTotalCards() {
        return totalCards;
    }
}
