package com.thesis.android_challenge_w6.presentation.now_playing

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesis.android_challenge_w6.data.RestaurantDataStore
import com.thesis.android_challenge_w6.model.Restaurant
import com.thesis.android_challenge_w6.movie.NowPlayingMovies
import com.thesis.android_challenge_w6.movie.NowPlayingMoviesResp
import com.thesis.android_challenge_w6.rest.RestClient
import kotlinx.coroutines.launch

class NowPlayingListViewModel : ViewModel() {

    private val restaurantList = MutableLiveData<List<Restaurant>>()
    val isLinearSwitched = MutableLiveData<Boolean>()
    private val nowPlayingListRespone = MutableLiveData<List<NowPlayingMovies>>()

    init {
        isLinearSwitched.value = true
    }

    val accessedEmail = MutableLiveData<String>()
    fun fetchRestaurantList(): LiveData<List<Restaurant>> {
        val data =
            RestaurantDataStore.getAllRestaurantListWithFavoriteChecked(accessedEmail.value!!)
        restaurantList.postValue(data)
        return restaurantList
    }

    fun getNowPlaying(): LiveData<List<NowPlayingMovies>> {
        viewModelScope.launch {
            val nowPlayingMoviesResp = RestClient.getInstance().API.listNowPlayMovies(
                language = "en-US",
                page = 1,
                apiKey = "7519cb3f829ecd53bd9b7007076dbe23"
            )
            Log.e("Now Play", nowPlayingMoviesResp.results.toString())
            nowPlayingListRespone.value = nowPlayingMoviesResp.results
        }
        return nowPlayingListRespone
    }

}