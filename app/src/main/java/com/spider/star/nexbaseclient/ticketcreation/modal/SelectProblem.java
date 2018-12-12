package com.spider.star.nexbaseclient.ticketcreation.modal;

import com.google.gson.annotations.SerializedName;

public class SelectProblem {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("name")
    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("id")
    String id;


}
