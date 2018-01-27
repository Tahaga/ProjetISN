package blasko.daniel.projetisn

import android.content.Context
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class WeatherNetworkClient(val context: Context){
    val apiKey = "4239ff0855db08e626d86d444748437f"

    fun getWeatherByCity(city:String): Call<Weather> {
        val network = Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val weatherServices = network.create(WeatherService::class.java)
        val weather = weatherServices.weatherByCity("imperial", city, apiKey)
        return weather
    }

}