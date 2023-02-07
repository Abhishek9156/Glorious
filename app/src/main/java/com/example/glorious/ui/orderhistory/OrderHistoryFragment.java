package com.example.glorious.ui.orderhistory;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glorious.R;
import com.example.glorious.network.ApiServices;
import com.example.glorious.network.AppRetrofit;
import com.example.glorious.response.OrderHistoryResponse;
import com.example.glorious.util.BaseApplication;
import com.example.glorious.util.KeyConstants;
import com.example.glorious.util.SharedPreferenceUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryFragment extends Fragment {
    RecyclerView recyclerView;
    OrderHistoryAdaptor orderHistoryAdaptor;
    String userid;
    TextView textView;
    ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);
        recyclerView=view.findViewById(R.id.orderHistoryRecyc);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        textView=view.findViewById(R.id.odertxt);
        String userid= SharedPreferenceUtil.getInstance(getContext())
                .getStringValue(KeyConstants.UID ,"");
        progressDialog = new ProgressDialog(getContext());
        CallApi(userid);
        return view;
    }

    private void CallApi(String userid) {
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        Call<OrderHistoryResponse> call= AppRetrofit.getClient().create(ApiServices.class).getorderHistory(userid);
        call.enqueue(new Callback<OrderHistoryResponse>() {
            @Override
            public void onResponse(Call<OrderHistoryResponse> call, Response<OrderHistoryResponse> response) {
                Log.d("Response",response.toString());
                progressDialog.dismiss();

                OrderHistoryResponse historyResponse= response.body();
                if(historyResponse.status.equals("true")){
                    recyclerView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                    orderHistoryAdaptor=new OrderHistoryAdaptor(historyResponse.data, getContext());
                    recyclerView.setAdapter(orderHistoryAdaptor);
                }else {
                    recyclerView.setVisibility(View.GONE);
                   textView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<OrderHistoryResponse> call, Throwable t) {
                Log.d("error",t.toString());
                progressDialog.dismiss();

            }
        });
    }
}