package com.muroplankton.testapp.receiptsaver;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.muroplankton.testapp.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import io.realm.Realm;

public class ReceiptActivity extends AppCompatActivity implements NoReceiptNameDialogFragment.NoticeDialogListener {

    private static final String TAG = "ReceiptActivity";
    private EditText nameEditText;
    private TextView dateTextView;
    private ImageButton datePickerButton;
    private ImageView receiptImageView;

    private String receiptID;
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
        ReceiptData receiptData = realm.where(ReceiptData.class)
                .equalTo("receiptID", receiptID).findFirst();
        nameEditText.setText(receiptData.getReceiptName());
        dateTextView.setText(receiptData.getReceiptDate());
        receiptUri = Uri.parse(receiptData.getReceiptUri());
        Glide.with(getApplicationContext()).load(receiptUri).into(receiptImageView);
    }

    private void captureNewReceipt() {
        ActivityResultContracts.TakePicture takePictureContract = new ActivityResultContracts.TakePicture();
        File receiptFile = createReceiptFile();
        receiptUri = FileProvider.getUriForFile(this, "com.muroplankton.android.fileprovider", receiptFile);

        ActivityResultLauncher<Uri> receiptCaptureLauncher = registerForActivityResult(
                takePictureContract, result -> {
                    if (!result) {
                        super.onBackPressed();
                    }
                    dateTextView.setText(new SimpleDateFormat("dd.MM.yyyy")
                            .format(Calendar.getInstance().getTime()));
                    Glide.with(getApplicationContext()).load(receiptUri).into(receiptImageView);
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

        return image;
    }

    @Override
    public void onBackPressed() {
        if (nameEditText.getText().toString().isEmpty()) {
            NoReceiptNameDialogFragment receiptNameDialog = new NoReceiptNameDialogFragment();
            receiptNameDialog.show(getSupportFragmentManager(), "name missing");
        } else {
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

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Log.d(TAG, "onDialogPositiveClick: user wanted to name the receipt");
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        super.onBackPressed();
    }
}
