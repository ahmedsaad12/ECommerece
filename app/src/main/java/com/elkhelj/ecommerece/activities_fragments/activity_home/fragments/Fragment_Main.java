package com.elkhelj.ecommerece.activities_fragments.activity_home.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_home.HomeStoreActivity;
import com.elkhelj.ecommerece.adapters.ViewPagerAdapter;
import com.elkhelj.ecommerece.databinding.FragmentMainBinding;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.preferences.Preferences;
import com.google.android.material.tabs.TabLayout;

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


        binding.tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
          if(tab.getPosition()==0){
              binding.scroll.setBackground(activity.getResources().getDrawable(R.drawable.exploerbackground));
          }
          else if(tab.getPosition()==1){
              binding.scroll.setBackground(activity.getResources().getDrawable(R.drawable.exploerbackground));

          }
          else if(tab.getPosition()==2){
              binding.scroll.setBackgroundColor(activity.getResources().getColor(R.color.white));

          }
          else if(tab.getPosition()==3){
              binding.scroll.setBackground(activity.getResources().getDrawable(R.drawable.womenbackground));

          }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void addFragments_Titles() {
        fragmentList.add(Fragment_Explore.newInstance());
        fragmentList.add(Fragment_Shops.newInstance());
        fragmentList.add(Fragment_Men.newInstance());
        fragmentList.add(Fragment_Women.newInstance());

        titles.add(getString(R.string.explore));
        titles.add(getString(R.string.shops));
        titles.add(getString(R.string.men));
        titles.add(getString(R.string.Women));


    }

}
