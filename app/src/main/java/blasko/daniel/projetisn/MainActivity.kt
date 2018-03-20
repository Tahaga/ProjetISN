package blasko.daniel.projetisn

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import blasko.daniel.projetisn.R.drawable.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    // Variables de  classe pour les réutiliser dans la méthode de partage
    var textMen : String = ""
    var textWomen : String = ""

    // Fonction pour convertir la température obtenue en degrés Fahrenheit en degrés Celsius
    fun convertToCelsius(temp: Float): Double{
        return Math.round(((temp - 32)/1.8)).toDouble()
    }

    // Fonction pour convertir la vitesse en mph en kmh
    fun convertToKmh(speed : Float): Double{
        return Math.round((speed * 1.60934)).toDouble()
    }


    // On inflate le menu partager dans l'actionbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_share_button, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // On définit l'action lors de l'appui dans l'action bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
        // Si l'on appuie sur le bouton de partage...
            R.id.buttonShare -> {

                // On prépare l'intent pour passer les données à l'application externe
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND

                // On définit ensuite les données que l'on va passer à l'application
                shareIntent.setType("text/plain")
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Today I'll wear ${textMen.toLowerCase()} and ${textWomen.toLowerCase()}! Download MeteoWear on the playstore to know what you should wear based on the weather forecast! https://play.google.com/store/apps/details?id=blasko.daniel.projetisn")

                // On passe les données
                startActivity(Intent.createChooser(shareIntent, "Partage ta tenue sur :"))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

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
                //t?.printStackTrace()
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


                when {
                    (temp <= 0 && temp > -40) -> {
                        textMen = "A warm jacket"
                        textWomen = "Warm boots"
                        imageView.setImageResource(doudoune)
                        imageView3.setImageResource(boots)
                    }
                    (temp <= 5 && temp > 0) -> {
                        textMen = "A warmer sweather"
                        textWomen = "A jacket"
                        imageView.setImageResource(pull)
                        imageView3.setImageResource(doudoune)
                    }
                    (temp <= 10 && temp > 5) -> {
                        textMen = "A shirt"
                        textWomen = "A warmer coat"
                        imageView.setImageResource(chemise)
                        imageView3.setImageResource(manteau)
                    }
                    (temp <= 15 && temp > 10) -> {
                        textMen = "A sweatshirt"
                        textWomen = "Jeans"
                        imageView.setImageResource(sweat)
                        imageView3.setImageResource(pantalon)
                    }
                    (temp <= 20 && temp > 15) -> {
                        textMen = "A shirt"
                        textWomen = "Sneakers"
                        imageView.setImageResource(tshirt)
                        imageView3.setImageResource(baskets)
                    }
                    else -> {
                        textMen = "A t-shirt"
                        textWomen = "A rock"
                        textView2.setText("OR")
                        imageView.setImageResource(tshirt)
                        imageView3.setImageResource(tshirtjupe)
                    }
                }

                textViewMenText.text = textMen
                textViewWomenText.text = textWomen

            }

        })


    }


}
