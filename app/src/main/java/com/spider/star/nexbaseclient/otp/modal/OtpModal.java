package com.spider.star.nexbaseclient.otp.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpModal {


    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    @SerializedName("otp")
    @Expose
    String otp;

    @SerializedName("mobile")
    @Expose
    String mobile;


}
