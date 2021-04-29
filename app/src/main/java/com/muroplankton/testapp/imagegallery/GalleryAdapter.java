package com.muroplankton.testapp.imagegallery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import io.realm.Realm;
import io.realm.RealmResults;

public class GalleryAdapter extends FragmentStateAdapter {
    private static final String GALLERY_POS_NUMBER = "gallery_pos_number";
    private String imageFilter = "";

    public GalleryAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle data = new Bundle();
        data.putInt(GALLERY_POS_NUMBER, position);
        fragment.setArguments(data);
        return null;
    }

    @Override
    public int getItemCount() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults galleryPieces = realm.where(GalleryData.class).findAll();
        return galleryPieces.size();
    }

    public void setImageFilter(String selectedFilter) {
        imageFilter = selectedFilter;
    }
}
