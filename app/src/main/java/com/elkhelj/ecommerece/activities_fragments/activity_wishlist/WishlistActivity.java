package com.elkhelj.ecommerece.activities_fragments.activity_wishlist;

import android.content.Context;
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
import com.elkhelj.ecommerece.adapters.NotificationAdapter;
import com.elkhelj.ecommerece.adapters.Wish_Adapter;
import com.elkhelj.ecommerece.databinding.ActivityNotificationBinding;
import com.elkhelj.ecommerece.databinding.ActivityWishlistBinding;
import com.elkhelj.ecommerece.interfaces.Listeners;
import com.elkhelj.ecommerece.language.LanguageHelper;
import com.elkhelj.ecommerece.models.NotificationDataModel;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.models.Wish_Model;
import com.elkhelj.ecommerece.preferences.Preferences;
import com.elkhelj.ecommerece.remote.Api;
import com.elkhelj.ecommerece.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistActivity extends AppCompatActivity implements Listeners.BackListener {

    private ActivityWishlistBinding binding;
    private String lang;
    private LinearLayoutManager manager;
    private List<Wish_Model> notificationModelList;
    private Wish_Adapter adapter;
    private Preferences preferences;
    private UserModel userModel;
    private boolean isLoading = false;
    private int current_page = 1;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(LanguageHelper.updateResources(newBase, Paper.book().read("lang", Locale.getDefault().getLanguage())));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wishlist);
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

        adapter = new Wish_Adapter(notificationModelList,this);
        binding.recView.setAdapter(adapter);

        getNotification();
    }


    public void getNotification() {

        try {

            Api.getService(Tags.base_url)
                    .getmylike(userModel.getId())
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
                                    Toast.makeText(WishlistActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(WishlistActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

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
                                        Toast.makeText(WishlistActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(WishlistActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {

        }
    }



    @Override
    public void back() {
        finish();
    }
}
