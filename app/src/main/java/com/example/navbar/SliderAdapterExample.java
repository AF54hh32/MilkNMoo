package com.example.navbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

   private Context context;
   List <imageSliderModel> imageSliderModelList;

    public SliderAdapterExample(Context context, List<imageSliderModel> imageSliderModelList) {
        this.context = context;
        this.imageSliderModelList = imageSliderModelList;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_display,parent,false);
        return new SliderAdapterVH(view);
    }


    @Override
    public void onBindViewHolder(SliderAdapterExample.SliderAdapterVH viewHolder, int position) {

        viewHolder.imageViewBackground.setImageResource(imageSliderModelList.get(position).getImage());
    }

    @Override
    public int getCount() {
        return imageSliderModelList.size();
    }

     class SliderAdapterVH extends SliderViewAdapter.ViewHolder
     {
           View itemView;
           ImageView imageViewBackground;
           ImageView imageGifContainer;
           TextView textViewDescription;

         public SliderAdapterVH(View itemView) {
             super(itemView);
             imageViewBackground=itemView.findViewById(R.id.iv_auto_image_slider);

         }
     }
}


































/*public class ImageAdapter extends PagerAdapter {
    Context mcontext;

    public ImageAdapter(Context context)
    {
        this.mcontext=context;
    }

private int[]sliderImageId=new int[]{
  R.drawable.buffmilk,R.drawable.cow_milk, R.drawable.dahi,R.drawable.download
};

    @Override
    public int getCount() {
        return sliderImageId.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==((ImageView)object);
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager)container).removeView((ImageView)object);
    }
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView=new ImageView(mcontext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(sliderImageId[position]);
        ((ViewPager)container).addView(imageView,0);
        return imageView;
    }

}*/
