package com.example.glorious.response;

import com.example.glorious.response.data.ProductData;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductResponse {
    @SerializedName("data")
    public ArrayList<ProductData> data;
    @SerializedName("status")
    public String status;
    @SerializedName("msg")
    public String msg;
}
