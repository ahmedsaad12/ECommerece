package com.elkhelj.ecommerece.activities_fragments.activity_home.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_home.HomeStoreActivity;
import com.elkhelj.ecommerece.databinding.FragmentShopProfileBinding;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.preferences.Preferences;

public class Fragment_Shop_Profile extends Fragment {

    private HomeStoreActivity activity;
    private FragmentShopProfileBinding binding;

    private Preferences preferences;
    private UserModel userModel;

    public static Fragment_Shop_Profile newInstance() {
        return new Fragment_Shop_Profile();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop_profile,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeStoreActivity) getActivity();
        preferences=Preferences.newInstance();
        userModel=preferences.getUserData(activity);





    }


}