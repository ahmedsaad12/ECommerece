package com.elkhelj.ecommerece.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_cart.CartActivity;
import com.elkhelj.ecommerece.databinding.CartRowBinding;
import com.elkhelj.ecommerece.models.Orders_Cart_Model;
import com.elkhelj.ecommerece.models.TypeDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Cart_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Orders_Cart_Model> orderlist;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
private CartActivity cartActivity;
    private ArrayList<TypeDataModel.TypeModel> numbModelList;

    public Cart_Adapter(List<Orders_Cart_Model> orderlist, Context context) {
        this.orderlist = orderlist;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        CartRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.cart_row, parent, false);

        return new EventHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        EventHolder eventHolder = (EventHolder) holder;
       eventHolder.binding.setLang(lang);
        eventHolder.binding.setModel(orderlist.get(position));
        numbModelList = new ArrayList<>();
        Spinner_Type_Adapter numAdapter = new Spinner_Type_Adapter(numbModelList, context);

        numbModelList.add(new TypeDataModel.TypeModel("1"));
        numbModelList.add(new TypeDataModel.TypeModel("2"));
        numbModelList.add(new TypeDataModel.TypeModel("3"));
        eventHolder.binding.spinnerType.setAdapter(numAdapter);
        eventHolder.binding.imageDelete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(context instanceof  CartActivity){
            cartActivity=(CartActivity)context;
            cartActivity.removeitem(eventHolder.getLayoutPosition());
        }
    }
});
        eventHolder.binding.spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if(context instanceof  CartActivity){
                    cartActivity=(CartActivity)context;
                    cartActivity.additem(eventHolder.getLayoutPosition(),i+1);
                }



                //  Log.e("cc",city_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        public CartRowBinding binding;

        public EventHolder(@NonNull CartRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
