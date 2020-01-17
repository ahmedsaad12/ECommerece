package com.elkhelj.ecommerece.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.models.Color_Model;
import com.elkhelj.ecommerece.models.Size_Model;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;


public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.MyHolder> {

    private List<Color_Model> bankDataModelList;
    private Context context;
    private String current_language;
    private Fragment fragment;

    public SkillsAdapter(List<Color_Model> bankDataModelList, Context context, Fragment fragment) {
        this.bankDataModelList = bankDataModelList;
        this.context = context;
        Paper.init(context);
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
this.fragment=fragment;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.service_row, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {

        Color_Model bankModel = bankDataModelList.get(position);
        holder.BindData(bankModel);
holder.image_delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});

    }

    @Override
    public int getItemCount() {
        return bankDataModelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
private ImageView image_delete;

        public MyHolder(View itemView) {
            super(itemView);
tv_title=itemView.findViewById(R.id.tv_title);
image_delete=itemView.findViewById(R.id.image_delete);



        }

        public void BindData(Color_Model bankModel) {
            tv_title.setText(bankModel.getName());}

           // tv_account_iban.setText(bankModel.getIban());


        }

}
