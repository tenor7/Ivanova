package com.example.kinopoiskapi.data.api

import com.example.myapplication.api.ApiResponse
import com.example.myapplication.data.FilmDetailsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiService {
    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("/api/v2.2/films/top")
    fun getTopFilms(@Query("type") type: String): Call<ApiResponse>

    /*
    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("/api/v2.2/films/{id}")
    fun getFilmDetails(@Query("id") id: Int): Call<FilmDetailsResponse> */
}