package com.tarikul.weatherapp.api


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("2.5/find")
    suspend fun getCityList(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("cnt") cnt: String,
        @Query("appid") key: String
    ): Response<WeatherModel?>
}