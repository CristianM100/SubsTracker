package com.example.substracker

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    private lateinit var icLogo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        icLogo = findViewById(R.id.ic_logo)
        icLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_in))

        val handler = Handler(Looper.getMainLooper())

        handler.postDelayed({
            icLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_out))
        }, 500)

        handler.postDelayed({
            icLogo.visibility = View.GONE
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1000)
    }
}
