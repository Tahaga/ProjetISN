package blasko.daniel.projetisn

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import blasko.daniel.projetisn.R.drawable.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // On récupère l'intent avec la ville
        val intent = intent
        var city : String = intent.getStringExtra("city")

        // On instancie le client pour envoyer la requête
        val network = WeatherNetworkClient(applicationContext)
        // On envoie une requête à l'API avec la ville passée en paramètre de l'intent
        val call = network.getWeatherByCity(city)
        call.enqueue(object : Callback<WeatherClass>{
            override fun onFailure(call: Call<WeatherClass>?, t: Throwable?) {
                t?.printStackTrace()
            }
            // En cas de réponse positive, on affiche les données dans l'interface
            override fun onResponse(call: Call<WeatherClass>?, response: Response<WeatherClass>?) {
                val weather: WeatherClass? = response?.body()
                val temp = convertToCelsius(weather?.main?.temp!!)
                val humidity = weather?.main?.humidity!!
                val wind = convertToKmh(weather?.wind?.speed!!)
                // Weather data...
                println("temp : $temp")
                textViewWind.setText("Current wind speed : ${wind.toString()}km/h")
                textViewHumidity.setText("Actual humidity : ${humidity.toString()}%")
                textViewTempAct.setText("Actual temperature : ${temp.toString()}°C")

                if(humidity > 90){
                    imageViewMeteo.setImageResource(rain)
                }
                else if(temp < 15){
                    imageViewMeteo.setImageResource(cloud)
                }
                else{
                    imageViewMeteo.setImageResource(sun)
                }
                // REMPLACER PAR UN CASE SWITCH (－‸ლ)
                if (temp <= 0 && temp > -40){
                    textViewMenText.setText("A warm jacket")
                    textViewWomenText.setText("Warm boots")
                    imageView.setImageResource(doudoune)
                    imageView3.setImageResource(boots)
                }
                else if (temp <= 5 && temp > 0){
                    textViewMenText.setText("A longsleeve shirt")
                    textViewWomenText.setText("A warmer sweather")
                    imageView.setImageResource(manchelongues)
                    imageView3.setImageResource(pull)
                }
                else if (temp <= 10 && temp > 5){
                    textViewMenText.setText("A shirt")
                    textViewWomenText.setText("A warmer coat")
                    imageView.setImageResource(chemise)
                    imageView3.setImageResource(manteau)
                }
                else if (temp <= 15 && temp > 10) {
                    textViewMenText.setText("A longsleeve t-shirt")
                    textViewWomenText.setText("Some jeans")
                    imageView.setImageResource(manchelongues)
                    imageView3.setImageResource(pantalon)
                }
                else if (temp <= 20 && temp > 15){
                    textViewMenText.setText("A t-shirt")
                    textViewWomenText.setText("A light vest")
                    imageView.setImageResource(tshirt)
                    imageView3.setImageResource(gilet)
                }
                else {
                    textViewMenText.setText("A t-shirt")
                    textViewWomenText.setText("A rock")
                    textView2.setText("OR")
                    imageView.setImageResource(tshirt)
                    imageView3.setImageResource(tshirtjupe)
                }
            }

        })


    }

    // Fonction pour convertir la température obtenue en degrés Fahrenheit en degrés Celsius
    fun convertToCelsius(temp: Float): Double{
        return Math.round(((temp - 32)/1.8)).toDouble()
    }

    // Fonction pour convertir la vitesse en mph en kmh
    fun convertToKmh(speed : Float): Double{
        return Math.round((speed * 1.60934)).toDouble()
    }

}
