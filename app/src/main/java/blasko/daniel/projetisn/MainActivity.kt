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
import java.util.*


class MainActivity : AppCompatActivity() {

    // Variables de  classe pour les réutiliser dans la méthode de partage
    var textMen : String = ""
    var textWomen : String = ""

    // Pour générer un nombre au hasard
    val random = Random()
    var randomNumber : Int = 0
    var randomNumberTwo : Int = 0

    // < 0°C
    // Tableaux des différentes tenues par tranches de températures
    val veryColdOne : IntArray = intArrayOf(doudoune, doudoune2, doudoune3, doudoun3, manteau, pull4, pull2, pull3, hoodie)
    val veryColdTwo : IntArray = intArrayOf(bonnet, bonnet2, bonnet3, boots, boots2, boots3, gants2, gants, scarf)
    val veryColdOneText : Array<String> = arrayOf("A warm jacket", "A warm jacket", "A warm jacket", "A warm jacket", "A warm jacket", "A sweater", "A sweater", "A sweater", "A sweater")
    val veryColdTwoText : Array<String> = arrayOf("A woolly hat", "A woolly hat", "A woolly hat", "Warm boots", "Warm boots", "Warm boots", "Gloves", "Gloves", "A scarf")

    // 0-7°C  pour l'instant
    val coldOne : IntArray = intArrayOf(hoodie, hoodie2, pull, pull2, pull3, pull4, sweat, bonnet, bonnet3, scarf)
    val coldTwo : IntArray = intArrayOf(doudoune, doudoune2, doudoun3, doudoune3, manteau, manteau3, manteau4, manteau5, manteau6, manteau7)
    val coldOneText : Array<String> = arrayOf("A warm sweater", "A warm sweater", "A warm sweater", "A warm sweater", "A warm sweater", "A warm sweater", "A warm sweater", "A woolly hat", "A woolly hat", "A scarf")
    val coldTwoText : Array<String> = arrayOf("A warm jacket", "A warm jacket", "A warm jacket", "A warm jacket", "A warm jacket", "A warm jacket", "A warm jacket", "A warm jacket", "A warm jacket", "A warm jacket")

    // 6-10°C (non utilisé pour l'instant)
    val normalOne : IntArray = intArrayOf()
    val normalTwo : IntArray = intArrayOf()
    val normalOneText : Array<String> = arrayOf()
    val normalTwoText : Array<String> = arrayOf()

    // 7-14°C pour l'instant
    val warmOne : IntArray = intArrayOf(chemise, chemise1, chemise2, chemise3, chemise4, chemise5, chemise6, suit, suit2, suit3, pull3, pull, pull2)
    val warmTwo : IntArray = intArrayOf(manteau, manteau3, manteau4, manteau5)
    val warmOneText : Array<String> = arrayOf("A shirt", "A shirt", "A shirt", "A shirt", "A shirt", "A shirt", "A shirt", "A suit", "A suit", "A suit", "A sweater", "A sweater", "A sweater")
    val warmTwoText : Array<String> = arrayOf("A coat", "A coat", "A coat", "A coat")

    // 14-20°C
    val hotOne : IntArray = intArrayOf()
    val hotTwo : IntArray = intArrayOf()
    val hotOneText : Array<String> = arrayOf()
    val hotTwoText : Array<String> = arrayOf()

    // 20+°C
    val boilingOne : IntArray = intArrayOf()
    val boilingTwo : IntArray = intArrayOf()
    val boilingOneText : Array<String> = arrayOf()
    val boilingTwoText : Array<String> = arrayOf()


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
        menuInflater.inflate(R.menu.main_refresh_button, menu)
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
            R.id.buttonRefresh -> {
                val intent = intent
                var city : String = intent.getStringExtra("city")
                val myIntent = Intent(this@MainActivity, MainActivity::class.java)
                myIntent.putExtra("city", city)
                startActivity(myIntent)
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
                        randomNumber = random.nextInt(veryColdOne.size + 1)
                        randomNumberTwo = random.nextInt(veryColdTwo.size + 1)
                        textMen = veryColdOneText[randomNumber]
                        textWomen = veryColdTwoText[randomNumberTwo]
                        imageView.setImageResource(veryColdOne[randomNumber])
                        imageView3.setImageResource(veryColdTwo[randomNumberTwo])
                    }
                    (temp <= 7 && temp > 0) -> {
                        randomNumber = random.nextInt(coldOne.size + 1)
                        randomNumberTwo = random.nextInt(coldTwo.size + 1)
                        textMen = coldOneText[randomNumber]
                        textWomen = coldTwoText[randomNumberTwo]
                        imageView.setImageResource(coldOne[randomNumber])
                        imageView3.setImageResource(coldTwo[randomNumberTwo])
                    }
                    (temp <= 14 && temp > 7) -> {
                        randomNumber = random.nextInt(warmOne.size + 1)
                        randomNumberTwo = random.nextInt(warmTwo.size + 1)
                        textMen = warmOneText[randomNumber]
                        textWomen = warmTwoText[randomNumberTwo]
                        imageView.setImageResource(warmOne[randomNumber])
                        imageView3.setImageResource(warmTwo[randomNumberTwo])
                    }
                    (temp <= 20 && temp > 14) -> {
                        randomNumber = random.nextInt(hotOne.size + 1)
                        randomNumberTwo = random.nextInt(hotTwo.size + 1)
                        textMen = hotOneText[randomNumber]
                        textWomen = hotTwoText[randomNumberTwo]
                        imageView.setImageResource(hotOne[randomNumber])
                        imageView3.setImageResource(hotTwo[randomNumberTwo])
                    }
                    else -> {
                        randomNumber = random.nextInt(boilingOne.size + 1)
                        randomNumberTwo = random.nextInt(boilingTwo.size + 1)
                        textMen = boilingOneText[randomNumber]
                        textWomen = boilingTwoText[randomNumberTwo]
                        imageView.setImageResource(boilingOne[randomNumber])
                        imageView3.setImageResource(boilingTwo[randomNumberTwo])
                    }
                }

                textViewMenText.text = textMen
                textViewWomenText.text = textWomen

                if (city == "Neuhof" || city == "neuhof") {
                    textView2.text = "ET"
                    textViewMenText.text = "Ce genre de survet"
                    textViewWomenText.text = "Un p'tit jogging"
                    imageView.setImageResource(yamaha)
                    imageView3.setImageResource(jogging)
                }

            }
        })


    }


}
