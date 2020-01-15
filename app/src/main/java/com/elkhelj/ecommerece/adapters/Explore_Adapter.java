package com.elkhelj.ecommerece.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_home.fragments.fragmentmaim.Fragment_Explore;
import com.elkhelj.ecommerece.databinding.HomeBrandRowBinding;
import com.elkhelj.ecommerece.models.Home_Model;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Explore_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Home_Model> orderlist;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private int i = 0;
    private Fragment_Explore fragment_explore;
    public Explore_Adapter(List<Home_Model> orderlist, Context context, Fragment_Explore fragment_explore) {
        this.orderlist = orderlist;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
this.fragment_explore=fragment_explore;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        HomeBrandRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.home_brand_row, parent, false);
        return new EventHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        EventHolder eventHolder = (EventHolder) holder;
        ((EventHolder) eventHolder).binding.tvName1.setText(orderlist.get(position).getName());
Explore_Product_Adapter explore_product_adapter=new Explore_Product_Adapter(orderlist.get(position).getProducts(),context);
        ((EventHolder) eventHolder).binding.recview1.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        ((EventHolder) eventHolder).binding.recview1.setAdapter(explore_product_adapter);
        eventHolder.binding.tvView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
fragment_explore.DisplayDepartmentMarket(orderlist.get(eventHolder.getLayoutPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        public HomeBrandRowBinding binding;

        public EventHolder(@NonNull HomeBrandRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
