package blasko.daniel.projetisn

import android.content.Context
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class WeatherNetworkClient(val context: Context){
    // Clé de l'API
    val apiKey = "4239ff0855db08e626d86d444748437f"

    // On construit l'URL de la requête avec retrofit et on instancie WeatherService pour effectuer la requête (fonction weatherByCity) avec la ville et la clé de l'API en paramètres, pour renvoyer l'objet weather contenant les différentes données
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