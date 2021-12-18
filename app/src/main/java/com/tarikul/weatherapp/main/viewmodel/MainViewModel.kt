package com.tarikul.weatherapp.main.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tarikul.weatherapp.api.WeatherModel
import com.tarikul.weatherapp.database.DataRepository
import com.tarikul.weatherapp.database.tables.WeatherTable
import com.tarikul.weatherapp.repository.Repository
import com.tarikul.weatherapp.utils.CommonMethods
import com.tarikul.weatherapp.utils.Config
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.*

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val commonMethods: CommonMethods,
    private val dataRepo: DataRepository
) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val cityList = MutableLiveData<List<WeatherModel.CityList>>()
    var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun cityList() {

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            if (commonMethods.isOnline()) {
                val response = repository.getCityList("23.68", "90.35", "50", Config.apiKey)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        cityList.postValue(response.body()!!.getList() as List<WeatherModel.CityList>)
                        loading.value = false
                        saveDataInRoom(response.body()!!.getList() as List<WeatherModel.CityList>)

                    } else {
                        onError("Error : ${response.message()} ")
                    }
                }
            } else {
                dataFromRoom()
            }


        }
    }


    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    //room

    private fun saveDataInRoom(list: List<WeatherModel.CityList>) {
        viewModelScope.launch {
            dataRepo.deleteAll()
            for (item in list) {
                val city = WeatherTable(
                    roomID = 0,
                    id = item.id!!,
                    name = item.name!!,
                    description = item.weather!![0]!!.description.toString(),
                    lat = item.coord?.lat!!,
                    lon = item.coord?.lon!!,
                    temp = item.main?.temp!!,
                    temp_min = item.main?.tempMin!!,
                    temp_max = item.main?.tempMax!!,
                    humidity = item.main?.humidity!!,
                    speed = item.wind?.speed!!
                )
                dataRepo.insertWeather(city)
            }

        }

    }

    private fun dataFromRoom() {
        val _cityList = mutableListOf<WeatherModel.CityList>()

        viewModelScope.launch {
            val weatherList = dataRepo.getWeather()

            for (item in weatherList) {
                val city = WeatherModel.CityList()
                val main = WeatherModel.Main()
                val coord = WeatherModel.Coord()
                val wind = WeatherModel.Wind()
                val weatherList = mutableListOf<WeatherModel.Weather>()
                val weather = WeatherModel.Weather()

                main.temp = item.temp
                main.tempMin = item.temp_min
                main.tempMax = item.temp_max
                main.humidity = item.humidity

                coord.lat = item.lat
                coord.lon = item.lon

                wind.speed = item.speed
                city.id = item.id
                city.name = item.name

                weather.description = item.description
                weatherList.add(weather)

                city.weather = weatherList
                city.coord = coord
                city.main = main
                city.wind = wind
                _cityList.add(city)
            }

            cityList.postValue(_cityList)
            loading.value = false
        }


    }
}