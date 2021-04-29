package com.muroplankton.testapp.imagegallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.muroplankton.testapp.R;

import io.realm.Realm;
import io.realm.RealmResults;

public class GalleryFragment extends Fragment {
    private static final String GALLERY_POS_NUMBER = "gallery_pos_number";

    private View view;
    private TextView imageUrlTitle;
    private Button deleteButton;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.gallery_item, container, false);

        imageUrlTitle = view.findViewById(R.id.gallery_item_title);
        deleteButton = view.findViewById(R.id.gallery_item_delete_button);
        imageView = view.findViewById(R.id.gallery_item_image);

        updateContent();

        return view;
    }

    private void updateContent() {
        int position = getArguments().getInt(GALLERY_POS_NUMBER, -1);

        Realm realm = Realm.getDefaultInstance();
        RealmResults<GalleryData> urls = realm.where(GalleryData.class).findAll();
        GalleryData url = urls.get(position);

        imageUrlTitle.setText(url.getUrlName());
        Glide.with(this).load(url.getUrl()).into(imageView);
    }
}
