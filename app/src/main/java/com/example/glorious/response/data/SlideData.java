package com.example.glorious.response.data;

import com.google.gson.annotations.SerializedName;

public class SlideData {
    @SerializedName("sno")
    public String sno;
    @SerializedName("image")
    public String image;

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
