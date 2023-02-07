package com.example.glorious.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.glorious.R;
import com.example.glorious.response.data.SlideData;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.Holder> {
    ArrayList<SlideData> images;
    Context context;
    public SliderAdapter(Context context, ArrayList<SlideData> images){
        this.context=context;
        this.images = images;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slider_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        SlideData s=images.get(position);
        Glide.with(context).load(s.image).placeholder(R.drawable.ic_launcher_background)
                .fitCenter().into(viewHolder.imageView);
       // viewHolder.imageView.setImageResource(images[position]);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    public class Holder extends ViewHolder {
        ImageView imageView;
        public Holder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
