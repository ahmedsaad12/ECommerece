package com.elkhelj.ecommerece.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.databinding.SpinnerCatogryRowBinding;
import com.elkhelj.ecommerece.databinding.SpinnerCityRowBinding;
import com.elkhelj.ecommerece.models.Catogry_Model;
import com.elkhelj.ecommerece.models.Cities_Model;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class CatogryAdapter extends BaseAdapter {
    private List<Catogry_Model> dataList;
    private Context context;
    private String lang;
    public CatogryAdapter(List<Catogry_Model> dataList, Context context) {
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
        @SuppressLint("ViewHolder") SpinnerCatogryRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.spinner_catogry_row,viewGroup,false);
        binding.setLang(lang);
        binding.setCityModel(dataList.get(i));
        return binding.getRoot();
    }
}
