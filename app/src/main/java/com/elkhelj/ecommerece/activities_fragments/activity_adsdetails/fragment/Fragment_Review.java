package com.elkhelj.ecommerece.activities_fragments.activity_adsdetails.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_adsdetails.AdsDetialsActivity;
import com.elkhelj.ecommerece.activities_fragments.activity_home.HomeStoreActivity;
import com.elkhelj.ecommerece.adapters.Rates_Adapter;
import com.elkhelj.ecommerece.databinding.FragmentReviewBinding;
import com.elkhelj.ecommerece.models.Single_Adversiment_Model;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class Fragment_Review extends Fragment {
    private AdsDetialsActivity activity;
    private FragmentReviewBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private List<Single_Adversiment_Model.Products.Rates> ratesList;
    private Rates_Adapter rates_adapter;

    public static Fragment_Review newInstance() {
        return new Fragment_Review();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_review, container, false);
        initView();
        return binding.getRoot();
    }


    private void initView() {
        ratesList = new ArrayList<>();
        activity = (AdsDetialsActivity) getActivity();
        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);

        rates_adapter = new Rates_Adapter(ratesList, activity);
        binding.recview.setLayoutManager(new LinearLayoutManager(activity));
        binding.recview.setAdapter(rates_adapter);


    }


    public void setdesc(List<Single_Adversiment_Model.Products.Rates> rates, Single_Adversiment_Model.Products products) {
        binding.tvprice.setText(products.getPrice());
        binding.tvName.setText(products.getName());
        ratesList.clear();
        ratesList.addAll(rates);
        rates_adapter.notifyDataSetChanged();
    }

    public void setdesc(Single_Adversiment_Model.Products singelproduct) {
        binding.tvprice.setText(singelproduct.getPrice());
        binding.tvName.setText(singelproduct.getName());

    }
}
