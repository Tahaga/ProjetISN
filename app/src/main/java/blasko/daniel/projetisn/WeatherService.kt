package blasko.daniel.projetisn

import retrofit2.http.GET
import retrofit2.http.Query

// Interface qui envoie la requête
interface WeatherService{
    @GET("/data/2.5/weather")
    fun weatherByCity(@Query("units") units:String, @Query("q") city:String, @Query("APPID") apiKey:String): retrofit2.Call<Weather>
}
