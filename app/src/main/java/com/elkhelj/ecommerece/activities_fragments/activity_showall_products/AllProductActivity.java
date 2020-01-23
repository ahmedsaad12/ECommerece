package com.elkhelj.ecommerece.activities_fragments.activity_showall_products;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;


import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_adsdetails.AdsDetialsActivity;
import com.elkhelj.ecommerece.activities_fragments.activity_home.HomeStoreActivity;
import com.elkhelj.ecommerece.adapters.Explore_Product_Adapter;
import com.elkhelj.ecommerece.databinding.ActivityMarketsBinding;
import com.elkhelj.ecommerece.interfaces.Listeners;
import com.elkhelj.ecommerece.language.LanguageHelper;
import com.elkhelj.ecommerece.models.Home_Model;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.preferences.Preferences;
import com.elkhelj.ecommerece.share.Common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllProductActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityMarketsBinding binding;

    private Preferences preferences;
    private UserModel userModel;

    private Explore_Product_Adapter markets_adapter;
private List<Home_Model.Products> usersList;
    private String lang;
private Home_Model data;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(LanguageHelper.updateResources(newBase, Paper.book().read("lang", "en")));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_markets);
        getdatafromintent();

        initView();
        if(data!=null){
        usersList.clear();
        usersList.addAll(data.getProducts());
        markets_adapter.notifyDataSetChanged();
        }
    }

    private void getdatafromintent() {
        if(getIntent().getSerializableExtra("cat")!=null){
            data = (Home_Model) getIntent().getSerializableExtra("cat");
        }
    }


    @SuppressLint("RestrictedApi")
    private void initView() {
        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(this);
        usersList=new ArrayList<>();

        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
markets_adapter=new Explore_Product_Adapter(usersList,this);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
binding.progBar.setVisibility(View.GONE);
binding.recMarket.setItemViewCacheSize(25);
binding.recMarket.setDrawingCacheEnabled(true);
binding.recMarket.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        binding.recMarket.setLayoutManager(new GridLayoutManager(this,2));
        binding.recMarket.setAdapter(markets_adapter);
binding.setLang(lang);
binding.setBackListener(this);
if(data!=null) {
  //  binding.setMarketmodel(data);
}
    }

    public void showdetials(int id) {
        Intent intent=new Intent(AllProductActivity.this, AdsDetialsActivity.class);
        intent.putExtra("search",id);
        startActivity(intent);

    }




    @Override
    public void back() {
        finish();
    }
}
