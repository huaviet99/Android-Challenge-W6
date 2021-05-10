package com.thesis.android_challenge_w6.presentation.top

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thesis.android_challenge_w6.data.RestaurantDataStore
import com.thesis.android_challenge_w6.model.Restaurant

class TopListViewModel : ViewModel(){

    private val restaurantList = MutableLiveData<List<Restaurant>>()
    val accessedEmail = MutableLiveData<String>()
    fun fetchRestaurantList(): LiveData<List<Restaurant>>{
       val data = RestaurantDataStore.getAllRestaurantListWithFavoriteChecked(accessedEmail.value!!)
        restaurantList.postValue(data)
        return restaurantList
    }

    fun toggleFavoriteRestaurant(restaurant: Restaurant){
        RestaurantDataStore.setFavoriteRestaurantListListener(favoriteRestaurantListListener)
        RestaurantDataStore.toggleFavoriteRestaurantByEmail(accessedEmail.value!!,restaurant)
    }


    private val favoriteRestaurantListListener = object : RestaurantDataStore.FavoriteRestaurantListListener {
        override fun onDataChanged(restaurantList: List<Restaurant>) {
            this@TopListViewModel.restaurantList.postValue(restaurantList)
        }
    }
}