package com.muroplankton.testapp.blackjack;

public class BlackjackBank {

    private static int moneyInBank;

    public static int getMoneyInBank() {
        return moneyInBank;
    }

    public static void setMoneyInBank(int moneyInBank) {
        BlackjackBank.moneyInBank = moneyInBank;
    }

    public static void addAmountToBank(int amount) {
        moneyInBank += amount;
    }

    public static boolean takeAmountFromBank(int amount) {
        if (moneyInBank - amount >= 0) {
            moneyInBank -= amount;
            return true;
        }
        return false;
    }

    public static int emptyBank() {
        int moneyToReturn = moneyInBank;
        moneyInBank = 0;
        return moneyToReturn;
    }
}
