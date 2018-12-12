package com.spider.star.nexbaseclient.login.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @SerializedName("mobile")
    @Expose
    String mobile;


}
