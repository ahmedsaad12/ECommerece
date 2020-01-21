package com.elkhelj.ecommerece.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_follower.FollowingActivity;
import com.elkhelj.ecommerece.databinding.FollowRowBinding;
import com.elkhelj.ecommerece.databinding.WishRowBinding;
import com.elkhelj.ecommerece.models.Wish_Model;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Following_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Wish_Model> orderlist;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private int i = 0;
   private FollowingActivity followingActivity;

    public Following_Adapter(List<Wish_Model> orderlist, Context context) {
        this.orderlist = orderlist;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        followingActivity=(FollowingActivity)context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        FollowRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.follow_row, parent, false);
        return new EventHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        EventHolder eventHolder = (EventHolder) holder;
    eventHolder.binding.setModel(orderlist.get(position));
eventHolder.binding.follow.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        followingActivity.followads(orderlist.get(eventHolder.getLayoutPosition()).getId()+"",eventHolder.getLayoutPosition());
    }
});
eventHolder.binding.share.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        followingActivity.share(orderlist.get(eventHolder.getLayoutPosition()));
    }
});
    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        public FollowRowBinding binding;

        public EventHolder(@NonNull FollowRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
