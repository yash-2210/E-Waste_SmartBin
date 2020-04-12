package com.example.smartbin.retrofit;


import com.example.smartbin.model.ModelBin;
import com.example.smartbin.model.ModelUser;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by murtuzanalawala on 11/23/17.
 */

public class DPSecurity extends DataProvider {


    public void checkLogin(RetrofitCallbacks<ModelUser> callbacks, String email, String password) {
        Call<ModelUser> call = apiService.checkLogin(email, password);
        call.enqueue(callbacks);
    }

    public void checkRegistration(RetrofitCallbacks<ModelUser> callbacks, String username, String email, String phone, String password) {
        Call<ModelUser> call = apiService.checkRegistration(username, email, phone, password);
        call.enqueue(callbacks);
    }

    public void updateProfile(RetrofitCallbacks<ModelUser> callbacks, String userId, String username, String email, String phone, String password) {
        Call<ModelUser> call = apiService.updateProfile(userId, username, email, phone, password);
        call.enqueue(callbacks);
        }

    public void getBinList(RetrofitCallbacks<ModelBin> callbacks) {
        Call<ModelBin> call = apiService.getBinList(true);
        call.enqueue(callbacks);
    }
    private Call<ModelBin> call;

    public void updateReward(RetrofitCallbacks<ModelUser> callbacks, String id, int rewards) {
        Call<ModelUser> call = apiService.updateReward(id, rewards);
        call.enqueue(callbacks);
    }

    public void addFeedback(RetrofitCallbacks<JsonObject> callbacks, String userId, String rating, String message) {
        Call<JsonObject> call = apiService.addFeedback(userId, rating, message);
        call.enqueue(callbacks);
    }



    public void updateProfileImage(RetrofitCallbacks<JsonObject> callbacks, String userId, MultipartBody.Part fileData)
    {
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), userId);

        Call<JsonObject> call = apiService.updateProfilePhoto(id, fileData);
        call.enqueue(callbacks);
    }
}
