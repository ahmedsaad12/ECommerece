package com.elkhelj.ecommerece.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_home.HomeStoreActivity;
import com.elkhelj.ecommerece.databinding.HomeBrandRowBinding;
import com.elkhelj.ecommerece.databinding.ProductsRowBinding;
import com.elkhelj.ecommerece.models.Home_Model;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Explore_Product_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Home_Model.Products> orderlist;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private int i = 0;
    private HomeStoreActivity homeStoreActivity;
    public Explore_Product_Adapter(List<Home_Model.Products> orderlist, Context context) {
        this.orderlist = orderlist;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        ProductsRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.products_row, parent, false);
        return new EventHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        EventHolder eventHolder = (EventHolder) holder;
        ((EventHolder) eventHolder).binding.setLang(lang);

        ((EventHolder) eventHolder).binding.setProductsmodel(orderlist.get(position));
eventHolder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(context instanceof HomeStoreActivity){
            homeStoreActivity=(HomeStoreActivity)context;
            homeStoreActivity.showdetials(orderlist.get(eventHolder.getLayoutPosition()).getId());
        }
    }
});

    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        public ProductsRowBinding binding;

        public EventHolder(@NonNull ProductsRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
