package com.choicely.myapplication.citysearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.choicely.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityViewHolder> {

    private final Context context;
    private List<String> cities = new ArrayList<>();

    public CityListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CityViewHolder(LayoutInflater.from(context).inflate(R.layout.city_result_line, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        String cityName = cities.get(position);
        holder.cityTextView.setText(cityName);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void addCity(String cityName) {
        cities.add(cityName);
    }

    public void clear() {
        cities.clear();
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {
        public TextView cityTextView;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            cityTextView = itemView.findViewById(R.id.city_result_line_text);
        }
    }
}
