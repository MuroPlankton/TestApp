package com.choicely.myapplication.receiptsaver;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.choicely.myapplication.R;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;

public class ReceiptActivity extends AppCompatActivity {

    private static final String TAG = "ReceiptActivity";
    private Button newButton;
    private EditText nameSearch;
    private Button nameSearchButton, dateSearch;
    private RecyclerView receiptRecycler;
    private ReceiptListAdapter adapter;
    private String searchQuery;

    private int year, month, day;

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.activity_receipt_new_receipt_button:
                startActivity(new Intent(this, ReceiptSaverActivity.class));
                break;
            case R.id.activity_receipt_name_search_button:
                String searchQuery = nameSearch.getText().toString();
                if (searchQuery.length() < 1) {
                    Toast.makeText(getApplicationContext(), "No receipts found with specified name.", Toast.LENGTH_LONG).show();
                } else {
                    getAndUpdateReceipts(1);
                }
                break;
            case R.id.activity_receipt_date_search:
                getAndUpdateReceipts(2);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        newButton = findViewById(R.id.activity_receipt_new_receipt_button);
        newButton.setOnClickListener(onClickListener);

        nameSearch = findViewById(R.id.activity_receipt_name_search);
        nameSearchButton = findViewById(R.id.activity_receipt_name_search_button);
        nameSearchButton.setOnClickListener(onClickListener);
        dateSearch = findViewById(R.id.activity_receipt_date_search);
        dateSearch.setOnClickListener(onClickListener);

        receiptRecycler = findViewById(R.id.activity_receipt_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        receiptRecycler.setLayoutManager(linearLayoutManager);

        adapter = new ReceiptListAdapter(this);
        receiptRecycler.setAdapter(adapter);

        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        getAndUpdateReceipts(0);
    }

    private void getAndUpdateReceipts(int receiptLimiter) {
        adapter.clearReceipts();

        Realm realm = Realm.getDefaultInstance();
        RealmResults<ReceiptData> receipts;

        if (receiptLimiter == 0) {
            receipts = realm.where(ReceiptData.class).findAll();
        } else {
            String fieldName = (receiptLimiter == 1) ? "title" : "date";
            if (receiptLimiter == 1) {
                searchQuery = nameSearch.getText().toString();
            } else {
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        searchQuery = String.format("%s.%s.%s", dayOfMonth, month, year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
            receipts = realm.where(ReceiptData.class).equalTo(fieldName, searchQuery).findAll();
        }

        Log.d(TAG, receipts.toString());

        for (ReceiptData data : receipts) {
            adapter.addReceipt(data);
        }

        adapter.notifyDataSetChanged();
    }
}
