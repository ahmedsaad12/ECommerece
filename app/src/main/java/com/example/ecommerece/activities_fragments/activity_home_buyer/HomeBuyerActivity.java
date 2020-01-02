package com.example.ecommerece.activities_fragments.activity_home_buyer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.ecommerece.R;
import com.example.ecommerece.activities_fragments.activity_home_buyer.fragments.Fragment_Buyer_Profile;
import com.example.ecommerece.activities_fragments.activity_home_buyer.fragments.Fragment_Notifications;
import com.example.ecommerece.activities_fragments.activity_home_buyer.fragments.Fragment_Orders;
import com.example.ecommerece.activities_fragments.activity_home_buyer.fragments.Fragment_Main;
import com.example.ecommerece.activities_fragments.activity_sign_in.activities.SignInActivity;
import com.example.ecommerece.databinding.ActivityHomeBinding;
import com.example.ecommerece.language.LanguageHelper;
import com.example.ecommerece.models.UserModel;
import com.example.ecommerece.preferences.Preferences;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

import io.paperdb.Paper;

public class HomeBuyerActivity extends AppCompatActivity  {
    private ActivityHomeBinding binding;
    private FragmentManager fragmentManager;
    private Fragment_Main fragment_main;
    private Fragment_Orders fragment_orders;
    private Fragment_Notifications fragment_notifications;
    private Preferences preferences;
    private UserModel userModel;
    private BottomSheetBehavior behavior;
    private View root;
    private Button btnNearby, btnFurthest, btnWithImage, btcancel, btfilter;
    private Spinner spinner;
    private String lat="0.0", lng="0.0";
    private Fragment_Buyer_Profile fragment_buyer_profile;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(LanguageHelper.updateResources(newBase, Paper.book().read("lang", "ar")));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initView();
        if (savedInstanceState == null) {

            displayFragmentMain();
        }


    }

    @SuppressLint("RestrictedApi")
    private void initView() {
        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(this);
        fragmentManager = getSupportFragmentManager();





        setUpBottomNavigation();
        setUpBottomSheet();
    }

    private void setUpBottomSheet() {

        behavior = BottomSheetBehavior.from(root);

    }

    private void setUpBottomNavigation() {

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getResources().getString(R.string.home), R.drawable.ic_nav_home);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getResources().getString(R.string.following), R.drawable.ic_nav_follow);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getResources().getString(R.string.wish), R.drawable.ic_nav_wish);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(getResources().getString(R.string.call_us), R.drawable.ic_nav_web);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(getResources().getString(R.string.profile), R.drawable.ic_nav_user);

        binding.ahBottomNav.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);
        binding.ahBottomNav.setDefaultBackgroundColor(ContextCompat.getColor(this, R.color.white));
        binding.ahBottomNav.setTitleTextSizeInSp(14, 12);
        binding.ahBottomNav.setForceTint(true);
        binding.ahBottomNav.setAccentColor(ContextCompat.getColor(this, R.color.colorPrimary));
        binding.ahBottomNav.setInactiveColor(ContextCompat.getColor(this, R.color.gray4));

        binding.ahBottomNav.addItem(item1);
        binding.ahBottomNav.addItem(item2);
        binding.ahBottomNav.addItem(item3);
        binding.ahBottomNav.addItem(item4);
        binding.ahBottomNav.addItem(item5);


        updateBottomNavigationPosition(0);

        binding.ahBottomNav.setOnTabSelectedListener((position, wasSelected) -> {
            switch (position) {
                case 0:
                    displayFragmentMain();
                    break;
                case 1:
                    if(userModel!=null){
                    displayFragmentOrders();}
                    else {
                       // Common.CreateNoSignAlertDialog(this);
                    }
                    break;
                case 2:
                    if(userModel!=null){
                    displayFragmentNotifcations();}
                    else {
                       // Common.CreateNoSignAlertDialog(this);

                    }
                    break;
                case 3:
                    displayFragmentprofile();
                    break;

            }
            return false;
        });


    }

    private void updateBottomNavigationPosition(int pos) {

        binding.ahBottomNav.setCurrentItem(pos, false);
    }

    private void displayFragmentMain() {
        try {
            if (fragment_main == null) {
                fragment_main = Fragment_Main.newInstance();
            }


            if (fragment_buyer_profile != null && fragment_buyer_profile.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_buyer_profile).commit();
            }
            if (fragment_orders != null && fragment_orders.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_orders).commit();
            }
            if (fragment_notifications != null && fragment_notifications.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_notifications).commit();
            }
            if (fragment_main.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_main).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_main, "fragment_main").addToBackStack("fragment_main").commit();

            }
            updateBottomNavigationPosition(0);
        } catch (Exception e) {
        }
    }



    private void displayFragmentOrders() {
        try {
            if (fragment_orders == null) {
                fragment_orders = Fragment_Orders.newInstance();
            }
            if (fragment_buyer_profile != null && fragment_buyer_profile.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_buyer_profile).commit();
            }
            if (fragment_main != null && fragment_main.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_main).commit();
            }

            if (fragment_notifications != null && fragment_notifications.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_notifications).commit();
            }
            if (fragment_orders.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_orders).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_orders, "fragment_orders").addToBackStack("fragment_orders").commit();

            }
            updateBottomNavigationPosition(1);
        } catch (Exception e) {
        }
    }

    private void displayFragmentNotifcations() {
        try {
            if (fragment_notifications == null) {
                fragment_notifications = Fragment_Notifications.newInstance();
            }
            if (fragment_buyer_profile != null && fragment_buyer_profile.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_buyer_profile).commit();
            }
            if (fragment_main != null && fragment_main.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_main).commit();
            }

            if (fragment_orders != null && fragment_orders.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_orders).commit();
            }
            if (fragment_notifications.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_notifications).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_notifications, "fragment_notifications").addToBackStack("fragment_notifications").commit();

            }
            updateBottomNavigationPosition(2);
        } catch (Exception e) {
        }
    }
    private void displayFragmentprofile() {
        try {
            if (fragment_buyer_profile == null) {
                fragment_buyer_profile = Fragment_Buyer_Profile.newInstance();
            }
            if (fragment_notifications != null && fragment_notifications.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_notifications).commit();
            }
            if (fragment_main != null && fragment_main.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_main).commit();
            }

            if (fragment_orders != null && fragment_orders.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_orders).commit();
            }
            if (fragment_buyer_profile.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_buyer_profile).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_buyer_profile, "fragment_buyer_profile").addToBackStack("fragment_buyer_profile").commit();

            }
            updateBottomNavigationPosition(3);
        } catch (Exception e) {
        }
    }


    private void NavigateToSignInActivity() {
        Intent intent = new Intent(HomeBuyerActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    public void refreshActivity(String lang) {
        Paper.book().write("lang", lang);
        LanguageHelper.setNewLocale(this, lang);
        Intent intent = getIntent();
        finish();
        startActivity(intent);

    }

    public void logout() {
        if (userModel == null) {
            NavigateToSignInActivity();
        } else {
           // Logout();
        }
    }




    @SuppressLint("RestrictedApi")
    @Override
    public void onBackPressed() {
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            binding.fab.setVisibility(View.VISIBLE);

            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            if (fragment_main != null && fragment_main.isAdded() && fragment_main.isVisible()) {
                if (userModel == null) {
                    NavigateToSignInActivity();
                } else {
                    finish();
                }
            } else {
                displayFragmentMain();
            }
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment : fragmentList) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment : fragmentList) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }









    public void NavigateToSignInActivity(boolean isSignIn) {
//Log.e("data",isSignIn+"");
        Intent intent = new Intent(HomeBuyerActivity.this, SignInActivity.class);
        intent.putExtra("sign_up", isSignIn);
        startActivity(intent);
        finish();

    }
}
