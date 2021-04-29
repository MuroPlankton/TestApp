package com.muroplankton.testapp.sulkeiset;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.muroplankton.testapp.R;

import java.util.ArrayList;
import java.util.List;

public class SulkeisetActivity extends AppCompatActivity {

    private LiikenneValot valot;
    private List<View> lightViews = new ArrayList<>();
    private LiikenneValot.LightUpdateCallback callback = selected -> {
        for (View view : lightViews) {
            view.setBackgroundColor(getResources().getColor(R.color.white));
        }

        View activatedView = lightViews.get(selected);
        Valo valo = valot.getSelectdLight(selected);
        activatedView.setBackgroundColor(getResources().getColor(valo.getColor()));
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sulkeiset);

        ViewGroup root = findViewById(R.id.sulkeiset_root);

        valot = new LiikenneValot(callback);

        for (Valo valo : valot.getValoList()) {
            View view = new View(this);
            view.setLayoutParams(new LinearLayout.LayoutParams(200,200));
            root.addView(view);
            lightViews.add(view);
            view.setBackgroundColor(getResources().getColor(valo.getColor()));

        }
    }

    public void changeLight(View view) {
        valot.selectNextLight();
    }
}
