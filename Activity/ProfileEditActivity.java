package com.example.smartbin.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartbin.R;
import com.example.smartbin.databinding.ActivityProfileEditBinding;
import com.example.smartbin.intrface.AppConstants;
import com.example.smartbin.model.ModelUser;
import com.example.smartbin.retrofit.DPSecurity;
import com.example.smartbin.retrofit.RetrofitCallbacks;
import com.example.smartbin.retrofit.ServiceURL;
import com.example.smartbin.utils.AppUtils;
import com.example.smartbin.utils.FileUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class ProfileEditActivity extends AppCompatActivity {
    private ActivityProfileEditBinding binding;
    private ModelUser.LoginAuth user;
    private String email, password, username, phone;
    private File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_edit);

        setData();
    }

    private void setData()
    {
        user = AppUtils.getUserModel();
        if (user == null) {
            return;
        }
        Glide.with(this)
                .load(ServiceURL.imageurl + user.getProfileImage())
                .placeholder(R.drawable.ic_profile_primary)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivProfile);

        binding.etUserName.setText(user.getName());
        binding.etEmail.setText(user.getEmail());
        binding.etPhone.setText(user.getUserPhone());
        binding.etPassword.setText(user.getUserPassword());

        binding.btnUpdate.setOnClickListener(view -> {
            if (checkValidation()) {
                if (!username.equalsIgnoreCase(user.getName())
                        || !email.equalsIgnoreCase(user.getEmail())
                        || !phone.equalsIgnoreCase(user.getUserPhone())
                        || !password.equalsIgnoreCase(user.getUserPassword())) {
                    updateProfile();
                }
            }
        });
        binding.ivProfile.setOnClickListener(view -> openGallery());
    }

    private void openGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
                showPermission();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            selectImage();

        }
    }

    private void showPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                try {
                    selectImage();
                } catch (Exception exp) {
                    Log.i("Error", exp.toString());
                }
//                Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();

            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    private void selectImage() {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(i, "Select File"), 1004);
    }

    private void updateProfile() {
        RetrofitCallbacks<ModelUser> callbacks = new RetrofitCallbacks<ModelUser>(this) {
            @Override
            public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                super.onResponse(call, response);

                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        if (response.body().getData() != null) {
                            ModelUser.LoginAuth user = response.body().getData();
                            Prefs.putString(ServiceURL.user, new Gson().toJson(user));
                        }
                    }
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                if (response.code() == 404) {
                    Toast.makeText(getApplicationContext(), "Page Not Found..", Toast.LENGTH_SHORT).show();
                }
                if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "Internal Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {
                super.onFailure(call, t);

             /*   xBtnVerify.setClickable(true);
                xLoader.setVisibility(View.GONE);*/
            }
        };

        new DPSecurity().updateProfile(callbacks, user.getId(), username, email, phone, password);
    }

    private boolean checkValidation() {
        username = binding.etUserName.getText().toString().trim();
        email = binding.etEmail.getText().toString().trim();
        phone = binding.etPhone.getText().toString().trim();
        password = binding.etPassword.getText().toString().trim();

        if (username.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Username is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Email is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phone.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Phone is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Password is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = null;
        if (requestCode == 1004) {
            if (resultCode == Activity.RESULT_OK) {
                uri = data.getData();
                String path = FileUtils.getFilePathFromURI(this, uri);
                if (path != null) {
                    path = AppUtils.compressImage(path);
                    uploadProfileImage(path);
                }
            }
        }
    }

    private void uploadProfileImage(String path) {
        RetrofitCallbacks<JsonObject> callbacks = new RetrofitCallbacks<JsonObject>(this) {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                super.onResponse(call, response);
                if (response.code() == 200) {
                    if (response.body().get(AppConstants.RESPONSE_STATUS).getAsBoolean()) {
                        user.setProfileImage(response.body().get("profile_photo").getAsString());
                        Prefs.putString(ServiceURL.user, new Gson().toJson(user));

                        Glide.with(ProfileEditActivity.this)
                                .load(ServiceURL.imageurl + user.getProfileImage())
                                .apply(RequestOptions.circleCropTransform())
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .placeholder(R.drawable.ic_profile_primary)
                                .skipMemoryCache(true)
                                .into(binding.ivProfile);
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

        MultipartBody.Part fileData;
        if (path != null) {
            File file = new File(path);
            RequestBody requestImage = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            String image_name = path.substring(path.lastIndexOf("/") + 1);
            fileData =
                    MultipartBody.Part.createFormData(ServiceURL.image, image_name, requestImage);
        } else {
            fileData = null;
        }
        new DPSecurity().updateProfileImage(callbacks, user.getId(), fileData);
    }
}
