package com.example.smartbin.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.smartbin.R;
import com.example.smartbin.databinding.ActivityAboutBinding;
import com.example.smartbin.intrface.AppConstants;
import com.example.smartbin.model.ModelUser;
import com.example.smartbin.retrofit.DPSecurity;
import com.example.smartbin.retrofit.RetrofitCallbacks;
import com.example.smartbin.utils.AppUtils;
import com.google.gson.JsonObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import retrofit2.Call;
import retrofit2.Response;

public class AboutActivity extends AppCompatActivity {


    ActivityAboutBinding binding;
    Dialog dialogFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_about);
        binding.btnFeedback.setOnClickListener(view -> openFeedbackDialog());
    }

    private void openFeedbackDialog() {
        dialogFeedback = new Dialog(this);
        dialogFeedback.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        dialogFeedback.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogFeedback.setContentView(R.layout.dialog_feedback);
        dialogFeedback.setCancelable(true);
        final RatingBar xRating = dialogFeedback.findViewById(R.id.xRating);
        final EditText xEtMessage = dialogFeedback.findViewById(R.id.xEtMessage);
        Button xBtnCancel = dialogFeedback.findViewById(R.id.xBtnCancel);
        Button xBtnSubmit = dialogFeedback.findViewById(R.id.xBtnSubmit);

        xBtnCancel.setOnClickListener(view -> dialogFeedback.dismiss());
        xBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkMessage(xEtMessage.getText().toString())) {
                    submitFeedback(xRating.getRating(), xEtMessage.getText().toString());
                }
            }
        });
        dialogFeedback.show();
    }

    private void submitFeedback(float rating, String message) {

        RetrofitCallbacks<JsonObject> callbacks = new RetrofitCallbacks<JsonObject>(this) {
            private Call<JsonObject> call;
            private Response<JsonObject> response;

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                this.call = call;
                this.response = response;
                super.onResponse(call, response);

                if (response.code() == 200) {
                    if (response.body().get(AppConstants.RESPONSE_STATUS).getAsBoolean()) {
                        if (dialogFeedback != null)
                            if (dialogFeedback.isShowing())
                                dialogFeedback.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), response.body().get(AppConstants.RESPONSE_MESSAGE).getAsString(), Toast.LENGTH_SHORT).show();
                }
                if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "Internal Server Error...", Toast.LENGTH_SHORT).show();
                }
                if (response.code() == 400) {
                    Toast.makeText(getApplicationContext(), "Page Not Found...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                super.onFailure(call, t);

            }
        };
        ModelUser.LoginAuth user = AppUtils.getUserModel();

        new DPSecurity().addFeedback(callbacks, user.getId(), String.valueOf(rating), message);
    }

    private boolean checkMessage(String message) {
        if (message.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Enter Message", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
