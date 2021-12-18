package com.tarikul.weatherapp.utils

import com.tarikul.weatherapp.api.WeatherModel


class Singleton {

    companion object {
        val INSTANCE = Singleton()
    }
    lateinit var cityDetails: WeatherModel.CityList

}