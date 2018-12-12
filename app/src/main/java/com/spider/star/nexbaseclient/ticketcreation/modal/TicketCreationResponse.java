package com.spider.star.nexbaseclient.ticketcreation.modal;

import com.google.gson.annotations.SerializedName;

public class TicketCreationResponse {

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @SerializedName("status")
    int status;

    @SerializedName("message")
    String message;

    @SerializedName("ticket_id")
    String ticket_id;

    @SerializedName("ticket_number")
    String ticket_number;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getTicket_number() {
        return ticket_number;
    }

    public void setTicket_number(String ticket_number) {
        this.ticket_number = ticket_number;
    }
}
