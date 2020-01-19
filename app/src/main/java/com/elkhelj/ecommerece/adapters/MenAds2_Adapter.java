package com.elkhelj.ecommerece.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.databinding.MenadRow2Binding;
import com.elkhelj.ecommerece.databinding.MenadRowBinding;
import com.elkhelj.ecommerece.models.Home_Model;
import com.elkhelj.ecommerece.models.Wish_Model;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class MenAds2_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Home_Model.Products> orderlist;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private int i = 0;
    public MenAds2_Adapter(List<Home_Model.Products> orderlist, Context context) {
        this.orderlist = orderlist;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        MenadRow2Binding binding = DataBindingUtil.inflate(inflater, R.layout.menad_row2, parent, false);
        return new EventHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        EventHolder eventHolder = (EventHolder) holder;
      eventHolder.binding.setAdmodel(orderlist.get(position));

    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        public MenadRow2Binding binding;

        public EventHolder(@NonNull MenadRow2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
