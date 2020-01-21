package com.elkhelj.ecommerece.interfaces;


import com.elkhelj.ecommerece.models.ContactUsModel;

public interface Listeners {

    interface LoginListener {
        void checkDataLogin(String phone, String password);
    }
    interface EditprofileListener
    {
        void Editprofile(String name,  String phone,String email);

    }
    interface ForgetpasswordListner {
        void checkDataForget(String emial);
    }
    interface PasswordListner {
        void checkDatapass(String pass);
    }

    interface ForgetListner
    {
        void forget();
    }
    interface CreateAccountListener
    {
        void createNewAccount();
    }

    interface ShowCountryDialogListener
    {
        void showDialog();
    }

    interface SignUpListener
    {
        void checkDataSignUp(String name, String phone_code, String phone, String email, String password, String confirmpassword);

    }


    interface RatingListener
    {
        void checkDataRating(String desc);

    }
    interface BackListener
    {
        void back();
    }





    interface UpdateProfileListener
    {
        void updateProfile();
    }
    interface ContactListener
    {
        void sendContact(ContactUsModel contactUsModel);
    }


}
