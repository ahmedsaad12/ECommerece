package com.elkhelj.ecommerece.services;


import com.elkhelj.ecommerece.models.Add_Order_Model;
import com.elkhelj.ecommerece.models.AllMessageModel;
import com.elkhelj.ecommerece.models.App_Data_Model;
import com.elkhelj.ecommerece.models.Brand_Model;
import com.elkhelj.ecommerece.models.Catogry_Model;
import com.elkhelj.ecommerece.models.Cities_Model;
import com.elkhelj.ecommerece.models.Color_Model;
import com.elkhelj.ecommerece.models.Home_Model;
import com.elkhelj.ecommerece.models.Market_Profile_Model;
import com.elkhelj.ecommerece.models.MessageModel;
import com.elkhelj.ecommerece.models.NotificationDataModel;
import com.elkhelj.ecommerece.models.Order_Model;
import com.elkhelj.ecommerece.models.Single_Adversiment_Model;
import com.elkhelj.ecommerece.models.Size_Model;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.models.Wish_Model;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Service {
    @GET("api/all_cities")
    Call<List<Cities_Model>> getCity();
    @GET("api/all_sizes")
    Call<List<Size_Model>> getSize();
    @GET("api/all_colors")
    Call<List<Color_Model>> getColor();
    @GET("api/brands")
    Call<List<Brand_Model>> getBrabnd();
    @GET("api/all_categories")
    Call<List<Catogry_Model>> getCatogry();
    @FormUrlEncoded
    @POST("api/login")
    Call<UserModel> login(
            @Field("kayWord") String mobile,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/register")
    Call<UserModel> signUp(@Field("name") String name,
                           @Field("shop_name") String shop_name,
                           @Field("email") String email,
                           @Field("password") String password,
                           @Field("phone") String phone,
                           @Field("phone_code") String phone_code,

                           @Field("type") String type,
                           @Field("shop_for") String shop_for,
                           @Field("country") String country,
                           @Field("city") String city

    );

    @FormUrlEncoded
    @POST("api/home")
    Call<List<Home_Model>> getproducts(@Field("key_word") String key_word
    );
    @FormUrlEncoded
    @POST("api/home")
    Call<Home_Model> getproductss(@Field("key_word") String key_word
    );
    @FormUrlEncoded
    @POST("api/filter_by_cat")
    Call<List<Wish_Model>> getproductsscat(@Field("category_id") String category_id
    );
    @FormUrlEncoded
    @POST("api/filter_shop_name")
    Call<List<Home_Model>> getSHOPS(@Field("key_word") String key_word
    );
    @FormUrlEncoded
    @POST("api/search")
    Call<List<Wish_Model>> search(@Field("key_word") String key_word
    );
    @FormUrlEncoded
    @POST("api/filter_all")
    Call<List<Wish_Model>> search(@Field("brand") String brand,
                                  @Field("size") String size
                                  ,
                                  @Field("price_from") String price_from,
                                  @Field("price_to") String price_to,
                                  @Field("category") String category,
                                  @Field("category_type") String category_type
    );
    @FormUrlEncoded
    @POST("api/contact_us")
    Call<ResponseBody> sendContact(@Field("name") String name,
                                   @Field("email") String email,
                                   @Field("subject") String subject,
                                   @Field("message") String message
    );
    @FormUrlEncoded
    @POST("api/logout")
    Call<ResponseBody> Logout(@Field("id") String id

    );
    @FormUrlEncoded
    @POST("api/single_product")
    Call<Single_Adversiment_Model> getSingleAds(

            @Field("product_id") String product_id

    );
    @POST("api/add_order")
    Call<ResponseBody> accept_orders(@Body Add_Order_Model add_order_model);
    @GET("api/condtions")
    Call<App_Data_Model> getterms();

    @GET("api/aboutUs")
    Call<App_Data_Model> getabout();
    @FormUrlEncoded
    @POST("api/my_notification")
    Call<List<NotificationDataModel>> getNotifications(@Field("user_id") int user_id
    );
    @FormUrlEncoded
    @POST("api/my_like")
    Call<List<Wish_Model>> getmylike(@Field("user_id") int user_id
    );
    @FormUrlEncoded
    @POST("api/who_i_follow")
    Call<List<Wish_Model>> getmyfollow(@Field("user_id") int user_id
    );
    @FormUrlEncoded
    @POST("api/profile")
    Call<Market_Profile_Model> getmyprofile(
            @Field("register_id") String register_id,
            @Field("user_id") String user_id


    );
    @FormUrlEncoded
    @POST("api/profile")
    Call<Market_Profile_Model> getmyprofile(
            @Field("user_id") String user_id


    );
    @FormUrlEncoded
    @POST("api/my_orders")
    Call<List<Order_Model>> getMyAds(
            @Field("user_id") String user_id,
            @Field("type") String type


    );
    @Multipart
    @POST("api/add_product")
    Call<ResponseBody> createcv
            (@Part("user_id") RequestBody user_id,
             @Part("name") RequestBody name,
             @Part("price") RequestBody phone,
             @Part("gender") RequestBody gender,
             @Part("category_id") RequestBody category_id,
             @Part("brand_id") RequestBody brand_id,
             @Part("colors_id[]") List<RequestBody> colors_id,
             @Part("sizes_id[]") List<RequestBody> sizes_id,
             @Part("des") RequestBody des,

             @Part List<MultipartBody.Part> partimageInsideList

//
            );
    @FormUrlEncoded
    @POST("api/send_admin_message")
    Call<MessageModel> sendmessagetext
            (
                    @Field("user_id") String user_id,
                    @Field("admin_id") String admin_id,
                    @Field("message") String message

//
            );
    @FormUrlEncoded
    @POST("api/single_admin_room")
    Call<List<AllMessageModel>> getMessge(
           @Field("room_id") String room_id,
           @Field("user_id") String user_id

    );
    @FormUrlEncoded
    @POST("api/update_profile")
    Call<UserModel> editprofile(@Field("name") String name,
                                @Field("mobile") String mobile,

                                @Field("email") String email,
                                @Field("city") String city,
                                @Field("user_id") int user_id
    );
    @FormUrlEncoded
    @POST("api/update_profile")
    Call<UserModel> editprofile(@Field("password") String password,

                                @Field("user_id") String user_id
    );
    @FormUrlEncoded
    @POST("api/like")
    Call<ResponseBody> Like(

            @Field("to_id") String to_id,
            @Field("form_id") String form_id
    );
    @FormUrlEncoded
    @POST("api/like")
    Call<ResponseBody> Like(

            @Field("to_id") String to_id,
            @Field("form_id") String form_id,
            @Field("product_id") String product_id

    );
    @FormUrlEncoded
    @POST("api/follow")
    Call<ResponseBody> follow(

            @Field("to_id") String to_id,
            @Field("form_id") String form_id
    );
    @FormUrlEncoded
    @POST("api/follow")
    Call<ResponseBody> rate(
            @Field("form_id") String form_id,

            @Field("to_id") String to_id,
            @Field("rate")int rate
    );
    @FormUrlEncoded
    @POST("api/canRest")
    Call<UserModel> forget(@Field("kayWord") String kayWord
    );
}