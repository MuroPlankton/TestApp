package com.muroplankton.testapp;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

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
import java.util.Date;

public class ReceiptActivity extends AppCompatActivity {

    private String currentReceiptPhotoPath;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        imageView = findViewById(R.id.activity_receipt_image);

        ActivityResultContracts.TakePicture takePictureContract = new ActivityResultContracts.TakePicture();
        File receiptFile = createReceiptFile();
        Uri receiptUri = FileProvider.getUriForFile(this, "com.muroplankton.android.fileprovider", receiptFile);

        ActivityResultLauncher<Uri> receiptCaptureLauncher = registerForActivityResult(takePictureContract,
                result -> Glide.with(getApplicationContext()).load(receiptUri).into(imageView));
        receiptCaptureLauncher.launch(receiptUri);
    }

    private File createReceiptFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "RECEIPT_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        currentReceiptPhotoPath = image.getAbsolutePath();
        return image;
    }
}
