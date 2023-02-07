package com.example.glorious.ui.category;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.glorious.R;
import com.example.glorious.network.ApiServices;
import com.example.glorious.network.AppRetrofit;
import com.example.glorious.response.ProductResponse;
import com.example.glorious.response.SlideResponse;
import com.example.glorious.ui.home.RecyclerViewAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryDetailActivity extends AppCompatActivity {
    private String categoryName;
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    SearchView searchView;
    TextView text;
    private CategoryDetailAdapter productAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
        recyclerView=findViewById(R.id.caterec);
        categoryName = getIntent().getStringExtra("KeyConstants.CategoryDetail");
        Log.d(TAG, "Intent Data:" + categoryName);
        searchView=findViewById(R.id.cateseachItem);
        text=findViewById(R.id.text);
        layoutManager=new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        progressDialog = new ProgressDialog(CategoryDetailActivity.this);

        callApi(categoryName);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(productAdapter==null){

                }else {
                    String text=s;
                    productAdapter.filter(text);
                }
                return false;
            }
        });
    }

    private void callApi(String categoryName) {
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        Call<ProductResponse> call= AppRetrofit.getClient().create(ApiServices.class).getcategorydetail(categoryName);
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                progressDialog.dismiss();

                Log.d("Response",response.toString());
                ProductResponse response1= response.body();
                if(response1.status.equals("true")) {
                    text.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    productAdapter = new CategoryDetailAdapter(getApplicationContext(), response1.data);
                    recyclerView.setAdapter(productAdapter);
                }else {
                    text.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d(String.valueOf(R.string.error_no_internet),t.toString());
                progressDialog.dismiss();

            }
        });
    }
}