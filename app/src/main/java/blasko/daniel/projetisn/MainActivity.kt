package blasko.daniel.projetisn

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.location.Geocoder
import java.util.*
import android.location.LocationManager
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.support.v7.widget.Toolbar
import blasko.daniel.projetisn.R.drawable.cloud
import blasko.daniel.projetisn.R.drawable.logo2
import blasko.daniel.projetisn.R.styleable.Toolbar
import blasko.daniel.projetisn.R.styleable.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    //@SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // On envoie la requête à l'API
        val network = WeatherNetworkClient(applicationContext)
        val call = network.getWeatherByCity("Strasbourg")
        call.enqueue(object : Callback<Weather>{
            override fun onFailure(call: Call<Weather>?, t: Throwable?) {
                t?.printStackTrace()
            }
            // En cas de réponse positive, on affiche les données dans l'interface
            override fun onResponse(call: Call<Weather>?, response: Response<Weather>?) {
                val weather: Weather? = response?.body()
                val temp = convertToCelsius(weather?.main?.temp!!)
                val minTemp = convertToCelsius(weather?.main?.minTemp!!)
                val maxTemp = convertToCelsius(weather?.main?.maxTemp!!)
                println("temp : $temp")
                textViewTempMax.setText("Maximal temperature : ${maxTemp.toString()}°C")
                textViewTempMin.setText("Minimal temperature : ${minTemp.toString()}°C")
                textViewTempAct.setText("Actual temperature : ${temp.toString()}°C")

                if(temp <= 13){
                    imageViewMeteo.setImageResource(cloud)
                }
            }

        })


    }

    // Fonction pour convertir la température obtenue en degrés Fahrenheit en degrés Celsius
    fun convertToCelsius(temp: Float): Double{
        return Math.round(((temp - 32)/1.8)).toDouble()
    }

}
