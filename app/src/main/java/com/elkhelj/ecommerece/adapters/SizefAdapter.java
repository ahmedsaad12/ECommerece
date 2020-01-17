package com.elkhelj.ecommerece.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_adsdetails.AdsDetialsActivity;
import com.elkhelj.ecommerece.activities_fragments.activity_search.FilterActivity;
import com.elkhelj.ecommerece.databinding.SizeRowBinding;
import com.elkhelj.ecommerece.databinding.SizefRowBinding;
import com.elkhelj.ecommerece.models.Single_Adversiment_Model;
import com.elkhelj.ecommerece.models.Size_Model;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class SizefAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ITEM_DATA = 1;
    private final int LOAD = 2;
    private List<Size_Model> orderModelList;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    int index = -1;
private FilterActivity adsDetialsActivity;
    public SizefAdapter(List<Size_Model> orderModelList, Context context) {
        this.orderModelList = orderModelList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
adsDetialsActivity=(FilterActivity) context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            SizefRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.sizef_row, parent, false);
            return new EventHolder(binding);



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Size_Model order_orderModel = orderModelList.get(position);
        if (holder instanceof EventHolder)
        {
        EventHolder eventHolder = (EventHolder) holder;
        eventHolder.binding.setSizemodel(order_orderModel);
eventHolder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        index=eventHolder.getLayoutPosition();
        notifyDataSetChanged();
    }
});
if(index==position){
adsDetialsActivity.setsize(orderModelList.get(index).getId());
eventHolder.binding.tvTitle.setBackground(context.getResources().getDrawable(R.drawable.image_user_bg3));
}
else {
    eventHolder.binding.tvTitle.setBackground(context.getResources().getDrawable(R.drawable.image_user_bg2));

}
        }
    }

    @Override
    public int getItemCount() {
        return orderModelList.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        public SizefRowBinding binding;

        public EventHolder(SizefRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


    @Override
    public int getItemViewType(int position) {
       Size_Model order_Model = orderModelList.get(position);
        if (order_Model!=null)
        {
            return ITEM_DATA;
        }else
        {
            return LOAD;
        }

    }


}
