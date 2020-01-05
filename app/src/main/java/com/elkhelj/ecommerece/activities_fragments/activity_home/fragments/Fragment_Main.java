package com.elkhelj.ecommerece.activities_fragments.activity_home.fragments;

import android.app.Dialog;
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
import com.elkhelj.ecommerece.adapters.ViewPagerAdapter;
import com.elkhelj.ecommerece.databinding.FragmentMainBinding;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class Fragment_Main extends Fragment {
    private static Dialog dialog;
    private HomeStoreActivity activity;
    private FragmentMainBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private List<Fragment> fragmentList;
    private List<String> titles;
    private ViewPagerAdapter adapter;
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
      //  fragmentList.add(Fragment_Current_Order.newInstance());
        //fragmentList.add(Fragment_Finshied_Order.newInstance());


        //titles.add(getString(R.string.current_order));
        //titles.add(getString(R.string.finish_order));



    }

}
