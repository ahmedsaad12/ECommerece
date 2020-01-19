package com.elkhelj.ecommerece.activities_fragments.activity_adsdetails.fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_adsdetails.AdsDetialsActivity;
import com.elkhelj.ecommerece.activities_fragments.activity_home.HomeStoreActivity;
import com.elkhelj.ecommerece.databinding.FragmentAdshopBinding;
import com.elkhelj.ecommerece.models.Single_Adversiment_Model;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.preferences.Preferences;
import com.elkhelj.ecommerece.tags.Tags;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class Fragment_AdShop extends Fragment {
    private AdsDetialsActivity activity;
    private FragmentAdshopBinding binding;
    private Preferences preferences;
    private UserModel userModel;

    public static Fragment_AdShop newInstance() {
        return new Fragment_AdShop();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_adshop, container, false);
        initView();
        return binding.getRoot();
    }



    private void initView() {

        activity = (AdsDetialsActivity) getActivity();
        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);
      //  binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);




    }


    public void setdesc(Single_Adversiment_Model.Products product) {
       binding.tvname.setText(product.getName());
        Picasso.with(activity).load(Tags.base_url+product.getImage()).fit().into(binding.image);
    }
}
