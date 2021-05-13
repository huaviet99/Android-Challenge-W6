package com.thesis.android_challenge_w6.presentation.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesis.android_challenge_w6.api.RestClient
import com.thesis.android_challenge_w6.api.movie.Movie
import kotlinx.coroutines.launch

class TopRatedListViewModel : ViewModel() {
    val isLinearSwitched = MutableLiveData(true)

    private val topRatedResponse = MutableLiveData<List<Movie>>()



    fun getTopRated(): LiveData<List<Movie>> {
        viewModelScope.launch {
            val topRatedMoviesResp = RestClient.getInstance().API.listTopRatedMovies(
                language = "en-US",
                page = 1
            )
            Log.e("Top Rated", topRatedMoviesResp.results.toString())
            topRatedResponse.value = topRatedMoviesResp.results
        }
        return topRatedResponse
    }
}