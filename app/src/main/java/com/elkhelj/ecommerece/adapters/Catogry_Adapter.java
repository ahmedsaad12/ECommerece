package com.elkhelj.ecommerece.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_home.fragments.fragmentmaim.Fragment_Men;
import com.elkhelj.ecommerece.activities_fragments.activity_home.fragments.fragmentmaim.Fragment_Mens;
import com.elkhelj.ecommerece.databinding.CatogryRowBinding;
import com.elkhelj.ecommerece.databinding.SizeRowBinding;
import com.elkhelj.ecommerece.models.Home_Model;
import com.elkhelj.ecommerece.models.Single_Adversiment_Model;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Catogry_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ITEM_DATA = 1;
    private final int LOAD = 2;
    private List<Home_Model.Categories> orderModelList;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    int index = -1;
private Fragment_Mens fragment_men;
private Fragment fragment;
    public Catogry_Adapter(List<Home_Model.Categories> orderModelList, Context context, Fragment fragment) {
        this.orderModelList = orderModelList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
//fragment_men=(Fragment_Men)fragment;
        this.fragment=fragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            CatogryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.catogry_row, parent, false);
            return new EventHolder(binding);



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Home_Model.Categories order_orderModel = orderModelList.get(position);
        if (holder instanceof EventHolder)
        {
        EventHolder eventHolder = (EventHolder) holder;
        eventHolder.binding.setLang(lang);
        eventHolder.binding.setCatogrymodel(order_orderModel);
        try {
            Log.e("da",order_orderModel.getName());

        }
        catch (Exception e){

        }
eventHolder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        fragment_men=(Fragment_Mens)fragment;
        fragment_men.getproduct(orderModelList.get(eventHolder.getLayoutPosition()).getId());
    }
});
        }
    }

    @Override
    public int getItemCount() {
        return orderModelList.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        public CatogryRowBinding binding;

        public EventHolder(CatogryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


    @Override
    public int getItemViewType(int position) {
        Home_Model.Categories order_Model = orderModelList.get(position);
        if (order_Model!=null)
        {
            return ITEM_DATA;
        }else
        {
            return LOAD;
        }

    }


}
