package com.choicely.myapplication.citysearch;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.choicely.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class CitySearchActivity extends AppCompatActivity {

    private static final String TAG = "CitySearchActivity";
    private EditText editText;
    private RecyclerView cityNameRecycler;
    private boolean isScheduled = false;
    private Timer searchTimer;
    private CityListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search);

        editText = findViewById(R.id.activity_city_search_input);
        cityNameRecycler = findViewById(R.id.activity_city_search_results);
        adapter = new CityListAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        cityNameRecycler.setLayoutManager(linearLayoutManager);
        cityNameRecycler.setAdapter(adapter);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchDelayer();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void searchDelayer() {
        TimerTask searchTask = new TimerTask() {
            @Override
            public void run() {
                updateContent();
            }
        };
        if (!isScheduled) {
            isScheduled = true;
            searchTimer = new Timer();
            searchTimer.schedule(searchTask, 1000);
        } else {
            isScheduled = false;
            searchTimer.cancel();
            searchTimer.purge();
            searchDelayer();
        }
    }

    private void updateContent() {
        adapter.clear();

        String searchTerm = editText.getText().toString();
        String apiURL = String.format("https://geo-test.choicely.com/search/cities/%s?limit=10", searchTerm);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiURL, response -> {
            JSONArray cityArray = null;
            try {
                cityArray = response.getJSONArray("data");
                for (int i = 0; i < cityArray.length(); i++) {
                    JSONObject cityObject = cityArray.getJSONObject(i);
                    String cityName = cityObject.getString("full_name");
                    adapter.addCity(cityName);
                    adapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.e(TAG, "There was an error with the response"));

        RequestQueueSingleton.getRqSingleton(this).addToRequestQueue(jsonObjectRequest);
    }
}
