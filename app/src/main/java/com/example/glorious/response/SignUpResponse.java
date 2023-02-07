package com.example.glorious.response;

import com.google.gson.annotations.SerializedName;

public class SignUpResponse {
    @SerializedName("data")
    public String data;
    @SerializedName("status")
    public String status;
    @SerializedName("msg")
    public String msg;
}
