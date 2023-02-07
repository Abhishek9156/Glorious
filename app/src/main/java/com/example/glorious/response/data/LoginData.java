package com.example.glorious.response.data;

import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("uid")
    public String uid;
    @SerializedName("fname")
    public String fname;
    @SerializedName("lname")
    public String lname;
    @SerializedName("email")
    public String email;
    @SerializedName("mobile")
    public String mobile;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;
}
