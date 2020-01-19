package com.elkhelj.ecommerece.activities_fragments.activity_addproduct;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.adapters.BrandAdapter;
import com.elkhelj.ecommerece.adapters.CatogryAdapter;
import com.elkhelj.ecommerece.adapters.ColorAdapter;
import com.elkhelj.ecommerece.adapters.FilterAdapter;
import com.elkhelj.ecommerece.adapters.ImagesAdapter;
import com.elkhelj.ecommerece.adapters.SizesAdapter;
import com.elkhelj.ecommerece.adapters.SkillAdapter;
import com.elkhelj.ecommerece.adapters.SkillsAdapter;
import com.elkhelj.ecommerece.databinding.ActivityAddproductBinding;
import com.elkhelj.ecommerece.databinding.DialogSelectImageBinding;
import com.elkhelj.ecommerece.interfaces.Listeners;
import com.elkhelj.ecommerece.language.LanguageHelper;
import com.elkhelj.ecommerece.models.Brand_Model;
import com.elkhelj.ecommerece.models.Catogry_Model;
import com.elkhelj.ecommerece.models.Color_Model;
import com.elkhelj.ecommerece.models.Filter_model;
import com.elkhelj.ecommerece.models.Size_Model;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.preferences.Preferences;
import com.elkhelj.ecommerece.remote.Api;
import com.elkhelj.ecommerece.share.Common;
import com.elkhelj.ecommerece.tags.Tags;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPoductActivity extends AppCompatActivity implements Listeners.BackListener {
    private final String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final int IMG_REQ1 = 3, IMG_REQ2 = 2;
    private Uri url = null;
    private List<Uri> urlList;
    private LinearLayoutManager manager,manager2;
    private ImagesAdapter imagesAdapter;
    private ActivityAddproductBinding binding;
    private String lang;
    private List<Filter_model> filter_models;
    private FilterAdapter filterAdapter;
    private Filter_model filter_model1,filter_model2,filter_model3;
    private int gender;
    private ColorAdapter adapter;
    private List<Color_Model> color_modelList,colorlist;
    private SizesAdapter sizesAdapter;
    private List<Size_Model> size_modelList,skills;
    private BrandAdapter brandAdapter;
    private List<Brand_Model> brand_modelList;
    private CatogryAdapter catogryAdapter;
    private List<Catogry_Model> catogry_modelList;
    private List<Integer> skillid,collorid;
    private SkillAdapter skillAdapter;
private SkillsAdapter skillsAdapter;
    private String catid;
    private String brandid;
private Preferences preferences;
private UserModel userModel;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(LanguageHelper.updateResources(newBase, Paper.book().read("lang", Locale.getDefault().getLanguage())));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addproduct);
        initView();

    }


    private void initView() {

        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        preferences=Preferences.newInstance();
        userModel=preferences.getUserData(this);
        binding.setBackListener(this);
        filter_models=new ArrayList<>();
color_modelList=new ArrayList<>();
size_modelList=new ArrayList<>();
brand_modelList=new ArrayList<>();
catogry_modelList=new ArrayList<>();
skills=new ArrayList<>();
skillid=new ArrayList<>();
collorid=new ArrayList<>();
colorlist=new ArrayList<>();
        urlList = new ArrayList<>();
        manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        binding.recView.setLayoutManager(manager);
        imagesAdapter = new ImagesAdapter(urlList,this);
        binding.recView.setAdapter(imagesAdapter);
        setfiltermodels();
        skillAdapter=new SkillAdapter(skills,this,null);
        skillsAdapter=new SkillsAdapter(colorlist,this,null);
        binding.recViewsize.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        binding.recViewsize.setAdapter(skillAdapter);

        binding.recViewcolor.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        binding.recViewcolor.setAdapter(skillsAdapter);
        filterAdapter=new FilterAdapter(filter_models,this);
        binding.spType.setAdapter(filterAdapter);
        catogryAdapter=new CatogryAdapter(catogry_modelList,this);
        binding.spCatogry.setAdapter(catogryAdapter);
        brandAdapter=new BrandAdapter(brand_modelList,this);
        binding.spBrand.setAdapter(brandAdapter);
        adapter=new ColorAdapter(color_modelList,this);
        binding.spColor.setAdapter(adapter);
        sizesAdapter=new SizesAdapter(size_modelList,this);
        binding.spSize.setAdapter(sizesAdapter);
        binding.imageSelectPhoto.setOnClickListener(view -> CreateImageAlertDialog());

        binding.spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){

                    gender=2;

                }
                else if(i==1){
                    gender=3;
                }
                else if(i==2){
                    gender=1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) {
                    if(notidisfound(position)){
                        skillid.add( size_modelList.get(position).getId());
                        skills.add(size_modelList.get(position));
                        skillAdapter.notifyDataSetChanged();
                        binding.recViewsize.setVisibility(View.VISIBLE);
                    }
                    // Move_Data_Model.setcityt(to_city);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.spColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) {
                    if(notidisfound2(position)){
                        collorid.add( color_modelList.get(position).getId());
                        colorlist.add(color_modelList.get(position));
                        skillsAdapter.notifyDataSetChanged();
                        binding.recViewcolor.setVisibility(View.VISIBLE);
                    }
                    // Move_Data_Model.setcityt(to_city);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.spCatogry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) {
                    catid=catogry_modelList.get(position).getId()+"";

                    // Move_Data_Model.setcityt(to_city);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.spBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) {
                    brandid=brand_modelList.get(position).getId()+"";

                    // Move_Data_Model.setcityt(to_city);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
getSize();
getColor();
getBrand();
getCattogry();
binding.btNext.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        chechdata();
    }
});
    }

    private void chechdata() {
        String name=binding.edtName.getText().toString();
        String addres=binding.edtShopname.getText().toString();
        if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(addres)&&!TextUtils.isEmpty(catid)&&!TextUtils.isEmpty(brandid)&&collorid.size()>0&&skillid.size()>0&&urlList.size()>0){
            CreateproductWithImage(name,addres);
        }
        else {

        }
    }

    private void setfiltermodels() {
        filter_model1=new Filter_model();
        filter_model2=new Filter_model();
        filter_model3=new Filter_model();
        filter_model1.setAr_filter("ذكر");
        filter_model1.setEn_filter("male");
        filter_model2.setAr_filter("انثى");
        filter_model2.setEn_filter("Female");
        filter_model3.setAr_filter("اخر");
        filter_model3.setEn_filter("other");
        filter_models.add(filter_model1);
        filter_models.add(filter_model2);
        filter_models.add(filter_model3);
    }

    private void updatSizeAdapter(List<Size_Model> body) {
        if (lang.equals("ar")) {
            size_modelList.add(new Size_Model("إختر الحجم"));
        } else {
            size_modelList.add(new Size_Model("choose size"));
        }

        size_modelList.addAll(body);
        sizesAdapter.notifyDataSetChanged();

    }

    private void getSize() {
        try {
            ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
            dialog.setCancelable(false);
            dialog.show();
            Api.getService(Tags.base_url)
                    .getSize()
                    .enqueue(new Callback<List<Size_Model>>() {
                        @Override
                        public void onResponse(Call<List<Size_Model>> call, Response<List<Size_Model>> response) {
                            dialog.dismiss();
                            if (response.isSuccessful() && response.body() != null) {
                                if (response.body() != null) {
                                    updatSizeAdapter(response.body());
                                } else {
                                    Log.e("error", response.code() + "_" + response.errorBody());

                                }

                            } else {

                                try {

                                    Log.e("error", response.code() + "_" + response.errorBody().string());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (response.code() == 500) {
                                    Toast.makeText(AddPoductActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(AddPoductActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();


                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Size_Model>> call, Throwable t) {
                            try {
                                dialog.dismiss();
                                if (t.getMessage() != null) {
                                    Log.e("error", t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(AddPoductActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(AddPoductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {
        }

    }
    private void getColor() {
        try {
            ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
            dialog.setCancelable(false);
            dialog.show();
            Api.getService(Tags.base_url)
                    .getColor()
                    .enqueue(new Callback<List<Color_Model>>() {
                        @Override
                        public void onResponse(Call<List<Color_Model>> call, Response<List<Color_Model>> response) {
                            dialog.dismiss();
                            if (response.isSuccessful() && response.body() != null) {
                                if (response.body() != null) {
                                    updatColorAdapter(response.body());
                                } else {
                                    Log.e("error", response.code() + "_" + response.errorBody());

                                }

                            } else {

                                try {

                                    Log.e("error", response.code() + "_" + response.errorBody().string());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (response.code() == 500) {
                                    Toast.makeText(AddPoductActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(AddPoductActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();


                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Color_Model>> call, Throwable t) {
                            try {
                                dialog.dismiss();
                                if (t.getMessage() != null) {
                                    Log.e("error", t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(AddPoductActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(AddPoductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {
        }

    }
    private void getCattogry() {
        try {
            ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
            dialog.setCancelable(false);
            dialog.show();
            Api.getService(Tags.base_url)
                    .getCatogry()
                    .enqueue(new Callback<List<Catogry_Model>>() {
                        @Override
                        public void onResponse(Call<List<Catogry_Model>> call, Response<List<Catogry_Model>> response) {
                            dialog.dismiss();
                            if (response.isSuccessful() && response.body() != null) {
                                if (response.body() != null) {
                                    updatcatAdapter(response.body());
                                } else {
                                    Log.e("error", response.code() + "_" + response.errorBody());

                                }

                            } else {

                                try {

                                    Log.e("error", response.code() + "_" + response.errorBody().string());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (response.code() == 500) {
                                    Toast.makeText(AddPoductActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(AddPoductActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();


                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Catogry_Model>> call, Throwable t) {
                            try {
                                dialog.dismiss();
                                if (t.getMessage() != null) {
                                    Log.e("error", t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(AddPoductActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(AddPoductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {
        }

    }

    private void updatcatAdapter(List<Catogry_Model> body) {
        if (lang.equals("ar")) {
            catogry_modelList.add(new Catogry_Model("إختر القسم"));
        } else {
            catogry_modelList.add(new Catogry_Model("choose Catogry"));
        }

        catogry_modelList.addAll(body);
        catogryAdapter.notifyDataSetChanged();
    }

    private void getBrand() {
        try {
            ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
            dialog.setCancelable(false);
            dialog.show();
            Api.getService(Tags.base_url)
                    .getBrabnd()
                    .enqueue(new Callback<List<Brand_Model>>() {
                        @Override
                        public void onResponse(Call<List<Brand_Model>> call, Response<List<Brand_Model>> response) {
                            dialog.dismiss();
                            if (response.isSuccessful() && response.body() != null) {
                                if (response.body() != null) {
                                    updatbrandAdapter(response.body());
                                } else {
                                    Log.e("error", response.code() + "_" + response.errorBody());

                                }

                            } else {

                                try {

                                    Log.e("error", response.code() + "_" + response.errorBody().string());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (response.code() == 500) {
                                    Toast.makeText(AddPoductActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(AddPoductActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();


                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Brand_Model>> call, Throwable t) {
                            try {
                                dialog.dismiss();
                                if (t.getMessage() != null) {
                                    Log.e("error", t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(AddPoductActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(AddPoductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {
        }

    }

    private void updatbrandAdapter(List<Brand_Model> body) {
        if (lang.equals("ar")) {
            brand_modelList.add(new Brand_Model("إختر البرند"));
        } else {
            brand_modelList.add(new Brand_Model("choose Brand"));
        }

        brand_modelList.addAll(body);
        brandAdapter.notifyDataSetChanged();
    }

    private void updatColorAdapter(List<Color_Model> body) {
        if (lang.equals("ar")) {
            color_modelList.add(new Color_Model("إختر اللون"));
        } else {
            color_modelList.add(new Color_Model("choose color"));
        }

        color_modelList.addAll(body);
        adapter.notifyDataSetChanged();

    }
    private boolean notidisfound(int position) {
        for(int i=0;i<skillid.size();i++){
            if(size_modelList.get(position).getId()==skillid.get(i)){
                return false;
            }
        }
        return true;
    }
    private boolean notidisfound2(int position) {
        for(int i=0;i<collorid.size();i++){
            if(color_modelList.get(position).getId()==collorid.get(i)){
                return false;
            }
        }
        return true;
    }
    private void CreateImageAlertDialog()
    {

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .create();

        DialogSelectImageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this),R.layout.dialog_select_image,null,false);



        binding.btnCamera.setOnClickListener(v -> {
            dialog.dismiss();
            Check_CameraPermission();

        });

        binding.btnGallery.setOnClickListener(v -> {
            dialog.dismiss();
            CheckReadPermission();



        });

        binding.btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
        dialog.show();
    }
    private void CheckReadPermission()
    {
        if (ActivityCompat.checkSelfPermission(this, READ_PERM) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{READ_PERM}, IMG_REQ1);
        } else {
            SelectImage(IMG_REQ1);
        }
    }

    private void Check_CameraPermission()
    {
        if (ContextCompat.checkSelfPermission(this,camera_permission)!= PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(this,write_permission)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{camera_permission,write_permission},IMG_REQ2);
        }else
        {
            SelectImage(IMG_REQ2);

        }

    }
    private void SelectImage(int img_req) {

        Intent intent = new Intent();

        if (img_req == IMG_REQ1)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            {
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            }else
            {
                intent.setAction(Intent.ACTION_GET_CONTENT);

            }

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/*");
            startActivityForResult(intent,img_req);

        }else if (img_req ==IMG_REQ2)
        {
            try {
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,img_req);
            }catch (SecurityException e)
            {
                Toast.makeText(this,R.string.perm_image_denied, Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(this,R.string.perm_image_denied, Toast.LENGTH_SHORT).show();

            }


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQ2 && resultCode == Activity.RESULT_OK && data != null) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            url = getUriFromBitmap(bitmap);
            urlList.add(url);
            imagesAdapter.notifyDataSetChanged();



        } else if (requestCode == IMG_REQ1 && resultCode == Activity.RESULT_OK && data != null) {

            url = data.getData();
            urlList.add(url);
            imagesAdapter.notifyDataSetChanged();



        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == IMG_REQ1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SelectImage(IMG_REQ1);
            } else {
                Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == IMG_REQ2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SelectImage(IMG_REQ2);
            } else {
                Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private Uri getUriFromBitmap(Bitmap bitmap) {
        String path = "";
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            path = MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "title", null);
            return Uri.parse(path);

        } catch (SecurityException e) {
            Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();

        }
        return null;
    }

    public void deleteImage(int adapterPosition) {
        urlList.remove(adapterPosition);
        imagesAdapter.notifyItemRemoved(adapterPosition);

    }
    private void CreateproductWithImage(String name, String price) {
        final Dialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        RequestBody user_part = Common.getRequestBodyText(userModel.getId() + "");
        RequestBody name_part = Common.getRequestBodyText(name);
        RequestBody price_part = Common.getRequestBodyText(price);
        RequestBody cat_part = Common.getRequestBodyText(catid);
        RequestBody brand_part = Common.getRequestBodyText(brandid);
        RequestBody gender_part = Common.getRequestBodyText(gender + "");
        RequestBody des_part = Common.getRequestBodyText("new");

        List<RequestBody> skill_part = new ArrayList<>();
        List<RequestBody> collor_part = new ArrayList<>();

        for(int i=0;i<skillid.size();i++){
            skill_part.add(Common.getRequestBodyText(skillid.get(i)+""));
        }
        for(int i=0;i<collorid.size();i++){
            collor_part.add(Common.getRequestBodyText(collorid.get(i)+""));
        }

        List<MultipartBody.Part> partimageList = getMultipartBodyList(urlList, "image[]");
        try {
            Api.getService(Tags.base_url)
                    .createcv(user_part, name_part, price_part, gender_part, cat_part, brand_part, collor_part, skill_part,des_part,partimageList).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    dialog.dismiss();
                    if (response.isSuccessful()) {
                        // Common.CreateSignAlertDialog(adsActivity,getResources().getString(R.string.suc));
                        Toast.makeText(AddPoductActivity.this, getString(R.string.suc), Toast.LENGTH_SHORT).show();
                      //  activity.Displayorder();

                        //  adsActivity.finish(response.body().getId_advertisement());

                    } else {
                        try {

                            Toast.makeText(AddPoductActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            Log.e("Error", response.toString() + " " + response.code() + "" + response.message() + "" + response.errorBody().string() + response.raw() + response.body() + response.headers() + " " + response.errorBody().toString());
                        } catch (Exception e) {


                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    dialog.dismiss();
                    try {
                        Toast.makeText(AddPoductActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                        Log.e("Error", t.getMessage());
                    } catch (Exception e) {

                    }
                }
            });
        } catch (Exception e) {
            dialog.dismiss();
            Log.e("error", e.getMessage().toString());
        }
    }

    private List<MultipartBody.Part> getMultipartBodyList(List<Uri> uriList, String image_cv) {
        List<MultipartBody.Part> partList = new ArrayList<>();
        for (Uri uri : uriList) {
            MultipartBody.Part part = Common.getMultiPart(this, uri, image_cv);
            partList.add(part);
        }
        return partList;
    }
    @Override
    public void back() {
        finish();
    }
}
