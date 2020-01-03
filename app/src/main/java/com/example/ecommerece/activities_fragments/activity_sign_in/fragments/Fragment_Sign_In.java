package com.example.ecommerece.activities_fragments.activity_sign_in.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.example.ecommerece.R;
import com.example.ecommerece.activities_fragments.activity_sign_in.activities.SignInActivity;
import com.example.ecommerece.databinding.FragmentSignInBinding;
import com.example.ecommerece.interfaces.Listeners;
import com.example.ecommerece.models.LoginModel;
import com.example.ecommerece.preferences.Preferences;
import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.listeners.OnCountryPickerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Sign_In extends Fragment implements Listeners.LoginListener {
    private FragmentSignInBinding binding;
    private SignInActivity activity;
    private String current_language;
    private Preferences preferences;
    private CountryPicker countryPicker;
    private LoginModel loginModel;

    @Override
    public View onCreateView(@androidx.annotation.NonNull LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false);
        View view = binding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        loginModel = new LoginModel();
        activity = (SignInActivity) getActivity();
        preferences = Preferences.newInstance();
        Paper.init(activity);
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLoginModel(loginModel);
        binding.setLang(current_language);
        binding.setLoginListener(this);
binding.tvForget.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       // activity.displayFragmentForgetpass();
    }
});



    }



    public static Fragment_Sign_In newInstance() {
        return new Fragment_Sign_In();
    }


    @Override
    public void checkDataLogin(String phone_code, String phone, String password) {
        if (phone.startsWith("0")) {
            phone = phone.replaceFirst("0", "");
        }
        loginModel = new LoginModel(phone,password);
        binding.setLoginModel(loginModel);

       if (loginModel.isDataValid(activity))
        {
          //  login(phone_code,phone,password);
        }
      //  navigateToHomeActivity();
    }





    private void navigateToHomeActivity() {
      /*  Intent intent = new Intent(activity, HomeBuyerActivity.class);
        startActivity(intent);
        activity.finish();*/
    }



}
