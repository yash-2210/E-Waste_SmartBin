package com.example.smartbin.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.smartbin.model.ModelUser;
import com.example.smartbin.retrofit.ServiceURL;
import com.google.gson.Gson;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
/*import androidx.exifinterface.media.ExifInterface;*/

public interface AppUtils {

    static boolean hasConnection(Context context) {
        if (context == null) return false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) return false;

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    static String assetJSONFile(String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();

        return new String(formArray);
    }

    static int getScreenWidth(Context context) {
        if (context == null)
            return 0;
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    static int getScreenHeight(Context context) {
        if (context == null)
            return 0;
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    static void showSoftKeyboard(Activity activity) {
        if (activity == null)
            return;
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(
                    InputMethodManager.SHOW_FORCED, 0);
        }
    }

    static void showSoftKeyboard(View view) {
        if (view == null)
            return;
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext()
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    static void hideSoftKeyboard(Activity activity) {
        if (activity == null)
            return;
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null)
            view = new View(activity);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    static void dispatchTouchEvent(Activity activity, MotionEvent event) {
        try {
            View view = activity.getCurrentFocus();
            if (view instanceof EditText) {
                View w = activity.getCurrentFocus();
                int scrcoords[] = new int[2];
                if (w != null) {
                    w.getLocationOnScreen(scrcoords);
                    float x = event.getRawX() + w.getLeft() - scrcoords[0];
                    float y = event.getRawY() + w.getTop() - scrcoords[1];

                    if (event.getAction() == MotionEvent.ACTION_UP
                            && (x < w.getLeft() || x >= w.getRight() ||
                            y < w.getTop() || y > w.getBottom())) {
                        AppUtils.hideSoftKeyboard(activity);
                        view.clearFocus();
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------
    //--------------------------------------Call Activity-------------------------------------------
    //----------------------------------------------------------------------------------------------
    static void callActivity(Activity activity, Class classActivity) {
        callActivityIsFinish(activity, classActivity, false);
    }

    static void callActivityWithFinish(Activity activity, Class classActivity) {
        callActivityIsFinish(activity, classActivity, true);
    }

    static void callStartActivity(int request, Activity activity, Class classActivity) {
        Intent intent = new Intent(activity, classActivity);
        activity.startActivity(intent);
        activity.startActivityForResult(intent, request);
    }

    static void callActivityWithClearTask(Activity activity, Class classActivity) {
        if (activity == null || classActivity == null)
            return;
        Intent intent = new Intent(activity, classActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    static void callActivityIsFinish(Activity activity, Class classActivity, boolean isFinish) {
        if (activity == null || classActivity == null)
            return;
        Intent intent = new Intent(activity, classActivity);
        activity.startActivity(intent);
        if (isFinish)
                activity.finish();
    }

    static void callActivity(Context context, Class classActivity) {
        Intent intent = new Intent(context, classActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //----------------------------------------------------------------------------------------------
    static void shareLiveStreaming(@NonNull Context context, Uri shortLink) {
        String shareBody = "Try Sello App!\n" + shortLink;

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        context.startActivity(sharingIntent);
    }

/*

    static String formatStringInUSCurrencyStyleFix2Decimal(String str) {
        NumberFormat form = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        if (form instanceof DecimalFormat) {
            DecimalFormat decimalFormat = (DecimalFormat) form;

            String s = str.trim();
            if (s.contains(AppConstants.DOLLER_SIGN))
                s = s.replace(AppConstants.DOLLER_SIGN, "");
            if (s.contains(","))
                s = s.replace(",", "");
            if (TextUtils.isEmpty(s))
                s = "0";

            if (s.contains(".") && s.split("\\.").length > 1 &&
                    s.split("\\.")[1].length() > 2)
                s = s.substring(0, s.indexOf(".") + 3);

            decimalFormat.applyPattern("$#,###,##0.00");

            try {
                return decimalFormat.format(Double.valueOf(s));
            } catch (Exception e) {
                e.printStackTrace();
                return str;
            }
        } else return str;
    }
*/

    /*static String formatStringInFix2Decimal(String str) {
        NumberFormat form = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        if (form instanceof DecimalFormat) {
            DecimalFormat decimalFormat = (DecimalFormat) form;

            String s = str.trim();
            if (s.contains(AppConstants.DOLLER_SIGN))
                s = s.replace(AppConstants.DOLLER_SIGN, "");
            if (s.contains(","))
                s = s.replace(",", "");
            if (TextUtils.isEmpty(s))
                s = "0";

            if (s.contains(".") && s.split("\\.").length > 1 &&
                    s.split("\\.")[1].length() > 2)
                s = s.substring(0, s.indexOf(".") + 3);

            decimalFormat.applyPattern("#0.00");
            try {
                return decimalFormat.format(Double.valueOf(s));
            } catch (Exception e) {
                e.printStackTrace();
                return str;
            }
        } else return str;
    }
*/

    //----------------------------------------------------------------------------------------------
    //---------------------------------Show Hide Toolbar Views--------------------------------------
    //----------------------------------------------------------------------------------------------
    static void showToolbarView(@Nullable View... showViews) {
        if (showViews != null)
            for (View view : showViews) {
                view.setVisibility(View.VISIBLE);
            }
    }

    static void goneToolbarView(@Nullable View... hideViews) {
        if (hideViews != null)
            for (View view : hideViews) {
                view.setVisibility(View.GONE);
            }
    }

    static void hideToolbarView(@Nullable View... hideViews) {
        if (hideViews != null)
            for (View view : hideViews) {
                view.setVisibility(View.INVISIBLE);
            }
    }
    //----------------------------------------------------------------------------------------------

    static void changeTextViewLength(TextView textView, int length) {
        textView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }
    //----------------------------------------------------------------------------------------------

    static void showPickImageDIalog(@NonNull Activity activity,
                                    @NonNull DialogInterface.OnClickListener listener) {
        String[] array = {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Choose Image from");
        builder.setItems(array, listener);
        builder.show();
    }

    //----------------------------------------------------------------------------------------------
    static String getFormattedUserName(@Nullable String username) {
        String formattedUsername;
        if (TextUtils.isEmpty(username))
            formattedUsername = "";
        else if (username.contains("@"))
            formattedUsername = username;
        else formattedUsername = "@" + username;
        return formattedUsername;
    }

    /*static String getFormattedPrice(float price) {
        return formatStringInUSCurrencyStyleFix2Decimal(String.valueOf(price));
    }*/

    static String perfectDecimal(String str, int MAX_BEFORE_POINT, int MAX_DECIMAL) {
        if (str.isEmpty()) return str;
        if (str.charAt(0) == '.') str = "0" + str;
        int max = str.length();

        String rFinal = "";
        boolean after = false;
        int i = 0, up = 0, decimal = 0;
        char t;
        while (i < max) {
            t = str.charAt(i);
            if (t != '.' && !after) {
                up++;
                if (up > MAX_BEFORE_POINT)
                    return rFinal;
            } else if (t == '.') {
                after = true;
            } else {
                decimal++;
                if (decimal > MAX_DECIMAL)
                    return rFinal;
            }
            rFinal = rFinal + t;
            i++;
        }
        return rFinal;
    }

    static String checkOrderIDFull(long orderId) {
        String orderNo = String.valueOf(orderId);
        if (orderNo.startsWith("#")) {
            return orderNo;
        } else {
            return "#" + orderNo;
        }
    }

    static ModelUser.LoginAuth getUserModel() {
        try {
            if (Prefs.contains(ServiceURL.user)){
                return new Gson().fromJson(Prefs.getString(ServiceURL.user, ""), ModelUser.LoginAuth.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String compressImage(String filePath) {

        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = filePath;
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                            int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }
}