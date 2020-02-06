package com.elkhelj.ecommerece.activities_fragments.activity_home.fragments;

import android.app.Dialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_home.HomeStoreActivity;
import com.elkhelj.ecommerece.adapters.Chat_Adapter;
import com.elkhelj.ecommerece.databinding.FragmentChatBinding;
import com.elkhelj.ecommerece.databinding.FragmentWishlistBinding;
import com.elkhelj.ecommerece.models.AllMessageModel;
import com.elkhelj.ecommerece.models.MessageModel;
import com.elkhelj.ecommerece.models.UserModel;
import com.elkhelj.ecommerece.preferences.Preferences;
import com.elkhelj.ecommerece.remote.Api;
import com.elkhelj.ecommerece.share.Common;
import com.elkhelj.ecommerece.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Chat extends Fragment {

    private HomeStoreActivity activity;
    private FragmentChatBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private String lang;
    private Chat_Adapter chat_adapter;
    private List<AllMessageModel> messagedatalist;
    private LinearLayoutManager manager;

    private String reciver_id = "0",reciver_name;

    public static Fragment_Chat newInstance() {

        return new Fragment_Chat();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        initView();
        getmessge();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeStoreActivity) getActivity();
        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(activity);
        messagedatalist = new ArrayList<>();
manager=new LinearLayoutManager(activity);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(manager);
        chat_adapter = new Chat_Adapter(messagedatalist, userModel.getId(), activity);
        binding.recView.setItemViewCacheSize(25);
        binding.recView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        binding.recView.setDrawingCacheEnabled(true);
        binding.progBar.setVisibility(View.GONE);
        // binding.llMsgContainer.setVisibility(View.GONE);
        binding.recView.setAdapter(chat_adapter);
        binding.imageSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkdata();
            }
        });
        binding.imagere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getmessge();
            }
        });

    }
    private void checkdata() {
        String message = binding.edtMsgContent.getText().toString();
        if (!TextUtils.isEmpty(message)) {
            Common.CloseKeyBoard(activity, binding.edtMsgContent);
            binding.edtMsgContent.setText("");
            sendmessagetext(message);

        } else {
            binding.edtMsgContent.setError(getResources().getString(R.string.field_req));
        }
    }

    public void getmessge() {
        //   Common.CloseKeyBoard(homeActivity, edt_name);
        messagedatalist.clear();
        chat_adapter.notifyDataSetChanged();
        binding.progBar.setVisibility(View.VISIBLE);

        // rec_sent.setVisibility(View.GONE);
        try {


            Api.getService(Tags.base_url)
                    .getMessge( "4",userModel.getId()+"")
                    .enqueue(new Callback<List<AllMessageModel>>() {
                        @Override
                        public void onResponse(Call<List<AllMessageModel>> call, Response<List<AllMessageModel>> response) {
                            binding.progBar.setVisibility(View.GONE);
                            //  binding.swipeRefresh.setRefreshing(false);
                            if (response.isSuccessful() && response.body() != null && response.body()!= null) {
                                messagedatalist.clear();
                                messagedatalist.addAll(response.body());
                                if (response.body().size() > 0) {
                                    // rec_sent.setVisibility(View.VISIBLE);
                                    //  Log.e("data",response.body().getData().get(0).getAr_title());

                                    // binding.llMsgContainer.setVisibility(View.GONE);
                                    chat_adapter.notifyDataSetChanged();
                                    binding.recView.scrollToPosition(messagedatalist.size() - 1);

                                    //   total_page = response.body().getMeta().getLast_page();

                                } else {
                                    chat_adapter.notifyDataSetChanged();

                                    //   binding.llNoStore.setVisibility(View.VISIBLE);

                                }
                            } else {

                                chat_adapter.notifyDataSetChanged();

                                //binding.llNoStore.setVisibility(View.VISIBLE);
                                //Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                try {
                                    Log.e("Error_code", response.code() + "_" + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<AllMessageModel>> call, Throwable t) {
                            try {
                                //binding.swipeRefresh.setRefreshing(false);

                                binding.progBar.setVisibility(View.GONE);
                                //binding.llNoStore.setVisibility(View.VISIBLE);


                                Toast.makeText(activity, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                Log.e("error", t.getMessage());
                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {
            binding.progBar.setVisibility(View.GONE);
            //binding.llNoStore.setVisibility(View.VISIBLE);

        }
    }


    private void sendmessagetext(String message) {
        final Dialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        try {
            Api.getService(Tags.base_url)
                    .sendmessagetext(userModel.getId() + "", "1", message).enqueue(new Callback<MessageModel>() {
                @Override
                public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                    dialog.dismiss();
                    if (response.isSuccessful()) {

                        Log.e("llll", response.toString());
messagedatalist.add(new AllMessageModel(response.body().getData().getType(),response.body().getData().getMessage()));
                        //messagedatalist.add(response.body().getData());
                        chat_adapter.notifyDataSetChanged();
                        binding.recView.scrollToPosition(messagedatalist.size() - 1);
                    } else {
                        try {

                            Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            Log.e("Error", response.toString() + " " + response.code() + "" + response.message() + "" + response.errorBody() + response.raw() + response.body() + response.headers() + " " + response.errorBody().toString());
                        } catch (Exception e) {


                        }
                    }
                }

                @Override
                public void onFailure(Call<MessageModel> call, Throwable t) {
                    dialog.dismiss();
                    try {
                        Toast.makeText(activity, getString(R.string.something), Toast.LENGTH_SHORT).show();
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



}
