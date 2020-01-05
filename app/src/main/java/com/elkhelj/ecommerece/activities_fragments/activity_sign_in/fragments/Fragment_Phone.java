package com.elkhelj.ecommerece.activities_fragments.activity_sign_in.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_home.HomeStoreActivity;
import com.elkhelj.ecommerece.activities_fragments.activity_sign_in.activities.SignInActivity;
import com.elkhelj.ecommerece.databinding.FragmentPhoneBinding;
import com.elkhelj.ecommerece.databinding.FragmentSignInBinding;
import com.elkhelj.ecommerece.interfaces.Listeners;
import com.elkhelj.ecommerece.models.LoginModel;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.preferences.Preferences;
import com.elkhelj.ecommerece.remote.Api;
import com.elkhelj.ecommerece.share.Common;
import com.elkhelj.ecommerece.tags.Tags;
import com.mukesh.countrypicker.CountryPicker;

import java.io.IOException;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Phone extends Fragment implements Listeners.ShowCountryDialogListener {
    private FragmentPhoneBinding binding;
    private SignInActivity activity;
    private String current_language;
    private Preferences preferences;
    private CountryPicker countryPicker;
    private LoginModel loginModel;

    @Override
    public View onCreateView(@androidx.annotation.NonNull LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_phone, container, false);
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
        binding.setShowDialogListener(this);
binding.btnLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        checkdata();
    }
});



    }

    private void checkdata() {
        String phone=binding.edtPhone.getText().toString();
        if(!phone.isEmpty()){
            activity.DisplayFragmentVerfiy(phone);
        }
        else {
            binding.edtPhone.setError(getResources().getString(R.string.field_req));
        }
    }


    public static Fragment_Phone newInstance() {
        return new Fragment_Phone();
    }






    private void navigateToHomeActivity() {
      /*  Intent intent = new Intent(activity, HomeBuyerActivity.class);
        startActivity(intent);
        activity.finish();*/
    }


    @Override
    public void showDialog() {

    }
}
