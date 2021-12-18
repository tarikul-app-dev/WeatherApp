package com.tarikul.weatherapp.utils

import com.tarikul.weatherapp.BuildConfig


class Config {
    companion object {
        const val baseUrl = BuildConfig.BASE_URL
        const val apiKey = BuildConfig.API_KEY
    }
}