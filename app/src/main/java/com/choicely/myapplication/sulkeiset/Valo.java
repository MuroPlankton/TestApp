package com.choicely.myapplication.sulkeiset;

import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;

public class Valo {
    private boolean isLit;
    @ColorRes
    private int color;

    public Valo(boolean isLit, int color) {
        this.isLit = isLit;
        this.color = color;


    }

    public boolean isLit() {
        return isLit;
    }

    public void setLit(boolean lit) {
        isLit = lit;
    }

    @ColorRes
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
