package com.elkhelj.ecommerece.activities_fragments.activity_home_buyer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_home_buyer.HomeBuyerActivity;
import com.elkhelj.ecommerece.databinding.FragmentClientProfileBinding;
import com.elkhelj.ecommerece.databinding.FragmentShopProfileBinding;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.preferences.Preferences;

public class Fragment_Buyer_Profile extends Fragment {

    private HomeBuyerActivity activity;
    private FragmentClientProfileBinding binding;

    private Preferences preferences;
    private UserModel userModel;

    public static Fragment_Buyer_Profile newInstance() {
        return new Fragment_Buyer_Profile();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_client_profile,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeBuyerActivity) getActivity();
        preferences=Preferences.newInstance();
        userModel=preferences.getUserData(activity);





    }


}
