package com.elkhelj.ecommerece.activities_fragments.activity_home.fragments.fragmentmaim;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_home.HomeStoreActivity;
import com.elkhelj.ecommerece.adapters.Catogry_Adapter;
import com.elkhelj.ecommerece.adapters.MenAds_Adapter;
import com.elkhelj.ecommerece.databinding.FragmentMenBinding;
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

public class Fragment_Men extends Fragment {
    private static Dialog dialog;
    private HomeStoreActivity activity;
    private FragmentMenBinding binding;
    private Preferences preferences;
    private UserModel userModel;
private List<Home_Model.Categories> categoriesList;
private Catogry_Adapter catogry_adapter;
private List<Home_Model.Products> productsList;
private MenAds_Adapter menAds_adapter;
    public static Fragment_Men newInstance() {
        return new Fragment_Men();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_men, container, false);
        initView();
getECPLORE();
        return binding.getRoot();
    }



    private void initView() {
categoriesList=new ArrayList<>();
productsList=new ArrayList<>();
        activity = (HomeStoreActivity) getActivity();
        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        catogry_adapter=new Catogry_Adapter(categoriesList,activity,null);
        menAds_adapter=new MenAds_Adapter(productsList,activity);
binding.recviewdepart.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL,false));
binding.recviewdepart.setAdapter(catogry_adapter);
        binding.recMarket.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL,false));
binding.recMarket.setAdapter(menAds_adapter);



    }
    private void getECPLORE()
    {
        binding.progBar.setVisibility(View.VISIBLE);

        Api.getService(Tags.base_url)
                .getproductss("men")
                .enqueue(new Callback<Home_Model>() {
                    @Override
                    public void onResponse(Call<Home_Model> call, Response<Home_Model> response) {
                        binding.progBar.setVisibility(View.GONE);
                        if (response.isSuccessful() && response.body() != null ) {
                          update(response.body());
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
                    public void onFailure(Call<Home_Model> call, Throwable t) {
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

    private void update(Home_Model body) {

        if(body.getCategories()!=null){
            Log.e("datas",body.getCategories().get(0).getName());
        categoriesList.clear();
        categoriesList.addAll(body.getCategories());
        catogry_adapter.notifyDataSetChanged();

      }
        if(body.getAds()!=null){
            productsList.clear();
            productsList.addAll(body.getAds());
            menAds_adapter.notifyDataSetChanged();
        if (productsList.size() > 0) {
            binding.tvNoEvents.setVisibility(View.GONE);
        } else {
            binding.tvNoEvents.setVisibility(View.VISIBLE);

        }}

    }

}
