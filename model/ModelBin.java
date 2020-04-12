package com.example.smartbin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ModelBin
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Bin> data = null;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Bin> getData() {
        return data;
    }

    public void setData(List<Bin> data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public static class Bin implements Serializable {

        @SerializedName("bin_id")
        @Expose
        private String bin_id;
        @SerializedName("area_name")
        @Expose
        private String area_name;
        @SerializedName("bin_latitude")
        @Expose
        private String bin_latitude;
        @SerializedName("bin_longitude")
        @Expose
        private String bin_longitude;
        @SerializedName("dustbin_storage")
        @Expose
        private String dustbin_storage;


        public String getBinId() {
            return bin_id;
        }

        public void setBinId(String bin_id) { this.bin_id = bin_id; }

        public String getAreaName() { return area_name; }

        public void setAreaName (String area_name) { this.area_name = area_name; }

        public String getBinLatitude() {
            return bin_latitude;
        }

        public void setBinLatitude(String bin_latitude) { this.bin_latitude = bin_latitude; }

        public String getBinLongitude() {
            return bin_longitude;
        }

        public void setBinLongitude(String bin_longitude) { this.bin_longitude = bin_longitude; }

        public String getBinStorage() {
            return dustbin_storage;
        }

        public void setBinStorage(String dustbin_storage) { this.dustbin_storage = dustbin_storage; }

    }

}
