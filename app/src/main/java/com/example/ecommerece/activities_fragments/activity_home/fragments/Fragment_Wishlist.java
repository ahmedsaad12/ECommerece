package com.example.ecommerece.activities_fragments.activity_home.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.example.ecommerece.R;
import com.example.ecommerece.activities_fragments.activity_home.HomeStoreActivity;
import com.example.ecommerece.databinding.FragmentWishlistBinding;
import com.example.ecommerece.models.UserModel;
import com.example.ecommerece.preferences.Preferences;

public class Fragment_Wishlist extends Fragment {

    private HomeStoreActivity activity;
    private FragmentWishlistBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private String lang;


    public static Fragment_Wishlist newInstance() {

        return new Fragment_Wishlist();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wishlist, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeStoreActivity) getActivity();
        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(activity);

    }



}
