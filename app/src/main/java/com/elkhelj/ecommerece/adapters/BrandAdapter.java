package com.elkhelj.ecommerece.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.databinding.SpinnerBrandRowBinding;
import com.elkhelj.ecommerece.databinding.SpinnerCatogryRowBinding;
import com.elkhelj.ecommerece.models.Brand_Model;
import com.elkhelj.ecommerece.models.Catogry_Model;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class BrandAdapter extends BaseAdapter {
    private List<Brand_Model> dataList;
    private Context context;
    private String lang;
    public BrandAdapter(List<Brand_Model> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") SpinnerBrandRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.spinner_brand_row,viewGroup,false);
        binding.setLang(lang);
        binding.setCityModel(dataList.get(i));
        return binding.getRoot();
    }
}
