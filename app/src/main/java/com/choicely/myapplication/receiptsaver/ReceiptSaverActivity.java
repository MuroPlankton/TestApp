package com.choicely.myapplication.receiptsaver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.choicely.myapplication.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import io.realm.Realm;

public class ReceiptSaverActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TAG = "ReceiptSaverActivity";

    private LinearLayout linearLayout;
    private EditText editTitle;
    private DatePicker datePicker;
    private Button saveButton;

    private String photoSavePath;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_saver);

        editTitle = findViewById(R.id.activty_receipt_saver_edit_title);
        datePicker = findViewById(R.id.activty_receipt_saver_date_picker);
        saveButton = findViewById(R.id.activty_receipt_saver_save_button);

        saveButton.setOnClickListener(v -> {
            Log.d(TAG, String.format("Date picked: %s.%s.%s", datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear()));
            if (editTitle.getText().length() != 0) {
                saveReceipt();
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, R.string.receipt_title_missing, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveReceipt() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        ReceiptData newReceipt = new ReceiptData();
        newReceipt.setId(UUID.randomUUID().getMostSignificantBits());
        newReceipt.setTitle(editTitle.getText().toString());
        String date = String.format("%s.%s.%s", datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear());
        newReceipt.setDate(date);
        newReceipt.setImagePath(photoSavePath);
        realm.insertOrUpdate(newReceipt);
        realm.commitTransaction();
    }

    private void dispatchTakePictureIntent() {
        Intent photographReceiptIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (photographReceiptIntent.resolveActivity(getPackageManager()) != null) {
            File receiptFile = null;
            receiptFile = createReceiptFile();

            if (receiptFile != null) {
                Uri receiptUri = FileProvider.getUriForFile(this, "com.choicely.myapplication.fileprovider", receiptFile);
                photographReceiptIntent.putExtra(MediaStore.EXTRA_OUTPUT, receiptUri);
                startActivityForResult(photographReceiptIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createReceiptFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File receipt = null;
        try {
            receipt = File.createTempFile("newReceipt", ".jpg", storageDirectory);
        } catch (IOException e) {
            Log.w(TAG, e.toString());
        }

        photoSavePath = receipt.getAbsolutePath();
        return receipt;
    }
}
