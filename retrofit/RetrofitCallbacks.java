package com.example.smartbin.retrofit;

import android.content.Context;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by murtuzanalawala on 8/10/17.
 */

public class RetrofitCallbacks<T> implements Callback<T> {
    private final Context context;

    public RetrofitCallbacks(Context context) {
        this.context = context;
    }


    @Override
    public void onResponse(Call<T> call, Response<T> response) {

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (t.getMessage() != null) {
            if (t.getMessage().contains("Unable to resolve host")) {
//                    openNoNetworkDialog();

                Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                //Toasty.error(context, "Please check your internet connection.", Toast.LENGTH_SHORT, true).show();
            } else {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast toast = Toast.makeText(context,
                    "No Message Found..", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
