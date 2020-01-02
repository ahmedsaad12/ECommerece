package com.example.ecommerece.activities_fragments.activity_home_buyer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.ecommerece.R;
import com.example.ecommerece.activities_fragments.activity_home_buyer.HomeBuyerActivity;
import com.example.ecommerece.databinding.FragmentNotidicationsBinding;
import com.example.ecommerece.databinding.FragmentWishlistBinding;
import com.example.ecommerece.models.UserModel;
import com.example.ecommerece.preferences.Preferences;

public class Fragment_Notifications extends Fragment {

    private HomeBuyerActivity activity;
    private FragmentNotidicationsBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private String lang;


    public static Fragment_Notifications newInstance() {

        return new Fragment_Notifications();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notidications, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeBuyerActivity) getActivity();
        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(activity);

    }



}