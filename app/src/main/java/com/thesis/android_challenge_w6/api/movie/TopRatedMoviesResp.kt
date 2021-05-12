package com.thesis.android_challenge_w6.movie

import com.thesis.android_challenge_w6.api.movie.Movie

data class TopRatedMoviesResp (
    val page: Long,
    val results: List<Movie>,
    val totalResults: Long,
    val totalPages: Long
)




