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
import okhttp3.ResponseBody;
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
    private Market_Profile_Model marketprofile;

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
userModel=preferences.getUserData(this);
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
binding.follow.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        followads();
    }
});
binding.like.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Likeads();
    }
});
        if(userModel!=null){
        getprofiledata();}
binding.rateBar.setOnRatingBarChangeListener(new SimpleRatingBar.OnRatingBarChangeListener() {
    @Override
    public void onRatingChanged(SimpleRatingBar simpleRatingBar, float rating, boolean fromUser) {
        if(marketprofile.getUser().getCan_rate()==1){
        rateuser((int) rating);}
        else {
            SimpleRatingBar.AnimationBuilder builder = binding.rateBar.getAnimationBuilder()
                    .setRatingTarget((float) 0.0)
                    .setDuration(1000)
                    .setRepeatCount(0)
                    .setInterpolator(new LinearInterpolator());
            builder.start();
            Toast.makeText(MarketProfileActivity.this,"You cannot rate",Toast.LENGTH_LONG).show();
        }
    }
});

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
        this.marketprofile=body;
        if(body.getCategoriesBoths()!=null){
            adModels.addAll(body.getCategoriesBoths());
            myAdsAdapter.notifyDataSetChanged();
        }
        if(body.getFollowers()!=null) {
            binding.tvfollow.setText(body.getFollowers().size()+body.getUser().getIs_following()+"");
        }
        else {
            binding.tvfollow.setText(body.getUser().getIs_following());

        }
        if(body.getUser().getIs_following()==1){
            binding.tvfollow2.setText("unfollow");
        }
        else {
            binding.tvfollow2.setText(getResources().getString(R.string.following));

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
if(body.getUser().getIs_like()==1){
    binding.tvlike.setText("1");
}
else {
    binding.tvlike.setText("2");

}
if(body.getMyLastRate()!=null){
    SimpleRatingBar.AnimationBuilder builder = binding.rateBar.getAnimationBuilder()
            .setRatingTarget((float) body.getMyLastRate().getRate())
            .setDuration(1000)
            .setRepeatCount(0)
            .setInterpolator(new LinearInterpolator());
    builder.start();
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
    public void Likeads() {
        //   Common.CloseKeyBoard(homeActivity, edt_name);

        ProgressDialog dialog = Common.createProgressDialog(MarketProfileActivity.this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        // rec_sent.setVisibility(View.GONE);
        try {


            Api.getService( Tags.base_url)
                    .Like(other_id,userModel.getId()+"")
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            dialog.dismiss();

                            //  binding.progBar.setVisibility(View.GONE);
                            if (response.isSuccessful() && response.body() != null && response.body() != null) {
                                //binding.coord1.scrollTo(0,0);

//getsingleads();

                                if(marketprofile.getUser().getIs_like()==1){
                                    marketprofile.getUser().setIs_like(0);
                                }
                                else {
                                    marketprofile.getUser().setIs_like(1);
                                }
                                updateprofile(marketprofile);
                            } else {


                                Toast.makeText(MarketProfileActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                try {
                                    Log.e("Error_code", response.code() + "_" + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            try {

                                dialog.dismiss();

                                Toast.makeText(MarketProfileActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                Log.e("error", t.getMessage());
                            } catch (Exception e) {
                            }
                        }
                    });
        }catch (Exception e){

            dialog.dismiss();
        }
    }
    public void followads() {
        //   Common.CloseKeyBoard(homeActivity, edt_name);
Log.e("ffh",userModel.getId()+" "+other_id);
        ProgressDialog dialog = Common.createProgressDialog(MarketProfileActivity.this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        // rec_sent.setVisibility(View.GONE);
        try {


            Api.getService( Tags.base_url)
                    .follow(other_id,userModel.getId()+"")
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            dialog.dismiss();

                            //  binding.progBar.setVisibility(View.GONE);
                            if (response.isSuccessful() && response.body() != null && response.body() != null) {
                                //binding.coord1.scrollTo(0,0);

//getsingleads();

                                if(marketprofile.getUser().getIs_following()==1){
                                    marketprofile.getUser().setIs_following(0);
                                }
                                else {
                                    marketprofile.getUser().setIs_following(1);
                                }

updateprofile(marketprofile);
                            } else {


                                Toast.makeText(MarketProfileActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                try {
                                    Log.e("Error_code", response.code() + "_" + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            try {

                                dialog.dismiss();

                                Toast.makeText(MarketProfileActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                Log.e("error", t.getMessage());
                            } catch (Exception e) {
                            }
                        }
                    });
        }catch (Exception e){

            dialog.dismiss();
        }
    }
    private void rateuser(int rate) {
        ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        // rec_sent.setVisibility(View.GONE);
        try {


            Api.getService(Tags.base_url)
                    .rate( userModel.getId() + "",other_id,rate)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            dialog.dismiss();

                            //  binding.progBar.setVisibility(View.GONE);
                            if (response.isSuccessful() && response.body() != null ) {
                                //binding.coord1.scrollTo(0,0);
                                SimpleRatingBar.AnimationBuilder builder = binding.rateBar.getAnimationBuilder()
                                        .setRatingTarget((float) rate)
                                        .setDuration(1000)
                                        .setRepeatCount(0)
                                        .setInterpolator(new LinearInterpolator());
                                builder.start();
                            } else {
                                SimpleRatingBar.AnimationBuilder builder = binding.rateBar.getAnimationBuilder()
                                        .setRatingTarget((float) 0.0)
                                        .setDuration(1000)
                                        .setRepeatCount(0)
                                        .setInterpolator(new LinearInterpolator());
                                builder.start();
                                if(response.code()==422){
                                    Toast.makeText(MarketProfileActivity.this, getString(R.string.you_rate_this_user_before), Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(MarketProfileActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();}
                                try {
                                    Log.e("Error_code", response.code() + "_" + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            try {

                                dialog.dismiss();

                                Toast.makeText(MarketProfileActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                Log.e("error", t.getMessage());
                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {

            dialog.dismiss();
        }
    }



}
