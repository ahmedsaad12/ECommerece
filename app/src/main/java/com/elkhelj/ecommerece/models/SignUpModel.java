package com.elkhelj.ecommerece.models;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;


import com.elkhelj.ecommerece.BR;
import com.elkhelj.ecommerece.*;
import java.io.Serializable;

public class SignUpModel extends BaseObservable implements Serializable {

    private String name;
    private String shop_name;
    private String phone;
    private String email;
    private String password;
    private String confirmpassword;

    private String gender_id;
    private String city_id;

    public ObservableField<String> error_name = new ObservableField<>();
    public ObservableField<String> error_shop_name = new ObservableField<>();
    public ObservableField<String> error_phone = new ObservableField<>();
    public ObservableField<String> error_email = new ObservableField<>();

    public ObservableField<String> error_password = new ObservableField<>();
    public ObservableField<String> error_confirm_password = new ObservableField<>();


    public SignUpModel() {
        this.name = "";
        this.shop_name = "";
        this.phone = "";
        this.password = "";
        this.confirmpassword = "";
        this.email = "";
        this.gender_id = "";
    }


    @Bindable
    public String getCity_id() {
        return city_id;
    }
    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }



    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);


    }

    @Bindable
    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
        notifyPropertyChanged(BR.shop_name);

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
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);


    }

    @Bindable

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);

    }

    @Bindable

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
        notifyPropertyChanged(BR.confirmpassword);

    }

    @Bindable
    public String getGender_id() {
        return gender_id;
    }


    public void setGender_id(String gender_id) {
        this.gender_id = gender_id;
    }

    public boolean isDataValid(Context context) {
        if (!TextUtils.isEmpty(shop_name) &&
                !TextUtils.isEmpty(phone) &&
                (password.length() >= 6 && password.equals(confirmpassword)) &&
                !TextUtils.isEmpty(name) &&
                ((!TextUtils.isEmpty(email) &&
                        Patterns.EMAIL_ADDRESS.matcher(email).matches()) ||! TextUtils.isEmpty(email))
                &&!TextUtils.isEmpty(city_id)
        ) {
            error_name.set(null);
            error_shop_name.set(null);
            error_phone.set(null);
            error_email.set(null);
            error_password.set(null);
            error_confirm_password.set(null);
            return true;
        } else {
            if (name.isEmpty()) {
                error_name.set(context.getString(R.string.field_req));
            } else {
                error_name.set(null);
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
              error_email.set(context.getString(R.string.inc_phone_pas));
            } else {
                error_email.set(null);

            }

            if (shop_name.isEmpty()) {
                error_shop_name.set(context.getString(R.string.field_req));
            } else {
                error_shop_name.set(null);
            }

            if (phone.isEmpty()) {
                error_phone.set(context.getString(R.string.field_req));
            } else {
                error_phone.set(null);
            }


            if (password.isEmpty()) {
                error_password.set(context.getString(R.string.field_req));
            } else if (password.length() < 6) {
                error_password.set(context.getString(R.string.pass_short));
            } else if (password.length() >= 6 && !password.equals(confirmpassword)) {
              //  error_password.set(context.getString(R.string.pass_equal_confirm));
                //error_confirm_password.set(context.getString(R.string.pass_equal_confirm));

            } else {
                error_password.set(null);

            }
            if (confirmpassword.isEmpty()) {
                error_confirm_password.set(context.getString(R.string.field_req));
            } else if (confirmpassword.length() < 6) {
                error_confirm_password.set(context.getString(R.string.pass_short));
            } else {
                error_confirm_password.set(null);
            }

            if (gender_id.isEmpty()) {
                Toast.makeText(context, context.getString(R.string.shop_type), Toast.LENGTH_SHORT).show();
            }

            if (city_id.isEmpty())
            {
                Toast.makeText(context, context.getString(R.string.ch_city), Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    }
}
