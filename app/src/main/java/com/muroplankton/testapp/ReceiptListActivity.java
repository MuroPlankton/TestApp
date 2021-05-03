package com.muroplankton.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.Realm;

public class ReceiptListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReceiptsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_list);

        recyclerView = findViewById(R.id.receipt_list_recycler);
        adapter = new ReceiptsAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        updateContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateContent();
    }

    private void updateContent() {
        adapter.clearReceipts();
        Realm realm = Realm.getDefaultInstance();
        adapter.setReceipts(realm.copyFromRealm(realm.where(ReceiptData.class).findAll()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.receipt_saver_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.receipt_list_activity_menu_new) {
            startActivity(new Intent(this, ReceiptActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
