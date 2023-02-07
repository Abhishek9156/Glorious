package com.example.glorious.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.glorious.R;
import com.example.glorious.util.KeyConstants;
import com.example.glorious.util.SharedPreferenceUtil;


public class ProfileFragment extends Fragment {

    TextView name,email,mobile,doj,uid;
    String fname,lname;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        name=view.findViewById(R.id.tvName);
        email=view.findViewById(R.id.tvEmail);
        mobile=view.findViewById(R.id.tvMobileNo);
        doj=view.findViewById(R.id.tvCompany);
        uid=view.findViewById(R.id.tvDOJ);
        fname=SharedPreferenceUtil.getInstance(getContext()).getStringValue(KeyConstants.FNAME,"");
        lname=SharedPreferenceUtil.getInstance(getContext()).getStringValue(KeyConstants.LNAME,"");
        name.setText(fname+" "+lname);
        email.setText(SharedPreferenceUtil.getInstance(getContext()).getStringValue(KeyConstants.EMAIL,""));
        mobile.setText(SharedPreferenceUtil.getInstance(getContext()).getStringValue(KeyConstants.MOBILE,""));
        uid.setText("User Id "+SharedPreferenceUtil.getInstance(getContext()).getStringValue(KeyConstants.UID,""));
doj.setText("Date of Join "+SharedPreferenceUtil.getInstance(getContext()).getStringValue(KeyConstants.CREATED_AT,""));
        return view;
    }
}