package com.muroplankton.testapp.imagegallery;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.muroplankton.testapp.R;

import java.util.UUID;

import io.realm.Realm;

public class NameUrlActivity extends AppCompatActivity {

    private EditText editTitle;
    private ImageView image;
    private Button saveButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_url);

        editTitle = findViewById(R.id.activity_name_url_edit_text);

        image = findViewById(R.id.activity_name_url_image);
        String url = getIntent().getStringExtra("URL");
        Glide.with(this).load(url).into(image);

        saveButton = findViewById(R.id.activity_name_url_save_button);
        saveButton.setOnClickListener(v -> {
            if (editTitle.getText().length() < 1) {
                Toast.makeText(this, R.string.url_title_missing, Toast.LENGTH_SHORT).show();
            } else {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                GalleryData data = new GalleryData();
                data.setId(UUID.randomUUID().getMostSignificantBits());
                data.setUrl(url);
                data.setUrlName(editTitle.getText().toString());
                realm.insertOrUpdate(data);
                realm.commitTransaction();
            }
        });
    }
}
