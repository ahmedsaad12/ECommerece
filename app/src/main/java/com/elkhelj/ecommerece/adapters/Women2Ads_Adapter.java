package com.elkhelj.ecommerece.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.databinding.MenadRowBinding;
import com.elkhelj.ecommerece.databinding.ProductswomenmenRow2Binding;
import com.elkhelj.ecommerece.models.Wish_Model;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Women2Ads_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Wish_Model> orderlist;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private int i = 0;
    public Women2Ads_Adapter(List<Wish_Model> orderlist, Context context) {
        this.orderlist = orderlist;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        ProductswomenmenRow2Binding binding = DataBindingUtil.inflate(inflater, R.layout.productswomenmen_row2, parent, false);
        return new EventHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        EventHolder eventHolder = (EventHolder) holder;
      eventHolder.binding.setProductsmodel(orderlist.get(position));

    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        public ProductswomenmenRow2Binding binding;

        public EventHolder(@NonNull ProductswomenmenRow2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
