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
import com.elkhelj.ecommerece.databinding.FragmentChatBinding;
import com.elkhelj.ecommerece.databinding.FragmentWishlistBinding;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.preferences.Preferences;

public class Fragment_Chat extends Fragment {

    private HomeStoreActivity activity;
    private FragmentChatBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private String lang;


    public static Fragment_Chat newInstance() {

        return new Fragment_Chat();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeStoreActivity) getActivity();
        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(activity);

    }



}
