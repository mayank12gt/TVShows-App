package com.example.tvshows.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshows.R;
import com.example.tvshows.databinding.ImageSliderItemBinding;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.viewholder> {

    private String[] sliderImages;

    public ImageSliderAdapter(String[] sliderImages) {
        this.sliderImages = sliderImages;
    }

    @NonNull
    @Override
    public ImageSliderAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageSliderItemBinding imageSliderItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.image_slider_item,parent,false);
        return new viewholder(imageSliderItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderAdapter.viewholder holder, int position) {
        holder.bindSliderImage(sliderImages[position]);
    }

    @Override
    public int getItemCount() {
        return sliderImages.length;
    }

    public class viewholder extends RecyclerView.ViewHolder{

        private ImageSliderItemBinding imageSliderItemBinding;


        public viewholder(ImageSliderItemBinding imageSliderItemBinding) {
            super(imageSliderItemBinding.getRoot());
            this.imageSliderItemBinding =imageSliderItemBinding;
        }
        public void bindSliderImage(String imageUrl){
            imageSliderItemBinding.setImageUrl(imageUrl);
        }
    }
}
