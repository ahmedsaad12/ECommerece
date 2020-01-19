package com.elkhelj.ecommerece.activities_fragments.marktprofile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.adapters.Profile_Catogry_Adapter;
import com.elkhelj.ecommerece.adapters.Trends_Adapter;
import com.elkhelj.ecommerece.databinding.ActivityMarketProfileBinding;
import com.elkhelj.ecommerece.interfaces.Listeners;
import com.elkhelj.ecommerece.language.LanguageHelper;
import com.elkhelj.ecommerece.models.Market_Profile_Model;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.preferences.Preferences;
import com.elkhelj.ecommerece.remote.Api;
import com.elkhelj.ecommerece.share.Common;
import com.elkhelj.ecommerece.tags.Tags;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarketProfileActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityMarketProfileBinding binding;
    private String lang;

    private UserModel userModel;
    private Preferences preferences;
private String other_id;
private List<Market_Profile_Model.CategoriesBoth> adModels;
private Profile_Catogry_Adapter myAdsAdapter;
    private List<Market_Profile_Model.Products> maProductsList;
    private Trends_Adapter trends_adapter;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(LanguageHelper.updateResources(newBase, Paper.book().read("lang", Locale.getDefault().getLanguage())));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_market_profile);
getdatafromintent();
        initView();


    }

    private void getdatafromintent() {
        other_id=getIntent().getStringExtra("id");
    }


    private void initView() {
        preferences = Preferences.newInstance();



//    Log.e("y",userModel.getUser().getId()+"");
userModel=new UserModel();
maProductsList=new ArrayList<>();

adModels=new ArrayList<>();

        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.setBackListener(this);
trends_adapter=new Trends_Adapter(maProductsList,this);
binding.recttrends.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
binding.recttrends.setAdapter(trends_adapter);
       // binding.setUsermodel(userModel.getUser());
myAdsAdapter=new Profile_Catogry_Adapter(adModels,this,null);
binding.reccat.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
binding.reccat.setAdapter(myAdsAdapter);

        if(userModel!=null){
        getprofiledata();}


    }





    @Override
    public void back() {
        finish();
    }

    private void getprofiledata() {
       final ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        try {

            Api.getService(Tags.base_url)
                    .getmyprofile(userModel.getId()+"",other_id)
                    .enqueue(new Callback<Market_Profile_Model>() {
                        @Override
                        public void onResponse(Call<Market_Profile_Model> call, Response<Market_Profile_Model> response) {
                            dialog.dismiss();
                            if (response.isSuccessful() && response.body() != null) {
                               updateprofile(response.body());
                            } else {

                                Toast.makeText(MarketProfileActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                                try {

                                    Log.e("error_data5", response.code() + "_" + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<Market_Profile_Model> call, Throwable t) {
                            try {
                                dialog.dismiss();
                                if (t.getMessage() != null) {
                                    Log.e("error", t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(MarketProfileActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MarketProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {
            if(dialog!=null){
            dialog.dismiss();}

           // Log.e("err", e.getMessage());
        }
    }

    private void updateprofile(Market_Profile_Model body) {
        if(body.getCategoriesBoths()!=null){
            adModels.addAll(body.getCategoriesBoths());
            myAdsAdapter.notifyDataSetChanged();
        }
        if(body.getFollowers()!=null){
            binding.tvfollow.setText(body.getFollowers().size()+"");
        }
        Picasso.with(this).load(body.getUser().getImage()).fit().into(binding.image);
if(body.getMyLastRate()!=null){
    SimpleRatingBar.AnimationBuilder builder = binding.rateBar.getAnimationBuilder()
            .setRatingTarget((float) body.getMyLastRate().getRate())
            .setDuration(1000)
            .setRepeatCount(0)
            .setInterpolator(new LinearInterpolator());
    builder.start();}
if(body.getTrends()!=null){
    maProductsList.clear();
    maProductsList.addAll(body.getTrends());
    trends_adapter.notifyDataSetChanged();
}
    }

  /*  private void updateprofile(UserModel userModel) {
        this.userModel=userModel;
        binding.setUsermodel(userModel.getUser());

        if(userModel.getAds()!=null) {
            adModels.clear();

            adModels.addAll(userModel.getAds());
            myAdsAdapter.notifyDataSetChanged();
        }

    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1002){
            //userModel=preferences.getUserData(this);
            //updateprofile(userModel);
          //  getprofiledata();
        }
    }


}
