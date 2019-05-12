package com.doomsday.tournamentserver.controller.response;

public class Response<T> {

    private int status;
    private Information information;
    private T body;

    public Response(int status, Information information, T body) {
        this.status = status;
        this.information = information;
        this.body = body;
    }

    public Response(int status, Information information) {
        this.status = status;
        this.information = information;
    }

    public int getStatus() {
        return status;
    }

    public Information getInformation() {
        return information;
    }

    public T getBody() {
        return body;
    }
}
