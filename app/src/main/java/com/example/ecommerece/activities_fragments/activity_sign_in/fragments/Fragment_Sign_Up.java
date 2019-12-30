package com.example.ecommerece.activities_fragments.activity_sign_in.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.example.ecommerece.R;
import com.example.ecommerece.activities_fragments.activity_sign_in.activities.SignInActivity;
import com.example.ecommerece.adapters.ViewPagerAdapter;
import com.example.ecommerece.databinding.FragmentSignUpBinding;
import com.example.ecommerece.interfaces.Listeners;
import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.listeners.OnCountryPickerListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Sign_Up extends Fragment implements Listeners.BackListener {
    private SignInActivity activity;
    private String current_language;
    private FragmentSignUpBinding binding;
    private List<Fragment> fragmentList;
    private List<String> titles;
    private ViewPagerAdapter adapter;

    @Override
    public View onCreateView(@androidx.annotation.NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);

        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (SignInActivity) getActivity();

        Paper.init(activity);
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        fragmentList = new ArrayList<>();
        titles = new ArrayList<>();
        binding.tab.setupWithViewPager(binding.pager);
        addFragments_Titles();
        binding.pager.setOffscreenPageLimit(fragmentList.size());

        adapter = new ViewPagerAdapter(activity.getSupportFragmentManager());
        adapter.addFragments(fragmentList);
        adapter.addTitles(titles);
        binding.pager.setAdapter(adapter);







    }

    private void addFragments_Titles() {
        fragmentList.add(Fragment_Sign_Up_Buyer.newInstance());
        fragmentList.add(Fragment_Sign_Up_Seller.newInstance());


        titles.add(getString(R.string.as_buyer));
        titles.add(getString(R.string.as_seller));



    }

    @Override
    public void back() {
        activity.Back();
    }
    public static Fragment_Sign_Up newInstance() {
        return new Fragment_Sign_Up();
    }


}
