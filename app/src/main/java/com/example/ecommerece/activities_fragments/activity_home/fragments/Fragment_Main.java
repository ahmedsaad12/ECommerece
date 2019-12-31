package com.example.ecommerece.activities_fragments.activity_home.fragments;

import android.app.Dialog;
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
import com.example.ecommerece.databinding.FragmentMainBinding;
import com.example.ecommerece.models.UserModel;
import com.example.ecommerece.preferences.Preferences;

import io.paperdb.Paper;

public class Fragment_Main extends Fragment {
    private static Dialog dialog;
    private HomeStoreActivity activity;
    private FragmentMainBinding binding;
    private Preferences preferences;
    private UserModel userModel;

    public static Fragment_Main newInstance() {
        return new Fragment_Main();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        initView();

        return binding.getRoot();
    }



    private void initView() {

        activity = (HomeStoreActivity) getActivity();
        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);




    }



}
