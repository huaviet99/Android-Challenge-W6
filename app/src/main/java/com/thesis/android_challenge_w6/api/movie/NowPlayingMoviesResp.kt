package com.thesis.android_challenge_w6.api.movie

data class NowPlayingMoviesResp (
    val dates: Dates,
    val page: Long,
    val results: List<Movie>,
    val totalPages: Long,
    val totalResults: Long
)

data class Dates (
    val maximum: String,
    val minimum: String
)

