package com.elkhelj.ecommerece.activities_fragments.activity_adsdetails.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_adsdetails.AdsDetialsActivity;
import com.elkhelj.ecommerece.activities_fragments.activity_home.HomeStoreActivity;
import com.elkhelj.ecommerece.databinding.FragmentDetialsBinding;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.preferences.Preferences;

import io.paperdb.Paper;

public class Fragment_Detials extends Fragment {
    private AdsDetialsActivity activity;
    private FragmentDetialsBinding binding;
    private Preferences preferences;
    private UserModel userModel;

    public static Fragment_Detials newInstance() {
        return new Fragment_Detials();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detials, container, false);
        initView();
        return binding.getRoot();
    }



    private void initView() {

        activity = (AdsDetialsActivity) getActivity();
        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);




    }


    public void setdesc(String desc) {
       binding.tvdesc.setText(desc);

    }


}
