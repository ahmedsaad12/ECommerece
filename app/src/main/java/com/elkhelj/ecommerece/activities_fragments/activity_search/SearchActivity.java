package com.elkhelj.ecommerece.activities_fragments.activity_search;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.adapters.Markets_Adapter;
import com.elkhelj.ecommerece.adapters.Wish_Adapter;
import com.elkhelj.ecommerece.databinding.ActivitySearchBinding;
import com.elkhelj.ecommerece.interfaces.Listeners;
import com.elkhelj.ecommerece.language.LanguageHelper;
import com.elkhelj.ecommerece.models.Home_Model;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.models.Wish_Model;
import com.elkhelj.ecommerece.preferences.Preferences;
import com.elkhelj.ecommerece.remote.Api;
import com.elkhelj.ecommerece.share.Common;
import com.elkhelj.ecommerece.tags.Tags;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivitySearchBinding binding;

    private String lang;

    private int cat_id = 0;
    private String query="";
    private UserModel userModel;
    private Preferences preferences;
    private List<Wish_Model> homeModelList;
    private Wish_Adapter explore_adapter;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(LanguageHelper.updateResources(newBase, Paper.book().read("lang", "en")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        initView();
    }

    private void initView()
    {

        homeModelList=new ArrayList<>();

        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setBackListener(this);
        binding.setLang(lang);

        binding.recView.setLayoutManager(new LinearLayoutManager(this));
      //  binding.recView.setAdapter(searchAdapter);
        explore_adapter=new Wish_Adapter(homeModelList,this);
        binding.recView.setLayoutManager(new GridLayoutManager(this,1));
        binding.recView.setAdapter(explore_adapter);

binding.imfilter.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(SearchActivity.this,FilterActivity.class);
        startActivityForResult(intent,1);
    }
});

        binding.edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                query = binding.edtSearch.getText().toString();
                if (!TextUtils.isEmpty(query)) {
                    Common.CloseKeyBoard(this,binding.edtSearch);
                    getECPLORE(query);
                    return false;
                }
                else {
                    binding.edtSearch.setError(getResources().getString(R.string.field_req));
                    return false;
                }
            }
            return false;
        });


    }
    private void getECPLORE(String search)
    {
    //    binding.progBar.setVisibility(View.VISIBLE);
        ProgressDialog dialog = Common.createProgressDialog(this,getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .search(search)
                .enqueue(new Callback<List<Wish_Model>>() {
                    @Override
                    public void onResponse(Call<List<Wish_Model>> call, Response<List<Wish_Model>> response) {
                  //      binding.progBar.setVisibility(View.GONE);
                        dialog.dismiss();
                        if (response.isSuccessful() && response.body() != null ) {
                            Log.e("error", response.code() + "_" + response.body().size());

                            homeModelList.clear();
                            homeModelList.addAll(response.body());
                            explore_adapter.notifyDataSetChanged();

                            if (homeModelList.size() > 0) {
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
                                Toast.makeText(SearchActivity.this, "Server Error", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(SearchActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(SearchActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SearchActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                homeModelList.addAll((Collection<? extends Wish_Model>) extras.getSerializable("returnKey"));
            }
        }
    }
    @Override
    public void back() {
        finish();
    }


}
