package com.elkhelj.ecommerece.activities_fragments.activity_home.fragments.fragmentmaim;

import android.app.Dialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_home.HomeStoreActivity;
import com.elkhelj.ecommerece.adapters.Markets_Adapter;
import com.elkhelj.ecommerece.databinding.FragmentMarketsBinding;
import com.elkhelj.ecommerece.models.Home_Model;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.preferences.Preferences;
import com.elkhelj.ecommerece.remote.Api;
import com.elkhelj.ecommerece.share.Common;
import com.elkhelj.ecommerece.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Shops extends Fragment {
    private static Dialog dialog;
    private HomeStoreActivity activity;
    private FragmentMarketsBinding binding;
    private Preferences preferences;
    private UserModel userModel;
private List<Home_Model> homeModelList;
private Markets_Adapter explore_adapter;
    private String query;

    public static Fragment_Shops newInstance() {
        return new Fragment_Shops();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_markets, container, false);
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

        explore_adapter=new Markets_Adapter(homeModelList,activity);
binding.recMarket.setLayoutManager(new GridLayoutManager(activity,2));
binding.recMarket.setAdapter(explore_adapter);

        binding.edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                query = binding.edtSearch.getText().toString();
                if (!TextUtils.isEmpty(query)) {
                    Common.CloseKeyBoard(activity,binding.edtSearch);
                    getECPLORE(query);
                    return false;
                }
                else {
                        getECPLORE();
                return false;
                }
            }
            return false;
        });



    }
    private void getECPLORE()
    {
        binding.progBar.setVisibility(View.VISIBLE);

        Api.getService(Tags.base_url)
                .getproducts("shops")
                .enqueue(new Callback<List<Home_Model>>() {
                    @Override
                    public void onResponse(Call<List<Home_Model>> call, Response<List<Home_Model>> response) {
                        binding.progBar.setVisibility(View.GONE);
                        if (response.isSuccessful() && response.body() != null ) {
                            homeModelList.clear();
                            homeModelList.addAll(response.body());
                            explore_adapter.notifyDataSetChanged();

                            if (homeModelList.size() > 0) {
                                binding.tvNoEvents.setVisibility(View.GONE);
                            } else {
                                binding.tvNoEvents.setVisibility(View.VISIBLE);

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
    private void getECPLORE(String search)
    {
        binding.progBar.setVisibility(View.VISIBLE);

        Api.getService(Tags.base_url)
                .getSHOPS(search)
                .enqueue(new Callback<List<Home_Model>>() {
                    @Override
                    public void onResponse(Call<List<Home_Model>> call, Response<List<Home_Model>> response) {
                        binding.progBar.setVisibility(View.GONE);
                        if (response.isSuccessful() && response.body() != null ) {
                            Log.e("error", response.code() + "_" + response.body().size());

                            homeModelList.clear();
                            homeModelList.addAll(response.body());
                            explore_adapter.notifyDataSetChanged();

                            if (homeModelList.size() > 0) {
                                binding.tvNoEvents.setVisibility(View.GONE);
                            } else {
                                binding.tvNoEvents.setVisibility(View.VISIBLE);

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
