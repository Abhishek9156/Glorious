package com.example.glorious.response;

import com.example.glorious.response.data.CategoryData;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoryResponse {
    @SerializedName("data")
    public ArrayList<CategoryData> data;
    @SerializedName("status")
    public String status;
    @SerializedName("msg")
    public String msg;
}
