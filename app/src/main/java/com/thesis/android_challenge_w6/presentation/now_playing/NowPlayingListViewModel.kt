package com.thesis.android_challenge_w6.presentation.now_playing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thesis.android_challenge_w6.data.RestaurantDataStore
import com.thesis.android_challenge_w6.model.Restaurant

class NowPlayingListViewModel : ViewModel(){

    private val restaurantList = MutableLiveData<List<Restaurant>>()
     val isLinearSwitched = MutableLiveData<Boolean>()
    init {
        isLinearSwitched.value = true
    }

    val accessedEmail = MutableLiveData<String>()
    fun fetchRestaurantList(): LiveData<List<Restaurant>>{
       val data = RestaurantDataStore.getAllRestaurantListWithFavoriteChecked(accessedEmail.value!!)
        restaurantList.postValue(data)
        return restaurantList
    }


}