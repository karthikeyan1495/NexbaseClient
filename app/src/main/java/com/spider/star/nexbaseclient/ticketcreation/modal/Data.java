package com.spider.star.nexbaseclient.ticketcreation.modal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    public List<SelectProblem> getSelectProblems() {
        return selectProblems;
    }

    public void setSelectProblems(List<SelectProblem> selectProblems) {
        this.selectProblems = selectProblems;
    }

    @SerializedName("data")
    List<SelectProblem> selectProblems;

}
