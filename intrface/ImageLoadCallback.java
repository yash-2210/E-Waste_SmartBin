package com.example.smartbin.intrface;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

public interface ImageLoadCallback {

    void onLoaded(@NonNull Drawable drawable);

}