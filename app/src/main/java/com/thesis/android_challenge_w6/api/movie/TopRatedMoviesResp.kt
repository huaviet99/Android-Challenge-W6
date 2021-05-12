package com.thesis.android_challenge_w6.api.movie


data class TopRatedMoviesResp (
    val page: Long,
    val results: List<Movie>,
    val totalResults: Long,
    val totalPages: Long
)




