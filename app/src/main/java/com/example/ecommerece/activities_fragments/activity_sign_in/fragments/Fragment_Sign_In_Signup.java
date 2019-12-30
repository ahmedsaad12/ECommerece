package com.example.ecommerece.activities_fragments.activity_sign_in.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.ecommerece.R;
import com.example.ecommerece.activities_fragments.activity_sign_in.activities.SignInActivity;
import com.example.ecommerece.databinding.FragmentSignInBinding;
import com.example.ecommerece.databinding.FragmentSignInSignupBinding;
import com.example.ecommerece.interfaces.Listeners;
import com.example.ecommerece.models.LoginModel;
import com.example.ecommerece.preferences.Preferences;
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
        binding.setLang(current_language);

binding.btnLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        activity.DisplayFragmentSignIn();
    }
});
binding.tvsignup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        activity.DisplayFragmentSignUp();
    }
});


    }



    public static Fragment_Sign_In_Signup newInstance() {
        return new Fragment_Sign_In_Signup();
    }






}
