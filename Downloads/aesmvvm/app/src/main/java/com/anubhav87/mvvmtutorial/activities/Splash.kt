package com.anubhav87.mvvmtutorial.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.anubhav87.mvvmtutorial.R

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()

        Handler().postDelayed(Runnable {
            // finish the splash activity so it can't be returned to
            this@Splash.finish()
            // create an Intent that will start the second activity
            val mainIntent = Intent(this@Splash, Create_Pincode::class.java)
            this@Splash.startActivity(mainIntent)
        }, 3000) // 3000 milliseconds
    }
}
