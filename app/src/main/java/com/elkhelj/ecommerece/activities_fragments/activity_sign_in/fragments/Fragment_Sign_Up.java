package com.elkhelj.ecommerece.activities_fragments.activity_sign_in.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_sign_in.activities.SignInActivity;
import com.elkhelj.ecommerece.adapters.ViewPagerAdapter;
import com.elkhelj.ecommerece.databinding.FragmentSignUpBinding;
import com.elkhelj.ecommerece.interfaces.Listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

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

        for(int i=0; i < binding.tab.getTabCount(); i++) {
            View tab = ((ViewGroup) binding.tab.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(50, 0, 50, 0);
            tab.requestLayout();
        }





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
