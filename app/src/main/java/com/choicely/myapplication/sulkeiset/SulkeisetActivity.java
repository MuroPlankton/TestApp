package com.choicely.myapplication.sulkeiset;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.choicely.myapplication.R;

public class SulkeisetActivity extends AppCompatActivity implements LiikenneValot.TrafficLightInterface {

    private LiikenneValot valot = new LiikenneValot(0, this);
    private View red, yellow, green;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sulkeiset);

        red = findViewById(R.id.activity_sulkeiset_punainen);
        yellow = findViewById(R.id.activity_sulkeiset_keltainen);
        green = findViewById(R.id.activity_sulkeiset_vihreä);
    }

    public void showToast(View view) {
        valot.nextStatus();
    }

    @Override
    public void lightCallback(int newStatus) {
        Toast.makeText(this, "Status changed.", Toast.LENGTH_SHORT).show();
        red.setBackgroundColor(getResources().getColor(R.color.white));
        yellow.setBackgroundColor(getResources().getColor(R.color.white));
        green.setBackgroundColor(getResources().getColor(R.color.white));
        switch (newStatus) {
            case 0:
                red.setBackgroundColor(getResources().getColor(R.color.red));
                break;
            case 1:
                yellow.setBackgroundColor(getResources().getColor(R.color.yellow));
                break;
            case 2:
                green.setBackgroundColor(getResources().getColor(R.color.green));
        }
    }
}
