package com.thesis.android_challenge_w6.api


import com.thesis.android_challenge_w6.api.movie.NowPlayingMoviesResp
import com.thesis.android_challenge_w6.api.movie.TopRatedMoviesResp
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDBService {

    @GET("movie/now_playing")
    suspend fun  listNowPlayMovies(
        @Query("language") language: String, @Query("page") page: Int
    ): NowPlayingMoviesResp

    @GET("movie/top_rated")
    suspend fun listTopRatedMovies(
        @Query("language") language: String, @Query("page") page: Int
    ): TopRatedMoviesResp
}