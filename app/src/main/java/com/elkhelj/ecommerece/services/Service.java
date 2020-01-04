package com.elkhelj.ecommerece.services;




import com.elkhelj.ecommerece.models.UserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Service {
    @FormUrlEncoded
    @POST("api/login")
    Call<UserModel> login(
            @Field("keyword") String mobile,
            @Field("password") String password
    );
}