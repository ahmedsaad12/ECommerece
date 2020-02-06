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
import com.elkhelj.ecommerece.activities_fragments.marktprofile.MarketProfileActivity;
import com.elkhelj.ecommerece.databinding.ProfileManCatogryRowBinding;
import com.elkhelj.ecommerece.models.Market_Profile_Model;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Profile_man_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ITEM_DATA = 1;
    private final int LOAD = 2;
    private List<Market_Profile_Model.CategoriesMale> orderModelList;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    int index = -1;
private MarketProfileActivity marketProfileActivity;

    public Profile_man_Adapter(List<Market_Profile_Model.CategoriesMale> orderModelList, Context context) {
        this.orderModelList = orderModelList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
//fragment_men=(Fragment_Men)fragment;
this.marketProfileActivity=(MarketProfileActivity)context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            ProfileManCatogryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.profile_man_catogry_row, parent, false);
            return new EventHolder(binding);



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Market_Profile_Model.CategoriesMale order_orderModel = orderModelList.get(position);
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
        index=eventHolder.getLayoutPosition();
        notifyDataSetChanged();
    }
});
        if(index==position){
            marketProfileActivity.getproduct(orderModelList.get(eventHolder.getLayoutPosition()).getId());
            eventHolder.binding.tvTitle.setBackground(context.getResources().getDrawable(R.drawable.linear_bg_gray));
       eventHolder.binding.tvTitle.setTextColor(context.getResources().getColor(R.color.white));
        }
        else {
            eventHolder.binding.tvTitle.setBackground(null);
            eventHolder.binding.tvTitle.setTextColor(context.getResources().getColor(R.color.black));

        }
        }
    }

    @Override
    public int getItemCount() {
        return orderModelList.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        public ProfileManCatogryRowBinding binding;

        public EventHolder(ProfileManCatogryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


    @Override
    public int getItemViewType(int position) {
        Market_Profile_Model.CategoriesMale order_Model = orderModelList.get(position);
        if (order_Model!=null)
        {
            return ITEM_DATA;
        }else
        {
            return LOAD;
        }

    }


}
