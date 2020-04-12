package com.example.smartbin.retrofit;


import com.example.smartbin.model.ModelBin;
import com.example.smartbin.model.ModelUser;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ApiInterface {

    @FormUrlEncoded
    @POST(ServiceURL.checkLogin)
    Call<ModelUser> checkLogin(
            @Field(ServiceURL.email) String email,
            @Field(ServiceURL.password) String password);

    @FormUrlEncoded
    @POST(ServiceURL.checkRegistration)
    Call<ModelUser> updateProfile(
            @Field(ServiceURL.user_id) String user_id,
            @Field(ServiceURL.username) String username,
            @Field(ServiceURL.email) String email,
            @Field(ServiceURL.phone) String phone,
            @Field(ServiceURL.password) String password);

    @FormUrlEncoded
    @POST(ServiceURL.updateReward)
    Call<ModelUser> updateReward(
            @Field(ServiceURL.userid) String userid,
            @Field(ServiceURL.reward) int reward);

    @FormUrlEncoded
    @POST(ServiceURL.checkRegistration)
    Call<ModelUser> checkRegistration(
            @Field(ServiceURL.username) String username,
            @Field(ServiceURL.email) String email,
            @Field(ServiceURL.phone) String phone,
            @Field(ServiceURL.password) String password);

    @Multipart
    @POST(ServiceURL.updateUserPhoto)
    Call<JsonObject> updateProfilePhoto(@Part(ServiceURL.userid) RequestBody userId,
                                        @Part MultipartBody.Part fileData);
    @FormUrlEncoded
    @POST(ServiceURL.binList)
    Call<ModelBin> getBinList(
            @Field(ServiceURL.status) boolean status);

    @FormUrlEncoded
    @POST(ServiceURL.feedback)
    Call<JsonObject> addFeedback(
            @Field(ServiceURL.user_id) String userId,
            @Field(ServiceURL.rating) String rating,
            @Field(ServiceURL.comment) String message);

   /* @FormUrlEncoded
    @POST(ServiceURL.cafeSearchList)
    Call<modelCafe> getSearchCafeList(
            @Field(ServiceURL.keyword) String keyword);

    @FormUrlEncoded
    @POST(ServiceURL.cafeTableList)
    Call<modelCafeTable> getCafeTableList(
            @Field(ServiceURL.cafe_id) String cafe_id);

    @FormUrlEncoded
    @POST(ServiceURL.cafeMenuList)
    Call<modelMenuItem> getCafeMenuList(
            @Field(ServiceURL.cafe_id) String cafe_id);

    @POST(ServiceURL.addOrder)
    Call<modelOrderResponse> addOrder(
            @Body requestCart cart);

    @FormUrlEncoded
    @POST(ServiceURL.orderList)
    Call<modelOrder> orderList(
            @Field(ServiceURL.user_id) String user_id);

    @FormUrlEncoded
    @POST(ServiceURL.orderDetails)
    Call<modelOrderDetail> orderDetails(
            @Field(ServiceURL.order_id) String order_id);

    @FormUrlEncoded
    @POST(ServiceURL.updateOrderStatus)
    Call<CommonResponse> updateOrderStatus(
            @Field(ServiceURL.order_id) String order_id,
            @Field(ServiceURL.table_id) String table_id,
            @Field(ServiceURL.order_status) String orderStatus);

    @FormUrlEncoded
    @POST(ServiceURL.offerList)
    Call<modelOffer> offerList(
            @Field(ServiceURL.status) boolean status
    );*/
}