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
import android.location.Location
import android.location.LocationListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    //@SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val lng = location.longitude
        val lat = location.latitude*/

        /*val locationListener: LocationListener = LocationListener() {
            fun onLocationChanged(location: Location) {
                lng = location.getLongitude()
                lat = location.getLatitude()
            }
        }*/



        /*val geocoder = Geocoder(this, Locale.getDefault())
        //lat,lng, your current location
        val addresses = geocoder.getFromLocation(lat, lng, 1)
        var code = addresses[0].getPostalCode()*/



        val network = WeatherNetworkClient(applicationContext)
        val call = network.getWeatherWithCode(67000)
        call.enqueue(object : Callback<Weather>{
            override fun onFailure(call: Call<Weather>?, t: Throwable?) {
                t?.printStackTrace()
            }

            override fun onResponse(call: Call<Weather>?, response: Response<Weather>?) {
                val weather: Weather? = response?.body()
                val temp = weather?.main?.temp
                println("temp : $temp")
                textViewTempMax.setText(temp.toString())
            }

        })


    }

    //private fun presentData(main: Main) {
    //currentTemp.text = main.temp.toString()
    //}
}
