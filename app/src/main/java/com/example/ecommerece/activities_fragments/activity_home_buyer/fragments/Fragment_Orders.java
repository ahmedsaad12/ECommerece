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
import com.example.ecommerece.databinding.FragmentFollowingBinding;
import com.example.ecommerece.databinding.FragmentOrdersBinding;
import com.example.ecommerece.models.UserModel;
import com.example.ecommerece.preferences.Preferences;

public class Fragment_Orders extends Fragment {

    private HomeBuyerActivity activity;
    private FragmentOrdersBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private boolean isLoading = false;
    private int current_page2 = 1;
    public static Fragment_Orders newInstance() {
        return new Fragment_Orders();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeBuyerActivity) getActivity();
        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(activity);

    }

}
