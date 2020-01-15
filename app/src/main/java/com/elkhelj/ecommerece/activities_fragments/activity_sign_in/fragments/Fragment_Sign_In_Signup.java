package com.elkhelj.ecommerece.activities_fragments.activity_sign_in.fragments;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_home.HomeStoreActivity;
import com.elkhelj.ecommerece.activities_fragments.activity_sign_in.activities.SignInActivity;
import com.elkhelj.ecommerece.databinding.FragmentSignInSignupBinding;
import com.elkhelj.ecommerece.models.LoginModel;
import com.elkhelj.ecommerece.preferences.Preferences;
import com.mukesh.countrypicker.CountryPicker;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Sign_In_Signup extends Fragment  {
    private FragmentSignInSignupBinding binding;
    private SignInActivity activity;
    private String current_language;
    private Preferences preferences;
    private CountryPicker countryPicker;
    private LoginModel loginModel;

    @Override
    public View onCreateView(@androidx.annotation.NonNull LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in_signup, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        loginModel = new LoginModel();
        activity = (SignInActivity) getActivity();
        preferences = Preferences.newInstance();
        Paper.init(activity);
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(current_language);
        binding.tvsignup.setPaintFlags(binding.tvsignup.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

binding.btnLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        activity.DisplayFragmentSignIn();
    }
});

binding.tvsignup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        activity.DisplayFragmentPhone();
    }
});
binding.btnGuest.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, HomeStoreActivity.class);
        startActivity(intent);

    }
});

    }



    public static Fragment_Sign_In_Signup newInstance() {
        return new Fragment_Sign_In_Signup();
    }






}
