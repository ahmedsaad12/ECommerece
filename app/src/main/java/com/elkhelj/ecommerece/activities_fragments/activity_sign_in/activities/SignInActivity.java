package com.elkhelj.ecommerece.activities_fragments.activity_sign_in.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;


import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_sign_in.fragments.Fragment_Forgetpass;
import com.elkhelj.ecommerece.activities_fragments.activity_sign_in.fragments.Fragment_Phone;
import com.elkhelj.ecommerece.activities_fragments.activity_sign_in.fragments.Fragment_Sign_In;
import com.elkhelj.ecommerece.activities_fragments.activity_sign_in.fragments.Fragment_Sign_In_Signup;
import com.elkhelj.ecommerece.activities_fragments.activity_sign_in.fragments.Fragment_Sign_Up;
import com.elkhelj.ecommerece.activities_fragments.activity_sign_in.fragments.Fragment_Verfiy;
import com.elkhelj.ecommerece.databinding.ActivitySignInBinding;
import com.elkhelj.ecommerece.language.LanguageHelper;
import com.elkhelj.ecommerece.preferences.Preferences;

import java.util.Locale;

import io.paperdb.Paper;

public class SignInActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private ActivitySignInBinding binding;

    private int fragment_count = 0;
    private Fragment_Sign_In fragment_sign_in;
    private Fragment_Sign_Up fragment_sign_up;
    private String cuurent_language;
    private Preferences preferences;
    private Fragment_Sign_In_Signup fragment_sign_in_signup;
    private Fragment_Phone fragment_phone;
    private Fragment_Verfiy fragment_verfiy;
    private Fragment_Forgetpass fragment_forgetpass;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(LanguageHelper.updateResources(newBase, Paper.book().read("lang", "en")));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        Paper.init(this);


        initView();
        if (savedInstanceState == null) {
            if (!preferences.isLanguageSelected(this))
            {
                //DisplayFragmentLanguage();
            }else
            {
                DisplayFragmentSignInSignup();

            }
            DisplayFragmentSignInSignup();

        }


    }

    private void initView() {
        Paper.init(this);
        preferences = Preferences.newInstance();
        cuurent_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        fragmentManager = this.getSupportFragmentManager();

    }
    public void DisplayFragmentSignInSignup() {
        fragment_count += 1;
        fragment_sign_in_signup = Fragment_Sign_In_Signup.newInstance();
        if (fragment_sign_in_signup.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_sign_in_signup).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_sign_in_container, fragment_sign_in_signup, "fragment_sign_in_signup").addToBackStack("fragment_sign_in_signup").commit();
        }
    }
    public void DisplayFragmentSignIn() {
        fragment_count += 1;
        fragment_sign_in = Fragment_Sign_In.newInstance();
        if (fragment_sign_in.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_sign_in).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_sign_in_container, fragment_sign_in, "fragment_sign_in").addToBackStack("fragment_sign_in").commit();
        }
    }
    public void DisplayFragmentPhone() {
        fragment_count += 1;
        fragment_phone= Fragment_Phone.newInstance();
        if (fragment_phone.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_phone).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_sign_in_container, fragment_phone, "fragment_phone").addToBackStack("fragment_phone").commit();
        }
    }
    public void DisplayFragmentpass() {
        fragment_count += 1;
        fragment_forgetpass= Fragment_Forgetpass.newInstance();
        if (fragment_forgetpass.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_forgetpass).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_sign_in_container, fragment_forgetpass, "fragment_forgetpass").addToBackStack("fragment_forgetpass").commit();
        }
    }
    public void DisplayFragmentVerfiy(String phone) {
        fragment_count += 1;
        fragment_verfiy= Fragment_Verfiy.newInstance(phone);
        if (fragment_verfiy.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_verfiy).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_sign_in_container, fragment_verfiy, "fragment_verfiy").addToBackStack("fragment_verfiy").commit();
        }
    }
    public void DisplayFragmentSignUp() {
        fragment_count += 1;
        fragment_sign_up = Fragment_Sign_Up.newInstance();
        if (fragment_sign_up.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_sign_up).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_sign_in_container, fragment_sign_up, "fragment_sign_up").addToBackStack("fragment_sign_up").commit();
        }
    }
    /*
        public void DisplayFragmentLanguage() {
            fragment_language = Fragment_Language.newInstance();
            if (fragment_language.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_language).commit();
            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_sign_in_container, fragment_language, "fragment_language").addToBackStack("fragment_language").commit();
            }
        }
        public void displayFragmentForgetpass() {
            fragment_count ++;
            fragment_forgetpass = Fragment_ForgetPassword.newInstance();

            fragmentManager.beginTransaction().add(R.id.fragment_sign_in_container, fragment_forgetpass, "fragment_forgetpass").addToBackStack("fragment_forgetpass").commit();

        }

        public void displayFragmentCodeVerification(UserModel userModel, int type) {
            fragment_count ++;
            fragment_code_verification = Fragment_Code_Verification.newInstance(userModel,type);
            fragmentManager.beginTransaction().add(R.id.fragment_sign_in_container, fragment_code_verification, "fragment_code_verification").addToBackStack("fragment_code_verification").commit();

        }
        public void displayFragmentNewpass(UserModel userModel) {
            fragment_count ++;
            fragment_newpass = Fragment_Newpass.newInstance(userModel);

            fragmentManager.beginTransaction().add(R.id.fragment_sign_in_container, fragment_newpass, "fragment_newpass").addToBackStack("fragment_newpass").commit();

        }*/
    public void RefreshActivity(String selected_language) {
        Paper.book().write("lang", selected_language);
        LanguageHelper.setNewLocale(this, selected_language);
        preferences.setIsLanguageSelected(this);


        Intent intent = getIntent();
        finish();
        startActivity(intent);


    }
    @Override
    public void onBackPressed() {
        Back();
    }

    public void Back() {

        if (fragment_count > 1) {
            fragment_count -= 1;
            super.onBackPressed();


        } else {

            finish();

        }


    }



}
