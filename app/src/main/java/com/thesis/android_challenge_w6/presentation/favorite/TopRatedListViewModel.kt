package com.thesis.android_challenge_w6.presentation.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesis.android_challenge_w6.data.RestaurantDataStore
import com.thesis.android_challenge_w6.model.Restaurant
import com.thesis.android_challenge_w6.rest.RestClient
import kotlinx.coroutines.launch

class TopRatedListViewModel : ViewModel(){
    val accessedEmail = MutableLiveData<String>()
    private val restaurantList = MutableLiveData<List<Restaurant>>()

    fun fetchRestaurantList(): LiveData<List<Restaurant>>{
        Log.d("FavoriteLs","email=${accessedEmail.value}")
        val data =  RestaurantDataStore.getAllRestaurantListWithFavoriteChecked(accessedEmail.value!!)
        restaurantList.postValue(data)
        return restaurantList
    }

    fun getTopRated(){
        viewModelScope.launch {
            val topRatedMoviesResp = RestClient.getInstance().API.listTopRatedMovies(language = "en-US", page = 1)
            Log.e("Top Rated", topRatedMoviesResp.results.toString())
        }
    }
}