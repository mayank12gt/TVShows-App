package com.example.tvshows.utility;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.tvshows.R;

public class BindingAdapters {
    @BindingAdapter("imageURL")
    public static void loadimageURL(ImageView imageView,String ImageURL ){
        imageView.setAlpha(1f);
        Glide.with(imageView.getContext()).load(ImageURL)
                .placeholder(R.drawable.ic_baseline_image_24).into(imageView);

    }
}
