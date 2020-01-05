package com.elkhelj.ecommerece.activities_fragments.activity_splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_home.HomeStoreActivity;
import com.elkhelj.ecommerece.activities_fragments.activity_sign_in.activities.SignInActivity;
import com.elkhelj.ecommerece.language.LanguageHelper;
import com.elkhelj.ecommerece.preferences.Preferences;
import com.elkhelj.ecommerece.tags.Tags;


public class Splash_Activity extends AppCompatActivity {
    private ConstraintLayout fl;
    private Preferences preferences;
    private String session;
    private Animation animation;


    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(LanguageHelper.updateResources(base, Preferences.newInstance().getLanguage(base)));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private void initView() {
        preferences=Preferences.newInstance();
        session=preferences.getSession(this);
        fl=findViewById(R.id.cons);

        animation= AnimationUtils.loadAnimation(getBaseContext(),R.anim.lanuch);

        fl.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(session.equals(Tags.session_login)){
                    Intent intent = new Intent(Splash_Activity.this, HomeStoreActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(Splash_Activity.this, SignInActivity.class);
                    startActivity(intent);
                }
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
