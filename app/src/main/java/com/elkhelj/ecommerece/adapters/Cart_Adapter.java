package com.elkhelj.ecommerece.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

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
    private Spinner_Type_Adapter numAdapter;
    private ArrayAdapter<String> spinnerArrayAdapter;
private int index=-1;

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
        int index2 [] = new int[orderlist.size()];

        numbModelList = new ArrayList<>();
        numAdapter = new Spinner_Type_Adapter(numbModelList, context);

        numbModelList.add(new TypeDataModel.TypeModel("1"));
        numbModelList.add(new TypeDataModel.TypeModel("2"));
        numbModelList.add(new TypeDataModel.TypeModel("3"));
        String[] array = {"1", "2", "3"};
        for (int i = 0; i<numbModelList.size(); i++){
    if(array[i].equals(orderlist.get(position).getAmount()+"")){
        index2[position] =i;
        break;
    }
}
      spinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, array); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        eventHolder.binding.spinnerType.setAdapter(numAdapter);
        eventHolder.binding.spinnerType.setSelection(index2[position]);
        eventHolder.binding.imageDelete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(context instanceof  CartActivity){
            cartActivity=(CartActivity)context;
            cartActivity.removeitem(eventHolder.getLayoutPosition());

        }
    }
});
        eventHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index=eventHolder.getLayoutPosition();
                notifyDataSetChanged();
            }
        });
        eventHolder.binding.spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

if(index2[eventHolder.getLayoutPosition()]!=i){

    if(context instanceof  CartActivity){
        cartActivity=(CartActivity)context;
        cartActivity.additem(eventHolder.getLayoutPosition(),i+1);
    }
    index2[eventHolder.getLayoutPosition()] =i;

}

                //  Log.e("cc",city_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



   if(position==index){
       if(eventHolder.binding.expandLayout.isExpanded())
       {
       eventHolder.binding.expandLayout.collapse(true);
       }
       else {
           eventHolder.binding.expandLayout.expand(true);
       }
   }
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
