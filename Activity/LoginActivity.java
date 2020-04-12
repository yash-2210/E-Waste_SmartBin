package com.example.smartbin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smartbin.R;
import com.example.smartbin.databinding.ActivityLoginBinding;
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

public class LoginActivity extends AppCompatActivity {

    //declare objects
    ActivityLoginBinding binding;
    EditText email, password;
    ImageView logoImg;
    Animation spls_fade;
    String setEmail, setPassword;
    ImageButton loginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        Intent i=new Intent();
        //store value
        loginBtn = findViewById(R.id.btnSignin);
        spls_fade = AnimationUtils.loadAnimation(this, R.anim.spls_fade);
        email = findViewById(R.id.signInUserName);
        password = findViewById(R.id.signInPassword);
        logoImg = findViewById(R.id.logoImg);

        setEmail = email.getText().toString();
        setPassword = password.getText().toString();


        //Animation
        logoImg.setAnimation(spls_fade);
        ModelUser.LoginAuth user = AppUtils.getUserModel();
        if (user != null) {
            AppUtils.callActivityWithFinish(LoginActivity.this, DashboardActivity.class);
            finish();
        }

        //on button click event and verify password and empty values
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEmail = email.getText().toString();
                setPassword = password.getText().toString();
                if (setEmail.isEmpty()) {
                    email.setError("Email is Required");
                }
                if (setPassword.isEmpty()){
                    password.setError("Password is Required");
                }
                else {
                  loginAuth();
                }
            }
        });
    }

    //login validation
    private void loginAuth() {
        final String tempEmail = email.getText().toString().trim();
        final String tempPassword = password.getText().toString().trim();

        RetrofitCallbacks<ModelUser> callbacks = new RetrofitCallbacks<ModelUser>(LoginActivity.this) {
            @Override
            public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                super.onResponse(call, response);
//                loadingDialog.dismissDialog();

                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        if (response.body().getLoginAuth() != null) {
                            ModelUser.LoginAuth user = response.body().getLoginAuth();
                            Prefs.putString(ServiceURL.user, new Gson().toJson(user));
                            AppUtils.callActivityWithFinish(LoginActivity.this, DashboardActivity.class);
                            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                            startActivity(intent);
                        }
                    }
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                if (response.code() == 404) {
                    Toast.makeText(LoginActivity.this, "Page Not Found..", Toast.LENGTH_SHORT).show();
                }
                if (response.code() == 500) {
                    Toast.makeText(LoginActivity.this, "Internal Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {
                super.onFailure(call, t);
//                loadingDialog.dismissDialog();
            }
        };

        new DPSecurity().checkLogin(callbacks, tempEmail, tempPassword);


    }

    //redirect signup page event
    public void signUp(View view) {
        Intent signUpIntentObj = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(signUpIntentObj);
    }
}
