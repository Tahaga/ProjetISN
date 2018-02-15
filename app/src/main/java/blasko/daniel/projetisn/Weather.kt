package blasko.daniel.projetisn

import com.google.gson.annotations.SerializedName

// Classe Weather avec une nested class Main (constructeurs = les données dont un aura besoin de l'API)
class Weather {
    var main : Main = Main()
}

class Main {
    var temp : Float = 0.0f
    @SerializedName("temp_min")
    var minTemp : Float = 0.0f
    @SerializedName("temp_max")
    var maxTemp : Float = 0.0f
}


