package com.elkhelj.ecommerece.activities_fragments.activity_home.fragments;

import android.app.Dialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_home.HomeStoreActivity;
import com.elkhelj.ecommerece.adapters.Explore_Adapter;
import com.elkhelj.ecommerece.adapters.ViewPagerAdapter;
import com.elkhelj.ecommerece.databinding.FragmentExploreBinding;
import com.elkhelj.ecommerece.databinding.FragmentMainBinding;
import com.elkhelj.ecommerece.models.Home_Model;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.preferences.Preferences;
import com.elkhelj.ecommerece.remote.Api;
import com.elkhelj.ecommerece.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Explore extends Fragment {
    private static Dialog dialog;
    private HomeStoreActivity activity;
    private FragmentExploreBinding binding;
    private Preferences preferences;
    private UserModel userModel;
private List<Home_Model> homeModelList;
private Explore_Adapter explore_adapter;
    public static Fragment_Explore newInstance() {
        return new Fragment_Explore();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_explore, container, false);
        initView();
getECPLORE();
        return binding.getRoot();
    }



    private void initView() {
homeModelList=new ArrayList<>();
        activity = (HomeStoreActivity) getActivity();
        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        explore_adapter=new Explore_Adapter(homeModelList,activity);
binding.recView.setLayoutManager(new GridLayoutManager(activity,1));
binding.recView.setAdapter(explore_adapter);




    }
    private void getECPLORE()
    {
        binding.progBar.setVisibility(View.VISIBLE);

        Api.getService(Tags.base_url)
                .getproducts("brands")
                .enqueue(new Callback<List<Home_Model>>() {
                    @Override
                    public void onResponse(Call<List<Home_Model>> call, Response<List<Home_Model>> response) {
                        binding.progBar.setVisibility(View.GONE);
                        if (response.isSuccessful() && response.body() != null ) {
                            homeModelList.clear();
                            homeModelList.addAll(response.body());
                            explore_adapter.notifyDataSetChanged();

                            if (homeModelList.size() > 0) {
                                binding.tvNoOrder.setVisibility(View.GONE);
                            } else {
                                binding.tvNoOrder.setVisibility(View.VISIBLE);

                            }
                        } else {

                            try {

                                Log.e("error", response.code() + "_" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (response.code() == 500) {
                                Toast.makeText(activity, "Server Error", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Home_Model>> call, Throwable t) {
                        try {
                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage());
                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(activity, R.string.something, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                });
    }

}
