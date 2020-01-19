package com.elkhelj.ecommerece.activities_fragments.activity_adsdetails;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_adsdetails.fragment.Fragment_AdShop;
import com.elkhelj.ecommerece.activities_fragments.activity_adsdetails.fragment.Fragment_Detials;
import com.elkhelj.ecommerece.activities_fragments.activity_adsdetails.fragment.Fragment_Review;
import com.elkhelj.ecommerece.adapters.ColorsAdapter;
import com.elkhelj.ecommerece.adapters.MaLike_Product_Adapter;
import com.elkhelj.ecommerece.adapters.SingleAdsSlidingImage_Adapter;
import com.elkhelj.ecommerece.adapters.SizeAdapter;
import com.elkhelj.ecommerece.adapters.ViewPagerAdapter;
import com.elkhelj.ecommerece.databinding.ActivityProductDetialsBinding;
import com.elkhelj.ecommerece.interfaces.Listeners;
import com.elkhelj.ecommerece.language.LanguageHelper;
import com.elkhelj.ecommerece.models.Orders_Cart_Model;
import com.elkhelj.ecommerece.models.Single_Adversiment_Model;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.preferences.Preferences;
import com.elkhelj.ecommerece.remote.Api;
import com.elkhelj.ecommerece.share.Common;
import com.elkhelj.ecommerece.tags.Tags;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdsDetialsActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityProductDetialsBinding binding;
    private String lang;


    private Preferences preferences;
    private UserModel userModel;
private String search_id;
    private int current_page = 0, NUM_PAGES;
private ColorsAdapter colorsAdapter;
private SizeAdapter sizeAdapter;
private List<Single_Adversiment_Model.Products.Youmaylike> youmaylikeList;
private MaLike_Product_Adapter maLike_product_adapter;
    private SingleAdsSlidingImage_Adapter singleslidingImage__adapter;
    private Single_Adversiment_Model single_adversiment_model;
