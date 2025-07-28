package com.hmzii.islamicquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Delay for a few seconds, then proceed to LoginActivity
        Handler(Looper.getMainLooper()).postDelayed({
            // Start LoginActivity
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            // Close SplashActivity
            finish()
        }, 3000) // 3000 milliseconds = 3 seconds
    }
}