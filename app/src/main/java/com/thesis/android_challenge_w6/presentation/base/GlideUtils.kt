package com.thesis.android_challenge_w6.presentation.base

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import android.widget.ImageView


object GlideUtils {

    fun loadImage(context:Context,imagePath: String,imageView:ImageView) {
        val circleProgressDrawable = CircularProgressDrawable(context)
        circleProgressDrawable.strokeWidth = 5f
        circleProgressDrawable.centerRadius = 30f
        circleProgressDrawable.start()

        Glide.with(context)
            .load(MOVIE_IMAGE_URL + imagePath)
            .placeholder(circleProgressDrawable)
            .into(imageView)
    }
}