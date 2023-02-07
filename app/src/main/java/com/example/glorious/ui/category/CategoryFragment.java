package com.example.glorious.ui.category;



import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glorious.R;
import com.example.glorious.network.ApiServices;
import com.example.glorious.network.AppRetrofit;
import com.example.glorious.response.CategoryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {
    RecyclerView recyclerView;
    CategoryAdaptor categoryAdaptor;
    GridLayoutManager layoutManager;
    ProgressDialog progressDialog;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        recyclerView=view.findViewById(R.id.recyccat);
        layoutManager=new GridLayoutManager(getContext(),3);
        // at last set adapter to recycler view.
        recyclerView.setLayoutManager(layoutManager);
        progressDialog = new ProgressDialog(getContext());
        callApi();


        return view;
    }

    private void callApi() {
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        Call<CategoryResponse> call= AppRetrofit.getClient().create(ApiServices.class).getallcategory();
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
               Log.d("Category resp",response.toString());
                progressDialog.dismiss();

                CategoryResponse response1= response.body();
                if(response1.status.equals("true")){
                    categoryAdaptor=new CategoryAdaptor(getContext(),response1.data);
                    recyclerView.setAdapter(categoryAdaptor);
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.d("Category error",t.toString());
                progressDialog.dismiss();

            }
        });
    }
}