package com.example.glorious.response.data;

import com.google.gson.annotations.SerializedName;

public class OrderHistoryData {
    @SerializedName("productID")
    public String productID;
    @SerializedName("productName")
    public String productName;
    @SerializedName("productPrice")
    public String productPrice;
    @SerializedName("productImage")
    public String productImage;
    @SerializedName("productCategory")
    public String productCategory;
    @SerializedName("productStock")
    public String productStock;
    @SerializedName("productSeller")
    public String productSeller;
    @SerializedName("productRating")
    public String productRating;
    @SerializedName("productOffer")
    public String productOffer;
    @SerializedName("productUnitPiece")
    public String productUnitPiece;


    @SerializedName("orderID")
    public String orderID;
    @SerializedName("orderAmount")
    public String orderAmount;
    @SerializedName("orderQuantity")
    public String orderQuantity;
    @SerializedName("orderShipName")
    public String orderShipName;
    @SerializedName("orderShipAddress")
    public String orderShipAddress;
    @SerializedName("orderShipAddress2")
    public String orderShipAddress2;
    @SerializedName("orderCity")
    public String orderCity;
    @SerializedName("orderState")
    public String orderState;
    @SerializedName("orderZip")
    public String orderZip;
    @SerializedName("orderPhone")
    public String orderPhone;
    @SerializedName("orderDate")
    public String orderDate;
    @SerializedName("paymentMethod")
    public String paymentMethod;
    @SerializedName("orderStatus")
    public String orderStatus;
    @SerializedName("totalAmoutPay")
    public String totalAmoutPay;

}
