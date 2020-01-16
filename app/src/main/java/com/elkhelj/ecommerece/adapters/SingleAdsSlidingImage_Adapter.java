package com.elkhelj.ecommerece.adapters;


import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;


import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.activities_fragments.activity_adsdetails.AdsDetialsActivity;
import com.elkhelj.ecommerece.databinding.SliderBinding;
import com.elkhelj.ecommerece.models.Single_Adversiment_Model;
import com.elkhelj.ecommerece.tags.Tags;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SingleAdsSlidingImage_Adapter extends PagerAdapter {


    List<Single_Adversiment_Model.Products.Images> IMAGES;
    private LayoutInflater inflater;
     Context context;

private AdsDetialsActivity adsDetialsActivity;
    public SingleAdsSlidingImage_Adapter(Context context, List<Single_Adversiment_Model.Products.Images> IMAGES) {
        this.context = context;
        this.IMAGES=IMAGES;
        inflater = LayoutInflater.from(context);
        adsDetialsActivity=(AdsDetialsActivity)context;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        SliderBinding rowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.slider,view,false);


        Picasso.with(context).load(Uri.parse(Tags.base_url+IMAGES.get(position).getImage())).fit().into(rowBinding.image);

        view.addView(rowBinding.getRoot());
        return rowBinding.getRoot();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
