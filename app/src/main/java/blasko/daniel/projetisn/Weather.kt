package blasko.daniel.projetisn

import com.google.gson.annotations.SerializedName

// Classe Weather avec une nested class Main (constructeurs = les données dont un aura besoin de l'API)
class WeatherClass{
    var main : Main = Main()
    var wind : Wind = Wind()
    // var weather : Weather = Weather()

}

class Main {
    var temp : Float = 0.0f
    @SerializedName("temp_min")
    var minTemp : Float = 0.0f
    @SerializedName("temp_max")
    var maxTemp : Float = 0.0f
    @SerializedName("humidity")
    var humidity : Float = 0.0f
}

class Wind {
    @SerializedName("speed")
    var speed : Float = 0.0f
}

/* class Weather{
    @SerializedName("weather")
    var weather = listOf(String)
} */





