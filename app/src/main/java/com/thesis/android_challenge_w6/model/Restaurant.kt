package com.thesis.android_challenge_w6.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Restaurant(var name: String,var address:String,var picturePath:String,var isFavorite:Boolean = false): Parcelable