package com.example.smartbin.retrofit;


class DataProvider {

    ApiInterface apiService;


    DataProvider() {
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }
}
