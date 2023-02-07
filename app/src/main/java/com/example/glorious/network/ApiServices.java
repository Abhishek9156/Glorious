package com.example.glorious.network;

import com.example.glorious.response.CategoryResponse;
import com.example.glorious.response.OrderHistoryResponse;
import com.example.glorious.response.WishlistResponse;
import com.example.glorious.response.ProductResponse;
import com.example.glorious.response.LoginResponse;
import com.example.glorious.response.SignUpResponse;
import com.example.glorious.response.SlideResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiServices {

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(@Field("mobile") String login,
                              @Field("password") String password);
    @FormUrlEncoded
    @POST("register.php")
    Call<SignUpResponse> signUp(@Field("fname") String fname,
                                @Field("lname") String lname,
                                @Field("email") String email,
                                @Field("mobile") String mobile,
                                @Field("password") String password);
    @FormUrlEncoded
    @POST("getregisternumber.php")
    Call<SignUpResponse> isuserRegister(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("updatepassword.php")
    Call<SignUpResponse> updatePassword(@Field("mobile") String mobile,
                                        @Field("password") String password);

    @GET("productget.php")
    Call<ProductResponse> getProduct();

    @GET("slideshowget.php")
    Call<SlideResponse> getSlider();

    @GET("getallCategory.php")
    Call<CategoryResponse> getallcategory();

    @FormUrlEncoded
    @POST("wishlistget.php")
    Call<WishlistResponse> getwishlistdata(@Field("whishlistUserID") String cartUserID);

    @FormUrlEncoded
    @POST("getproductcategory.php")
    Call<ProductResponse> getcategorydetail(@Field("productCategory") String productCategory);

    @FormUrlEncoded
    @POST("cartadd.php")
    Call<SignUpResponse> addtoCart(@Field("cartUserID") String cartUserID,
                                        @Field("cartProductID") String cartProductID);

    @FormUrlEncoded
    @POST("wishlishtadd.php")
    Call<SignUpResponse> addtowishlist(@Field("whishlistProductID") String whishlistProductID,
                                        @Field("whishlistUserID") String whishlistUserID);

    @FormUrlEncoded
    @POST("getorders.php")
    Call<OrderHistoryResponse> getorderHistory(@Field("UserID") String UserID);

    @FormUrlEncoded
    @POST("cartget.php")
    Call<ProductResponse> getcartdata(@Field("cartUserID") String cartUserID);

    @FormUrlEncoded
    @POST("cartremove.php")
    Call<SignUpResponse> removecartdata(@Field("cartUserID") String cartUserID,
                                         @Field("cartProductID") String cartProductID);

    @FormUrlEncoded
    @POST("order.php")
    Call<SignUpResponse> ordered(@Field("orderUserID") String orderUserID,
                                 @Field("orderAmount") String orderAmount,
                                 @Field("orderShipName") String orderShipName,
                                 @Field("orderShipAddress") String orderShipAddress,
                                 @Field("orderShipAddress2") String orderShipAddress2,
                                 @Field("orderCity") String orderCity,
                                 @Field("orderState") String orderState,
                                 @Field("orderZip") String orderZip,
                                 @Field("orderPhone") String orderPhone,
                                 @Field("orderQuantity") String orderQuantity,
                                 @Field("orderProductId") String orderProductId,
                                 @Field("totalAmoutPay") String totalAmoutPay);

    @FormUrlEncoded
    @POST("wishlistremove.php")
    Call<SignUpResponse>   removewishlistdata(@Field("whishlistProductID") String whishlistProductID,
                                        @Field("whishlistUserID") String whishlistUserID);

}
