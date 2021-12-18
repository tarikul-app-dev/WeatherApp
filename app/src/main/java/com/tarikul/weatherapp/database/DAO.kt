package com.tarikul.weatherapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tarikul.weatherapp.database.tables.WeatherTable
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {

    @Insert
    suspend fun insertWeather(weatherTable: WeatherTable): Long

    @Query("DELETE FROM city")
    suspend fun deleteAll()

    @Query("SELECT * FROM city")
    fun getAllWeather(): List<WeatherTable>

}