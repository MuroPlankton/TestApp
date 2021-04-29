package com.muroplankton.testapp;

import android.annotation.SuppressLint;
import android.opengl.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ImageMoveActivity extends AppCompatActivity {

    private final static String TAG = "ImageMoveActivity";

    private ImageView coolView;
    private long startPosX, startPosY, updateX, updateY, pivotX = 0, pivotY = 0;
    private Matrix matrix;
    private LinearLayout rotateActivity;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_move);

        coolView = findViewById(R.id.activity_image_move_image);
        rotateActivity = findViewById(R.id.activity_image_move_layout);

        rotateActivity.setOnTouchListener(coolViewTouchListener);
    }

    @SuppressLint("ClickableViewAccessibility")
    private final View.OnTouchListener coolViewTouchListener = (ItemView, event) -> {
        Log.d(TAG, "x: " + event.getX() + " y: " + event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startPosX = (long) event.getX();
                startPosY = (long) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                updateX = (long) (event.getX() - startPosX);
                updateY = (long) (event.getY() - startPosY);
                coolView.setRotationY(pivotX + updateX);
                coolView.setRotationX(pivotY - updateY);
                Log.d(TAG, "x change: " + updateX + " y change: " + updateY);
                break;
            case MotionEvent.ACTION_UP:
                pivotX += updateX;
                pivotY -= updateY;
                break;
        }
        return true;
    };
}
