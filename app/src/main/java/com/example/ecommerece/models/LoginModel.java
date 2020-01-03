package com.example.ecommerece.models;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import com.example.ecommerece.BR;
import com.example.ecommerece.R;


public class LoginModel extends BaseObservable {

    private String phone;
    private String password;
    public ObservableField<String> error_phone = new ObservableField<>();
    public ObservableField<String> error_password = new ObservableField<>();


    public LoginModel() {
        this.phone="";
        this.password="";
    }

    public LoginModel( String phone, String password) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
        this.password = password;
        notifyPropertyChanged(BR.password);


    }



    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);

    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);

    }

    public int isDataValid(Context context)
    {
        if (
                !TextUtils.isEmpty(phone)&&
                password.length()>=6
        )
        {
            error_phone.set(null);
            error_password.set(null);

if (Patterns.EMAIL_ADDRESS.matcher(phone).matches()){
    return 1;
}
else {
    return 2;
}
        }else
            {


                if (phone.isEmpty())
                {
                    error_phone.set(context.getString(R.string.field_req));
                }else
                {
                    error_phone.set(null);
                }

                if (password.isEmpty())
                {
                    error_password.set(context.getString(R.string.field_req));
                }else if (password.length()<6)
                {
                    error_password.set(context.getString(R.string.pass_short));
                }else
                    {
                        error_password.set(null);

                    }
                return -1;
            }
    }


}
