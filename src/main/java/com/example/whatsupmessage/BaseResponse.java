package com.example.whatsupmessage;

public class BaseResponse {
    private int statusCode;
    private String status;
    private String message;

    public BaseResponse(int statusCode, String status, String message) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "statusCode=" + statusCode +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
