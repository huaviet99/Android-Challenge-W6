package com.thesis.android_challenge_w6.presentation.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thesis.android_challenge_w6.data.RestaurantDataStore
import com.thesis.android_challenge_w6.model.Restaurant

class TopRatedListViewModel : ViewModel(){
    val accessedEmail = MutableLiveData<String>()
    private val restaurantList = MutableLiveData<List<Restaurant>>()
    val isLinearSwitched = MutableLiveData<Boolean>()

    init {
        isLinearSwitched.value = true
    }
    fun fetchRestaurantList(): LiveData<List<Restaurant>>{
        Log.d("FavoriteLs","email=${accessedEmail.value}")
        val data =  RestaurantDataStore.getAllRestaurantListWithFavoriteChecked(accessedEmail.value!!)
        restaurantList.postValue(data)
        return restaurantList

    }
}