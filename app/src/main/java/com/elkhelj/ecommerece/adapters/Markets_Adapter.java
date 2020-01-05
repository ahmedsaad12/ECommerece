package com.elkhelj.ecommerece.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.databinding.MarketRowBinding;
import com.elkhelj.ecommerece.models.Home_Model;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Markets_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Home_Model> orderlist;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private int i = 0;
    public Markets_Adapter(List<Home_Model> orderlist, Context context) {
        this.orderlist = orderlist;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        MarketRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.market_row, parent, false);
        return new EventHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        EventHolder eventHolder = (EventHolder) holder;
      eventHolder.binding.setMarketmodel(orderlist.get(position));

    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        public MarketRowBinding binding;

        public EventHolder(@NonNull MarketRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
