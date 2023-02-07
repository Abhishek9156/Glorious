package com.example.glorious.cart;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glorious.R;
import com.example.glorious.network.ApiServices;
import com.example.glorious.network.AppRetrofit;
import com.example.glorious.response.ProductResponse;
import com.example.glorious.util.KeyConstants;
import com.example.glorious.util.SharedPreferenceUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment {
    TextView textView;
    CartDataAdaptor dataAdaptor;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView=view.findViewById(R.id.cartrecyc);
        textView=view.findViewById(R.id.tex);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressDialog = new ProgressDialog(getContext());

        String s= SharedPreferenceUtil.getInstance(getContext()).getStringValue(KeyConstants.UID,"");
        callApi(s);
        return view;
    }

    private void callApi(String s) {
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        Call<ProductResponse> call= AppRetrofit.getClient().create(ApiServices.class).getcartdata(s);
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                progressDialog.dismiss();
                Log.d("Response",response.toString());
                ProductResponse response1= response.body();
                if(response1.status.equals("true")){
                    textView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    dataAdaptor=new CartDataAdaptor(getContext(),response1.data);
                    recyclerView.setAdapter(dataAdaptor);
                }else{
                    textView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    Toast.makeText(getContext(), response1.msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d("Error",t.toString());
                progressDialog.dismiss();
            }
        });
    }
}