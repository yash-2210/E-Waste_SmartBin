package com.example.smartbin.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.smartbin.R;
import com.example.smartbin.databinding.ActivitySignupBinding;
import com.example.smartbin.model.ModelUser;
import com.example.smartbin.retrofit.DPSecurity;
import com.example.smartbin.retrofit.RetrofitCallbacks;
import com.example.smartbin.retrofit.ServiceURL;
import com.example.smartbin.utils.AppUtils;
import com.google.gson.Gson;
import com.pixplicity.easyprefs.library.Prefs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import retrofit2.Call;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;
    Activity activity = SignupActivity.this;
    String set_name,set_email,set_mobNo,set_pswd,set_cpswd;
    EditText get_name,get_email,get_mobNo,get_pswd,get_cpswd;
    ImageButton regBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_signup);

        get_name= binding.signUpUserName;
        get_email =binding.userEmail;
        get_cpswd = binding.confirmPassword;
        get_mobNo = binding.userMobileNumber;
        get_pswd = binding.signUpPassword;
        regBtn = binding.btnSignUp;

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkRegister();
            }
        });
    }
    private void checkRegister() {

        set_name = binding.signUpUserName.getText().toString().trim();
        set_mobNo = get_mobNo.getText().toString().trim();
        set_email = get_email.getText().toString().trim();
        set_pswd = get_pswd.getText().toString().trim();
        set_cpswd = get_cpswd.getText().toString().trim();


        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        // String passwordPattern = "[^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        if (set_name.isEmpty()) {
            binding.signUpUserName.setError("Name is Required");
        }
        if (set_email.isEmpty()) {
            get_email.setError("Email is Required");
        }
        if (set_pswd.isEmpty()) {
            get_pswd.setError("Password is Required");
        }
        if (set_mobNo.isEmpty()) {
            get_mobNo.setError("Contact Number is Required");
        }
        if (set_cpswd.isEmpty()) {
            get_cpswd.setError("Re-Type password");
        }
        else if (!set_pswd.equals(set_cpswd)) {
            Toast.makeText(getApplicationContext(), "Password not matched ! :(", Toast.LENGTH_LONG).show();
        }
        else if(set_pswd.length()>=8 && set_pswd.length()<=15)
        {
            Toast.makeText(getApplicationContext(),"Password Must be between 8 to 15 characters",Toast.LENGTH_LONG).show();
        }
        else if (!set_email.matches(emailPattern)) {
            Toast.makeText(getApplicationContext(), "Enter a Valid Email", Toast.LENGTH_LONG).show();
        } else {

            RetrofitCallbacks<ModelUser> callbacks = new RetrofitCallbacks<ModelUser>(activity) {
                @Override
                public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                    super.onResponse(call, response);
                    //loadingDialog.dismissDialog();
                    if (response.code() == 200) {
                        if (response.body().getStatus()) {
                            if (response.body().getData() != null) {
                                ModelUser.LoginAuth user = response.body().getData();
                                Prefs.putString(ServiceURL.user, new Gson().toJson(user));
                                AppUtils.callActivityWithFinish(activity, DashboardActivity.class);
                            }
                        }
                        Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    if (response.code() == 404) {
                        Toast.makeText(activity, "Page Not Found..", Toast.LENGTH_SHORT).show();
                    }
                    if (response.code() == 500) {
                        Toast.makeText(activity, "Internal Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelUser> call, Throwable t) {
                    super.onFailure(call, t);
                    //loadingDialog.dismissDialog();
                }
            };

            new DPSecurity().checkRegistration(callbacks, set_name, set_email, set_mobNo, set_pswd);
        }

    }

}
