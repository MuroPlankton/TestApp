package com.choicely.myapplication.receiptsaver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.choicely.myapplication.R;

import java.io.File;

import io.realm.Realm;

public class SingleReceiptActivity extends AppCompatActivity {

    private static final String INTENT_RECEIPT_ID = "intent_receipt_id";

    private TextView title, date;
    private ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_receipt);

        title = findViewById(R.id.activity_single_receipt_title);
        date = findViewById(R.id.activity_single_receipt_date);
        image = findViewById(R.id.activity_single_receipt_image);

        loadReceipt();
    }

    private void loadReceipt() {
        Realm realm = Realm.getDefaultInstance();
        long id = getIntent().getLongExtra(INTENT_RECEIPT_ID, -1);
        if (id == -1) {
            title.setText(R.string.no_receipt_passed);
        } else {
            ReceiptData data = realm.where(ReceiptData.class).equalTo("id", id).findFirst();
            title.setText(data.getTitle());
            date.setText(data.getDate());
            File receiptImage = new File(data.getImagePath());

            if (receiptImage.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(receiptImage.getAbsolutePath());
                image.setImageBitmap(bitmap);
            }
        }
    }
}
