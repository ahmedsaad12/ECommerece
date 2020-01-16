package com.elkhelj.ecommerece.activities_fragments.activity_cart;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;


import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.adapters.Cart_Adapter;
import com.elkhelj.ecommerece.databinding.ActivityCartBinding;
import com.elkhelj.ecommerece.interfaces.Listeners;
import com.elkhelj.ecommerece.language.LanguageHelper;
import com.elkhelj.ecommerece.models.Add_Order_Model;
import com.elkhelj.ecommerece.models.Orders_Cart_Model;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.preferences.Preferences;
import com.elkhelj.ecommerece.remote.Api;
import com.elkhelj.ecommerece.share.Common;
import com.elkhelj.ecommerece.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityCartBinding binding;

    private Preferences preferences;
    private UserModel userModel;
    private String lang;
private List<Orders_Cart_Model> order_details;
private Cart_Adapter cart_adapter;
private double totalcost;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(LanguageHelper.updateResources(newBase, Paper.book().read("lang", Locale.getDefault().getLanguage())));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        initView();
getorders();

    }

    private void getorders() {
        if(preferences.getUserOrder(this)!=null){
            order_details.clear();
            order_details.addAll(preferences.getUserOrder(this));
            cart_adapter.notifyDataSetChanged();
        gettotal();
        }
        else {
            binding.llNoStore.setVisibility(View.VISIBLE);
            binding.tvTotal.setVisibility(View.GONE);
            binding.btCom.setVisibility(View.GONE);

        }
    }

    private void gettotal() {
        double total=0;
        for(int i=0;i<order_details.size();i++){
            total+=Double.parseDouble(order_details.get(i).getPrice());
        }
        totalcost=total;
        binding.tvTotal.setText(getResources().getString(R.string.total)+total+"");
    }

    @SuppressLint("RestrictedApi")
    private void initView() {
        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(this);
        binding.toolbar.setTitle("");
order_details=new ArrayList<>();
cart_adapter=new Cart_Adapter(order_details,this);
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.setBackListener(this);
        binding.recCart.setLayoutManager(new GridLayoutManager(this,1));
        binding.recCart.setAdapter(cart_adapter);
binding.btCom.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(userModel!=null){
            checkdata();
        }
        else {
           // Common.CreateNoSignAlertDialog(CartActivity.this);
        }
    }
});

    }

    private void checkdata() {
        Add_Order_Model order_model=new Add_Order_Model();

if(preferences.getUserOrder(this)!=null){
    order_model.setUser_id(userModel.getId());
    order_model.setOrder_detials(preferences.getUserOrder(this));
}

accept_order(order_model);
    }


    public void removeitem(int layoutPosition) {
        order_details.remove(layoutPosition);
        if(order_details.size()>0){

            preferences.create_update_order(this,order_details);
            gettotal();
        }
        else {
            preferences.create_update_order(this,null);
            binding.llNoStore.setVisibility(View.VISIBLE);
            binding.tvTotal.setVisibility(View.GONE);
            binding.btCom.setVisibility(View.GONE);

        }

        cart_adapter.notifyDataSetChanged();

    }
    public void additem(int layoutPosition, int i) {
        Orders_Cart_Model products1 =order_details.get(layoutPosition);
products1.setPrice(((Double.parseDouble(products1.getPrice())/ products1.getAmount())*(i))+"");
        products1.setAmount(i);
        order_details.remove(layoutPosition);
        order_details.add(layoutPosition, products1);

        preferences.create_update_order(this,order_details);
        cart_adapter.notifyDataSetChanged();
        gettotal();
    }

    @Override
    public void back() {
        finish();
    }

    private void accept_order(Add_Order_Model order_model) {

        final ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url).accept_orders(order_model).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                dialog.dismiss();
                if (response.isSuccessful()) {
showorders();
                    // Common.CreateSignAlertDialog(activity, getResources().getString(R.string.sucess));

                  //  activity.refresh(Send_Data.getType());
                } else {
                  //  Common.CreateDialogAlert(CartActivity.this, getString(R.string.failed));

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
                    Toast.makeText(CartActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                    Log.e("Error", t.getMessage());
                } catch (Exception e) {
                }
            }
        });
    }

    private void showorders() {

        preferences.create_update_order(CartActivity.this, null);
        order_details.clear();
        cart_adapter.notifyDataSetChanged();
        binding.llNoStore.setVisibility(View.VISIBLE);
        binding.tvTotal.setVisibility(View.GONE);
        binding.btCom.setVisibility(View.GONE);
    //  Common.CreateDialogAlert2(this,getResources().getString(R.string.suc));
    }



}
