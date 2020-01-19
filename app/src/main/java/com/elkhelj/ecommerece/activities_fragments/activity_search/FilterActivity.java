package com.elkhelj.ecommerece.activities_fragments.activity_search;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_addproduct.AddPoductActivity;
import com.elkhelj.ecommerece.adapters.BrandsAdapter;
import com.elkhelj.ecommerece.adapters.SizefAdapter;
import com.elkhelj.ecommerece.adapters.Wish_Adapter;
import com.elkhelj.ecommerece.databinding.ActivityFilterBinding;
import com.elkhelj.ecommerece.databinding.ActivitySearchBinding;
import com.elkhelj.ecommerece.interfaces.Listeners;
import com.elkhelj.ecommerece.language.LanguageHelper;
import com.elkhelj.ecommerece.models.Brand_Model;
import com.elkhelj.ecommerece.models.Size_Model;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.models.Wish_Model;
import com.elkhelj.ecommerece.preferences.Preferences;
import com.elkhelj.ecommerece.remote.Api;
import com.elkhelj.ecommerece.share.Common;
import com.elkhelj.ecommerece.tags.Tags;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityFilterBinding binding;

    private String lang;

    private String cat_id = "all";
    private String query="";
    private UserModel userModel;
    private Preferences preferences;
    private List<Brand_Model> homeModelList;
    private List<Size_Model> size_modelList;

    private List<Wish_Model> wish_models;
private SizefAdapter sizefAdapter;
    private BrandsAdapter brandsAdapter;
    private String sizeid="all";


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(LanguageHelper.updateResources(newBase, Paper.book().read("lang", Locale.getDefault().getLanguage())));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter);
        initView();
    }

    private void initView()
    {

        homeModelList=new ArrayList<>();
wish_models=new ArrayList<>();
size_modelList=new ArrayList<>();

        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setBackListener(this);
        binding.setLang(lang);
binding.recbrand.setLayoutManager(new LinearLayoutManager(this));
brandsAdapter=new BrandsAdapter(homeModelList,this);
sizefAdapter=new SizefAdapter(size_modelList,this);
        binding.recbrand.setAdapter(brandsAdapter);
        binding.recmay.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
binding.recmay.setAdapter(sizefAdapter);
getBrand();
getSize();
binding.btnDone.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
getECPLORE();
    }
});



    }


    private void getECPLORE()
    {
        //    binding.progBar.setVisibility(View.VISIBLE);
        ProgressDialog dialog = Common.createProgressDialog(this,getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .search(cat_id,sizeid,binding.rangeSeekbar3.getSelectedMinValue()+"",binding.rangeSeekbar3.getSelectedMaxValue()+"","all","all")
                .enqueue(new Callback<List<Wish_Model>>() {
                    @Override
                    public void onResponse(Call<List<Wish_Model>> call, Response<List<Wish_Model>> response) {
                        //      binding.progBar.setVisibility(View.GONE);
                        dialog.dismiss();
                        if (response.isSuccessful() && response.body() != null ) {
                            Log.e("error", response.code() + "_" + response.body().size());

                            wish_models.clear();
                            wish_models.addAll(response.body());
                            Intent intent = new Intent();
                            intent.putExtra("returnKey", (Serializable) wish_models);
                            setResult(RESULT_OK,intent);
                            finish();
                            if (wish_models.size() > 0) {
                                //  binding.llSearchResult.setVisibility(View.GONE);
                            } else {
                                //binding.llSearchResult.setVisibility(View.VISIBLE);

                            }
                        } else {

                            try {

                                Log.e("error", response.code() + "_" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (response.code() == 500) {
                                Toast.makeText(FilterActivity.this, "Server Error", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(FilterActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Wish_Model>> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage());
                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(FilterActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(FilterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    public void back() {
        finish();
    }
    private void getSize() {
        try {
            ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
            dialog.setCancelable(false);
            dialog.show();
            Api.getService(Tags.base_url)
                    .getSize()
                    .enqueue(new Callback<List<Size_Model>>() {
                        @Override
                        public void onResponse(Call<List<Size_Model>> call, Response<List<Size_Model>> response) {
                            dialog.dismiss();
                            if (response.isSuccessful() && response.body() != null) {
                                if (response.body() != null) {
                                    //updatSizeAdapter(response.body());
                                    size_modelList.clear();
                                    size_modelList.addAll(response.body());
                                    sizefAdapter.notifyDataSetChanged();
                                } else {
                                    Log.e("error", response.code() + "_" + response.errorBody());

                                }

                            } else {

                                try {

                                    Log.e("error", response.code() + "_" + response.errorBody().string());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (response.code() == 500) {
                                    Toast.makeText(FilterActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(FilterActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();


                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Size_Model>> call, Throwable t) {
                            try {
                                dialog.dismiss();
                                if (t.getMessage() != null) {
                                    Log.e("error", t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(FilterActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(FilterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {
        }

    }
    private void getBrand() {
        try {
            ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
            dialog.setCancelable(false);
            dialog.show();
            Api.getService(Tags.base_url)
                    .getBrabnd()
                    .enqueue(new Callback<List<Brand_Model>>() {
                        @Override
                        public void onResponse(Call<List<Brand_Model>> call, Response<List<Brand_Model>> response) {
                            dialog.dismiss();
                            if (response.isSuccessful() && response.body() != null) {
                                if (response.body() != null) {
                                   // updatbrandAdapter(response.body());
                                    homeModelList.clear();
                                    homeModelList.addAll(response.body());
                                    brandsAdapter.notifyDataSetChanged();
                                } else {
                                    Log.e("error", response.code() + "_" + response.errorBody());

                                }

                            } else {

                                try {

                                    Log.e("error", response.code() + "_" + response.errorBody().string());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (response.code() == 500) {
                                    Toast.makeText(FilterActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(FilterActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();


                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Brand_Model>> call, Throwable t) {
                            try {
                                dialog.dismiss();
                                if (t.getMessage() != null) {
                                    Log.e("error", t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(FilterActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(FilterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {
        }

    }


    public void setsize(int id) {
        sizeid=id+"";
    }

    public void setbrand(int id) {
        cat_id=id+"";
    }
}
