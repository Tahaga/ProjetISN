package blasko.daniel.projetisn

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_welcome.*
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent



class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // On lance MainActivity au clic sur le logo, pas d'extra avec l'intent pour l'instant
        activityLogo.setOnClickListener {
            val myIntent = Intent(this@WelcomeActivity, MainActivity::class.java)
            //myIntent.putExtra("key", value) -> Quand on demandera la ville
            startActivity(myIntent)
        }
    }
}
