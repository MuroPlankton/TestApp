package com.muroplankton.testapp.timer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.muroplankton.testapp.R;

import java.util.ArrayList;
import java.util.List;

public class TimerActivity extends AppCompatActivity {

    private static final String TAG = "TimerActivity";
    private TextView hoursText, minutesText, secondsText;
    private Button controlButton;
    private boolean isTimerRunning = false;
    long time;
    private CountDownTimer countDownTimer;

    private View.OnClickListener onClickListener = v -> {
        List<Integer> digits = getDigitsOnScreen();

        switch (v.getId()) {
            default:
            case R.id.activity_timer_hours:
                hoursText.setText(advanceDigit(digits.get(2), 24));
                break;
            case R.id.activity_timer_minutes:
                minutesText.setText(advanceDigit(digits.get(1), 59));
                break;
            case R.id.activity_timer_seconds:
                secondsText.setText(advanceDigit(digits.get(0), 59));
                break;
        }

        updateTimeValue();
        if (!isTimerRunning && time > 0) {
            controlButton.setEnabled(true);
        } else if (isTimerRunning) {
            countDownTimer.cancel();
            controlButton.setText(R.string.timer_start);
            isTimerRunning = false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        hoursText = findViewById(R.id.activity_timer_hours);
        hoursText.setText("00");
        minutesText = findViewById(R.id.activity_timer_minutes);
        minutesText.setText("00");
        secondsText = findViewById(R.id.activity_timer_seconds);
        secondsText.setText("00");

        hoursText.setOnClickListener(onClickListener);
        minutesText.setOnClickListener(onClickListener);
        secondsText.setOnClickListener(onClickListener);

        controlButton = findViewById(R.id.activity_timer_button);
        controlButton.setText(R.string.timer_start);
        controlButton.setOnClickListener(v -> {
            controlButton.setText((isTimerRunning) ? R.string.timer_start : R.string.stop_timer);
            if (isTimerRunning) {
                countDownTimer.cancel();
                hoursText.setText("00");
                minutesText.setText("00");
                secondsText.setText("00");
                controlButton.setEnabled(false);
                isTimerRunning = false;
            } else {
                startTimer();
                isTimerRunning = true;
            }
        });
    }

    private void updateTimeValue() {
        List<Integer> digits = getDigitsOnScreen();
        time = digits.get(2) * 60 * 60 * 1000 + digits.get(1) * 60 * 1000 + digits.get(0) * 1000;
    }

    private String advanceDigit(int currentNumber, int maxNumber) {
        if (currentNumber >= maxNumber) {
            return "00";
        }

        currentNumber++;
        return (currentNumber < 10) ? "0" + currentNumber : "" + currentNumber;
    }

    private void startTimer() {
        updateTimeValue();
        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                lowerDigits();
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Time is up!", Toast.LENGTH_LONG).show();
                isTimerRunning = false;
                controlButton.setEnabled(false);
                controlButton.setText(R.string.timer_start);
            }
        };
        countDownTimer.start();
    }

    private void lowerDigits() {
        List<Integer> digits = getDigitsOnScreen();
        int seconds = digits.get(0);
        if (seconds < 1) {
            secondsText.setText("59");
            int minutes = digits.get(1);
            if (minutes < 1) {
                minutesText.setText("59");
                int hours = digits.get(2) - 1;
                hoursText.setText((hours < 10) ? "0" + hours : "" + hours);
            } else {
                minutesText.setText((minutes - 1 < 10) ? "0" + (minutes - 1) : "" + (minutes - 1));
            }
        } else {
            secondsText.setText((seconds - 1 < 10) ? "0" + (seconds - 1) : "" + (seconds - 1));
        }
    }

    private List<Integer> getDigitsOnScreen() {
        List<Integer> digits = new ArrayList<>();
        digits.add(Integer.parseInt(secondsText.getText().toString()));
        digits.add(Integer.parseInt(minutesText.getText().toString()));
        digits.add(Integer.parseInt(hoursText.getText().toString()));
        return digits;
    }
}
