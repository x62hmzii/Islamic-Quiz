package com.hmzii.islamicquiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hmzii.islamicquiz.fragment.HomeFragment
import com.hmzii.islamicquiz.fragment.NotifyFragment
import com.hmzii.islamicquiz.fragment.SettingFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isGuest = intent.getBooleanExtra("is_guest", false)

        if (!isGuest) {
            val sharedPref = getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
            val isLoggedIn = sharedPref.getBoolean("is_logged_in", false)

            if (!isLoggedIn) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                return
            }
        }

        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    title = "Home"
                }
                R.id.nav_lb -> {
                    replaceFragment(NotifyFragment())
                    title = "Notification"
                }
                R.id.nav_set -> {
                    replaceFragment(SettingFragment())
                    title = "Settings"
                }
            }
            true
        }

        replaceFragment(HomeFragment())
        title = "Home"
        bottomNavigationView.selectedItemId = R.id.nav_home
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmnetContainer, fragment)
            .commit()
    }
}