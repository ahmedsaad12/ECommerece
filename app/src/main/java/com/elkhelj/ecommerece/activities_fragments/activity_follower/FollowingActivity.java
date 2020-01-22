package com.elkhelj.ecommerece.activities_fragments.activity_follower;

import android.app.ProgressDialog;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.marktprofile.MarketProfileActivity;
import com.elkhelj.ecommerece.adapters.Following_Adapter;
import com.elkhelj.ecommerece.adapters.Wish_Adapter;
import com.elkhelj.ecommerece.databinding.ActivityFollowingBinding;
import com.elkhelj.ecommerece.databinding.ActivityWishlistBinding;
import com.elkhelj.ecommerece.interfaces.Listeners;
import com.elkhelj.ecommerece.language.LanguageHelper;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.models.Wish_Model;
import com.elkhelj.ecommerece.preferences.Preferences;
import com.elkhelj.ecommerece.remote.Api;
import com.elkhelj.ecommerece.share.Common;
import com.elkhelj.ecommerece.tags.Tags;

import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowingActivity extends AppCompatActivity implements Listeners.BackListener {

    private ActivityFollowingBinding binding;
    private String lang;
    private LinearLayoutManager manager;
    private List<Wish_Model> notificationModelList;
    private Following_Adapter adapter;
    private Preferences preferences;
    private UserModel userModel;
    private boolean isLoading = false;
    private int current_page = 1;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(LanguageHelper.updateResources(newBase, Paper.book().read("lang", "en")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_following);
        initView();
    }

    private void initView() {
        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setBackListener(this);
        binding.setLang(lang);

        notificationModelList = new ArrayList<>();
        manager = new LinearLayoutManager(this);
        binding.recView.setLayoutManager(manager);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        adapter = new Following_Adapter(notificationModelList,this);
        binding.recView.setAdapter(adapter);

        getNotification();
    }


    public void getNotification() {

        try {

            Api.getService(Tags.base_url)
                    .getmyfollow(userModel.getId())
                    .enqueue(new Callback<List<Wish_Model>>() {
                        @Override
                        public void onResponse(Call<List<Wish_Model>> call, Response<List<Wish_Model>> response) {
                            binding.progBar.setVisibility(View.GONE);
                            if (response.isSuccessful() && response.body() != null && response.body() != null) {
                                notificationModelList.clear();
                                notificationModelList.addAll(response.body());
                                if (notificationModelList.size() > 0) {
                                    adapter.notifyDataSetChanged();
                                    binding.tvNoNotification.setVisibility(View.GONE);
                                } else {
                                    binding.tvNoNotification.setVisibility(View.VISIBLE);

                                }
                            } else {
                                if (response.code() == 500) {
                                    Toast.makeText(FollowingActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(FollowingActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                                    try {

                                        Log.e("error", response.code() + "_" + response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Wish_Model>> call, Throwable t) {
                            try {
                                binding.progBar.setVisibility(View.GONE);

                                if (t.getMessage() != null) {
                                    Log.e("error", t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(FollowingActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(FollowingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {

        }
    }
    public void followads(String other_id,int idex) {
        //   Common.CloseKeyBoard(homeActivity, edt_name);
        Log.e("ffh",userModel.getId()+" "+other_id);
        ProgressDialog dialog = Common.createProgressDialog(FollowingActivity.this, getString(R.string.wait));
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
                                notificationModelList.remove(idex);
                                adapter.notifyDataSetChanged();



                            } else {


                                Toast.makeText(FollowingActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
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

                                Toast.makeText(FollowingActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                Log.e("error", t.getMessage());
                            } catch (Exception e) {
                            }
                        }
                    });
        }catch (Exception e){

            dialog.dismiss();
        }
    }



    @Override
    public void back() {
        finish();
    }

    public void share(Wish_Model wish_model) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, wish_model.getId()+"");
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }
}
