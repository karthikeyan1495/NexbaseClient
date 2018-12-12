package com.spider.star.nexbaseclient.ticketcreation.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketCreation {

    public String getProblem_reported() {
        return problem_reported;
    }

    public void setProblem_reported(String problem_reported) {
        this.problem_reported = problem_reported;
    }

    public String getProblem_type() {
        return problem_type;
    }

    public void setProblem_type(String problem_type) {
        this.problem_type = problem_type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBooked_date() {
        return booked_date;
    }

    public void setBooked_date(String booked_date) {
        this.booked_date = booked_date;
    }

    @SerializedName("problem_reported")
    @Expose
    String problem_reported;

    @SerializedName("problem_type")
    @Expose
    String problem_type;

    @SerializedName("mobile")
    @Expose
    String mobile;

    @SerializedName("booked_date")
    @Expose
    String booked_date;


}
