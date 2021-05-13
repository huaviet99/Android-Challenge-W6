package com.thesis.android_challenge_w6.presentation.now_playing

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesis.android_challenge_w6.api.RestClient
import com.thesis.android_challenge_w6.api.movie.Movie
import kotlinx.coroutines.launch

class NowPlayingListViewModel : ViewModel() {

    val isLinearSwitched = MutableLiveData(true)
    private val nowPlayingListResponse = MutableLiveData<List<Movie>>()


    fun getNowPlaying(): LiveData<List<Movie>> {
        viewModelScope.launch {
            val nowPlayingMoviesResp = RestClient.getInstance().API.listNowPlayMovies(
                language = "en-US",
                page = 1
            )
            Log.e("Now Play", nowPlayingMoviesResp.results.toString())
            nowPlayingListResponse.value = nowPlayingMoviesResp.results
        }
        return nowPlayingListResponse
    }

}