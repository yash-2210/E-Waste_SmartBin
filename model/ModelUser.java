package com.example.smartbin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelUser {
    @SerializedName("status")
    @Expose
    private Boolean status;

    @SerializedName("loginAuth")
    @Expose
    private LoginAuth loginAuth = null;

    @SerializedName("message")
    @Expose
    private String message;


    public LoginAuth getLoginAuth() { return loginAuth; }

    public void setLoginAuth(LoginAuth loginAuth) {
        this.loginAuth = loginAuth;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() { return status; }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LoginAuth getData() { return loginAuth; }

    public void setData(LoginAuth loginAuth) { this.loginAuth = loginAuth; }


    public class LoginAuth
    {
        @SerializedName("id")
        @Expose
        private String id;


        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("email")
        @Expose
        private String email;

        @SerializedName("phno")
        @Expose
        private String phno;

        @SerializedName("password")
        @Expose
        private String password;

        @SerializedName("profile_image")
        @Expose
        private String profileImage;

        @SerializedName("rewards")
        @Expose
        private String rewards;

        public String getRewards() {
            return rewards;
        }

        public void setRewards(String rewards) {
            this.rewards = rewards;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getId() { return id; }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserPassword() {
            return password;
        }

        public void setUserPassword(String password) {
            this.password = password;
        }

        public String getUserPhone() {
            return phno;
        }

        public void setUserPhone(String userPhone) {
            this.phno = phno;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

    }
}
