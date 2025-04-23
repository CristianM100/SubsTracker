package com.example.substracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView

class SplashScreen : AppCompatActivity() {

    private lateinit var icLogo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Assign the view
        icLogo = findViewById(R.id.ic_logo)

        // Start splash animation
        icLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_in))

        Handler().postDelayed({
            icLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_out))
        }, 500)

        Handler().postDelayed({
            icLogo.visibility = View.GONE
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1000) // Increased delay to let both animations happen sequentially
    }
}
