package com.thesis.android_challenge_w6.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestClient {
    private var api : MovieDBService

    val API : MovieDBService
        get() = api

    init {
        api = createMovieDBService()
    }

    companion object {
        private var mInstance: RestClient? = null

        fun getInstance() = mInstance ?: synchronized(this){
            mInstance ?: RestClient().also { mInstance = it }
        }
    }

    private fun createMovieDBService() : MovieDBService {

        //create retrofit
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(MovieDBService::class.java)
    }
}