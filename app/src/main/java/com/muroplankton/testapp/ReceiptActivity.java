package com.muroplankton.testapp;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import io.realm.Realm;

public class ReceiptActivity extends AppCompatActivity {

    private EditText nameEditText;
    private TextView dateTextView;
    private Button datePickerButton;
    private ImageView receiptImageView;

    private String receiptID;
    private String currentReceiptPhotoPath;
    private Realm realm = Realm.getDefaultInstance();
    private Uri receiptUri;

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dateTextView.setText(String.format("%d.%d.%d", dayOfMonth, month + 1, year));
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(ReceiptActivity.this, dateSetListener,
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).
                    show();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        nameEditText = findViewById(R.id.activity_receipt_edit_name);
        dateTextView = findViewById(R.id.activity_receipt_date_view);
        datePickerButton = findViewById(R.id.activity_receipt_date_button);
        datePickerButton.setOnClickListener(onClickListener);
        receiptImageView = findViewById(R.id.activity_receipt_image);

        receiptID = getIntent().getStringExtra("receipt_id");

        if (receiptID == null) {
            receiptID = UUID.randomUUID().toString();
            captureNewReceipt();
        } else {
            loadReceipt();
        }
    }

    private void loadReceipt() {
        //TODO: load the receipt
    }

    private void captureNewReceipt() {
        ActivityResultContracts.TakePicture takePictureContract = new ActivityResultContracts.TakePicture();
        File receiptFile = createReceiptFile();
        receiptUri = FileProvider.getUriForFile(this, "com.muroplankton.android.fileprovider", receiptFile);

        ActivityResultLauncher<Uri> receiptCaptureLauncher = registerForActivityResult(
                takePictureContract, result -> {
                    dateTextView.setText(new SimpleDateFormat("dd.MM.yyyy")
                            .format(Calendar.getInstance().getTime()));
                    displayReceiptImage(receiptUri);
                });
        receiptCaptureLauncher.launch(receiptUri);
    }

    private File createReceiptFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "RECEIPT_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        currentReceiptPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void displayReceiptImage(Uri receiptImage) {
        Glide.with(getApplicationContext()).load(receiptImage).into(receiptImageView);
    }

    @Override
    public void onBackPressed() {
        ReceiptData receiptData = realm.where(ReceiptData.class).equalTo("receiptID", receiptID).findFirst();
        realm.beginTransaction();
        if (receiptData == null) {
            receiptData = new ReceiptData();
            receiptData.setReceiptID(receiptID);
        }
        receiptData.setReceiptName(nameEditText.getText().toString());
        receiptData.setReceiptDate(dateTextView.getText().toString());
        receiptData.setReceiptUri(receiptUri.toString());
        realm.insertOrUpdate(receiptData);
        realm.commitTransaction();
        super.onBackPressed();
    }
}
