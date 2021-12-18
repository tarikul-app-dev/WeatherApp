package com.tarikul.weatherapp.database


import com.tarikul.weatherapp.database.tables.WeatherTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepository @Inject constructor(private val dao: DAO) {

    fun getWeather() :  List<WeatherTable>   {
        val response = dao.getAllWeather()
        return  response
    }

    suspend fun insertWeather(weatherTable: WeatherTable) = withContext(Dispatchers.IO) {
        dao.insertWeather(weatherTable)
    }

    suspend fun deleteAll() = withContext(Dispatchers.IO) {
        dao.deleteAll()
    }

}