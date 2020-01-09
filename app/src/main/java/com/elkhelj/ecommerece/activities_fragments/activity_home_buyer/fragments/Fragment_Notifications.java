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
import com.elkhelj.ecommerece.databinding.FragmentNotidicationsBinding;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.preferences.Preferences;

public class Fragment_Notifications extends Fragment {

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


    }



}
