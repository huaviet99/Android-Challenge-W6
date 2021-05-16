package com.thesis.android_challenge_w6.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thesis.android_challenge_w6.api.movie.Movie

class DetailViewModel : ViewModel(){
    var movieData = MutableLiveData<Movie>()
}