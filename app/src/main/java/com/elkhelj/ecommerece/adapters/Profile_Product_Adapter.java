package com.elkhelj.ecommerece.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_home.HomeStoreActivity;
import com.elkhelj.ecommerece.activities_fragments.marktprofile.MarketProfileActivity;
import com.elkhelj.ecommerece.databinding.ProductsprofileRowBinding;
import com.elkhelj.ecommerece.databinding.ProductswomenmenRowBinding;
import com.elkhelj.ecommerece.models.Home_Model;
import com.elkhelj.ecommerece.models.Wish_Model;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Profile_Product_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Wish_Model> orderlist;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private int i = 0;
    private MarketProfileActivity homeStoreActivity;
    public Profile_Product_Adapter(List<Wish_Model> orderlist, Context context) {
        this.orderlist = orderlist;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        ProductsprofileRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.productsprofile_row, parent, false);
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
        Log.e("datas",context.getApplicationContext().toString());
        if(context instanceof MarketProfileActivity){
            homeStoreActivity=(MarketProfileActivity) context;
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
        public ProductsprofileRowBinding binding;

        public EventHolder(@NonNull ProductsprofileRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
