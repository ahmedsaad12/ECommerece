package com.elkhelj.ecommerece.activities_fragments.activity_sign_in.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_sign_in.activities.SignInActivity;
import com.elkhelj.ecommerece.databinding.FragmentForgetpasswordBinding;
import com.elkhelj.ecommerece.databinding.FragmentPhoneBinding;
import com.elkhelj.ecommerece.interfaces.Listeners;
import com.elkhelj.ecommerece.models.LoginModel;
import com.elkhelj.ecommerece.preferences.Preferences;
import com.mukesh.countrypicker.CountryPicker;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Forgetpass extends Fragment implements Listeners.ShowCountryDialogListener {
    private FragmentForgetpasswordBinding binding;
    private SignInActivity activity;
    private String current_language;
    private Preferences preferences;

    @Override
    public View onCreateView(@androidx.annotation.NonNull LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgetpassword, container, false);
        View view = binding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        activity = (SignInActivity) getActivity();
        preferences = Preferences.newInstance();
        Paper.init(activity);
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(current_language);
        binding.setShowDialogListener(this);




    }



    public static Fragment_Forgetpass newInstance() {
        return new Fragment_Forgetpass();
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
