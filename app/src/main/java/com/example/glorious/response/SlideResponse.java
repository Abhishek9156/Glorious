package com.example.glorious.response;

import com.example.glorious.response.data.LoginData;
import com.example.glorious.response.data.SlideData;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SlideResponse {
    @SerializedName("data")
    public ArrayList<SlideData> data;
    @SerializedName("status")
    public String status;
    @SerializedName("msg")
    public String msg;
}
