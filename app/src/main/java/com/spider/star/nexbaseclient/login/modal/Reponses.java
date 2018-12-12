package com.spider.star.nexbaseclient.login.modal;

import com.google.gson.annotations.SerializedName;

public class Reponses {

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("message")
    String message;

    @SerializedName("status")
    String status;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @SerializedName("login")
    String login;


}
