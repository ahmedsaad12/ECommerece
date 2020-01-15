package com.elkhelj.ecommerece.activities_fragments.activity_home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_adsdetails.AdsDetialsActivity;
import com.elkhelj.ecommerece.activities_fragments.activity_home.fragments.Fragment_More;
import com.elkhelj.ecommerece.activities_fragments.activity_home.fragments.Fragment_Shop_Profile;
import com.elkhelj.ecommerece.activities_fragments.activity_home.fragments.fragmentmaim.Fragment_Main;
import com.elkhelj.ecommerece.activities_fragments.activity_home.fragments.Fragment_Wishlist;
import com.elkhelj.ecommerece.activities_fragments.activity_home.fragments.Fragment_Following;
import com.elkhelj.ecommerece.activities_fragments.activity_sign_in.activities.SignInActivity;
import com.elkhelj.ecommerece.databinding.ActivityHomeBinding;
import com.elkhelj.ecommerece.language.LanguageHelper;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.preferences.Preferences;
import com.elkhelj.ecommerece.remote.Api;
import com.elkhelj.ecommerece.share.Common;
import com.elkhelj.ecommerece.tags.Tags;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeStoreActivity extends AppCompatActivity  {
    private ActivityHomeBinding binding;
    private FragmentManager fragmentManager;
    private Fragment_Main fragment_main;
    private Fragment_More fragment_more;
    private Fragment_Following fragment_following;
    private Fragment_Wishlist fragment_wishlist;
    private Preferences preferences;
    private UserModel userModel;
    private Fragment_Shop_Profile fragment_shop_profile;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(LanguageHelper.updateResources(newBase, Paper.book().read("lang", Locale.getDefault().getLanguage())));

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
    }



    private void setUpBottomNavigation() {

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getResources().getString(R.string.home), R.drawable.ic_nav_home);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getResources().getString(R.string.search), R.drawable.ic_search);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getResources().getString(R.string.wish), R.drawable.ic_nav_wish);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(getResources().getString(R.string.profile), R.drawable.ic_nav_user);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(getResources().getString(R.string.more), R.drawable.ic_more);

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
                        displayFragmentFollowing();

                    }
                    else {
                        Common.CreateNoSignAlertDialog(this);
                    }
                    break;
                case 2:
                    displayFragmentWishlist();

                    if(userModel!=null){
                    }
                    else {
                       Common.CreateNoSignAlertDialog(this);

                    }
                    break;
                case 3:

                    if(userModel!=null){
                        displayFragmentprofile();

                    }
                    else {
                        Common.CreateNoSignAlertDialog(this);

                    }
                    break;
                case 4:
                    displayFragmentMore();

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

            if (fragment_more != null && fragment_more.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_more).commit();
            }
            if (fragment_shop_profile != null && fragment_shop_profile.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_shop_profile).commit();
            }
            if (fragment_following != null && fragment_following.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_following).commit();
            }
            if (fragment_wishlist != null && fragment_wishlist.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_wishlist).commit();
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

    private void displayFragmentMore() {
        try {
            if (fragment_more == null) {
                fragment_more = Fragment_More.newInstance();
            }
            if (fragment_main != null && fragment_main.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_main).commit();
            }
            if (fragment_shop_profile != null && fragment_shop_profile.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_shop_profile).commit();
            }
            if (fragment_following != null && fragment_following.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_following).commit();
            }
            if (fragment_wishlist != null && fragment_wishlist.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_wishlist).commit();
            }
            if (fragment_more.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_more).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_more, "fragment_more").addToBackStack("fragment_more").commit();

            }
            updateBottomNavigationPosition(4);
        } catch (Exception e) {
        }
    }

    private void displayFragmentFollowing() {
        try {
            if (fragment_following == null) {
                fragment_following = Fragment_Following.newInstance();
            }
            if (fragment_shop_profile != null && fragment_shop_profile.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_shop_profile).commit();
            }
            if (fragment_main != null && fragment_main.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_main).commit();
            }
            if (fragment_more != null && fragment_more.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_more).commit();
            }
            if (fragment_wishlist != null && fragment_wishlist.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_wishlist).commit();
            }
            if (fragment_following.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_following).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_following, "fragment_following").addToBackStack("fragment_following").commit();

            }
            updateBottomNavigationPosition(1);
        } catch (Exception e) {
        }
    }

    private void displayFragmentWishlist() {
        try {
            if (fragment_wishlist == null) {
                fragment_wishlist = Fragment_Wishlist.newInstance();
            }
            if (fragment_shop_profile != null && fragment_shop_profile.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_shop_profile).commit();
            }
            if (fragment_main != null && fragment_main.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_main).commit();
            }
            if (fragment_more != null && fragment_more.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_more).commit();
            }
            if (fragment_following != null && fragment_following.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_following).commit();
            }
            if (fragment_wishlist.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_wishlist).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_wishlist, "fragment_wishlist").addToBackStack("fragment_wishlist").commit();

            }
            updateBottomNavigationPosition(2);
        } catch (Exception e) {
        }
    }
    private void displayFragmentprofile() {
        try {
            if (fragment_shop_profile == null) {
                fragment_shop_profile = Fragment_Shop_Profile.newInstance();
            }
            if (fragment_wishlist != null && fragment_wishlist.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_wishlist).commit();
            }
            if (fragment_main != null && fragment_main.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_main).commit();
            }
            if (fragment_more != null && fragment_more.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_more).commit();
            }
            if (fragment_following != null && fragment_following.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_following).commit();
            }
            if (fragment_shop_profile.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_shop_profile).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_shop_profile, "fragment_shop_profile").addToBackStack("fragment_shop_profile").commit();

            }
            updateBottomNavigationPosition(3);
        } catch (Exception e) {
        }
    }


    private void NavigateToSignInActivity() {
        Intent intent = new Intent(HomeStoreActivity.this, SignInActivity.class);
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
           Logout();
        }
    }


    public void Logout() {
        final ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));

        dialog.show();
        Api.getService(Tags.base_url)
                .Logout(userModel.getId() + "")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            /*new Handler()
                                    .postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                            manager.cancelAll();
                                        }
                                    },1);
                            userSingleTone.clear(ClientHomeActivity.this);*/
                            preferences.create_update_userdata(HomeStoreActivity.this, null);
                            preferences.create_update_session(HomeStoreActivity.this, Tags.session_logout);
                            Intent intent = new Intent(HomeStoreActivity.this, SignInActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onBackPressed() {

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
        Intent intent = new Intent(HomeStoreActivity.this, SignInActivity.class);
        intent.putExtra("sign_up", isSignIn);
        startActivity(intent);
        finish();

    }
    public void showdetials(int id) {
        Intent intent=new Intent(HomeStoreActivity.this, AdsDetialsActivity.class);
        intent.putExtra("search",id);
        startActivity(intent);
    }
}
