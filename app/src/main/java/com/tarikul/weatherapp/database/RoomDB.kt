package com.safi.assignment.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tarikul.weatherapp.database.DAO
import com.tarikul.weatherapp.database.tables.WeatherTable


@Database(entities = [WeatherTable::class], version = 1)
abstract class RoomDB : RoomDatabase() {

    abstract fun dao() : DAO

}