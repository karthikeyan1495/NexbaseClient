package com.spider.star.nexbaseclient.rateing.modal;

import com.google.gson.annotations.SerializedName;

public class RatingModal {

    @SerializedName("comment")
    String commit;

    @SerializedName("ticket_number")
    String ticket_number;

    @SerializedName("rating")
    String rating;

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public String getTicket_number() {
        return ticket_number;
    }

    public void setTicket_number(String ticket_number) {
        this.ticket_number = ticket_number;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
