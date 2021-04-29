package com.muroplankton.testapp.sulkeiset;

import com.muroplankton.testapp.R;

import java.util.ArrayList;
import java.util.List;

public class LiikenneValot {

    private List<Valo> valoList = new ArrayList<>();
    private int selected;
    LightUpdateCallback updateCallback;


    public LiikenneValot(LightUpdateCallback callback) {
        valoList.add(new Valo(true, R.color.red));
        valoList.add(new Valo(true, R.color.yellow));
        valoList.add(new Valo(true, R.color.green));
        valoList.add(new Valo(true, R.color.blue));
        valoList.add(new Valo(true, R.color.light_purple));
        selected = 0;
        updateCallback = callback;
    }

    public Valo getSelectdLight(int index) {
        return valoList.get(index);
    }

    public void selectNextLight() {
        switch (selected) {
            default:
            case 0:
            case 1:
            case 2:
            case 3:
                selected++;
                break;
            case 4:
                selected = 0;
                break;
        }
        updateCallback.onLightChanged(selected);
    }

    public interface LightUpdateCallback {
        public void onLightChanged(int selected);
    }

    public List<Valo> getValoList() {
        return valoList;
    }
}