private List<Single_Adversiment_Model.Products.Colors> colorsList;
private List<Single_Adversiment_Model.Products.Sizes> sizesList;
    private Single_Adversiment_Model.Products.Colors colors;
    private Single_Adversiment_Model.Products.Sizes sizes;
    private List<Fragment> fragmentList;
    private List<String> titles;
    private ViewPagerAdapter adapter;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(LanguageHelper.updateResources(newBase, Paper.book().read("lang", Locale.getDefault().getLanguage())));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detials);
        initView();
        if(search_id!=null){
        getsingleads();

        }
        change_slide_image();


    }
    private void change_slide_image() {
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (current_page == NUM_PAGES) {
                    current_page = 0;
                }
                binding.pager.setCurrentItem(current_page++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
    }

    private void initView() {
if(getIntent().getIntExtra("search",-1)!=0){
    search_id=getIntent().getIntExtra("search",-1)+"";
}
colorsList=new ArrayList<>();
sizesList=new ArrayList<>();
youmaylikeList=new ArrayList<>();
        single_adversiment_model=new Single_Adversiment_Model();
        preferences=  Preferences.newInstance();
        userModel=preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.setBackListener(this);

colorsAdapter=new ColorsAdapter(colorsList,this);
sizeAdapter=new SizeAdapter(sizesList,this);
maLike_product_adapter=new MaLike_Product_Adapter(youmaylikeList,this);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        binding.progBar.setVisibility(View.GONE);
binding.recColor.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
binding.recColor.setAdapter(colorsAdapter);
binding.recSize.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
binding.recSize.setAdapter(sizeAdapter);
binding.recmay.setLayoutManager(new
        GridLayoutManager(this,3));
binding.recmay.setAdapter(maLike_product_adapter);
binding.frAddcart.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(colors!=null&&sizes!=null){
            addtocart();
        }
        else {
            if(colors==null){
                Toast.makeText(AdsDetialsActivity.this,getResources().getString(R.string.choose_color),Toast.LENGTH_LONG).show();
            }
         if(sizes==null){
            Toast.makeText(AdsDetialsActivity.this,getResources().getString(R.string.choose_size),Toast.LENGTH_LONG).show();
        }}
    }
});
        fragmentList = new ArrayList<>();
        titles = new ArrayList<>();
        binding.tab.setupWithViewPager(binding.pager2);
        addFragments_Titles();
        binding.pager2.setOffscreenPageLimit(fragmentList.size());

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragments(fragmentList);
        adapter.addTitles(titles);
        binding.pager2.setAdapter(adapter);
        binding.tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void addtocart() {
        List<Orders_Cart_Model> orders_cart_models;
        if(preferences.getUserOrder(this)!=null){
            orders_cart_models=preferences.getUserOrder(this);
        }
        else {
            orders_cart_models=new ArrayList<>();
        }
        Orders_Cart_Model orders_cart_model=new Orders_Cart_Model();
        orders_cart_model.setColor_id(colors.getId());
        orders_cart_model.setColor_name(colors.getName());
        orders_cart_model.setSize_id(sizes.getId());
        orders_cart_model.setSize_name(sizes.getName());
        orders_cart_model.setImage(single_adversiment_model.getSingelproduct().getImage());
orders_cart_model.setPrice(single_adversiment_model.getSingelproduct().getPrice());
orders_cart_model.setName(single_adversiment_model.getSingelproduct().getName());
orders_cart_model.setProduct_id(single_adversiment_model.getSingelproduct().getId());
orders_cart_model.setAmount(1);
orders_cart_models.add(orders_cart_model);
preferences.create_update_order(this,orders_cart_models);
Toast.makeText(AdsDetialsActivity.this,getResources().getString(R.string.suc),Toast.LENGTH_LONG).show();
    }


    public void getsingleads() {
        //   Common.CloseKeyBoard(homeActivity, edt_name);

        ProgressDialog dialog = Common.createProgressDialog(AdsDetialsActivity.this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        // rec_sent.setVisibility(View.GONE);
        try {


            Api.getService( Tags.base_url)
                    .getSingleAds(search_id,userModel.getId()+"")
                    .enqueue(new Callback<Single_Adversiment_Model>() {
                        @Override
                        public void onResponse(Call<Single_Adversiment_Model> call, Response<Single_Adversiment_Model> response) {
                            dialog.dismiss();

                          //  binding.progBar.setVisibility(View.GONE);
                            if (response.isSuccessful() && response.body() != null && response.body() != null) {
                                //binding.coord1.scrollTo(0,0);

                                update(response.body());
                            } else {


                                Toast.makeText(AdsDetialsActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                try {
                                    Log.e("Error_code", response.code() + "_" + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Single_Adversiment_Model> call, Throwable t) {
                            try {

dialog.dismiss();

                                Toast.makeText(AdsDetialsActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                Log.e("error", t.getMessage());
                            } catch (Exception e) {
                            }
                        }
                    });
        }catch (Exception e){

dialog.dismiss();
        }
    }


    private void update(Single_Adversiment_Model body) {
        this.single_adversiment_model=body;


Fragment_Detials fragment_detials= (Fragment_Detials) fragmentList.get(0);
fragment_detials.setdesc(body.getSingelproduct().getDes());
        Fragment_AdShop fragment_adShop= (Fragment_AdShop) fragmentList.get(1);
        fragment_adShop.setdesc(body.getSingelproduct());
        if(body.getSingelproduct().getProduct_images()!=null&&body.getSingelproduct().getProduct_images().size()>0){
            Log.e("lll",body.getSingelproduct().getProduct_images().size()+"");
            NUM_PAGES = body.getSingelproduct().getProduct_images().size();
            singleslidingImage__adapter = new SingleAdsSlidingImage_Adapter(this, body.getSingelproduct().getProduct_images());
            binding.pager.setAdapter(singleslidingImage__adapter);
        }
        if(body.getSingelproduct().getColors()!=null){
            colorsList.clear();
            colorsList.addAll(body.getSingelproduct().getColors());
            colorsAdapter.notifyDataSetChanged();
        }
        if(body.getSingelproduct().getSizes()!=null){
            sizesList.clear();
            sizesList.addAll(body.getSingelproduct().getSizes());
            sizeAdapter.notifyDataSetChanged();
        }
        if(single_adversiment_model.getSingelproduct().getYoumaylike()!=null){
            youmaylikeList.clear();
            youmaylikeList.addAll(body.getSingelproduct().getYoumaylike());
            maLike_product_adapter.notifyDataSetChanged();
        }
    }



    @Override
    public void back() {
        finish();
    }

    public void setcolor(Single_Adversiment_Model.Products.Colors colors) {
        this.colors=colors;
    }

    public void setsize(Single_Adversiment_Model.Products.Sizes sizes) {
        this.sizes=sizes;
    }
    private void addFragments_Titles() {
        fragmentList.add(Fragment_Detials.newInstance());
        fragmentList.add(Fragment_AdShop.newInstance());
        fragmentList.add(Fragment_Review.newInstance());

        titles.add(getString(R.string.detials));
        titles.add(getString(R.string.shop));
        titles.add(getString(R.string.review));


    }

    public void showdetials(int id) {
if(userModel!=null){
            Intent intent=new Intent(this, AdsDetialsActivity.class);
            intent.putExtra("search",id);
            startActivity(intent);

    }}
}
