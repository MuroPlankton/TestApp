package com.muroplankton.testapp.randomlistgenerator;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StringListGenerator {

    private static final String TAG = "StringListGenerator";
    public static List<String> randomStrings = new ArrayList<>();

    public static void clearAndGenerateStrings() {
        new Thread(() -> {
            randomStrings.clear();
            long startTime = System.currentTimeMillis();
            for (int i = 0; i <1000000; i++) {
                randomStrings.add(UUID.randomUUID().toString());
            }
            Log.d(TAG, "" + (System.currentTimeMillis() - startTime));
        }).start();
    }
}
