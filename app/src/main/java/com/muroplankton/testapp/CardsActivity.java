package com.muroplankton.testapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.slider.Slider;

public class CardsActivity extends AppCompatActivity {

    private static final String TAG = "CardsActivity";

    private long startPosX, startPosY, updateX, updateY, locationX = 0, locationY = 132, secondPosX, secondPosY, secondUpdateX, secondUpdateY, locationTwoX = 0, locationTwoY = 257;
    private CardView cardOne, cardTwo;
    private Slider elevationSlide;
    private int cardSelected;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        elevationSlide = findViewById(R.id.activity_cards_slider);
        elevationSlide.addOnChangeListener(elevationSliderListener);
        elevationSlide.setValue((float) 0.3);

        cardOne = findViewById(R.id.activity_cards_first_card);
        cardOne.setOnTouchListener(firstCardListener);

        cardTwo = findViewById(R.id.activity_cards_second_card);
        cardTwo.setOnTouchListener(secondCardListener);
    }

    @SuppressLint("ClickableViewAccessibility")
    private final View.OnTouchListener firstCardListener = (ItemView, event) -> {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startPosX = (long) event.getRawX();
                startPosY = (long) event.getRawY();
                cardSelected = 1;
                elevationSlide.setValue(cardOne.getElevation() / 10);
                break;
            case MotionEvent.ACTION_MOVE:
                updateX = (long) (event.getRawX() - startPosX);
                updateY = (long) (event.getRawY() - startPosY);
                cardOne.setY(locationY + updateY);
                cardOne.setX(locationX + updateX);
                break;
            case MotionEvent.ACTION_UP:
                locationX += updateX;
                locationY += updateY;
                Log.d(TAG, "y height: " + cardOne.getY());
                break;
        }
        return true;
    };

    @SuppressLint("ClickableViewAccessibility")
    private final View.OnTouchListener secondCardListener = (ItemView, event) -> {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                secondPosX = (long) event.getRawX();
                secondPosY = (long) event.getRawY();
                cardSelected = 2;
                elevationSlide.setValue(cardTwo.getElevation() / 10);
                break;
            case MotionEvent.ACTION_MOVE:
                secondUpdateX = (long) (event.getRawX() - secondPosX);
                secondUpdateY = (long) (event.getRawY() - secondPosY);
                cardTwo.setY(locationTwoY + secondUpdateY);
                cardTwo.setX(locationTwoX + secondUpdateX);
                break;
            case MotionEvent.ACTION_UP:
                locationTwoX += secondUpdateX;
                locationTwoY += secondUpdateY;
                break;
        }
        return true;
    };

    private final Slider.OnChangeListener elevationSliderListener = ((slider, value, fromUser) -> {
        if (cardSelected == 1) {
            cardOne.setElevation(elevationSlide.getValue() * 10);
        } else if (cardSelected == 2) {
            cardTwo.setElevation(elevationSlide.getValue() * 10);
        }
    });
}
