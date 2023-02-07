package com.example.glorious.wishlist;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.glorious.R;
import com.example.glorious.network.ApiServices;
import com.example.glorious.network.AppRetrofit;
import com.example.glorious.response.WishlistResponse;
import com.example.glorious.util.BaseApplication;
import com.example.glorious.util.KeyConstants;
import com.example.glorious.util.SharedPreferenceUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistFragment extends Fragment {
    RecyclerView rvWishlist;
    WishlistAdaptor wishlistAdaptor;
    TextView textView;
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_wishlist, container, false);
        rvWishlist=view.findViewById(R.id.rvWishlist);
        textView=view.findViewById(R.id.Tvnodata);
        rvWishlist.setLayoutManager(new GridLayoutManager(getContext(),2));
        String USERID= SharedPreferenceUtil.getInstance(BaseApplication.getsApplicationContext())
                .getStringValue(KeyConstants.UID ,"");
        progressDialog = new ProgressDialog(getContext());
        callApi(USERID);


        return view;
    }
    void callApi(String userid){
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        Call<WishlistResponse> call= AppRetrofit.getClient().create(ApiServices.class).getwishlistdata(userid);
        call.enqueue(new Callback<WishlistResponse>() {
            @Override
            public void onResponse(Call<WishlistResponse> call, Response<WishlistResponse> response) {
                progressDialog.dismiss();
                Log.d("Cart Response",response.toString());
                WishlistResponse wishlistResponse =response.body();
                if(wishlistResponse.status.equals("true")){
                    rvWishlist.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                    wishlistAdaptor=new WishlistAdaptor(getContext(),wishlistResponse.data);
                    rvWishlist.setAdapter(wishlistAdaptor);

                }else{
                    rvWishlist.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<WishlistResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
                progressDialog.dismiss();
            }
        });

    }
}