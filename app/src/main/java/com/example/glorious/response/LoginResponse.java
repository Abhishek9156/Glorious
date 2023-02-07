package com.example.glorious.response;

import com.example.glorious.response.data.LoginData;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("data")
    public LoginData data;
    @SerializedName("status")
    public String status;
    @SerializedName("msg")
    public String msg;

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
