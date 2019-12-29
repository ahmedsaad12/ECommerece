package com.example.ecommerece.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.creative.share.apps.ebranch.R;
import com.creative.share.apps.ebranch.activities_fragments.activity_departmnet_detials.DepartmentDetialsActivity;
import com.creative.share.apps.ebranch.activities_fragments.activity_home.HomeActivity;
import com.creative.share.apps.ebranch.activities_fragments.activity_home.fragments.Fragment_Views;
import com.creative.share.apps.ebranch.activities_fragments.activitymarketprofile.MarketProfileActivity;
import com.creative.share.apps.ebranch.databinding.LoadMoreBinding;
import com.creative.share.apps.ebranch.databinding.ProductsHomeRowBinding;
import com.creative.share.apps.ebranch.models.Products_Model;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Products_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ITEM_DATA = 1;
    private final int LOAD = 2;
    private List<Products_Model.Data> orderlist;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
private DepartmentDetialsActivity departmentDetialsActivity;
private MarketProfileActivity marketProfileActivity;
private Fragment_Views fragment_views;
private Fragment fragment;
    public Products_Adapter(List<Products_Model.Data> orderlist, Context context, Fragment fragment) {
        this.orderlist = orderlist;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
this.fragment=fragment;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType==ITEM_DATA)
        {
            ProductsHomeRowBinding binding  = DataBindingUtil.inflate(inflater, R.layout.products_home_row,parent,false);
            return new EventHolder(binding);

        }else
            {
                LoadMoreBinding binding = DataBindingUtil.inflate(inflater, R.layout.load_more,parent,false);
                return new LoadHolder(binding);
            }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EventHolder)
        {

((EventHolder) holder).binding.setLang(lang);
((EventHolder) holder).binding.setProductsmodel(orderlist.get(position));
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(context instanceof DepartmentDetialsActivity){
            departmentDetialsActivity=(DepartmentDetialsActivity)context;
            departmentDetialsActivity.displayproduct(orderlist.get(holder.getLayoutPosition()).getId()+"");
        }
        else if(context instanceof  MarketProfileActivity){
            marketProfileActivity=(MarketProfileActivity)context;
            marketProfileActivity.displayproduct(orderlist.get(holder.getLayoutPosition()).getId()+"");
        }
        else if(context instanceof HomeActivity){
            fragment_views=(Fragment_Views)fragment;
            fragment_views.displayproduct(orderlist.get(holder.getLayoutPosition()).getId()+"");
        }
    }
});
        }else
            {
                LoadHolder loadHolder = (LoadHolder) holder;
                loadHolder.binding.progBar.setIndeterminate(true);
            }
    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        public ProductsHomeRowBinding binding;
        public EventHolder(@NonNull ProductsHomeRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public class LoadHolder extends RecyclerView.ViewHolder {
        private LoadMoreBinding binding;
        public LoadHolder(@NonNull LoadMoreBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        }

    }

    @Override
    public int getItemViewType(int position) {
     Products_Model.Data order_Model = orderlist.get(position);
        if (order_Model!=null)
        {
            return ITEM_DATA;
        }else
            {
                return LOAD;
            }

    }


}
