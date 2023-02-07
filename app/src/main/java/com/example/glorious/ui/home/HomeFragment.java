package com.example.glorious.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glorious.R;
import com.example.glorious.network.ApiServices;
import com.example.glorious.network.AppRetrofit;
import com.example.glorious.response.ProductResponse;
import com.example.glorious.response.SlideResponse;
import com.example.glorious.signin.SignInActivity;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private RecyclerView productRecyclerView;
    private ProductResponse productList;
    private  SlideResponse slideResponse;
    private RecyclerViewAdapter productAdapter;
    SearchView searchView;
    SliderView sliderView;
    TextView textView;
    ProgressDialog progressDialog;


    GridLayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        productRecyclerView=view.findViewById(R.id.recyc);
        searchView=view.findViewById(R.id.seachItem);
        sliderView =view.findViewById(R.id.image_slider);
        textView=view.findViewById(R.id.textView1);
        layoutManager=new GridLayoutManager(getContext(),2);
        // at last set adapter to recycler view.
        productRecyclerView.setLayoutManager(layoutManager);
        progressDialog = new ProgressDialog(getContext());
//slider Adaptor




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
        callApi();
        sliderApi();

        return view;
    }

    private void sliderApi() {
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        Call<SlideResponse> call=AppRetrofit.getClient().create(ApiServices.class).getSlider();
        call.enqueue(new Callback<SlideResponse>() {
            @Override
            public void onResponse(Call<SlideResponse> call, Response<SlideResponse> response) {
                Log.d("Slide response",response.toString());
                progressDialog.dismiss();
                slideResponse=response.body();
                SliderAdapter sliderAdapter = new SliderAdapter(getActivity(),slideResponse.data);
                sliderView.setSliderAdapter(sliderAdapter);
                sliderView.setIndicatorAnimation(IndicatorAnimationType.SCALE);
                sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                sliderView.startAutoCycle();
            }

            @Override
            public void onFailure(Call<SlideResponse> call, Throwable t) {
                Log.d("Slide error",t.toString());
                progressDialog.dismiss();
            }
        });

    }

    private void callApi() {

        Call<ProductResponse> call=AppRetrofit.getClient().create(ApiServices.class).getProduct();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                Log.d("data",response.toString());
                productList=  response.body();
                if(productList.status.equals("true")) {
                    textView.setVisibility(View.GONE);
                    productRecyclerView.setVisibility(View.VISIBLE);
                    productAdapter = new RecyclerViewAdapter(getContext(), productList.data);
                    productRecyclerView.setAdapter(productAdapter);
                }
                else{
                    textView.setVisibility(View.VISIBLE);
                    productRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d("data",t+"");
            }
        });
    }


}