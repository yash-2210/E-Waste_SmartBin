package com.example.smartbin.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.bumptech.glide.Glide;
import com.example.smartbin.MyApp;
import com.example.smartbin.R;
import com.example.smartbin.intrface.ImageLoadCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

public interface FileUtils {

    static byte[] convertFileToByteArray(@NonNull File file) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    static File createImageFile() {
        try {
            String imageFileName = "Profile_" + System.currentTimeMillis() + "_";
            File storageDir = MyApp.getApp().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            return File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    static String getFilePathFromIntentData(@NonNull Activity activity, @Nullable Intent data) {
        return data == null ? null : getFilePathFromURI(activity, data.getData());
    }

    static String getFilePathFromURI(@NonNull Activity activity, @Nullable Uri contentURI) {
        try {
            if (contentURI == null)
                throw new NullPointerException();
            ContentResolver contentResolver = activity.getContentResolver();
            if (contentResolver == null)
                throw new NullPointerException();

            Cursor cursor = contentResolver.query(contentURI,
                    null, null, null, null);
            if (cursor == null)
                return contentURI.getPath();
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            String filePath = cursor.getString(index);
            cursor.close();
            return filePath;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    static void callActionImagePickFromCamera(@NonNull Fragment fragment, @Nullable File file) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(fragment.getContext().getPackageManager()) == null)
            return;
        if (file == null)
            return;
        Uri photoURI = FileProvider.getUriForFile(fragment.getContext(),
                fragment.getString(R.string.app_provider), file);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        fragment.startActivityForResult(takePictureIntent,
                1003);
    }

    static void callActionImagePickFromCamera(@NonNull Activity activity, @Nullable File file) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) == null)
            return;
        if (file == null)
            return;
        Uri photoURI = FileProvider.getUriForFile(activity,
                activity.getString(R.string.app_provider), file);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        activity.startActivityForResult(takePictureIntent,
                1003);
    }

    static void callActionImagePickFromStorage(@NonNull Fragment fragment) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        fragment.startActivityForResult(intent, 1004);
    }

    static void callActionImagePickFromStorage(@NonNull Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, 1004);
    }


    static void loadImage(@NonNull Activity activity, @NonNull String imageUrl,
                          @NonNull ImageLoadCallback imageLoadCallback) {
        new Thread(() -> {
            try {
                Drawable drawable = Glide.with(activity)
                        .load(imageUrl)
                        .submit()
                        .get();
                activity.runOnUiThread(() -> {
                    if (drawable != null)
                        imageLoadCallback.onLoaded(drawable);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}