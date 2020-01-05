package com.elkhelj.ecommerece.activities_fragments.activity_sign_in.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_home.HomeStoreActivity;
import com.elkhelj.ecommerece.activities_fragments.activity_sign_in.activities.SignInActivity;
import com.elkhelj.ecommerece.adapters.FilterAdapter;
import com.elkhelj.ecommerece.databinding.FragmentSignUpBinding;
import com.elkhelj.ecommerece.databinding.FragmentSignUpSellerBinding;
import com.elkhelj.ecommerece.interfaces.Listeners;
import com.elkhelj.ecommerece.models.Filter_model;
import com.elkhelj.ecommerece.models.SignUpModel;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.preferences.Preferences;
import com.elkhelj.ecommerece.remote.Api;
import com.elkhelj.ecommerece.share.Common;
import com.elkhelj.ecommerece.tags.Tags;
import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.listeners.OnCountryPickerListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Sign_Up_Seller extends Fragment implements Listeners.SignUpListener,Listeners.BackListener,Listeners.ShowCountryDialogListener, OnCountryPickerListener {
    private SignInActivity activity;
    private String current_language;
    private FragmentSignUpSellerBinding binding;
    private CountryPicker countryPicker;
    private SignUpModel signUpModel;
    private Preferences preferences;

    private List<Filter_model> filter_models;
    private FilterAdapter filterAdapter;
    private Filter_model filter_model1,filter_model2,filter_model3;
    private int gender;
    public static Fragment_Sign_Up_Seller newInstance() {
        return new Fragment_Sign_Up_Seller();
    }
    @Override
    public View onCreateView(@androidx.annotation.NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_seller, container, false);

        initView();
        return binding.getRoot();
    }

    private void initView() {
        signUpModel = new SignUpModel();
        activity = (SignInActivity) getActivity();
        Paper.init(activity);
        preferences = Preferences.newInstance();
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());

        createCountryDialog();

        filter_models=new ArrayList<>();
        setfiltermodels();

        filterAdapter=new FilterAdapter(filter_models,activity);

       binding.spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              if(i==0){

gender=2;

              }
              else if(i==1){
gender=3;
              }
              else if(i==2){
gender=1;
              }
              signUpModel.setGender_id(gender+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





    }



    private void setfiltermodels() {
        filter_model1=new Filter_model();
        filter_model2=new Filter_model();
        filter_model3=new Filter_model();
        filter_model1.setAr_filter("ذكر");
        filter_model1.setEn_filter("male");
        filter_model2.setAr_filter("انثى");
        filter_model2.setEn_filter("Female");
        filter_model3.setAr_filter("اخر");
        filter_model3.setEn_filter("other");
        filter_models.add(filter_model1);
        filter_models.add(filter_model2);
        filter_models.add(filter_model3);
    }


    private void createCountryDialog()
    {
        countryPicker = new CountryPicker.Builder()
                .canSearch(true)
                .listener(this)
                .theme(CountryPicker.THEME_NEW)
                .with(activity)
                .build();

        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);

        if (countryPicker.getCountryFromSIM()!=null)
        {
            updatePhoneCode(countryPicker.getCountryFromSIM());
        }else if (telephonyManager!=null&&countryPicker.getCountryByISO(telephonyManager.getNetworkCountryIso())!=null)
        {
            updatePhoneCode(countryPicker.getCountryByISO(telephonyManager.getNetworkCountryIso()));
        }else if (countryPicker.getCountryByLocale(Locale.getDefault())!=null)
        {
            updatePhoneCode(countryPicker.getCountryByLocale(Locale.getDefault()));
        }else
        {
            String code = "+974";
         //   binding.tvCode.setText(code);
           // signUpModel.set(code.replace("+","00"));

        }

    }

    @Override
    public void showDialog() {

        countryPicker.showDialog(activity);
    }

    @Override
    public void onSelectCountry(Country country) {
        updatePhoneCode(country);

    }

    private void updatePhoneCode(Country country)
    {
       // binding.tvCode.setText(country.getDialCode());
       // signUpModel.setPhone_code(country.getDialCode().replace("+","00"));
     //   signUpModel.setPhone_code("00974");
    }

    @Override
    public void checkDataSignUp(String name, String shopname, String phone,String email, String password,String confirmpassword) {
        if (phone.startsWith("0")) {
            phone = phone.replaceFirst("0", "");
        }
signUpModel.setPhone(phone);
signUpModel.setShop_name(shopname);
signUpModel.setName(name);
signUpModel.setEmail(email);
signUpModel.setPassword(password);
signUpModel.setConfirmpassword(confirmpassword);
        if (signUpModel.isDataValid(activity))
        {
            signUp(signUpModel);
        }
    }

    @Override
    public void back() {
        activity.Back();
    }

    private void signUp(SignUpModel signUpModel) {
        try {
            ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
            dialog.setCancelable(false);
            dialog.show();
            Api.getService(Tags.base_url)
                    .signUp(signUpModel.getName(),signUpModel.getShop_name(),signUpModel.getEmail(),signUpModel.getPassword(),signUpModel.getPhone(),"00974","2",signUpModel.getGender_id())
                    .enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            dialog.dismiss();
                            if (response.isSuccessful() && response.body() != null) {
                               // activity.displayFragmentCodeVerification(response.body(),2);
                                preferences.create_update_userdata(activity,response.body());
                                preferences.create_update_session(activity, Tags.session_login);
                                Intent intent = new Intent(activity, HomeStoreActivity.class);
                                startActivity(intent);
                                activity.finish();
                            } else {
                                if (response.code() == 422) {
                                    Toast.makeText(activity,getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                    try {

                                        Log.e("error",response.code()+"_"+response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                } else if (response.code() == 500) {
                                    Toast.makeText(activity, "Server Error", Toast.LENGTH_SHORT).show();

                                }else if (response.code() == 406) {
                                    Toast.makeText(activity,getString(R.string.em_exist), Toast.LENGTH_SHORT).show();

                                }else
                                {
                                    Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                                    try {

                                        Log.e("error",response.code()+"_"+response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {
                            try {
                                dialog.dismiss();
                                if (t.getMessage() != null) {
                                    Log.e("error", t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(activity, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {
        }
    }

}
