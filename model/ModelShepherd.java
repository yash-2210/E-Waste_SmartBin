package com.example.smartbin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelShepherd
{
    @SerializedName("status")
    @Expose
    private Boolean status;

    @SerializedName("loginAuth")
    @Expose
    private ModelShepherd.LoginAuth loginAuth = null;

    @SerializedName("message")
    @Expose
    private String message;


    public ModelShepherd.LoginAuth getLoginAuth() { return loginAuth; }

    public void setLoginAuth(ModelShepherd.LoginAuth loginAuth) {
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

    public ModelShepherd.LoginAuth getData() { return loginAuth; }

    public void setData(ModelShepherd.LoginAuth loginAuth) { this.loginAuth = loginAuth; }


    public class LoginAuth
    {
        @SerializedName("id")
        @Expose
        private String id;


        @SerializedName("sh_name")
        @Expose
        private String name;

        @SerializedName("sh_email")
        @Expose
        private String email;

        @SerializedName("sh_mobile")
        @Expose
        private String Phno;

        @SerializedName("sh_password")
        @Expose
        private String password;


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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getmobile_no() {
            return Phno;
        }

        public void setmobile_no(String sh_phone) {
            this.Phno = Phno;
        }


    }
}
