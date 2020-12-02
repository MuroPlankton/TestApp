package com.choicely.myapplication.imagegallery;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.choicely.myapplication.R;

public class CheckUrlActivity  extends AppCompatActivity {

    private EditText editURL;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_url);

        editURL = findViewById(R.id.activity_check_url_edit_text);
        button = findViewById(R.id.activity_check_url_button);

        button.setOnClickListener(v -> {
            String url = editURL.getText().toString();
            if (URLUtil.isValidUrl(url)) {
                Intent intent = new Intent(this, NameUrlActivity.class);
                intent.putExtra("URL", url);
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.url_not_valid, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
