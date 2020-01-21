package com.elkhelj.ecommerece.activities_fragments.activity_sign_in.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_sign_in.activities.SignInActivity;
import com.elkhelj.ecommerece.databinding.FragmentPhoneBinding;
import com.elkhelj.ecommerece.databinding.FragmentVerficationBinding;
import com.elkhelj.ecommerece.interfaces.Listeners;
import com.elkhelj.ecommerece.models.LoginModel;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.preferences.Preferences;
import com.elkhelj.ecommerece.share.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukesh.countrypicker.CountryPicker;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;

public class Fragment_Verfiy extends Fragment {
    private static final String TAG = "DATA";

    private FragmentVerficationBinding binding;
    private SignInActivity activity;
    private String current_language;
    private Preferences preferences;
    private String vercode;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String id;
    private ProgressDialog dialo;
    private String phone;

    @Override
    public View onCreateView(@androidx.annotation.NonNull LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_verfication, container, false);
        View view = binding.getRoot();
        initView();
        return view;
    }
    private void authn() {

        mAuth= FirebaseAuth.getInstance();
        mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                id=s;
                Log.e("authid",id);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                Log.e("code",phoneAuthCredential.getSmsCode());
//phoneAuthCredential.getProvider();
                if(phoneAuthCredential.getSmsCode()!=null){
                    vercode=phoneAuthCredential.getSmsCode();
                   binding.edtVer.setText(vercode);
                    Log.e("code",vercode);
                    verfiycode(vercode);}
                else {
                    siginwithcredental(phoneAuthCredential);
                }


            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("llll",e.getMessage());
            }
        };

    }
    private void verfiycode(String code) {

        if(id!=null){
            PhoneAuthCredential credential=PhoneAuthProvider.getCredential(id,code);
            siginwithcredental(credential);}
    }

    private void siginwithcredental(PhoneAuthCredential credential) {
        dialo = Common.createProgressDialog(activity,getString(R.string.wait));
        dialo.setCancelable(false);
        dialo.show();
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                dialo.dismiss();
                if(task.isSuccessful()){
               activity.DisplayFragmentSignUp();
                }

            }
        });
    }

    private void initView() {
        authn();
        Bundle bundle = getArguments();
        if (bundle != null) {
            phone =  bundle.getString(TAG);
sendverficationcode(phone,"+974");

        }
        activity = (SignInActivity) getActivity();
        preferences = Preferences.newInstance();
        Paper.init(activity);
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(current_language);
binding.btnLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        checkdata();
    }
});



    }

    private void checkdata() {
        vercode=binding.edtVer.getText().toString();
        if(!vercode.isEmpty()){
            verfiycode(vercode);
        }
        else {
            binding.edtVer.setError(getResources().getString(R.string.field_req));
        }
    }


    public static Fragment_Verfiy newInstance(String phone) {

        Bundle bundle = new Bundle();
        bundle.putString(TAG, phone);
        Fragment_Verfiy fragment_code_verification = new Fragment_Verfiy();
        fragment_code_verification.setArguments(bundle);
        return fragment_code_verification;
    }






    private void navigateToHomeActivity() {
      /*  Intent intent = new Intent(activity, HomeBuyerActivity.class);
        startActivity(intent);
        activity.finish();*/
    }

    public void sendverficationcode(String phone, String phone_code) {
        Log.e("kkk",phone_code+phone);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone_code+phone,59, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,  mCallbacks);

    }

}
