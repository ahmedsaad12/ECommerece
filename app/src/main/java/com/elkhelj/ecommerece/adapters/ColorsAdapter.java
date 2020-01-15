package com.elkhelj.ecommerece.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_adsdetails.AdsDetialsActivity;
import com.elkhelj.ecommerece.databinding.ColorRowBinding;
import com.elkhelj.ecommerece.models.Single_Adversiment_Model;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class ColorsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ITEM_DATA = 1;
    private final int LOAD = 2;
    private List<Single_Adversiment_Model.Colors> orderModelList;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    int index = 0;
private AdsDetialsActivity adsDetialsActivity;
    public ColorsAdapter(List<Single_Adversiment_Model.Colors> orderModelList, Context context) {
        this.orderModelList = orderModelList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
adsDetialsActivity=(AdsDetialsActivity)context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            ColorRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.color_row, parent, false);
            return new EventHolder(binding);



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Single_Adversiment_Model.Colors order_orderModel = orderModelList.get(position);
        if (holder instanceof EventHolder)
        {
        EventHolder eventHolder = (EventHolder) holder;
        eventHolder.binding.image.setCircleBackgroundColor(Color.parseColor("#6f4eb1"));
        eventHolder.binding.setColormodel(order_orderModel);
            GradientDrawable draw = new GradientDrawable();
            draw.setShape(GradientDrawable.OVAL);
            draw.setColor(Color.parseColor(order_orderModel.getColor().replace(" ","")));
            eventHolder.binding.image.setBackground(draw);
            eventHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    index=eventHolder.getLayoutPosition();
                    adsDetialsActivity.setcolor(orderModelList.get(index));
                    notifyDataSetChanged();
                }
            });
            if(index==position){
                eventHolder.binding.image.setBackground(context.getResources().getDrawable(R.drawable.ic_check));

            }else {
                draw.setShape(GradientDrawable.OVAL);
                draw.setColor(Color.parseColor(order_orderModel.getColor().replace(" ","")));
                eventHolder.binding.image.setBackground(draw);
            }
        }
    }

    @Override
    public int getItemCount() {
        return orderModelList.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        public ColorRowBinding binding;

        public EventHolder(ColorRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


    @Override
    public int getItemViewType(int position) {
        Single_Adversiment_Model.Colors order_Model = orderModelList.get(position);
        if (order_Model!=null)
        {
            return ITEM_DATA;
        }else
        {
            return LOAD;
        }

    }


}
