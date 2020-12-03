package com.choicely.myapplication.blackjack;

public class BlackjackBank {

    public static int moneyInBank;

    public static int getMoneyInBank() {
        return moneyInBank;
    }

    public static void setMoneyInBank(int moneyInBank) {
        BlackjackBank.moneyInBank = moneyInBank;
    }

    public void addAmountToBank(int amount) {
        moneyInBank += amount;
    }

    public static boolean takeAmountFromBank(int amount) {
        if (moneyInBank - amount >= 0) {
            moneyInBank -= amount;
            return true;
        }
        return false;
    }
}
