package com.tarikul.weatherapp.api

import android.content.Context
import androidx.room.Room
import com.safi.assignment.roomDB.RoomDB
import com.tarikul.weatherapp.database.DAO
import com.tarikul.weatherapp.database.DataRepository
import com.tarikul.weatherapp.repository.Repository
import com.tarikul.weatherapp.utils.Config
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Config.baseUrl)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun providesRepository(apiService: ApiService) = Repository(apiService)

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): RoomDB =
        Room.databaseBuilder(context, RoomDB::class.java, "weatherDB").build()

    @Provides
    fun provideDao(roomDB: RoomDB) : DAO =
        roomDB.dao()

    @Provides
    fun provideRoomRepo(dao: DAO) : DataRepository =
        DataRepository(dao)

}