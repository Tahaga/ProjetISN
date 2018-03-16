package blasko.daniel.projetisn

import android.app.PendingIntent.getActivity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_welcome.*
import android.content.Intent
import android.widget.Toast


class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // On lance MainActivity au clic sur le logo, pas d'extra avec l'intent pour l'instant
        activityLogo.setOnClickListener {
            var city : String = editTextCity.text.toString()
            if (city == ""){
                Toast.makeText(this, "Please, enter you city name!", Toast.LENGTH_SHORT).show()
            }
            else {
                val myIntent = Intent(this@WelcomeActivity, MainActivity::class.java)
                myIntent.putExtra("city", city)
                startActivity(myIntent)
            }
        }
    }
}
