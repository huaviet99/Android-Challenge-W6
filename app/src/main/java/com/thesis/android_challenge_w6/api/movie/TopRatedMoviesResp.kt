package com.thesis.android_challenge_w6.movie

data class TopRatedMoviesResp (
    val page: Long,
    val results: List<TopRatedMovies>,
    val totalResults: Long,
    val totalPages: Long
)




