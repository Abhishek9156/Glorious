package com.example.glorious.response;

import com.example.glorious.response.data.WishlistData;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WishlistResponse {
    @SerializedName("data")
    public ArrayList<WishlistData> data;
    @SerializedName("status")
    public String status;
    @SerializedName("msg")
    public String msg;
}
