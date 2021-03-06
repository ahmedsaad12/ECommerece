package com.elkhelj.ecommerece.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;


import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.databinding.SpinnerFilterRowBinding;
import com.elkhelj.ecommerece.models.Filter_model;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class FilterAdapter extends BaseAdapter {
    private List<Filter_model> dataList;
    private Context context;
    private String lang;
    public FilterAdapter(List<Filter_model> dataList, Context context) {
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
        @SuppressLint("ViewHolder") SpinnerFilterRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.spinner_filter_row,viewGroup,false);
        binding.setLang(lang);
        binding.setFilterModel(dataList.get(i));
        return binding.getRoot();

    }
}
