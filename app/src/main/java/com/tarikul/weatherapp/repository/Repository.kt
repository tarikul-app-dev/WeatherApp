package com.tarikul.weatherapp.repository

import com.tarikul.weatherapp.api.ApiService
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {

    suspend fun getCityList(
        latitude: String,
        longitude: String,
        cnt: String,
        key: String
    ) = apiService.getCityList(latitude, longitude, cnt, key)

}