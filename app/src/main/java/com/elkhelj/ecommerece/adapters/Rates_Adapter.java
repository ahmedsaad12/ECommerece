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
import com.elkhelj.ecommerece.databinding.ReviewRowBinding;
import com.elkhelj.ecommerece.models.Home_Model;
import com.elkhelj.ecommerece.models.Single_Adversiment_Model;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Rates_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Single_Adversiment_Model.Products.Rates> orderlist;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private int i = 0;
    public Rates_Adapter(List<Single_Adversiment_Model.Products.Rates> orderlist, Context context) {
        this.orderlist = orderlist;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        ReviewRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.review_row, parent, false);
        return new EventHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        EventHolder eventHolder = (EventHolder) holder;
       eventHolder.binding.setLang(lang);
       eventHolder.binding.setModel(orderlist.get(position));
    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        public ReviewRowBinding binding;

        public EventHolder(@NonNull ReviewRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
