package com.elkhelj.ecommerece.activities_fragments.activity_adsdetails;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.adapters.ColorsAdapter;
import com.elkhelj.ecommerece.adapters.SingleAdsSlidingImage_Adapter;
import com.elkhelj.ecommerece.adapters.SizeAdapter;
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

import org.androidannotations.annotations.sharedpreferences.Pref;
import org.intellij.lang.annotations.Language;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;
import okhttp3.ResponseBody;
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
    private SingleAdsSlidingImage_Adapter singleslidingImage__adapter;
    private Single_Adversiment_Model single_adversiment_model;
private List<Single_Adversiment_Model.Colors> colorsList;
private List<Single_Adversiment_Model.Sizes> sizesList;
    private Single_Adversiment_Model.Colors colors;
    private Single_Adversiment_Model.Sizes sizes;

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
        single_adversiment_model=new Single_Adversiment_Model();
        preferences=  Preferences.newInstance();
        userModel=preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.setBackListener(this);

colorsAdapter=new ColorsAdapter(colorsList,this);
sizeAdapter=new SizeAdapter(sizesList,this);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        binding.progBar.setVisibility(View.GONE);
binding.recColor.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
binding.recColor.setAdapter(colorsAdapter);
binding.recSize.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
binding.recSize.setAdapter(sizeAdapter);
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
        orders_cart_model.setImage(single_adversiment_model.getProducts().getImage());
orders_cart_model.setPrice(single_adversiment_model.getProducts().getPrice());
orders_cart_model.setName(single_adversiment_model.getProducts().getName());
orders_cart_model.setProduct_id(single_adversiment_model.getProducts().getId());
orders_cart_model.setAmount(1);
orders_cart_models.add(orders_cart_model);
preferences.create_update_order(this,orders_cart_models);
    }


    public void getsingleads() {
        //   Common.CloseKeyBoard(homeActivity, edt_name);

        ProgressDialog dialog = Common.createProgressDialog(AdsDetialsActivity.this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        // rec_sent.setVisibility(View.GONE);
        try {


            Api.getService( Tags.base_url)
                    .getSingleAds(search_id)
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



        if(body.getProduct_images()!=null&&body.getProduct_images().size()>0){
            Log.e("lll",body.getProduct_images().size()+"");
            NUM_PAGES = body.getProduct_images().size();
            singleslidingImage__adapter = new SingleAdsSlidingImage_Adapter(this, body.getProduct_images());
            binding.pager.setAdapter(singleslidingImage__adapter);
        }
        if(body.getColors()!=null){
            colorsList.clear();
            colorsList.addAll(body.getColors());
            colorsAdapter.notifyDataSetChanged();
        }
        if(body.getSizes()!=null){
            sizesList.clear();
            sizesList.addAll(body.getSizes());
            sizeAdapter.notifyDataSetChanged();
        }
    }



    @Override
    public void back() {
        finish();
    }

    public void setcolor(Single_Adversiment_Model.Colors colors) {
        this.colors=colors;
    }

    public void setsize(Single_Adversiment_Model.Sizes sizes) {
        this.sizes=sizes;
    }
}
